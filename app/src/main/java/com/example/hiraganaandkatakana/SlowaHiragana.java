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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    private String[][] slowa = {
            {"きっと", "kitto"},
            {"もっと", "motto"},
            {"ひと", "hito"},
            {"ねこ", "neko"},
            {"いぬ", "inu"},
            {"みず", "mizu"},
            {"ほん", "hon"},
            {"かわ", "kawa"},
            {"やま", "yama"},
            {"そら", "sora"},
            {"あさ", "asa"},
            {"ひる", "hiru"},
            {"よる", "yoru"},
            {"つき", "tsuki"},
            {"ほし", "hoshi"},
            {"はな", "hana"},
            {"はる", "haru"},
            {"なつ", "natsu"},
            {"あき", "aki"},
            {"ふゆ", "fuyu"},
            {"かぜ", "kaze"},
            {"あめ", "ame"},
            {"ゆき", "yuki"},
            {"くも", "kumo"},
            {"かさ", "kasa"},
            {"てがみ", "tegami"},
            {"くるま", "kuruma"},
            {"でんしゃ", "densha"},
            {"じてんしゃ", "jitensha"},
            {"えき", "eki"},
            {"みち", "michi"},
            {"うみ", "umi"},
            {"もり", "mori"},
            {"みどり", "midori"},
            {"せかい", "sekai"},
            {"くち", "kuchi"},
            {"め", "me"},
            {"みみ", "mimi"},
            {"て", "te"},
            {"あし", "ashi"},
            {"かお", "kao"},
            {"かみ", "kami"},
            {"は", "ha"},
            {"しろ", "shiro"},
            {"くろ", "kuro"},
            {"あか", "aka"},
            {"あお", "ao"},
            {"きいろ", "kiiro"},
            {"ちゃいろ", "chairo"},
            {"みせ", "mise"},
            {"にく", "niku"},
            {"さかな", "sakana"},
            {"くだもの", "kudamono"},
            {"りんご", "ringo"},
            {"みかん", "mikan"},
            {"いちご", "ichigo"},
            {"ごはん", "gohan"},
            {"すし", "sushi"},
            {"てんぷら", "tenpura"},
            {"おちゃ", "ocha"},
            {"ぎゅうにゅう", "gyuunyuu"},
            {"こおり", "koori"},
            {"みず", "mizu"},
            {"ぎんこう", "ginkou"},
            {"がっこう", "gakkou"},
            {"せんせい", "sensei"},
            {"がくせい", "gakusei"},
            {"ともだち", "tomodachi"},
            {"かぞく", "kazoku"},
            {"ちち", "chichi"},
            {"はは", "haha"},
            {"あに", "ani"},
            {"あね", "ane"},
            {"いもうと", "imouto"},
            {"おとうと", "otouto"},
            {"うた", "uta"},
            {"おんがく", "ongaku"},
            {"てんき", "tenki"},
            {"しごと", "shigoto"},
            {"やすみ", "yasumi"},
            {"へや", "heya"},
            {"いえ", "ie"},
            {"まど", "mado"},
            {"つくえ", "tsukue"},
            {"いす", "isu"},
            {"けしごむ", "keshigomu"},
            {"えんぴつ", "enpitsu"},
            {"ほんだな", "hondana"},
            {"はし", "hashi"},
            {"とけい", "tokei"},
            {"かばん", "kaban"},
            {"ぼうし", "boushi"},
            {"ふく", "fuku"},
            {"くつ", "kutsu"},
            {"くつした", "kutsushita"},
            {"あそび", "asobi"},
            {"えいが", "eiga"},
            {"まんが", "manga"},
            {"にゅうす", "nyuusu"},
            {"かいもの", "kaimono"},
            {"すうじ", "suuji"},
            {"え", "e"},
            {"おかね", "okane"},
            {"しゅくだい", "shukudai"},
            {"れいぞうこ", "reizouko"},
            {"てれび", "terebi"},
            {"けいたい", "keitai"},
            {"さら", "sara"},
            {"おはし", "ohashi"},
            {"せっけん", "sekken"},
            {"かぜ", "kaze"},
            {"ぎょぎょ", "gyogyo"},
            {"たまご", "tamago"},
            {"あめ", "ame"},
            {"おにぎり", "onigiri"}
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

        losujSlowo();
    }

    private void losujSlowo() {
        aktualnyIndex = random.nextInt(slowa.length);
        hiraganaText.setText(slowa[aktualnyIndex][0]);
        hiraganaText.setTextColor(Color.parseColor("#E65100"));
        odpowiedzPodana.setText("");
    }

    private void sprawdzOdpowiedz() {
        if (aktualnyIndex == -1) {
            return;
        }

        String odpowiedz = odpowiedzPodana.getText().toString().toLowerCase().trim();
        String poprawna = slowa[aktualnyIndex][1];

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