package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.hiraganaandkatakana.Dialog.GeneralSettingsDialogFragment;
import com.example.hiraganaandkatakana.Dialog.LoginDialogFragment;
import com.example.hiraganaandkatakana.Dialog.UserProfileDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements AuthCallback,
        UserProfileDialogFragment.OnProfileDialogListener,
        PremiumStatusTracker.OnPremiumStatusChangedListener {

    private MaterialButton przyciskStart;
    private ImageButton przyciskAuth;
    private ImageButton przyciskUstawienia;
    private TextView madeByText;
    private TextView linkTextView;
    private FirebaseAuth autoryzacja;
    private FirebaseAuth.AuthStateListener authStateListener;
    private long czasZalogowania = 0;
    private FragmentManager fragmentManager;
    private boolean czyMaPremium = false;
    private PremiumStatusTracker premiumTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("ustawienia_aplikacji", MODE_PRIVATE);
        String jezyk = prefs.getString("jezyk_aplikacji", "pl");
        Utils.ustawJezyk(this, jezyk);

        if (prefs.getBoolean("pierwsze_uruchomienie", true)) {
            prefs.edit().putBoolean("pierwsze_uruchomienie", false).apply();
            startActivity(new Intent(this, LanguageSelectionActivity.class));
            finish();
            return;
        }

        String tryb = prefs.getString("tryb_aplikacji", "jasny");
        if (tryb.equals("ciemny")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

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
        przyciskUstawienia = findViewById(R.id.imageButtonSettings);
        madeByText = findViewById(R.id.madeByText);
        linkTextView = findViewById(R.id.linkTextView);

        if (przyciskStart == null) {
            Toast.makeText(this, "Błąd UI: nie znaleziono przycisku", Toast.LENGTH_SHORT).show();
        }

        madeByText.setText(R.string.made_by);
        przyciskStart.setText(R.string.start);

        linkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/f4Mythical"));
            startActivity(intent);
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser uzytkownik = firebaseAuth.getCurrentUser();
            if (uzytkownik != null) {
                czasZalogowania = System.currentTimeMillis();
                premiumTracker.aktualizujStatusDlaUzytkownika();
                przyciskAuth.setImageResource(R.drawable.konto1);
                przyciskAuth.setContentDescription(getString(R.string.profil));
            } else {
                czasZalogowania = 0;
                czyMaPremium = false;
                premiumTracker.stopListening();
                przyciskAuth.setImageResource(R.drawable.login);
                przyciskAuth.setContentDescription(getString(R.string.zaloguj));
            }
        };

        przyciskStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PoczatekHiraganaKatakana.class);
            intent.putExtra("czyMaPremium", czyMaPremium);
            intent.putExtra("czasZalogowania", czasZalogowania);
            startActivity(intent);
        });

        przyciskAuth.setOnClickListener(v -> obsluzPrzyciskAuth());
        przyciskUstawienia.setOnClickListener(v -> {
            GeneralSettingsDialogFragment dialog = new GeneralSettingsDialogFragment();
            dialog.show(fragmentManager, "ustawieniaOgolne");
        });
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
            przyciskAuth.setContentDescription(getString(R.string.profil));
        } else {
            czasZalogowania = 0;
            czyMaPremium = false;
            premiumTracker.stopListening();
            przyciskAuth.setImageResource(R.drawable.login);
            przyciskAuth.setContentDescription(getString(R.string.zaloguj));
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
            przyciskAuth.setContentDescription(getString(R.string.profil));
        } else {
            czasZalogowania = 0;
            czyMaPremium = false;
            premiumTracker.stopListening();
            przyciskAuth.setImageResource(R.drawable.login);
            przyciskAuth.setContentDescription(getString(R.string.zaloguj));
        }
    }

    @Override
    public void onUserLoggedOut() {
        czasZalogowania = 0;
        czyMaPremium = false;
        premiumTracker.stopListening();
        przyciskAuth.setImageResource(R.drawable.login);
        przyciskAuth.setContentDescription(getString(R.string.zaloguj));
        finish();
    }

    @Override
    public void onPremiumStatusChanged(boolean statusPremium) {
        czyMaPremium = statusPremium;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        premiumTracker.stopListening();
    }
}