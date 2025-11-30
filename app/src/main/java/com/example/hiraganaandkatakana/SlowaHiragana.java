package com.example.hiraganaandkatakana;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SlowaHiragana extends AppCompatActivity {
    private Button buttonLosuj;
    private Button buttonSprawdz;
    private TextView hiraganaText;
    private EditText odpowiedzPodana;
    private ImageButton Powrot;
    private Switch zmianaJezyka;

    private String[][] slowa = {
            {"きっと", "kitto", "na pewno"},
            {"もっと", "motto", "bardziej"},
            {"ひと", "hito", "człowiek"},
            {"ねこ", "neko", "kot"},
            {"いぬ", "inu", "pies"},
            {"みず", "mizu", "woda"},
            {"ほん", "hon", "książka"},
            {"かわ", "kawa", "rzeka"},
            {"やま", "yama", "góra"},
            {"そら", "sora", "niebo"},
            {"あさ", "asa", "rano"},
            {"ひる", "hiru", "południe"},
            {"よる", "yoru", "noc"},
            {"つき", "tsuki", "księżyc"},
            {"ほし", "hoshi", "gwiazda"},
            {"はな", "hana", "kwiat"},
            {"はる", "haru", "wiosna"},
            {"なつ", "natsu", "lato"},
            {"あき", "aki", "jesień"},
            {"ふゆ", "fuyu", "zima"},
            {"かぜ", "kaze", "wiatr"},
            {"あめ", "ame", "deszcz"},
            {"ゆき", "yuki", "śnieg"},
            {"くも", "kumo", "chmura"},
            {"かさ", "kasa", "parasolka"},
            {"てがみ", "tegami", "list"},
            {"くるま", "kuruma", "samochód"},
            {"でんしゃ", "densha", "pociąg"},
            {"じてんしゃ", "jitensha", "rower"},
            {"えき", "eki", "stacja"},
            {"みち", "michi", "droga"},
            {"うみ", "umi", "morze"},
            {"もり", "mori", "las"},
            {"みどり", "midori", "zieleń"},
            {"せかい", "sekai", "świat"},
            {"くち", "kuchi", "usta"},
            {"め", "me", "oko"},
            {"みみ", "mimi", "ucho"},
            {"て", "te", "ręka"},
            {"あし", "ashi", "noga"},
            {"かお", "kao", "twarz"},
            {"かみ", "kami", "włosy"},
            {"は", "ha", "ząb"},
            {"しろ", "shiro", "biały"},
            {"くろ", "kuro", "czarny"},
            {"あか", "aka", "czerwony"},
            {"あお", "ao", "niebieski"},
            {"きいろ", "kiiro", "żółty"},
            {"ちゃいろ", "chairo", "brązowy"},
            {"みせ", "mise", "sklep"},
            {"にく", "niku", "mięso"},
            {"さかな", "sakana", "ryba"},
            {"くだもの", "kudamono", "owoce"},
            {"りんご", "ringo", "jabłko"},
            {"みかん", "mikan", "mandarynka"},
            {"いちご", "ichigo", "truskawka"},
            {"ごはん", "gohan", "ryż"},
            {"すし", "sushi", "sushi"},
            {"てんぷら", "tenpura", "tempura"},
            {"おちゃ", "ocha", "herbata"},
            {"ぎゅうにゅう", "gyuunyuu", "mleko"},
            {"こおり", "koori", "lód"},
            {"ぎんこう", "ginkou", "bank"},
            {"がっこう", "gakkou", "szkoła"},
            {"せんせい", "sensei", "nauczyciel"},
            {"がくせい", "gakusei", "uczeń"},
            {"ともだち", "tomodachi", "przyjaciel"},
            {"かぞく", "kazoku", "rodzina"},
            {"ちち", "chichi", "ojciec"},
            {"はは", "haha", "matka"},
            {"あに", "ani", "brat"},
            {"あね", "ane", "siostra"},
            {"いもうと", "imouto", "siostra"},
            {"おとうと", "otouto", "brat"},
            {"うた", "uta", "piosenka"},
            {"おんがく", "ongaku", "muzyka"},
            {"てんき", "tenki", "pogoda"},
            {"しごと", "shigoto", "praca"},
            {"やすみ", "yasumi", "odpoczynek"},
            {"へや", "heya", "pokój"},
            {"いえ", "ie", "dom"},
            {"まど", "mado", "okno"},
            {"つくえ", "tsukue", "biurko"},
            {"いす", "isu", "krzesło"},
            {"けしごむ", "keshigomu", "gumka"},
            {"えんぴつ", "enpitsu", "ołówek"},
            {"ほんだな", "hondana", "regał"},
            {"はし", "hashi", "pałeczki"},
            {"とけい", "tokei", "zegar"},
            {"かばん", "kaban", "torba"},
            {"ぼうし", "boushi", "czapka"},
            {"ふく", "fuku", "ubranie"},
            {"くつ", "kutsu", "buty"},
            {"くつした", "kutsushita", "skarpetki"},
            {"あそび", "asobi", "zabawa"},
            {"えいが", "eiga", "film"},
            {"まんが", "manga", "manga"},
            {"にゅうす", "nyuusu", "wiadomości"},
            {"かいもの", "kaimono", "zakupy"},
            {"すうじ", "suuji", "cyfry"},
            {"え", "e", "obrazek"},
            {"おかね", "okane", "pieniądze"},
            {"しゅくだい", "shukudai", "praca"},
            {"れいぞうこ", "reizouko", "lodówka"},
            {"てれび", "terebi", "telewizor"},
            {"けいたい", "keitai", "telefon"},
            {"さら", "sara", "talerz"},
            {"おはし", "ohashi", "pałeczki"},
            {"せっけん", "sekken", "mydło"},
            {"たまご", "tamago", "jajko"},
            {"おにぎり", "onigiri", "onigiri"}
    };

    private Random random = new Random();
    private int aktualnyIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slowa_hiragana);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Powrot = findViewById(R.id.buttonBack);
        buttonLosuj = findViewById(R.id.buttonRand);
        buttonSprawdz = findViewById(R.id.buttonSprawdz);
        hiraganaText = findViewById(R.id.textHiragana);
        odpowiedzPodana = findViewById(R.id.editTextYourAnswer);
        zmianaJezyka = findViewById(R.id.switchChangeLanguage);

        Powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonLosuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losujSlowo();
            }
        });

        buttonSprawdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprawdzOdpowiedz();
            }
        });

        zmianaJezyka.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zmianaJezyka.setText("JPN");
                    if (aktualnyIndex != -1) {
                        hiraganaText.setText(slowa[aktualnyIndex][2]);
                    }
                } else {
                    zmianaJezyka.setText("PL");
                    if (aktualnyIndex != -1) {
                        hiraganaText.setText(slowa[aktualnyIndex][0]);
                    }
                }
            }
        });

        losujSlowo();
    }

    private void losujSlowo() {
        aktualnyIndex = random.nextInt(slowa.length);

        boolean czyPolski = zmianaJezyka.isChecked();
        if (czyPolski) {
            hiraganaText.setText(slowa[aktualnyIndex][2]);
        } else {
            hiraganaText.setText(slowa[aktualnyIndex][0]);
        }

        hiraganaText.setTextColor(Color.parseColor("#E65100"));
        odpowiedzPodana.setText("");
    }

    private void sprawdzOdpowiedz() {
        if (aktualnyIndex == -1) {
            return;
        }

        String odpowiedz = odpowiedzPodana.getText().toString().toLowerCase().trim();

        boolean czyPolski = zmianaJezyka.isChecked();
        String poprawna = czyPolski ? slowa[aktualnyIndex][1] : slowa[aktualnyIndex][1];

        SpannableString spannable = new SpannableString(odpowiedz);

        int minLength = Math.min(odpowiedz.length(), poprawna.length());

        for (int i = 0; i < minLength; i++) {
            if (odpowiedz.charAt(i) == poprawna.charAt(i)) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if (odpowiedz.length() > poprawna.length()) {
            for (int i = minLength; i < odpowiedz.length(); i++) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        odpowiedzPodana.setText(spannable);
    }
}