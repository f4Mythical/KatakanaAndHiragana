package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WidokBasicHiragana extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_widok_basic_hiragana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton ibtnKatakana = findViewById(R.id.imageButtonKatakana);
        ibtnKatakana.setOnClickListener(v -> {
            Intent intent = new Intent(WidokBasicHiragana.this,widok_basic_katakana.class);
            startActivity(intent);
            finish();

        });
        ImageButton ibtnBack = findViewById(R.id.buttonBack);
        ibtnBack.setOnClickListener(v -> {
            finish();
        });
        Button btnZnaki = findViewById(R.id.buttonZnaki);
        btnZnaki.setOnClickListener(v -> {
            Intent intent1 = new Intent(
                    WidokBasicHiragana.this,
                    HiraganaBasicZnaki.class
            );
            startActivity(intent1);
        });
    }
}