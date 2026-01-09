package com.example.hiraganaandkatakana;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.hiraganaandkatakana.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class WybraneZnakiPremium extends AppCompatActivity {

    private List<String> dostepneZnaki = new ArrayList<>();
    private String obecnyZnak = "";
    private String ostatniZnak = "";
    private String obecnaOdpowiedz = "";

    private TextView displayText;
    private TextView inputText;
    private StringBuilder input = new StringBuilder();

    private HashMap<String, String> mapaZnakowRomaji = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybrane_znaki_premium);

        displayText = findViewById(R.id.displayText);
        inputText = findViewById(R.id.inputText);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        setupKeyboard();

        ArrayList<String> znaki = getIntent().getStringArrayListExtra("znaki");
        ArrayList<Integer> powtorzenia = getIntent().getIntegerArrayListExtra("powtorzenia");
        mapaZnakowRomaji = (HashMap<String, String>) getIntent().getSerializableExtra("mapaZnakow");

        if (znaki == null || powtorzenia == null || mapaZnakowRomaji == null || znaki.size() != powtorzenia.size()) {
            finish();
            return;
        }

        for (int i = 0; i < znaki.size(); i++) {
            String znak = znaki.get(i);
            int ileRazy = powtorzenia.get(i);
            String romaji = mapaZnakowRomaji.get(znak);

            if (romaji != null && !romaji.trim().isEmpty()) {
                if (ileRazy == 0) {
                    dostepneZnaki.add(znak + "," + romaji + ",INF");
                } else {
                    for (int j = 0; j < ileRazy; j++) {
                        dostepneZnaki.add(znak + "," + romaji + ",NORMAL");
                    }
                }
            }
        }

        if (dostepneZnaki.isEmpty()) {
            finish();
            return;
        }

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

        buttonQ.setOnClickListener(v -> addCharacter("q"));
        buttonW.setOnClickListener(v -> addCharacter("w"));
        buttonE.setOnClickListener(v -> addCharacter("e"));
        buttonR.setOnClickListener(v -> addCharacter("r"));
        buttonT.setOnClickListener(v -> addCharacter("t"));
        buttonY.setOnClickListener(v -> addCharacter("y"));
        buttonU.setOnClickListener(v -> addCharacter("u"));
        buttonI.setOnClickListener(v -> addCharacter("i"));
        buttonO.setOnClickListener(v -> addCharacter("o"));
        buttonP.setOnClickListener(v -> addCharacter("p"));

        buttonA.setOnClickListener(v -> addCharacter("a"));
        buttonS.setOnClickListener(v -> addCharacter("s"));
        buttonD.setOnClickListener(v -> addCharacter("d"));
        buttonF.setOnClickListener(v -> addCharacter("f"));
        buttonG.setOnClickListener(v -> addCharacter("g"));
        buttonH.setOnClickListener(v -> addCharacter("h"));
        buttonJ.setOnClickListener(v -> addCharacter("j"));
        buttonK.setOnClickListener(v -> addCharacter("k"));
        buttonL.setOnClickListener(v -> addCharacter("l"));

        buttonZ.setOnClickListener(v -> addCharacter("z"));
        buttonX.setOnClickListener(v -> addCharacter("x"));
        buttonC.setOnClickListener(v -> addCharacter("c"));
        buttonV.setOnClickListener(v -> addCharacter("v"));
        buttonB.setOnClickListener(v -> addCharacter("b"));
        buttonN.setOnClickListener(v -> addCharacter("n"));
        buttonM.setOnClickListener(v -> addCharacter("m"));

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

    private void losujNowyZnak() {
        if (dostepneZnaki.isEmpty()) {
            displayText.setText("Ukończono!");
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

            for (int i = 0; i < dostepneZnaki.size(); i++) {
                String entry = dostepneZnaki.get(i);
                String[] parts = entry.split(",");
                if (parts[0].equals(obecnyZnak) && parts[2].equals("NORMAL")) {
                    dostepneZnaki.remove(i);
                    break;
                }
            }

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