package com.example.hiraganaandkatakana.HiraganaBasic;

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

public class HiraganaBasicCharacters extends AppCompatActivity {

    private final String[][] hiraPodstawowe = {
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

    private final String[][] hiraDakuten = {
            {"が","ga"}, {"ぎ","gi"}, {"ぐ","gu"}, {"げ","ge"}, {"ご","go"},
            {"ざ","za"}, {"じ","ji"}, {"ず","zu"}, {"ぜ","ze"}, {"ぞ","zo"},
            {"だ","da"}, {"ぢ","ji"}, {"づ","zu"}, {"で","de"}, {"ど","do"},
            {"ば","ba"}, {"び","bi"}, {"ぶ","bu"}, {"べ","be"}, {"ぼ","bo"},
            {"ぱ","pa"}, {"ぴ","pi"}, {"ぷ","pu"}, {"ぺ","pe"}, {"ぽ","po"}
    };

    private final String[][] hiraKombinowane = {
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
        setContentView(R.layout.hiragana_basic_characters);
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
            for (String[] para : hiraPodstawowe) {
                if (!para[0].trim().isEmpty() && !para[1].trim().isEmpty()) {
                    dostepneZnaki.add(para[0] + "," + para[1]);
                }
            }
        }

        if (czyDakuten) {
            for (String[] para : hiraDakuten) {
                if (!para[0].trim().isEmpty() && !para[1].trim().isEmpty()) {
                    dostepneZnaki.add(para[0] + "," + para[1]);
                }
            }
        }

        if (czyKombinowane) {
            for (String[] para : hiraKombinowane) {
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