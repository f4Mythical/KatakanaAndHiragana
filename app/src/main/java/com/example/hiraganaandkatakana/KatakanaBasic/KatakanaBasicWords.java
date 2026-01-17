package com.example.hiraganaandkatakana.KatakanaBasic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.UstawienieKlawiatury;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KatakanaBasicWords extends AppCompatActivity {

    private TextView katakanaText;
    private TextView odpowiedzPodana;
    private final StringBuilder input = new StringBuilder();

    private final List<String[]> slowa = new ArrayList<>();
    private int aktualnyIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.katakana_basic_words);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        katakanaText = findViewById(R.id.displayText);
        odpowiedzPodana = findViewById(R.id.inputText);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        wczytajSlowaZExcela();
        losujNoweSlowo();

        UstawienieKlawiatury.setupKeyboard(this, odpowiedzPodana, input, new UstawienieKlawiatury.KeyboardCallbacks() {
            @Override
            public void onCheckClicked() {
                sprawdzOdpowiedz();
            }
            @Override
            public void onRandomClicked() {
                losujNoweSlowo();
            }
        });
    }

    private void wczytajSlowaZExcela() {
        slowa.clear();
        try {
            InputStream is = getAssets().open("slowaKatakana.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(is);
            var sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String katakana = row.getCell(0).getStringCellValue().trim();
                String romaji = row.getCell(1).getStringCellValue().trim();

                if (!katakana.isEmpty() && !romaji.isEmpty()) {
                    slowa.add(new String[]{katakana, romaji});
                }
            }

            wb.close();
            is.close();

        } catch (Exception e) {
            katakanaText.setText("Błąd wczytywania");
            e.printStackTrace();
        }
    }

    public void losujNoweSlowo() {
        if (slowa.isEmpty()) {
            katakanaText.setText("Brak słów!");
            aktualnyIndex = -1;
            return;
        }

        Random random = new Random();
        aktualnyIndex = random.nextInt(slowa.size());
        katakanaText.setText(slowa.get(aktualnyIndex)[0]);
        input.setLength(0);
        odpowiedzPodana.setText("");
        odpowiedzPodana.setTextColor(getColor(R.color.tekst_podstawowy));
    }

    public void sprawdzOdpowiedz() {
        if (aktualnyIndex == -1) return;

        String odpowiedz = input.toString().trim();
        if (odpowiedz.isEmpty()) return;

        String poprawna = slowa.get(aktualnyIndex)[1];

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