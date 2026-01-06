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

        setupKeyboard();

        przygotujDostepneZnaki();
        losujNowyZnak();
    }

    private void setupKeyboard() {
        Button buttonQ = findViewById(R.id.buttonQ);
        Button buttonW = findViewById(R.id.buttonW);
        Button buttonE = findViewById(R.id.buttonE);
        Button buttonR = findViewById(R.id.buttonR);
        Button buttonT = findViewById(R.id.buttonT);
        Button buttonY = findViewById(R.id.buttonY);
        Button buttonU = findViewById(R.id.buttonU);
        Button buttonI = findViewById(R.id.buttonI);
        Button buttonO = findViewById(R.id.buttonO);
        Button buttonP = findViewById(R.id.buttonP);

        Button buttonA = findViewById(R.id.buttonA);
        Button buttonS = findViewById(R.id.buttonS);
        Button buttonD = findViewById(R.id.buttonD);
        Button buttonF = findViewById(R.id.buttonF);
        Button buttonG = findViewById(R.id.buttonG);
        Button buttonH = findViewById(R.id.buttonH);
        Button buttonJ = findViewById(R.id.buttonJ);
        Button buttonK = findViewById(R.id.buttonK);
        Button buttonL = findViewById(R.id.buttonL);

        Button buttonZ = findViewById(R.id.buttonZ);
        Button buttonX = findViewById(R.id.buttonX);
        Button buttonC = findViewById(R.id.buttonC);
        Button buttonV = findViewById(R.id.buttonV);
        Button buttonB = findViewById(R.id.buttonB);
        Button buttonN = findViewById(R.id.buttonN);
        Button buttonM = findViewById(R.id.buttonM);

        buttonQ.setOnClickListener(v -> addCharacter("Q"));
        buttonW.setOnClickListener(v -> addCharacter("W"));
        buttonE.setOnClickListener(v -> addCharacter("E"));
        buttonR.setOnClickListener(v -> addCharacter("R"));
        buttonT.setOnClickListener(v -> addCharacter("T"));
        buttonY.setOnClickListener(v -> addCharacter("Y"));
        buttonU.setOnClickListener(v -> addCharacter("U"));
        buttonI.setOnClickListener(v -> addCharacter("I"));
        buttonO.setOnClickListener(v -> addCharacter("O"));
        buttonP.setOnClickListener(v -> addCharacter("P"));


        buttonA.setOnClickListener(v -> addCharacter("A"));
        buttonS.setOnClickListener(v -> addCharacter("S"));
        buttonD.setOnClickListener(v -> addCharacter("D"));
        buttonF.setOnClickListener(v -> addCharacter("F"));
        buttonG.setOnClickListener(v -> addCharacter("G"));
        buttonH.setOnClickListener(v -> addCharacter("H"));
        buttonJ.setOnClickListener(v -> addCharacter("J"));
        buttonK.setOnClickListener(v -> addCharacter("K"));
        buttonL.setOnClickListener(v -> addCharacter("L"));


        buttonZ.setOnClickListener(v -> addCharacter("Z"));
        buttonX.setOnClickListener(v -> addCharacter("X"));
        buttonC.setOnClickListener(v -> addCharacter("C"));
        buttonV.setOnClickListener(v -> addCharacter("V"));
        buttonB.setOnClickListener(v -> addCharacter("B"));
        buttonN.setOnClickListener(v -> addCharacter("N"));
        buttonM.setOnClickListener(v -> addCharacter("M"));

        Button buttonCheck = findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(v -> sprawdzOdpowiedz());

        TextView buttonRandom = findViewById(R.id.buttonRandom);
        buttonRandom.setOnClickListener(v -> losujInnyZnak());

        Button space = findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> addCharacter(" "));

        TextView delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                inputText.setText(input.toString());
            }
        });
    }

    private void addCharacter(String character) {
        input.append(character);
        inputText.setText(input.toString());
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

    private void losujInnyZnak() {
        input.setLength(0);
        inputText.setText("");
        inputText.setTextColor(getColor(R.color.tekst_podstawowy));
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
            inputText.setTextColor(getColor(R.color.tekst_podstawowy));
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