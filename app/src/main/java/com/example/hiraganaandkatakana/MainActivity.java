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

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements AuthCallback {

    private MaterialButton przyciskStart;
    private ImageButton przyciskAuth;
    private FirebaseAuth autoryzacja;
    private FirebaseAuth.AuthStateListener authStateListener;

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
        przyciskStart = findViewById(R.id.Poczatek);
        przyciskAuth = findViewById(R.id.imageButtonAuth);

        authStateListener = firebaseAuth -> {
            FirebaseUser uzytkownik = firebaseAuth.getCurrentUser();
            if (uzytkownik != null) {
                przyciskAuth.setImageResource(R.drawable.konto1);
                przyciskAuth.setContentDescription("Wyloguj się");
            } else {
                przyciskAuth.setImageResource(R.drawable.login);
                przyciskAuth.setContentDescription("Zaloguj się");
            }
        };

        przyciskStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PoczatekHiraganaKatakana.class);
            startActivity(intent);
        });

        przyciskAuth.setOnClickListener(v -> obsluzPrzyciskAuth());
    }

    private void obsluzPrzyciskAuth() {
        FirebaseUser aktualnyUzytkownik = autoryzacja.getCurrentUser();
        if (aktualnyUzytkownik != null) {
            autoryzacja.signOut();
            // Po wylogowaniu odświeżamy stan
            sprawdzStanZalogowania();
        } else {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(getSupportFragmentManager(), "logowanie");
        }
    }

    private void sprawdzStanZalogowania() {
        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
        if (uzytkownik != null) {
            przyciskAuth.setImageResource(R.drawable.konto1);
            przyciskAuth.setContentDescription("Wyloguj się");
        } else {
            przyciskAuth.setImageResource(R.drawable.login);
            przyciskAuth.setContentDescription("Zaloguj się");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoryzacja.addAuthStateListener(authStateListener);
        sprawdzStanZalogowania();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoryzacja.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onUserAuthenticated() {
        sprawdzStanZalogowania();
    }
}