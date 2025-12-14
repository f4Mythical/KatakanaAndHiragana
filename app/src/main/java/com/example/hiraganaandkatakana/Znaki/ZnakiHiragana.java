package com.example.hiraganaandkatakana.Znaki;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.R;

public class ZnakiHiragana extends AppCompatActivity {
    private ImageButton Powrot;
    private ImageButton buttonSettings;
    private LinearLayout listaZnakow;
    private TableLayout tabelaZnakow;
    private LinearLayout navigationButtons;
    private Button buttonKolejne;
    private Button buttonWczesniejsze;
    private Button filterAll, filterA, filterI, filterU, filterE, filterO;

    private String aktualnyFiltr = "wszystkie";
    private String trybWyswietlania = "lista";
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
        buttonSettings = findViewById(R.id.buttonSettings);
        listaZnakow = findViewById(R.id.listaZnakow);
        tabelaZnakow = findViewById(R.id.tabelaZnakow);
        navigationButtons = findViewById(R.id.navigationButtons);
        buttonKolejne = findViewById(R.id.buttonKolejne);
        buttonWczesniejsze = findViewById(R.id.buttonWczesniejsze);

        filterAll = findViewById(R.id.filterAll);
        filterA = findViewById(R.id.filterA);
        filterI = findViewById(R.id.filterI);
        filterU = findViewById(R.id.filterU);
        filterE = findViewById(R.id.filterE);
        filterO = findViewById(R.id.filterO);

        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazMenuWyswietlania();
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

        filterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "wszystkie";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        filterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "a";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        filterI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "i";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        filterU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "u";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        filterE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "e";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        filterO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktualnyFiltr = "o";
                aktualizujKoloryPrzyciskow();
                wyswietlTabele();
            }
        });

        aktualizujKoloryPrzyciskow();
        wyswietlTabele();
    }

    private void pokazMenuWyswietlania() {
        String[] opcje = {"W linii", "W tabelce", "Z przyciskami Next/Pre"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Wybierz sposób wyświetlania");
        builder.setItems(opcje, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        trybWyswietlania = "lista";
                        break;
                    case 1:
                        trybWyswietlania = "tabela";
                        break;
                    case 2:
                        trybWyswietlania = "z-przyciskami";
                        break;
                }
                wyswietlTabele();
            }
        });
        builder.show();
    }

    private void aktualizujKoloryPrzyciskow() {
        filterAll.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));
        filterA.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));
        filterI.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));
        filterU.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));
        filterE.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));
        filterO.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_light));

        switch (aktualnyFiltr) {
            case "wszystkie":
                filterAll.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
            case "a":
                filterA.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
            case "i":
                filterI.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
            case "u":
                filterU.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
            case "e":
                filterE.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
            case "o":
                filterO.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
        }
    }

    private boolean czyZnakPasuje(String znak) {
        if (aktualnyFiltr.equals("wszystkie") || znak.isEmpty()) {
            return true;
        }

        String[] czesci = znak.split("\n");
        if (czesci.length < 2) {
            return false;
        }

        String romaji = czesci[1].toLowerCase();

        return romaji.endsWith(aktualnyFiltr);
    }

    private void wyswietlTabele() {
        listaZnakow.removeAllViews();
        tabelaZnakow.removeAllViews();

        if (trybWyswietlania.equals("lista")) {
            listaZnakow.setVisibility(View.VISIBLE);
            tabelaZnakow.setVisibility(View.GONE);
            navigationButtons.setVisibility(View.GONE);
            wypelnijListeWszystkie();
        } else if (trybWyswietlania.equals("tabela")) {
            listaZnakow.setVisibility(View.GONE);
            tabelaZnakow.setVisibility(View.VISIBLE);
            navigationButtons.setVisibility(View.GONE);
            wypelnijTabeleWszystkie();
        } else if (trybWyswietlania.equals("z-przyciskami")) {
            listaZnakow.setVisibility(View.GONE);
            tabelaZnakow.setVisibility(View.VISIBLE);
            navigationButtons.setVisibility(View.VISIBLE);
            wypelnijTabeleJedna();
        }
    }

    private void wypelnijListeWszystkie() {
        String[] naglowki = {"Podstawowe", "Dakuten", "Yōon"};

        for (int tablicaIndex = 0; tablicaIndex < wszystkieZnaki.length; tablicaIndex++) {
            TextView naglowek = new TextView(this);
            naglowek.setText(naglowki[tablicaIndex]);
            naglowek.setTextSize(24);
            naglowek.setTextColor(0xFFE65100);
            naglowek.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            naglowek.setTypeface(null, android.graphics.Typeface.BOLD);
            naglowek.setPadding(16, 32, 16, 16);

            LinearLayout.LayoutParams naglowekParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            naglowek.setLayoutParams(naglowekParams);
            listaZnakow.addView(naglowek);

            String[][] znaki = wszystkieZnaki[tablicaIndex];

            for (int i = 0; i < znaki.length; i++) {
                for (int j = 0; j < znaki[i].length; j++) {
                    if (!znaki[i][j].isEmpty() && czyZnakPasuje(znaki[i][j])) {
                        Button przycisk = new Button(this);
                        przycisk.setText(znaki[i][j]);
                        przycisk.setTextSize(20);
                        przycisk.setTextColor(0xFFE65100);
                        przycisk.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
                        przycisk.setPadding(24, 24, 24, 24);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(8, 8, 8, 8);
                        przycisk.setLayoutParams(params);
                        przycisk.setElevation(4);

                        String tekst = znaki[i][j];
                        przycisk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ZnakiHiragana.this, tekst, Toast.LENGTH_SHORT).show();
                            }
                        });

                        listaZnakow.addView(przycisk);
                    }
                }
            }
        }
    }

    private void wypelnijTabeleWszystkie() {
        String[] naglowki = {"Podstawowe", "Dakuten", "Yōon"};

        for (int tablicaIndex = 0; tablicaIndex < wszystkieZnaki.length; tablicaIndex++) {
            TableRow naglowekRow = new TableRow(this);
            TextView naglowek = new TextView(this);
            naglowek.setText(naglowki[tablicaIndex]);
            naglowek.setTextSize(24);
            naglowek.setTextColor(0xFFE65100);
            naglowek.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            naglowek.setTypeface(null, android.graphics.Typeface.BOLD);
            naglowek.setPadding(16, 32, 16, 16);

            TableRow.LayoutParams naglowekParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            naglowek.setLayoutParams(naglowekParams);
            naglowekRow.addView(naglowek);
            tabelaZnakow.addView(naglowekRow);

            String[][] znaki = wszystkieZnaki[tablicaIndex];

            for (int i = 0; i < znaki.length; i++) {
                TableRow wiersz = new TableRow(this);
                boolean czyWierszMaZnaki = false;

                for (int j = 0; j < znaki[i].length; j++) {
                    Button przycisk = new Button(this);
                    przycisk.setText(znaki[i][j]);
                    przycisk.setTextSize(12);
                    przycisk.setTextColor(0xFFE65100);
                    przycisk.setMinWidth(0);
                    przycisk.setMinimumWidth(0);

                    if (znaki[i][j].isEmpty() || !czyZnakPasuje(znaki[i][j])) {
                        przycisk.setVisibility(View.INVISIBLE);
                    } else {
                        czyWierszMaZnaki = true;
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

                if (czyWierszMaZnaki) {
                    tabelaZnakow.addView(wiersz);
                }
            }
        }
    }

    private void wypelnijTabeleJedna() {
        String[][] znaki = wszystkieZnaki[aktualnaTablica];

        for (int i = 0; i < znaki.length; i++) {
            TableRow wiersz = new TableRow(this);
            boolean czyWierszMaZnaki = false;

            for (int j = 0; j < znaki[i].length; j++) {
                Button przycisk = new Button(this);
                przycisk.setText(znaki[i][j]);
                przycisk.setTextSize(12);
                przycisk.setTextColor(0xFFE65100);
                przycisk.setMinWidth(0);
                przycisk.setMinimumWidth(0);

                if (znaki[i][j].isEmpty() || !czyZnakPasuje(znaki[i][j])) {
                    przycisk.setVisibility(View.INVISIBLE);
                } else {
                    czyWierszMaZnaki = true;
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

            if (czyWierszMaZnaki) {
                tabelaZnakow.addView(wiersz);
            }
        }
    }
}