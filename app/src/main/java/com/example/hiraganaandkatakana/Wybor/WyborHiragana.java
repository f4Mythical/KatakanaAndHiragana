package com.example.hiraganaandkatakana.Wybor;

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

import com.example.hiraganaandkatakana.Poziom.PoziomHiragana;
import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.WyborPoziomu.WyborPoziomuHiragana;
import com.example.hiraganaandkatakana.Znaki.ZnakiHiragana;

public class WyborHiragana extends AppCompatActivity {
    private ImageButton Powrot;
    private Button buttonZnaki;
    private Button buttonZadanie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wybor_hiragana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Powrot = findViewById(R.id.buttonBackHiragana);
        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonZnaki = findViewById(R.id.buttonZnaki);
        buttonZnaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WyborHiragana.this, ZnakiHiragana.class);
                startActivity(intent);
            }
        });
        buttonZadanie = findViewById(R.id.buttonZadania);
        buttonZadanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent = new Intent(WyborHiragana.this,WyborPoziomuHiragana.class);
                  startActivity(intent);
            }


        });
    }
}