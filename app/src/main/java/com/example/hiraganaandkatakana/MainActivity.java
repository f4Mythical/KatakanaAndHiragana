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

        initUI();
    }

    private void initUI() {
        przyciskStart = findViewById(R.id.Poczatek);
        przyciskAuth = findViewById(R.id.imageButtonAuth);
        przyciskUstawienia = findViewById(R.id.imageButtonSettings);
        madeByText = findViewById(R.id.madeByText);
        linkTextView = findViewById(R.id.linkTextView);

        if (przyciskStart == null) {
            Toast.makeText(this, "Błąd UI", Toast.LENGTH_SHORT).show();
            finish();
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
                premiumTracker.aktualizujStatusDlaUzytkownika();
                przyciskAuth.setImageResource(R.drawable.konto1);
                przyciskAuth.setContentDescription(getString(R.string.profil));
            } else {
                czyMaPremium = false;
                premiumTracker.stopListening();
                przyciskAuth.setImageResource(R.drawable.login);
                przyciskAuth.setContentDescription(getString(R.string.zaloguj));
            }
        };

        przyciskStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PoczatekHiraganaKatakana.class);
            intent.putExtra("czyMaPremium", czyMaPremium);
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
            if (SessionTimer.getInstance().getElapsedSeconds() == 0) {
                SessionTimer.getInstance().start();
            }
            UserProfileDialogFragment dialog = UserProfileDialogFragment.newInstance(premiumTracker.getCzyMaPremium());
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
        premiumTracker.startListening();
        premiumTracker.aktualizujStatusDlaUzytkownika();
    }

    @Override
    protected void onPause() {
        super.onPause();
        premiumTracker.stopListening();
        autoryzacja.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onUserAuthenticated() {
        SessionTimer.getInstance().start();
        premiumTracker.startListening();
        premiumTracker.aktualizujStatusDlaUzytkownika();
    }

    @Override
    public void onUserLoggedOut() {
        SessionTimer.getInstance().reset();
        czyMaPremium = false;
        premiumTracker.stopListening();
        finish();
    }

    @Override
    public void onPremiumStatusChanged(boolean statusPremium) {
        czyMaPremium = statusPremium;
    }
}