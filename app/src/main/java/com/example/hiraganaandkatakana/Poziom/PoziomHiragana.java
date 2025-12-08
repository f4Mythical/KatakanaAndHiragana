package com.example.hiraganaandkatakana.Poziom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.Litery.LiteryHiragana;
import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.Slowa.SlowaHiragana;
import com.example.hiraganaandkatakana.WyborPoziomu.WyborPoziomuHiragana;

public class PoziomHiragana extends AppCompatActivity {
private Button Slowa;
private Button Zdania;
private Button Litery;
private ImageButton Powrot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_poziom_hiragana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Powrot = findViewById(R.id.buttonBack);
        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Slowa = findViewById(R.id.buttonSlowa);
        Slowa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoziomHiragana.this, SlowaHiragana.class);
                startActivity(intent);
            }
        });
        Litery = findViewById(R.id.buttonLitery);
        Litery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoziomHiragana.this, WyborPoziomuHiragana.class);
                startActivity(intent);
            }
        });
    }
}