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

public class WyborHiraganaAKatakana extends AppCompatActivity {
private Button buttonKatakana;
private Button buttonHiragana;
private ImageButton Powrot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wybor_hiragana_akatakana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonHiragana = findViewById(R.id.buttonHiragana);
        buttonKatakana = findViewById(R.id.buttonKatakana);
        buttonHiragana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WyborHiraganaAKatakana.this, WyborHiragana.class);
                startActivity(intent1);
            }

        });
        buttonKatakana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WyborHiraganaAKatakana.this, WyborKatakana.class);
                startActivity(intent);
            }
        });
        Powrot = findViewById(R.id.buttonBack);
        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}