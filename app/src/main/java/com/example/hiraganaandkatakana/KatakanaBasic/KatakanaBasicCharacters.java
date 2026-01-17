package com.example.hiraganaandkatakana.KatakanaBasic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.UstawienieKlawiatury;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KatakanaBasicCharacters extends AppCompatActivity {

    private final String[][] kataPodstawowe = {
            {"ア","a"}, {"イ","i"}, {"ウ","u"}, {"エ","e"}, {"オ","o"},
            {"カ","ka"}, {"キ","ki"}, {"ク","ku"}, {"ケ","ke"}, {"コ","ko"},
            {"サ","sa"}, {"シ","shi"}, {"ス","su"}, {"セ","se"}, {"ソ","so"},
            {"タ","ta"}, {"チ","chi"}, {"ツ","tsu"}, {"テ","te"}, {"ト","to"},
            {"ナ","na"}, {"ニ","ni"}, {"ヌ","nu"}, {"ネ","ne"}, {"ノ","no"},
            {"ハ","ha"}, {"ヒ","hi"}, {"フ","fu"}, {"ヘ","he"}, {"ホ","ho"},
            {"マ","ma"}, {"ミ","mi"}, {"ム","mu"}, {"メ","me"}, {"モ","mo"},
            {"ヤ","ya"}, {"  ","  "}, {"ユ","yu"}, {"  ","  "}, {"ヨ","yo"},
            {"ラ","ra"}, {"リ","ri"}, {"ル","ru"}, {"レ","re"}, {"ロ","ro"},
            {"ワ","wa"}, {"  ","  "}, {"  "," "}, {"  ","  "}, {"ヲ","wo"},
            {"  ","  "}, {"  ","  "}, {"  ","  "}, {"  ","  "}, {"ン","n"}
    };

    private final String[][] kataDakuten = {
            {"ガ","ga"}, {"ギ","gi"}, {"グ","gu"}, {"ゲ","ge"}, {"ゴ","go"},
            {"ザ","za"}, {"ジ","ji"}, {"ズ","zu"}, {"ゼ","ze"}, {"ゾ","zo"},
            {"ダ","da"}, {"ヂ","ji"}, {"ヅ","zu"}, {"デ","de"}, {"ド","do"},
            {"バ","ba"}, {"ビ","bi"}, {"ブ","bu"}, {"ベ","be"}, {"ボ","bo"},
            {"パ","pa"}, {"ピ","pi"}, {"プ","pu"}, {"ペ","pe"}, {"ポ","po"}
    };

    private final String[][] kataKombinowane = {
            {"キャ","kya"},{" "," "}, {"キュ","kyu"}, {" "," "},{"キョ","kyo"},
            {"ギャ","gya"}, {" "," "},{"ギュ","gyu"}, {" "," "},{"ギョ","gyo"},
            {"シャ","sha"},{" "," "}, {"シュ","shu"},{" "," "}, {"ショ","sho"},
            {"ジャ","ja"}, {" "," "},{"ジュ","ju"}, {" "," "},{"ジョ","jo"},
            {"チャ","cha"},{" "," "}, {"チュ","chu"},{" "," "}, {"チョ","cho"},
            {"ヂャ","ja"}, {" "," "},{"ヂュ","ju"},{" "," "}, {"ヂョ","jo"},
            {"ニャ","nya"},{" "," "}, {"ニュ","nyu"},{" "," "}, {"ニョ","nyo"},
            {"ヒャ","hya"},{" "," "}, {"ヒュ","hyu"},{" "," "}, {"ヒョ","hyo"},
            {"ビャ","bya"},{" "," "}, {"ビュ","byu"},{" "," "}, {"ビョ","byo"},
            {"ピャ","pya"},{" "," "}, {"ピュ","pyu"},{" "," "}, {"ピョ","pyo"},
            {"ミャ","mya"}, {" "," "},{"ミュ","myu"}, {" "," "}, {"ミョ","myo"},
            {"リャ","rya"},{" "," "}, {"リュ","ryu"}, {" "," "},{"リョ","ryo"}
    };

    private boolean czyPodstawowe = true;
    private boolean czyDakuten = false;
    private boolean czyKombinowane = false;

    private final List<String> dostepneZnaki = new ArrayList<>();
    private String obecnyZnak = "";
    private String ostatniZnak = "";
    private String obecnaOdpowiedz = "";

    private TextView displayText;
    private TextView inputText;
    private final StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.katakana_basic_characters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        displayText = findViewById(R.id.displayText);
        inputText = findViewById(R.id.inputText);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        ImageButton settings = findViewById(R.id.buttonSettings);
        settings.setOnClickListener(v -> pokazDialogUstawien());

        UstawienieKlawiatury.setupKeyboard(this, inputText, input, new UstawienieKlawiatury.KeyboardCallbacks() {
            @Override
            public void onCheckClicked() {
                sprawdzOdpowiedz();
            }
            @Override
            public void onRandomClicked() {
                losujInnyZnak();
            }
        });

        przygotujDostepneZnaki();
        losujNowyZnak();
    }

    public void sprawdzOdpowiedz() {
        String odpowiedz = input.toString().trim();

        if (odpowiedz.isEmpty()) {
            return;
        }

        if (odpowiedz.equalsIgnoreCase(obecnaOdpowiedz)) {
            input.setLength(0);
            inputText.setText("");
            inputText.setTextColor(getColor(R.color.tekst_podstawowy));
            losujNowyZnak();
        } else {
            pokazKolorowanaOdpowiedz(odpowiedz, obecnaOdpowiedz);
        }
    }

    public void losujInnyZnak() {
        input.setLength(0);
        inputText.setText("");
        inputText.setTextColor(getColor(R.color.tekst_podstawowy));
        losujNowyZnak();
    }

    private void pokazDialogUstawien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_settings, null);
        builder.setView(dialogView);

        ToggleButton togglePodstawowe = dialogView.findViewById(R.id.togglePodstawowe);
        ToggleButton toggleDakuten = dialogView.findViewById(R.id.toggleDakuten);
        ToggleButton toggleKombinowane = dialogView.findViewById(R.id.toggleKombinowane);
        Button buttonUstaw = dialogView.findViewById(R.id.buttonUstaw);

        togglePodstawowe.setChecked(czyPodstawowe);
        toggleDakuten.setChecked(czyDakuten);
        toggleKombinowane.setChecked(czyKombinowane);

        togglePodstawowe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            czyPodstawowe = isChecked;
            resetIfNoneSelected();
            buttonUstaw.setEnabled(czyPodstawowe || czyDakuten || czyKombinowane);
        });

        toggleDakuten.setOnCheckedChangeListener((buttonView, isChecked) -> {
            czyDakuten = isChecked;
            resetIfNoneSelected();
            buttonUstaw.setEnabled(czyPodstawowe || czyDakuten || czyKombinowane);
        });

        toggleKombinowane.setOnCheckedChangeListener((buttonView, isChecked) -> {
            czyKombinowane = isChecked;
            resetIfNoneSelected();
            buttonUstaw.setEnabled(czyPodstawowe || czyDakuten || czyKombinowane);
        });

        AlertDialog dialog = builder.create();

        buttonUstaw.setOnClickListener(v -> {
            przygotujDostepneZnaki();
            losujNowyZnak();
            input.setLength(0);
            inputText.setText("");
            inputText.setTextColor(getColor(R.color.tekst_podstawowy));
            dialog.dismiss();
        });

        dialog.show();

        buttonUstaw.setEnabled(czyPodstawowe || czyDakuten || czyKombinowane);
    }

    private void resetIfNoneSelected() {
        if (!czyPodstawowe && !czyDakuten && !czyKombinowane) {
            czyPodstawowe = true;
        }
    }

    private void przygotujDostepneZnaki() {
        dostepneZnaki.clear();

        if (czyPodstawowe) {
            for (String[] para : kataPodstawowe) {
                if (!para[0].trim().isEmpty() && !para[1].trim().isEmpty()) {
                    dostepneZnaki.add(para[0] + "," + para[1]);
                }
            }
        }

        if (czyDakuten) {
            for (String[] para : kataDakuten) {
                if (!para[0].trim().isEmpty() && !para[1].trim().isEmpty()) {
                    dostepneZnaki.add(para[0] + "," + para[1]);
                }
            }
        }

        if (czyKombinowane) {
            for (String[] para : kataKombinowane) {
                if (!para[0].trim().isEmpty() && !para[1].trim().isEmpty()) {
                    dostepneZnaki.add(para[0] + "," + para[1]);
                }
            }
        }
    }

    private void losujNowyZnak() {
        if (dostepneZnaki.isEmpty()) {
            displayText.setText("Brak znaków!");
            obecnyZnak = "";
            ostatniZnak = "";
            obecnaOdpowiedz = "";
            return;
        }

        Random random = new Random();
        String paraZnakow;
        String[] parts;

        do {
            paraZnakow = dostepneZnaki.get(random.nextInt(dostepneZnaki.size()));
            parts = paraZnakow.split(",");
        } while (parts[0].equals(ostatniZnak) && dostepneZnaki.size() > 1);

        obecnyZnak = parts[0];
        ostatniZnak = obecnyZnak;
        obecnaOdpowiedz = parts[1];

        displayText.setText(obecnyZnak);
    }

    private void pokazKolorowanaOdpowiedz(String odpowiedz, String poprawna) {
        SpannableString spannable = new SpannableString(odpowiedz);
        int minLength = Math.min(odpowiedz.length(), poprawna.length());

        for (int i = 0; i < minLength; i++) {
            if (String.valueOf(odpowiedz.charAt(i)).equalsIgnoreCase(String.valueOf(poprawna.charAt(i)))) {
                spannable.setSpan(
                        new ForegroundColorSpan(getColor(R.color.poprawny)),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannable.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            } else {
                spannable.setSpan(
                        new ForegroundColorSpan(getColor(R.color.bledny)),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannable.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        if (odpowiedz.length() > poprawna.length()) {
            for (int i = minLength; i < odpowiedz.length(); i++) {
                spannable.setSpan(
                        new ForegroundColorSpan(getColor(R.color.bledny)),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannable.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        i, i + 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        inputText.setText(spannable);
    }
}
