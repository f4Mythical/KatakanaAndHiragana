package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements AuthCallback,
        UserProfileDialogFragment.OnProfileDialogListener,
        PremiumStatusTracker.OnPremiumStatusChangedListener {

    private MaterialButton przyciskStart;
    private ImageButton przyciskAuth;
    private FirebaseAuth autoryzacja;
    private FirebaseAuth.AuthStateListener authStateListener;
    private long czasZalogowania = 0;
    private FragmentManager fragmentManager;
    private boolean czyMaPremium = false;
    private PremiumStatusTracker premiumTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        autoryzacja = FirebaseAuth.getInstance();
        premiumTracker = PremiumStatusTracker.getInstance();
        premiumTracker.setOnPremiumStatusChangedListener(this);
        fragmentManager = getSupportFragmentManager();
        przyciskStart = findViewById(R.id.Poczatek);
        przyciskAuth = findViewById(R.id.imageButtonAuth);

        authStateListener = firebaseAuth -> {
            FirebaseUser uzytkownik = firebaseAuth.getCurrentUser();
            if (uzytkownik != null) {
                czasZalogowania = System.currentTimeMillis();
                premiumTracker.aktualizujStatusDlaUzytkownika();
                przyciskAuth.setImageResource(R.drawable.konto1);
                przyciskAuth.setContentDescription("Mój profil");
            } else {
                czasZalogowania = 0;
                czyMaPremium = false;
                premiumTracker.stopListening();
                przyciskAuth.setImageResource(R.drawable.login);
                przyciskAuth.setContentDescription("Zaloguj się");
            }
        };

        przyciskStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PoczatekHiraganaKatakana.class);
            intent.putExtra("czyMaPremium", czyMaPremium);
            intent.putExtra("czasZalogowania", czasZalogowania);
            startActivity(intent);
        });

        przyciskAuth.setOnClickListener(v -> obsluzPrzyciskAuth());
    }

    private void obsluzPrzyciskAuth() {
        FirebaseUser aktualnyUzytkownik = autoryzacja.getCurrentUser();
        if (aktualnyUzytkownik != null) {
            UserProfileDialogFragment dialog = UserProfileDialogFragment.newInstance(czasZalogowania, premiumTracker.getCzyMaPremium());
            dialog.show(fragmentManager, "profilUzytkownika");
        } else {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(fragmentManager, "logowanie");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoryzacja.addAuthStateListener(authStateListener);
        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
        if (uzytkownik != null) {
            czasZalogowania = System.currentTimeMillis();
            premiumTracker.startListening();
            premiumTracker.aktualizujStatusDlaUzytkownika();
            przyciskAuth.setImageResource(R.drawable.konto1);
            przyciskAuth.setContentDescription("Mój profil");
        } else {
            czasZalogowania = 0;
            czyMaPremium = false;
            premiumTracker.stopListening();
            przyciskAuth.setImageResource(R.drawable.login);
            przyciskAuth.setContentDescription("Zaloguj się");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        premiumTracker.stopListening();
        autoryzacja.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onUserAuthenticated() {
        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
        if (uzytkownik != null) {
            czasZalogowania = System.currentTimeMillis();
            premiumTracker.startListening();
            premiumTracker.aktualizujStatusDlaUzytkownika();
            przyciskAuth.setImageResource(R.drawable.konto1);
            przyciskAuth.setContentDescription("Mój profil");
        } else {
            czasZalogowania = 0;
            czyMaPremium = false;
            premiumTracker.stopListening();
            przyciskAuth.setImageResource(R.drawable.login);
            przyciskAuth.setContentDescription("Zaloguj się");
        }
    }

    @Override
    public void onUserLoggedOut() {
        czasZalogowania = 0;
        czyMaPremium = false;
        premiumTracker.stopListening();
        przyciskAuth.setImageResource(R.drawable.login);
        przyciskAuth.setContentDescription("Zaloguj się");
    }

    @Override
    public void onPremiumStatusChanged(boolean czyMaPremium) {
        this.czyMaPremium = czyMaPremium;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        premiumTracker.stopListening();
    }
}