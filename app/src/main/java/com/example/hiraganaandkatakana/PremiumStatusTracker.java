package com.example.hiraganaandkatakana;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class PremiumStatusTracker {

    private static PremiumStatusTracker instance;
    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private DocumentReference dokumentUzytkownika;
    private EventListener<DocumentSnapshot> premiumListener;
    private ListenerRegistration premiumListenerRegistration;
    private OnPremiumStatusChangedListener statusListener;
    private boolean czyMaPremium = false;
    private boolean nasluchiwanieAktywne = false;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private PremiumStatusTracker() {
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();
    }

    public static synchronized PremiumStatusTracker getInstance() {
        if (instance == null) {
            instance = new PremiumStatusTracker();
        }
        return instance;
    }

    public void startListening() {
        if (nasluchiwanieAktywne) return;

        FirebaseUser aktualnyUzytkownik = autoryzacja.getCurrentUser();
        if (aktualnyUzytkownik != null) {
            dokumentUzytkownika = bazaDanych.collection("users").document(aktualnyUzytkownik.getUid());

            premiumListener = (snapshot, error) -> {
                if (error != null || !snapshot.exists()) {
                    zaktualizujStatusPremium(false);
                    return;
                }

                Boolean nowyStatusPremium = snapshot.getBoolean("czyMaPremium");
                boolean czyMaPremium = nowyStatusPremium != null && nowyStatusPremium;
                zaktualizujStatusPremium(czyMaPremium);
            };

            premiumListenerRegistration = dokumentUzytkownika.addSnapshotListener(premiumListener);
            nasluchiwanieAktywne = true;
        }
    }

    public void stopListening() {
        if (premiumListenerRegistration != null) {
            premiumListenerRegistration.remove();
            premiumListenerRegistration = null;
            nasluchiwanieAktywne = false;
        }
    }

    private void zaktualizujStatusPremium(boolean nowyStatus) {
        if (czyMaPremium != nowyStatus) {
            czyMaPremium = nowyStatus;
            mainHandler.post(() -> {
                if (statusListener != null) {
                    statusListener.onPremiumStatusChanged(czyMaPremium);
                }
            });
        }
    }

    public boolean getCzyMaPremium() {
        return czyMaPremium;
    }

    public void setOnPremiumStatusChangedListener(OnPremiumStatusChangedListener listener) {
        this.statusListener = listener;
    }

    public void aktualizujStatusDlaUzytkownika() {
        FirebaseUser aktualnyUzytkownik = autoryzacja.getCurrentUser();
        if (aktualnyUzytkownik != null) {
            bazaDanych.collection("users").document(aktualnyUzytkownik.getUid()).get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            Boolean statusPremium = snapshot.getBoolean("czyMaPremium");
                            boolean czyMaPremium = statusPremium != null && statusPremium;
                            zaktualizujStatusPremium(czyMaPremium);
                        } else {
                            zaktualizujStatusPremium(false);
                        }
                    })
                    .addOnFailureListener(e -> {
                        zaktualizujStatusPremium(false);
                    });
        } else {
            zaktualizujStatusPremium(false);
        }
    }

    public interface OnPremiumStatusChangedListener {
        void onPremiumStatusChanged(boolean czyMaPremium);
    }
}