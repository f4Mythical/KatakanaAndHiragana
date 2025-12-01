package com.example.hiraganaandkatakana.Litery;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hiraganaandkatakana.R;
import java.util.Random;

public class LiteryHiragana extends AppCompatActivity {

    private Button buttonLosuj;
    private Button buttonSprawdz;
    private TextView hiraganaText;
    private EditText odpowiedzPodana;
    private ImageButton Powrot;

    private String[][] zestawPodstawowy = {
            {"あ", "a"}, {"い", "i"}, {"う", "u"}, {"え", "e"}, {"お", "o"},
            {"か", "ka"}, {"き", "ki"}, {"く", "ku"}, {"け", "ke"}, {"こ", "ko"},
            {"さ", "sa"}, {"し", "shi"}, {"す", "su"}, {"せ", "se"}, {"そ", "so"},
            {"た", "ta"}, {"ち", "chi"}, {"つ", "tsu"}, {"て", "te"}, {"と", "to"},
            {"な", "na"}, {"に", "ni"}, {"ぬ", "nu"}, {"ね", "ne"}, {"の", "no"},
            {"は", "ha"}, {"ひ", "hi"}, {"ふ", "fu"}, {"へ", "he"}, {"ほ", "ho"},
            {"ま", "ma"}, {"み", "mi"}, {"む", "mu"}, {"め", "me"}, {"も", "mo"},
            {"や", "ya"}, {"ゆ", "yu"}, {"よ", "yo"},
            {"ら", "ra"}, {"り", "ri"}, {"る", "ru"}, {"れ", "re"}, {"ろ", "ro"},
            {"わ", "wa"}, {"を", "wo"}, {"ん", "n"}
    };

    private String[][] zestawDakuten = {
            {"が", "ga"}, {"ぎ", "gi"}, {"ぐ", "gu"}, {"げ", "ge"}, {"ご", "go"},
            {"ざ", "za"}, {"じ", "ji"}, {"ず", "zu"}, {"ぜ", "ze"}, {"ぞ", "zo"},
            {"だ", "da"}, {"ぢ", "ji"}, {"づ", "zu"}, {"で", "de"}, {"ど", "do"},
            {"ば", "ba"}, {"び", "bi"}, {"ぶ", "bu"}, {"べ", "be"}, {"ぼ", "bo"},
            {"ぱ", "pa"}, {"ぴ", "pi"}, {"ぷ", "pu"}, {"ぺ", "pe"}, {"ぽ", "po"}
    };

    private String[][] zestawKombinowane = {
            {"きゃ", "kya"}, {"きゅ", "kyu"}, {"きょ", "kyo"},
            {"しゃ", "sha"}, {"しゅ", "shu"}, {"しょ", "sho"},
            {"ちゃ", "cha"}, {"ちゅ", "chu"}, {"ちょ", "cho"},
            {"にゃ", "nya"}, {"にゅ", "nyu"}, {"にょ", "nyo"},
            {"ひゃ", "hya"}, {"ひゅ", "hyu"}, {"ひょ", "hyo"},
            {"みゃ", "mya"}, {"みゅ", "myu"}, {"みょ", "myo"},
            {"りゃ", "rya"}, {"りゅ", "ryu"}, {"りょ", "ryo"},
            {"ぎゃ", "gya"}, {"ぎゅ", "gyu"}, {"ぎょ", "gyo"},
            {"じゃ", "ja"}, {"じゅ", "ju"}, {"じょ", "jo"},
            {"びゃ", "bya"}, {"びゅ", "byu"}, {"びょ", "byo"},
            {"ぴゃ", "pya"}, {"ぴゅ", "pyu"}, {"ぴょ", "pyo"}
    };

    private String[][] aktualnyZestaw;
    private Random random = new Random();
    private int aktualnyIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litery_hiragana);

        boolean p = getIntent().getBooleanExtra("PODSTAWOWE", false);
        boolean d = getIntent().getBooleanExtra("DAKUTEN", false);
        boolean k = getIntent().getBooleanExtra("KOMBINOWANE", false);

        int total = (p ? zestawPodstawowy.length : 0)
                + (d ? zestawDakuten.length : 0)
                + (k ? zestawKombinowane.length : 0);

        aktualnyZestaw = new String[total][2];
        int index = 0;

        if (p) for (String[] item : zestawPodstawowy) aktualnyZestaw[index++] = item;
        if (d) for (String[] item : zestawDakuten) aktualnyZestaw[index++] = item;
        if (k) for (String[] item : zestawKombinowane) aktualnyZestaw[index++] = item;

        Powrot = findViewById(R.id.buttonBack);
        buttonLosuj = findViewById(R.id.buttonRand);
        buttonSprawdz = findViewById(R.id.buttonSprawdz);
        hiraganaText = findViewById(R.id.textHiragana);
        odpowiedzPodana = findViewById(R.id.editTextYourAnswer);

        Powrot.setOnClickListener(v -> finish());
        buttonLosuj.setOnClickListener(v -> losujLitere());
        buttonSprawdz.setOnClickListener(v -> sprawdzOdpowiedz());

        losujLitere();
    }

    private void losujLitere() {
        aktualnyIndex = random.nextInt(aktualnyZestaw.length);
        hiraganaText.setText(aktualnyZestaw[aktualnyIndex][0]);
        hiraganaText.setTextColor(Color.parseColor("#E65100"));
        odpowiedzPodana.setText("");
    }

    private void sprawdzOdpowiedz() {
        if (aktualnyIndex == -1) return;

        String odpowiedz = odpowiedzPodana.getText().toString().toLowerCase().trim();
        String poprawna = aktualnyZestaw[aktualnyIndex][1];

        SpannableString spannable = new SpannableString(odpowiedz);

        int minLength = Math.min(odpowiedz.length(), poprawna.length());

        for (int i = 0; i < minLength; i++) {
            if (odpowiedz.charAt(i) == poprawna.charAt(i)) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if (odpowiedz.length() > poprawna.length()) {
            for (int i = minLength; i < odpowiedz.length(); i++) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        odpowiedzPodana.setText(spannable);
    }
}
