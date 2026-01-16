package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.hiraganaandkatakana.Dialog.LoginDialogFragment;
import com.example.hiraganaandkatakana.Dialog.UserProfileDialogFragment;
import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicCharacters;
import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicSentences;
import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicWords;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumCharacters;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumSentences;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumWords;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Probny extends AppCompatActivity implements AuthCallback,
        UserProfileDialogFragment.OnProfileDialogListener,
        PremiumStatusTracker.OnPremiumStatusChangedListener {

    private boolean isHiraganaActive = true;
    private boolean isPremium = false;

    private TextView tabHiragana, tabKatakana;
    private ImageButton buttonBack, imageButtonAuth;
    private LinearLayout buttonCharacters, buttonWords, buttonSentences;
    private TextView iconCharacters, iconWords, iconSentences;

    private FirebaseAuth autoryzacja;
    private FirebaseAuth.AuthStateListener authStateListener;
    private PremiumStatusTracker premiumTracker;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_nic_robic_nie_bede_pozniej_zmienie);

        initFirebase();
        initViews();
        setupTabs();
        setupButtons();
        setupAuth();
        updateUIForCurrentTab();
    }

    private void initFirebase() {
        autoryzacja = FirebaseAuth.getInstance();
        premiumTracker = PremiumStatusTracker.getInstance();
        premiumTracker.setOnPremiumStatusChangedListener(this);
        fragmentManager = getSupportFragmentManager();
        isPremium = getIntent().getBooleanExtra("czyMaPremium", false);
    }

    private void initViews() {
        tabHiragana = findViewById(R.id.tabHiragana);
        tabKatakana = findViewById(R.id.tabKatakana);
        buttonBack = findViewById(R.id.buttonBack);
        imageButtonAuth = findViewById(R.id.imageButtonAuth);
        buttonCharacters = findViewById(R.id.buttonCharacters);
        buttonWords = findViewById(R.id.buttonWords);
        buttonSentences = findViewById(R.id.buttonSentences);
        iconCharacters = findViewById(R.id.iconCharacters);
        iconWords = findViewById(R.id.iconWords);
        iconSentences = findViewById(R.id.iconSentences);
    }

    private void setupTabs() {
        tabHiragana.setOnClickListener(v -> {
            if (!isHiraganaActive) {
                isHiraganaActive = true;
                updateUIForCurrentTab();
            }
        });

        tabKatakana.setOnClickListener(v -> {
            if (isHiraganaActive) {
                isHiraganaActive = false;
                updateUIForCurrentTab();
            }
        });

        buttonBack.setOnClickListener(v -> finish());
    }

    private void setupButtons() {
        buttonCharacters.setOnClickListener(v -> openCharactersActivity());
        buttonWords.setOnClickListener(v -> openWordsActivity());
        buttonSentences.setOnClickListener(v -> openSentencesActivity());
    }

    private void setupAuth() {
        authStateListener = firebaseAuth -> {
            FirebaseUser uzytkownik = firebaseAuth.getCurrentUser();
            if (uzytkownik != null) {
                premiumTracker.startListening();
                premiumTracker.aktualizujStatusDlaUzytkownika();
                imageButtonAuth.setImageResource(R.drawable.konto1);
                imageButtonAuth.setContentDescription("Mój profil");
            } else {
                isPremium = false;
                premiumTracker.stopListening();
                imageButtonAuth.setImageResource(R.drawable.login);
                imageButtonAuth.setContentDescription("Zaloguj się");
            }
        };

        imageButtonAuth.setOnClickListener(v -> obsluzPrzyciskAuth());
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

    private void openCharactersActivity() {
        boolean premium = premiumTracker.getCzyMaPremium();
        Intent intent = new Intent(this, premium ? HiraganaDialog.class : HiraganaBasicCharacters.class);
        intent.putExtra("czyMaPremium", premium);
        startActivity(intent);
    }

    private void openWordsActivity() {
        boolean premium = premiumTracker.getCzyMaPremium();
        Intent intent = new Intent(this, premium ? HiraganaPremiumWords.class : HiraganaBasicWords.class);
        intent.putExtra("czyMaPremium", premium);
        startActivity(intent);
    }

    private void openSentencesActivity() {
        boolean premium = premiumTracker.getCzyMaPremium();
        Intent intent = new Intent(this, premium ? HiraganaPremiumSentences.class : HiraganaBasicSentences.class);
        intent.putExtra("czyMaPremium", premium);
        startActivity(intent);
    }

    private void updateUIForCurrentTab() {
        if (isHiraganaActive) {
            tabHiragana.setBackgroundColor(getColor(R.color.tlo_powierzchnia));
            tabKatakana.setBackgroundColor(getColor(R.color.tlo_zakladki));
            iconCharacters.setText("あ");
            iconWords.setText("本");
            iconSentences.setText("文");
        } else {
            tabKatakana.setBackgroundColor(getColor(R.color.tlo_powierzchnia));
            tabHiragana.setBackgroundColor(getColor(R.color.tlo_zakladki));
            iconCharacters.setText("ア");
            iconWords.setText("本");
            iconSentences.setText("文");
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
    protected void onDestroy() {
        super.onDestroy();
        premiumTracker.stopListening();
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
        finish();
    }

    @Override
    public void onPremiumStatusChanged(boolean czyMaPremium) {
        this.isPremium = czyMaPremium;
    }
}