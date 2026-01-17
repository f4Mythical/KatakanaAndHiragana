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

        UstawienieKlawiatury.setupKeyboard(this, odpowiedzPodana, input, new UstawienieKlawiatury.KeyboardCallbacks() {
            @Override
            public void onCheckClicked() {
                sprawdzOdpowiedz();
            }
            @Override
            public void onRandomClicked() {}
        });

        handler.post(dodajLewy);
    }

    public void sprawdzOdpowiedz() {
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