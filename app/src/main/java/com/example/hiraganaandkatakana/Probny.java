package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicCharacters;
import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicSentences;
import com.example.hiraganaandkatakana.HiraganaBasic.HiraganaBasicWords;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumCharacters;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumSentences;
import com.example.hiraganaandkatakana.HiraganaPremium.HiraganaPremiumWords;
import com.example.hiraganaandkatakana.KatakanaBasic.KatakanaBasicSentences;
import com.example.hiraganaandkatakana.KatakanaBasic.KatakanaBasicWords;
import com.example.hiraganaandkatakana.KatakanaPremium.KatakanaPremiumCharacters;
import com.example.hiraganaandkatakana.KatakanaPremium.KatakanaPremiumSentences;
import com.example.hiraganaandkatakana.KatakanaPremium.KatakanaPremiumWords;
import com.example.hiraganaandkatakana.KatakanaBasic.KatakanaBasicCharacters;
public class Probny extends AppCompatActivity {

    private boolean isHiraganaActive = true;

    private TextView tabHiragana, tabKatakana;
    private Button btnZnakiH, btnSlowaH, btnZdaniaH;
    private Button btnZnakiK, btnSlowaK, btnZdaniaK;
    private ImageButton back;
    private ConstraintLayout mainLayout;
    private boolean isPremium = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test_nic_robic_nie_bede_pozniej_zmienie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        isPremium = getIntent().getBooleanExtra("czyMaPremium", false);

        initViews();
        setupTabs();
        showHiragana();
        back.setOnClickListener(v -> {finish();});
        btnZnakiH.setOnClickListener(v -> {
            if (isPremium) {
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaPremiumCharacters.class
                );
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaBasicCharacters.class
                );
                startActivity(intent);
            }
        });
        btnSlowaH.setOnClickListener(v -> {
            if(isPremium){
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaPremiumWords.class
                );
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaBasicWords.class
                );
                startActivity(intent);
            }
        });
        btnZdaniaH.setOnClickListener(v -> {
            if(isPremium){
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaPremiumSentences.class
                );
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(
                        Probny.this,
                        HiraganaBasicSentences.class
                );
                startActivity(intent);
            }
        });
        btnZnakiK.setOnClickListener(v -> {
            if (isPremium) {
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaPremiumCharacters.class
                );
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaBasicCharacters.class
                );
                startActivity(intent);
            }
        });
        btnSlowaK.setOnClickListener(v -> {
            if(isPremium){
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaPremiumWords.class
                );
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaBasicWords.class
                );
                startActivity(intent);
            }
        });
        btnZdaniaK.setOnClickListener(v -> {
            if(isPremium){
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaPremiumSentences.class
                );
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(
                        Probny.this,
                        KatakanaBasicSentences.class
                );
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        tabHiragana = findViewById(R.id.tabHiragana);
        tabKatakana = findViewById(R.id.tabKatakana);

        btnZnakiH = findViewById(R.id.buttonZnakiHiragana);
        btnSlowaH = findViewById(R.id.buttonSlowaHiragana);
        btnZdaniaH = findViewById(R.id.buttonZdaniaHiragana);

        btnZnakiK = findViewById(R.id.buttonZnakiKatakana);
        btnSlowaK = findViewById(R.id.buttonSlowaKatakana);
        btnZdaniaK = findViewById(R.id.buttonZdaniaKatakana);

        mainLayout = findViewById(R.id.main);
        back = findViewById(R.id.buttonBack);
    }

    private void setupTabs() {
        tabHiragana.setOnClickListener(v -> {
            if (!isHiraganaActive) {
                isHiraganaActive = true;
                showHiragana();
            }
        });

        tabKatakana.setOnClickListener(v -> {
            if (isHiraganaActive) {
                isHiraganaActive = false;
                showKatakana();
            }
        });
    }

    private void showHiragana() {
        btnZnakiH.setVisibility(View.VISIBLE);
        btnSlowaH.setVisibility(View.VISIBLE);
        btnZdaniaH.setVisibility(View.VISIBLE);

        btnZnakiK.setVisibility(View.GONE);
        btnSlowaK.setVisibility(View.GONE);
        btnZdaniaK.setVisibility(View.GONE);



        tabHiragana.setBackgroundColor(getColor(R.color.tlo_powierzchnia));
        tabKatakana.setBackgroundColor(getColor(R.color.tlo_zakladki));
    }

    private void showKatakana() {
        btnZnakiH.setVisibility(View.GONE);
        btnSlowaH.setVisibility(View.GONE);
        btnZdaniaH.setVisibility(View.GONE);

        btnZnakiK.setVisibility(View.VISIBLE);
        btnSlowaK.setVisibility(View.VISIBLE);
        btnZdaniaK.setVisibility(View.VISIBLE);



        tabKatakana.setBackgroundColor(getColor(R.color.tlo_powierzchnia));
        tabHiragana.setBackgroundColor(getColor(R.color.tlo_zakladki));
    }
}