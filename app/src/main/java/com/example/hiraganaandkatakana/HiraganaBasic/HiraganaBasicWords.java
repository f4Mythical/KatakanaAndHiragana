package com.example.hiraganaandkatakana.HiraganaBasic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.WordData;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HiraganaBasicWords extends AppCompatActivity {

    private TextView hiraganaText;
    private TextView odpowiedzPodana;
    private final StringBuilder input = new StringBuilder();

    private final List<WordData> slowa = new ArrayList<>();
    private int aktualnyIndex = -1;
    private boolean pokazujHiragane = true;
    private TextView switchLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.hiragana_basic_words);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hiraganaText = findViewById(R.id.displayText);
        odpowiedzPodana = findViewById(R.id.inputText);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        switchLabel = findViewById(R.id.switchLabel);
        Switch switchLanguage = findViewById(R.id.switchLanguage);
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pokazujHiragane = !isChecked;
            aktualizujWyswietlaneSlowo();
            aktualizujTekstSwitcha();
        });

        setupKeyboard();
        wczytajSlowaZExcela();
        losujNoweSlowo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aktualizujWyswietlaneSlowo();
        aktualizujTekstSwitcha();
    }

    private void wczytajSlowaZExcela() {
        slowa.clear();
        try {
            InputStream is = getAssets().open("slowa.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(is);
            var sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String hiragana = row.getCell(0).getStringCellValue().trim();
                String romaji = row.getCell(1).getStringCellValue().trim();
                String polski = row.getCell(2).getStringCellValue().trim();
                String angielski = row.getCell(3).getStringCellValue().trim();
                String hiszpanski = row.getCell(4).getStringCellValue().trim();
                String kategoria = row.getCell(5).getStringCellValue().trim();

                if (!hiragana.isEmpty() && !romaji.isEmpty()) {
                    slowa.add(new WordData(hiragana, romaji, polski, angielski, hiszpanski, kategoria));
                }
            }

            wb.close();
            is.close();

        } catch (Exception e) {
            hiraganaText.setText("Błąd wczytywania");
            e.printStackTrace();
        }
    }

    private void losujNoweSlowo() {
        if (slowa.isEmpty()) {
            hiraganaText.setText("Brak słów!");
            aktualnyIndex = -1;
            return;
        }

        Random random = new Random();
        aktualnyIndex = random.nextInt(slowa.size());
        aktualizujWyswietlaneSlowo();
        aktualizujTekstSwitcha();
        input.setLength(0);
        odpowiedzPodana.setText("");
        odpowiedzPodana.setTextColor(getColor(R.color.tekst_podstawowy));
    }

    private String getPobranyKodJezyka() {
        SharedPreferences prefs = getSharedPreferences("ustawienia_aplikacji", MODE_PRIVATE);
        return prefs.getString("jezyk_aplikacji", "pl");
    }

    private void aktualizujWyswietlaneSlowo() {
        if (aktualnyIndex == -1 || slowa.isEmpty()) return;

        String wyswietlanyTekst;
        if (pokazujHiragane) {
            wyswietlanyTekst = slowa.get(aktualnyIndex).getHiragana();
        } else {
            String kodJezyka = getPobranyKodJezyka();
            wyswietlanyTekst = slowa.get(aktualnyIndex).getTlumaczenieDlaJezyka(kodJezyka);
        }
        hiraganaText.setText(wyswietlanyTekst);
    }

    private void aktualizujTekstSwitcha() {
        if (pokazujHiragane) {
            switchLabel.setText(R.string.hiragana);
        } else {
            String kodJezyka = getPobranyKodJezyka();
            if ("en".equals(kodJezyka)) {
                switchLabel.setText("English");
            } else if ("es".equals(kodJezyka)) {
                switchLabel.setText("Español");
            } else {
                switchLabel.setText("Polski");
            }
        }
        switchLabel.setTextColor(getColor(R.color.tekst_podstawowy));
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
        buttonRandom.setOnClickListener(v -> losujNoweSlowo());

        Button space = findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> addCharacter(" "));

        TextView delete = findViewById(R.id.buttonDelete);
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
        if (aktualnyIndex == -1) return;

        String odpowiedz = input.toString().trim();
        if (odpowiedz.isEmpty()) return;

        String poprawna = slowa.get(aktualnyIndex).getRomaji();

        if (odpowiedz.equalsIgnoreCase(poprawna)) {
            losujNoweSlowo();
        } else {
            pokazKolorowanaOdpowiedz(odpowiedz, poprawna);
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
}