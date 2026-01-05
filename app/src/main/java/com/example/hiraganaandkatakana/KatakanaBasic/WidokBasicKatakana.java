package com.example.hiraganaandkatakana.KatakanaBasic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.HiraganaBasic.WidokBasicHiragana;
import com.example.hiraganaandkatakana.R;

public class WidokBasicKatakana extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_widok_basic_katakana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        ImageButton ibtnHiragana = findViewById(R.id.imageButtonHiragana);
        ibtnHiragana.setOnClickListener(v -> {
            Intent intent = new Intent(WidokBasicKatakana.this, WidokBasicHiragana.class);
            startActivity(intent);
            finish();
        });
        ImageButton ibtnBack = findViewById(R.id.buttonBack);
        ibtnBack.setOnClickListener(v -> {
            finish();
        });
    }
}