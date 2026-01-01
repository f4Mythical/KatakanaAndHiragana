package com.example.hiraganaandkatakana.Slowa;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hiraganaandkatakana.R;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlowaHiragana extends AppCompatActivity {

    private Button buttonLosuj;
    private Button buttonSprawdz;
    private TextView hiraganaText;
    private EditText odpowiedzPodana;
    private ImageButton Powrot;
    private Switch zmianaJezyka;
    private List<String[]> slowa = new ArrayList<>();
    private Random random = new Random();
    private int aktualnyIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slowa_hiragana);

        Powrot = findViewById(R.id.buttonBack);
        buttonLosuj = findViewById(R.id.buttonRand);
        buttonSprawdz = findViewById(R.id.buttonSprawdz);
        hiraganaText = findViewById(R.id.textHiragana);
        odpowiedzPodana = findViewById(R.id.editTextYourAnswer);
        zmianaJezyka = findViewById(R.id.switchChangeLanguage);

        wczytajSlowaZExcel();

        Powrot.setOnClickListener(v -> finish());
        buttonLosuj.setOnClickListener(v -> losujSlowo());
        buttonSprawdz.setOnClickListener(v -> sprawdzOdpowiedz());

        zmianaJezyka.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (aktualnyIndex != -1) {
                if (isChecked) {
                    hiraganaText.setText(slowa.get(aktualnyIndex)[2]);
                } else {
                    hiraganaText.setText(slowa.get(aktualnyIndex)[0]);
                }
            }
        });

        losujSlowo();
    }

    private void wczytajSlowaZExcel() {
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

                slowa.add(new String[]{hiragana, romaji, polski});
            }

            wb.close();
            is.close();

        } catch (Exception e) {
            hiraganaText.setText("Blad");
        }
    }

    private void losujSlowo() {
        if (slowa.isEmpty()) {
            hiraganaText.setText("Brak słów!");
            return;
        }

        aktualnyIndex = random.nextInt(slowa.size());

        if (zmianaJezyka.isChecked()) {
            hiraganaText.setText(slowa.get(aktualnyIndex)[2]);
        } else {
            hiraganaText.setText(slowa.get(aktualnyIndex)[0]);
        }

        odpowiedzPodana.setText("");
        hiraganaText.setTextColor(Color.parseColor("#E65100"));
    }

    private void sprawdzOdpowiedz() {
        if (aktualnyIndex == -1) return;

        String odpowiedz = odpowiedzPodana.getText().toString().toLowerCase().trim();
        String poprawna = slowa.get(aktualnyIndex)[1].toLowerCase();

        SpannableString spannable = new SpannableString(odpowiedz);
        int minLength = Math.min(odpowiedz.length(), poprawna.length());

            for (int i = 0; i < minLength; i++) {
                if (odpowiedz.charAt(i) == poprawna.charAt(i)) {
                    spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            if (odpowiedz.length() > poprawna.length()) {
                for (int i = minLength; i < odpowiedz.length(); i++) {
                    spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        odpowiedzPodana.setText(spannable);
    }
}