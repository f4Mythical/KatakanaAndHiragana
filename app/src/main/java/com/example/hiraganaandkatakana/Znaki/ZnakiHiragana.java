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
    private Button buttonKolejne;
    private Button buttonWczesniejsze;
    private TableLayout tabela;

    private int aktualnaTablica = 0;

    private String[][][] wszystkieZnaki = {
            {
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
            },
            {
                    {"が\nga","ぎ\ngi","ぐ\ngu","げ\nge","ご\ngo"},
                    {"ざ\nza","じ\nji","ず\nzu","ぜ\nze","ぞ\nzo"},
                    {"だ\nda","ぢ\ndji","づ\ndzu","で\nde","ど\ndo"},
                    {"ば\nba","び\nbi","ぶ\nbu","べ\nbe","ぼ\nbo"},
                    {"ぱ\npa","ぴ\npi","ぷ\npu","ぺ\npe","ぽ\npo"}
            },
            {
                    {"きゃ\nkya", "きゅ\nkyu", "きょ\nkyo"},
                    {"ぎゃ\ngya", "ぎゅ\ngyu", "ぎょ\ngyo"},
                    {"しゃ\nsha", "しゅ\nshu", "しょ\nsho"},
                    {"じゃ\nja",  "じゅ\nju",  "じょ\njo"},
                    {"ちゃ\ncha", "ちゅ\nchu", "ちょ\ncho"},
                    {"ぢゃ\ndja", "ぢゅ\ndju", "ぢょ\ndjo"},
                    {"にゃ\nnya", "にゅ\nnyu", "にょ\nnyo"},
                    {"ひゃ\nhya", "ひゅ\nhyu", "ひょ\nhyo"},
                    {"びゃ\nbya", "びゅ\nbyu", "びょ\nbyo"},
                    {"ぴゃ\npya", "ぴゅ\npyu", "ぴょ\npyo"},
                    {"みゃ\nmya", "みゅ\nmyu", "みょ\nmyo"},
                    {"りゃ\nrya", "りゅ\nryu", "りょ\nryo"}
            }
    };

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
        buttonKolejne = findViewById(R.id.buttonKolejne);
        buttonWczesniejsze = findViewById(R.id.buttonWczesniejsze);
        tabela = findViewById(R.id.tabelaZnakow);

        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonKolejne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnaTablica++;
                if (aktualnaTablica >= wszystkieZnaki.length) {
                    aktualnaTablica = 0;
                }
                wyswietlTabele();
            }
        });

        buttonWczesniejsze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnaTablica--;
                if (aktualnaTablica < 0) {
                    aktualnaTablica = wszystkieZnaki.length - 1;
                }
                wyswietlTabele();
            }
        });

        wyswietlTabele();
    }

    private void wyswietlTabele() {
        tabela.removeAllViews();

        String[][] znaki = wszystkieZnaki[aktualnaTablica];

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