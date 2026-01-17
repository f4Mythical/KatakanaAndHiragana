package com.example.hiraganaandkatakana;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class HiraganaDialog extends AppCompatActivity {

    private final String TEKST_LEWY   = "きのう、がっこうに　いました。";
    private final String TEKST_PRAWY  = "すごい！　わたしも！";
    private final String ODPOWIEDZ    = "sugoi! watashi mo!";

    private int indeksLewy  = 0;
    private int indeksPrawy = 0;
    private final Handler handler = new Handler();

    private Button przyciskLewy;
    private Button przyciskPrawy;
    private TextView odpowiedzPodana;
    private ImageButton cofnij;
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);

        ConstraintLayout root = findViewById(R.id.main);
        cofnij = findViewById(R.id.buttonBack);

        przyciskLewy = new Button(this);
        przyciskLewy.setId(View.generateViewId());
        root.addView(przyciskLewy);

        przyciskPrawy = new Button(this);
        przyciskPrawy.setId(View.generateViewId());
        root.addView(przyciskPrawy);

        odpowiedzPodana = new TextView(this);
        odpowiedzPodana.setId(View.generateViewId());
        root.addView(odpowiedzPodana);

        ConstraintSet set = new ConstraintSet();
        set.clone(root);

        set.connect(przyciskLewy.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        set.connect(przyciskLewy.getId(), ConstraintSet.TOP,  cofnij.getId(), ConstraintSet.BOTTOM, 16);

        set.connect(przyciskPrawy.getId(), ConstraintSet.END,   ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
        set.connect(przyciskPrawy.getId(), ConstraintSet.TOP,   przyciskLewy.getId(), ConstraintSet.BOTTOM, 16);

        set.connect(odpowiedzPodana.getId(), ConstraintSet.START, przyciskPrawy.getId(), ConstraintSet.START, 0);
        set.connect(odpowiedzPodana.getId(), ConstraintSet.END,   przyciskPrawy.getId(), ConstraintSet.END,   0);
        set.connect(odpowiedzPodana.getId(), ConstraintSet.TOP,    przyciskPrawy.getId(), ConstraintSet.BOTTOM, 8);
        set.applyTo(root);

        cofnij.setOnClickListener(v -> finish());

        setupKeyboard();
        handler.post(dodajLewy);
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

        Button space = findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> addCharacter(" "));

        Button delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                odpowiedzPodana.setText(input.toString());
            }
        });
    }

    private void addCharacter(String character) {
        input.append(character);
        odpowiedzPodana.setText(input.toString());
    }

    private void sprawdzOdpowiedz() {
        String odpowiedz = input.toString().trim();
        if (odpowiedz.isEmpty()) return;

        if (odpowiedz.equalsIgnoreCase(ODPOWIEDZ)) {
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
            input.setLength(0);
            odpowiedzPodana.setText("");
        } else {
            pokazKolorowanaOdpowiedz(odpowiedz, ODPOWIEDZ);
        }
    }

    private void pokazKolorowanaOdpowiedz(String odpowiedz, String poprawna) {
        String odpowiedzUpper = odpowiedz.toUpperCase();
        String poprawnaUpper = poprawna.toUpperCase();

        SpannableString spannable = new SpannableString(odpowiedz);
        int minLength = Math.min(odpowiedz.length(), poprawna.length());

        for (int i = 0; i < minLength; i++) {
            if (odpowiedzUpper.charAt(i) == poprawnaUpper.charAt(i)) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if (odpowiedz.length() > poprawna.length()) {
            for (int i = minLength; i < odpowiedz.length(); i++) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        odpowiedzPodana.setText(spannable);
    }

    private final Runnable dodajLewy = new Runnable() {
        private final long opoznienie = 300;
        @Override
        public void run() {
            if (indeksLewy < TEKST_LEWY.length()) {
                przyciskLewy.setText(TEKST_LEWY.substring(0, indeksLewy));
                indeksLewy++;
                handler.postDelayed(this, opoznienie);
            } else {
                handler.post(dodajPrawy);
            }
        }
    };

    private final Runnable dodajPrawy = new Runnable() {
        private final long opoznienie = 300;
        @Override
        public void run() {
            if (indeksPrawy < TEKST_PRAWY.length()) {
                przyciskPrawy.setText(TEKST_PRAWY.substring(0, indeksPrawy));
                indeksPrawy++;
                handler.postDelayed(this, opoznienie);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}