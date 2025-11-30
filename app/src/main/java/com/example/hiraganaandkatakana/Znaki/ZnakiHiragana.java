package com.example.hiraganaandkatakana.Znaki;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.R;

public class ZnakiHiragana extends AppCompatActivity {
    private ImageButton Powrot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_znaki_hiragana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Powrot = findViewById(R.id.buttonBackZnakiHiragana);
        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TableLayout tabela = findViewById(R.id.tabelaZnakow);

        String[][] znaki = {
                {"あ\na", "い\ni", "う\nu", "え\ne", "お\no"},
                {"か\nka", "き\nki", "く\nku", "け\nke", "こ\nko"},
                {"さ\nsa", "し\nshi", "す\nsu", "せ\nse", "そ\nso"},
                {"た\nta", "ち\nchi", "つ\ntsu", "て\nte", "と\nto"},
                {"な\nna", "に\nni", "ぬ\nnu", "ね\nne", "の\nno"},
                {"は\nha", "ひ\nhi", "ふ\nfu", "へ\nhe", "ほ\nho"},
                {"ま\nma", "み\nmi", "む\nmu", "め\nme", "も\nmo"},
                {"や\nya", "", "ゆ\nyu", "", "よ\nyo"},
                {"ら\nra", "り\nri", "る\nru", "れ\nre", "ろ\nro"},
                {"わ\nwa", "", "", "", "ん\nn"}
        };

        for (int i = 0; i < znaki.length; i++) {
            TableRow wiersz = new TableRow(this);

            for (int j = 0; j < znaki[i].length; j++) {
                Button przycisk = new Button(this);
                przycisk.setText(znaki[i][j]);
                przycisk.setTextSize(12);
                przycisk.setTextColor(0xFFE65100);
                przycisk.setMinWidth(0);
                przycisk.setMinimumWidth(0);

                if (znaki[i][j].isEmpty()) {
                    przycisk.setVisibility(View.INVISIBLE);
                }

                String tekst = znaki[i][j];
                przycisk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ZnakiHiragana.this, tekst, Toast.LENGTH_SHORT).show();
                    }
                });

                wiersz.addView(przycisk);
            }

            tabela.addView(wiersz);
        }
    }
}