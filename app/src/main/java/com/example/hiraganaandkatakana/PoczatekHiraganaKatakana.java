package com.example.hiraganaandkatakana;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PoczatekHiraganaKatakana extends AppCompatActivity {

    GridLayout container;
    boolean czyHiragana = true;
    String aktualnyTyp = "PODSTAWOWE";

    String[][] hiraPodstawowe = {
            {"あ","a"}, {"い","i"}, {"う","u"}, {"え","e"}, {"お","o"},
            {"か","ka"}, {"き","ki"}, {"く","ku"}, {"け","ke"}, {"こ","ko"},
            {"さ","sa"}, {"し","shi"}, {"す","su"}, {"せ","se"}, {"そ","so"},
            {"た","ta"}, {"ち","chi"}, {"つ","tsu"}, {"て","te"}, {"と","to"},
            {"な","na"}, {"に","ni"}, {"ぬ","nu"}, {"ね","ne"}, {"の","no"},
            {"は","ha"}, {"ひ","hi"}, {"ふ","fu"}, {"へ","he"}, {"ほ","ho"},
            {"ま","ma"}, {"み","mi"}, {"む","mu"}, {"め","me"}, {"も","mo"},
            {"や","ya"}, {"   ","  "}, {"ゆ","yu"}, {"   ","  "}, {"よ","yo"},
            {"ら","ra"}, {"り","ri"}, {"る","ru"}, {"れ","re"}, {"ろ","ro"},
            {"わ","wa"}, {"   "," "}, {"   "," "}, {"   "," "}, {"を","wo"},
            {"   ","  "}, {"   ","  "}, {"   ","  "}, {"   ","  "}, {"ん","n"}
    };

    String[][] hiraDakuten = {
            {"が","ga"}, {"ぎ","gi"}, {"ぐ","gu"}, {"げ","ge"}, {"ご","go"},
            {"ざ","za"}, {"じ","ji"}, {"ず","zu"}, {"ぜ","ze"}, {"ぞ","zo"},
            {"だ","da"}, {"ぢ","ji"}, {"づ","zu"}, {"で","de"}, {"ど","do"},
            {"ば","ba"}, {"び","bi"}, {"ぶ","bu"}, {"べ","be"}, {"ぼ","bo"},
            {"ぱ","pa"}, {"ぴ","pi"}, {"ぷ","pu"}, {"ぺ","pe"}, {"ぽ","po"}
    };

    String[][] hiraKombinowane = {
            {"きゃ","kya"}, {"きゅ","kyu"}, {"きょ","kyo"},
            {"ぎゃ","gya"}, {"ぎゅ","gyu"}, {"ぎょ","gyo"},
            {"しゃ","sha"}, {"しゅ","shu"}, {"しょ","sho"},
            {"じゃ","ja"}, {"じゅ","ju"}, {"じょ","jo"},
            {"ちゃ","cha"}, {"ちゅ","chu"}, {"ちょ","cho"},
            {"ぢゃ","ja"}, {"ぢゅ","ju"}, {"ぢょ","jo"},
            {"にゃ","nya"}, {"にゅ","nyu"}, {"にょ","nyo"},
            {"ひゃ","hya"}, {"ひゅ","hyu"}, {"ひょ","hyo"},
            {"びゃ","bya"}, {"びゅ","byu"}, {"びょ","byo"},
            {"ぴゃ","pya"}, {"ぴゅ","pyu"}, {"ぴょ","pyo"},
            {"みゃ","mya"}, {"みゅ","myu"}, {"みょ","myo"},
            {"りゃ","rya"}, {"りゅ","ryu"}, {"りょ","ryo"}
    };

    String[][] kataPodstawowe = {
            {"ア","a"}, {"イ","i"}, {"ウ","u"}, {"エ","e"}, {"オ","o"},
            {"カ","ka"}, {"キ","ki"}, {"ク","ku"}, {"ケ","ke"}, {"コ","ko"},
            {"サ","sa"}, {"シ","shi"}, {"ス","su"}, {"セ","se"}, {"ソ","so"},
            {"タ","ta"}, {"チ","chi"}, {"ツ","tsu"}, {"テ","te"}, {"ト","to"},
            {"ナ","na"}, {"ニ","ni"}, {"ヌ","nu"}, {"ネ","ne"}, {"ノ","no"},
            {"ハ","ha"}, {"ヒ","hi"}, {"フ","fu"}, {"ヘ","he"}, {"ホ","ho"},
            {"マ","ma"}, {"ミ","mi"}, {"ム","mu"}, {"メ","me"}, {"モ","mo"},
            {"ヤ","ya"}, {"  ","  "}, {"ユ","yu"}, {"  ","  "}, {"ヨ","yo"},
            {"ラ","ra"}, {"リ","ri"}, {"ル","ru"}, {"レ","re"}, {"ロ","ro"},
            {"ワ","wa"}, {"  ","  "}, {"  ","  "}, {"  ","  "}, {"ヲ","wo"},
            {"  ","  "}, {"  ","  "}, {"  ","  "}, {"  ","  "}, {"ン","n"}
    };

    String[][] kataDakuten = {
            {"ガ","ga"}, {"ギ","gi"}, {"グ","gu"}, {"ゲ","ge"}, {"ゴ","go"},
            {"ザ","za"}, {"ジ","ji"}, {"ズ","zu"}, {"ゼ","ze"}, {"ゾ","zo"},
            {"ダ","da"}, {"ヂ","ji"}, {"ヅ","zu"}, {"デ","de"}, {"ド","do"},
            {"バ","ba"}, {"ビ","bi"}, {"ブ","bu"}, {"ベ","be"}, {"ボ","bo"},
            {"パ","pa"}, {"ピ","pi"}, {"プ","pu"}, {"ペ","pe"}, {"ポ","po"}
    };

    String[][] kataKombinowane = {
            {"キャ","kya"}, {"キュ","kyu"}, {"キョ","kyo"},
            {"ギャ","gya"}, {"ギュ","gyu"}, {"ギョ","gyo"},
            {"シャ","sha"}, {"シュ","shu"}, {"ショ","sho"},
            {"ジャ","ja"}, {"ジュ","ju"}, {"ジョ","jo"},
            {"チャ","cha"}, {"チュ","chu"}, {"チョ","cho"},
            {"ヂャ","ja"}, {"ヂュ","ju"}, {"ヂョ","jo"},
            {"ニャ","nya"}, {"ニュ","nyu"}, {"ニョ","nyo"},
            {"ヒャ","hya"}, {"ヒュ","hyu"}, {"ヒョ","hyo"},
            {"ビャ","bya"}, {"ビュ","byu"}, {"ビョ","byo"},
            {"ピャ","pya"}, {"ピュ","pyu"}, {"ピョ","pyo"},
            {"ミャ","mya"}, {"ミュ","myu"}, {"ミョ","myo"},
            {"リャ","rya"}, {"リュ","ryu"}, {"リョ","ryo"}
    };

    String[][] aktualnyZestaw;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable[] pendingTasks = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poczatek_hiragana_katakana);

        container = findViewById(R.id.dynamicContainer);

        ImageButton back = findViewById(R.id.buttonBack);
        ImageButton login = findViewById(R.id.imageButtonLogin);

        TextView tabHira = findViewById(R.id.tabHiragana);
        TextView tabKata = findViewById(R.id.tabKatakana);

        Button btnPodstawowe = findViewById(R.id.buttonPodstawowe);
        Button btnDakuten = findViewById(R.id.buttonDakuten);
        Button btnKombinowane = findViewById(R.id.buttonKombinowane);

        back.setOnClickListener(v -> finish());

        login.setOnClickListener(v -> {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(getSupportFragmentManager(), "login");
        });

        tabHira.setOnClickListener(v -> {
            if (!czyHiragana) {
                czyHiragana = true;
                updateTabStyles(tabHira, tabKata);
                ustawZestaw();
            }
        });

        tabKata.setOnClickListener(v -> {
            if (czyHiragana) {
                czyHiragana = false;
                updateTabStyles(tabHira, tabKata);
                ustawZestaw();
            }
        });

        btnPodstawowe.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("PODSTAWOWE")) {
                aktualnyTyp = "PODSTAWOWE";
                updateButtonStyles(btnPodstawowe, btnDakuten, btnKombinowane);
                ustawZestaw();
            }
        });

        btnDakuten.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("DAKUTEN")) {
                aktualnyTyp = "DAKUTEN";
                updateButtonStyles(btnDakuten, btnPodstawowe, btnKombinowane);
                ustawZestaw();
            }
        });

        btnKombinowane.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("KOMBINOWANE")) {
                aktualnyTyp = "KOMBINOWANE";
                updateButtonStyles(btnKombinowane, btnPodstawowe, btnDakuten);
                ustawZestaw();
            }
        });

        aktualnyZestaw = hiraPodstawowe;
        wyswietlZestaw();
    }

    private void updateTabStyles(TextView tabHira, TextView tabKata) {
        if (czyHiragana) {
            tabHira.setBackgroundColor(0xFFFFFFFF);
            tabKata.setBackgroundColor(0xFFFFF3E0);
        } else {
            tabHira.setBackgroundColor(0xFFFFF3E0);
            tabKata.setBackgroundColor(0xFFFFFFFF);
        }
    }

    private void updateButtonStyles(Button active, Button inactive1, Button inactive2) {
        active.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFF7043));
        active.setTextColor(0xFFFFFFFF);

        inactive1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFFCCBC));
        inactive1.setTextColor(0xFFE65100);

        inactive2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFFCCBC));
        inactive2.setTextColor(0xFFE65100);
    }

    private void ustawZestaw() {
        if (czyHiragana) {
            if (aktualnyTyp.equals("PODSTAWOWE")) aktualnyZestaw = hiraPodstawowe;
            else if (aktualnyTyp.equals("DAKUTEN")) aktualnyZestaw = hiraDakuten;
            else aktualnyZestaw = hiraKombinowane;
        } else {
            if (aktualnyTyp.equals("PODSTAWOWE")) aktualnyZestaw = kataPodstawowe;
            else if (aktualnyTyp.equals("DAKUTEN")) aktualnyZestaw = kataDakuten;
            else aktualnyZestaw = kataKombinowane;
        }
        wyswietlZestaw();
    }

    private void wyswietlZestaw() {
        if (pendingTasks != null) {
            for (Runnable task : pendingTasks) {
                if (task != null) {
                    handler.removeCallbacks(task);
                }
            }
        }

        container.removeAllViews();

        pendingTasks = new Runnable[aktualnyZestaw.length];

        int columns = 5;
        container.setColumnCount(columns);

        for (int i = 0; i < aktualnyZestaw.length; i++) {
            final String[] znak = aktualnyZestaw[i];
            final long delay = i * 30L;

            Runnable task = () -> {
                CardView card = new CardView(this);
                GridLayout.LayoutParams cardParams = new GridLayout.LayoutParams();
                cardParams.width = 0;
                cardParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                cardParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
                cardParams.setMargins(8, 8, 8, 8);
                card.setLayoutParams(cardParams);
                card.setCardElevation(4f);
                card.setRadius(12f);
                card.setCardBackgroundColor(0xFFFFFFFF);

                LinearLayout item = new LinearLayout(this);
                item.setOrientation(LinearLayout.VERTICAL);
                item.setPadding(16, 24, 16, 24);
                item.setGravity(Gravity.CENTER);

                TextView kana = new TextView(this);
                kana.setText(znak[0]);
                kana.setTextSize(32);
                kana.setTextColor(0xFFE65100);
                kana.setGravity(Gravity.CENTER);
                item.addView(kana);

                if (!znak[1].isEmpty()) {
                    TextView romaji = new TextView(this);
                    romaji.setText(znak[1]);
                    romaji.setTextSize(14);
                    romaji.setTextColor(0xFF999999);
                    romaji.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams romajiParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    romajiParams.setMargins(0, 8, 0, 0);
                    romaji.setLayoutParams(romajiParams);
                    item.addView(romaji);
                }

                card.addView(item);

                card.setAlpha(0f);
                card.setTranslationY(50f);
                card.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(300)
                        .setStartDelay(0)
                        .start();

                container.addView(card);
            };

            pendingTasks[i] = task;
            handler.postDelayed(task, delay);
        }
    }
    //TODO zrobic przyciski ale to dopiero gdy bedzie polaczenie z baza danych
}