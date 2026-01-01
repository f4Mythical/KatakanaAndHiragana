package com.example.hiraganaandkatakana;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HiraganaBasicZnaki extends AppCompatActivity {

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
        setContentView(R.layout.activity_hiragana_basic_znaki);

        displayText = findViewById(R.id.displayText);
        inputText = findViewById(R.id.inputText);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        ImageButton settings = findViewById(R.id.buttonSettings);
        settings.setOnClickListener(v -> pokazDialogUstawien());

        GridLayout keyboard = findViewById(R.id.keyboardContainer);
        keyboard.setColumnCount(10);

        for (String litera : getAlphabetButtons()) {
            Button key = new Button(this);
            key.setText(litera);
            key.setAllCaps(false);
            key.setTextSize(12f);
            key.setMinHeight(48);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 2;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(3, 3, 3, 3);
            key.setLayoutParams(params);

            if (litera.equals("👍")) {
                key.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF4CAF50));
                key.setTextColor(0xFFFFFFFF);
                key.setOnClickListener(v -> sprawdzOdpowiedz());
            } else if (litera.equals("🎲")) {
                key.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFF7043));
                key.setTextColor(0xFFFFFFFF);
                key.setOnClickListener(v -> losujInnyZnak());
            } else {
                key.setTextColor(0xFFE65100);
                key.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFFCCBC));
                key.setOnClickListener(v -> {
                    input.append(litera);
                    inputText.setText(input.toString());
                });
            }

            keyboard.addView(key);
        }

        Button space = findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> {
            input.append(" ");
            inputText.setText(input.toString());
        });

        Button delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                inputText.setText(input.toString());
            }
        });

        przygotujDostepneZnaki();
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
            inputText.setTextColor(0xFFE65100);
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

    private void losujInnyZnak() {
        input.setLength(0);
        inputText.setText("");
        inputText.setTextColor(0xFFE65100);
        losujNowyZnak();
    }

    private void sprawdzOdpowiedz() {
        String odpowiedz = input.toString().trim();

        if (odpowiedz.isEmpty()) {
            return;
        }

        if (odpowiedz.equalsIgnoreCase(obecnaOdpowiedz)) {
            input.setLength(0);
            inputText.setText("");
            inputText.setTextColor(0xFFE65100);
            losujNowyZnak();
        } else {
            pokazKolorowanaOdpowiedz(odpowiedz, obecnaOdpowiedz);
        }
    }

    private void pokazKolorowanaOdpowiedz(String odpowiedz, String poprawna) {
        SpannableString spannable = new SpannableString(odpowiedz);
        int minLength = Math.min(odpowiedz.length(), poprawna.length());

        for (int i = 0; i < minLength; i++) {
            if (String.valueOf(odpowiedz.charAt(i)).equalsIgnoreCase(String.valueOf(poprawna.charAt(i)))) {
                spannable.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#4CAF50")),
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
                        new ForegroundColorSpan(Color.parseColor("#B71C1C")),
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
                        new ForegroundColorSpan(Color.parseColor("#B71C1C")),
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

    private String[] getAlphabetButtons() {
        return new String[] {
                "Q","W","E","R","T","Y","U","I","O","P",
                "A","S","D","F","G","H","J","K","L","X",
                "C","V","B","N","M","👍","🎲"
        };
    }
}