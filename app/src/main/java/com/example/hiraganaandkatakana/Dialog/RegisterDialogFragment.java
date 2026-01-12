package com.example.hiraganaandkatakana.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.AuthCallback;
import com.example.hiraganaandkatakana.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterDialogFragment extends DialogFragment {

    private static final String TAG = "RegisterDialogFragment";
    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private EditText poleEmail, poleHaslo;
    private TextView tekstBladEmail, tekstPodpowiedzHaslo;
    private Button przyciskRejestracja;
    private TextView tekstLogowanie;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "===== onCreateDialog() START =====");
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_register, null);

        poleEmail = view.findViewById(R.id.poleEmail);
        poleHaslo = view.findViewById(R.id.poleHaslo);
        przyciskRejestracja = view.findViewById(R.id.przyciskRejestracja);
        tekstLogowanie = view.findViewById(R.id.tekstLogowanie);
        tekstBladEmail = view.findViewById(R.id.tekstBladEmail);
        tekstPodpowiedzHaslo = view.findViewById(R.id.tekstPodpowiedzHaslo);

        Log.d(TAG, "Widoki zainicjalizowane: email=" + (poleEmail != null) + ", haslo=" + (poleHaslo != null));

        ustawWeryfikacjeEmail();
        ustawWeryfikacjeHasla();

        przyciskRejestracja.setOnClickListener(v -> {
            String emailUzytkownika = poleEmail.getText().toString().trim();
            String hasloUzytkownika = poleHaslo.getText().toString().trim();

            Log.d(TAG, "Kliknięto przycisk rejestracji. Email: " + emailUzytkownika);

            if (emailUzytkownika.isEmpty() || hasloUzytkownika.isEmpty()) {
                Log.w(TAG, "Rejestracja odrzucona: pusty email lub hasło");
                return;
            }

            Log.d(TAG, "Rozpoczynam createUserWithEmailAndPassword");
            autoryzacja.createUserWithEmailAndPassword(emailUzytkownika, hasloUzytkownika)
                    .addOnSuccessListener(authResult -> {
                        Log.d(TAG, "createUserWithEmailAndPassword: SUKCES");
                        String identyfikatorUzytkownika = autoryzacja.getCurrentUser().getUid();
                        Log.d(TAG, "UID użytkownika: " + identyfikatorUzytkownika);

                        SharedPreferences prefs = requireActivity().getSharedPreferences("ustawienia_aplikacji", requireContext().MODE_PRIVATE);
                        String jezyk = prefs.getString("jezyk_aplikacji", "en");
                        Log.d(TAG, "Aktualny język z SharedPrefs: " + jezyk);

                        Map<String, Object> daneUzytkownika = new HashMap<>();
                        daneUzytkownika.put("email", emailUzytkownika);
                        daneUzytkownika.put("czyMaPremium", false);
                        daneUzytkownika.put("jezyk", jezyk);

                        Log.d(TAG, "Przygotowano dane do zapisu: " + daneUzytkownika.toString());
                        Log.d(TAG, "Zapisuję do Firestore: collection=users, document=" + identyfikatorUzytkownika);

                        bazaDanych.collection("users").document(identyfikatorUzytkownika).set(daneUzytkownika)
                                .addOnSuccessListener(unused -> {
                                    Log.d(TAG, "Firestore zapis: SUKCES");
                                    if (getActivity() instanceof AuthCallback) {
                                        ((AuthCallback) getActivity()).onUserAuthenticated();
                                    }
                                    dismiss();
                                    Log.d(TAG, "Dialog zamknięty po sukcesie");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Firestore zapis: BŁĄD", e);
                                    tekstBladEmail.setVisibility(View.VISIBLE);
                                });
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "createUserWithEmailAndPassword: BŁĄD", e);
                        tekstBladEmail.setVisibility(View.VISIBLE);
                    });
        });

        tekstLogowanie.setOnClickListener(v -> {
            Log.d(TAG, "Kliknięto 'Masz już konto?'");
            dismiss();
            new LoginDialogFragment().show(getParentFragmentManager(), "loginDialog");
        });

        builder.setView(view);
        Dialog dialog = builder.create();
        Log.d(TAG, "===== onCreateDialog() KONIEC =====");
        return dialog;
    }

    private void ustawWeryfikacjeEmail() {
        Log.d(TAG, "Ustawiam weryfikację email");
        poleEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v(TAG, "beforeTextChanged: tekst=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v(TAG, "onTextChanged: tekst=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
            }
            @Override
            public void afterTextChanged(Editable s) {
                String emailUzytkownika = s.toString().trim();
                Log.d(TAG, "afterTextChanged: email=" + emailUzytkownika + ", isEmpty=" + emailUzytkownika.isEmpty());

                if (emailUzytkownika.isEmpty()) {
                    Log.d(TAG, "Email pusty, ukrywam błąd");
                    tekstBladEmail.setVisibility(View.GONE);
                    return;
                }
                Log.d(TAG, "Sprawdzam czy email istnieje: " + emailUzytkownika);
                sprawdzCzyEmailIstnieje(emailUzytkownika);
            }
        });
    }

    private void sprawdzCzyEmailIstnieje(String emailUzytkownika) {
        Log.d(TAG, "fetchSignInMethodsForEmail dla: " + emailUzytkownika);
        autoryzacja.fetchSignInMethodsForEmail(emailUzytkownika)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean czyIstnieje = task.getResult().getSignInMethods() != null
                                && !task.getResult().getSignInMethods().isEmpty();
                        Log.d(TAG, "fetchSignInMethodsForEmail zakończone. Email istnieje: " + czyIstnieje);
                        tekstBladEmail.setVisibility(czyIstnieje ? View.VISIBLE : View.GONE);
                    } else {
                        Log.w(TAG, "fetchSignInMethodsForEmail nieudane lub result=null");
                        tekstBladEmail.setVisibility(View.GONE);
                    }
                });
    }

    private void ustawWeryfikacjeHasla() {
        Log.d(TAG, "Ustawiam weryfikację hasła");
        poleHaslo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String haslo = s.toString();
                Log.d(TAG, "Weryfikacja hasła: długość=" + haslo.length());
                tekstPodpowiedzHaslo.setText(sprawdzPoprawnoscHasla(haslo));
            }
        });
    }

    private String sprawdzPoprawnoscHasla(String hasloUzytkownika) {
        StringBuilder podpowiedz = new StringBuilder();
        boolean ma6Znakow = hasloUzytkownika.length() >= 6;
        boolean maCyfre = false;
        boolean maWielkaLitere = false;
        boolean maMalaLitere = false;

        String cyfry = "1234567890";
        for (char znak : hasloUzytkownika.toCharArray()) {
            if (cyfry.contains(String.valueOf(znak))) maCyfre = true;
            if (Character.isUpperCase(znak)) maWielkaLitere = true;
            if (Character.isLowerCase(znak)) maMalaLitere = true;
        }

        podpowiedz.append(ma6Znakow ? "✅" : "❌").append(getString(R.string.znaki6));
        podpowiedz.append(maCyfre ? "✅" : "❌").append(getString(R.string.cyfra));
        podpowiedz.append(maWielkaLitere ? "✅" : "❌").append(getString(R.string.wielka));
        podpowiedz.append(maMalaLitere ? "✅" : "❌").append(getString(R.string.mala));

        Log.d(TAG, "Weryfikacja hasła: 6znaków=" + ma6Znakow + ", cyfra=" + maCyfre + ", wielka=" + maWielkaLitere + ", mala=" + maMalaLitere);

        return podpowiedz.toString();
    }
}