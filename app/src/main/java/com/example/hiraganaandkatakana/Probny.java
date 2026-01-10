package com.example.hiraganaandkatakana;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Probny extends AppCompatActivity {

    private boolean isHiraganaActive = true;

    private TextView tabHiragana, tabKatakana;
    private Button btnZnakiH, btnSlowaH, btnZdaniaH;
    private Button btnZnakiK, btnSlowaK, btnZdaniaK;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_nic_robic_nie_bede_pozniej_zmienie);

        initViews();
        setupTabs();
        showHiragana();
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