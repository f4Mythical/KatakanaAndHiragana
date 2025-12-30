package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AuthCallback {

    private Button przyciskStart;
    private ImageButton przyciskLogowanie;
    private ImageButton przyciskRejestracja;
    private ImageButton przyciskKonto;

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

        przyciskStart = findViewById(R.id.Poczatek);
        przyciskLogowanie = findViewById(R.id.imageButtonLogin);
        przyciskRejestracja = findViewById(R.id.imageButtonRegister);
        przyciskKonto = findViewById(R.id.imageButtonAccount);

        przyciskKonto.setVisibility(View.GONE);

        przyciskStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PoczatekHiraganaKatakana.class);
            startActivity(intent);
        });

        przyciskLogowanie.setOnClickListener(v -> {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(getSupportFragmentManager(), "logowanie");
        });

        przyciskRejestracja.setOnClickListener(v -> {
            RegisterDialogFragment dialog = new RegisterDialogFragment();
            dialog.show(getSupportFragmentManager(), "rejestracja");
        });

        sprawdzStanZalogowania();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sprawdzStanZalogowania();
    }

    private void sprawdzStanZalogowania() {
        FirebaseAuth autoryzacja = FirebaseAuth.getInstance();
        if (autoryzacja.getCurrentUser() != null) {
            przyciskLogowanie.setVisibility(View.GONE);
            przyciskRejestracja.setVisibility(View.GONE);
            przyciskKonto.setVisibility(View.VISIBLE);
        } else {
            przyciskLogowanie.setVisibility(View.VISIBLE);
            przyciskRejestracja.setVisibility(View.VISIBLE);
            przyciskKonto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserAuthenticated() {
        przyciskLogowanie.setVisibility(View.GONE);
        przyciskRejestracja.setVisibility(View.GONE);
        przyciskKonto.setVisibility(View.VISIBLE);
    }
}