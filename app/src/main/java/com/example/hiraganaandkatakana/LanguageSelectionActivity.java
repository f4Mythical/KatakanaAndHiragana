package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    private Spinner spinnerJezyki;
    private TextView tvWybierzJezyk;
    private MaterialButton btnGotowy;
    private HashMap<String, Locale> mapaJezykow = new HashMap<>();
    private SharedPreferences preferencje;
    private String wybranyKodJezyka = "pl";

    @Override
    protected void onCreate(Bundle zapisStanu) {
        super.onCreate(zapisStanu);

        preferencje = getSharedPreferences("ustawienia_aplikacji", MODE_PRIVATE);
        wczytajZapisanyJezyk();
        ustawLocale(wybranyKodJezyka);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language_selection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicjalizujWidoki();
        przygotujMapeJezykow();
        ustawSpinner();
        ustawPrzyciskGotowy();
    }

    private void wczytajZapisanyJezyk() {
        wybranyKodJezyka = preferencje.getString("jezyk_aplikacji", "pl");
    }

    private void zapiszJezyk(String kod) {
        preferencje.edit().putString("jezyk_aplikacji", kod).apply();
    }

    private void ustawLocale(String kod) {
        Locale locale = new Locale(kod);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void inicjalizujWidoki() {
        spinnerJezyki = findViewById(R.id.spinnerJezyki);
        tvWybierzJezyk = findViewById(R.id.tvWybierzJezyk);
        btnGotowy = findViewById(R.id.btnGotowy);
    }

    private void przygotujMapeJezykow() {
        mapaJezykow.put("Polski", new Locale("pl"));
        mapaJezykow.put("English", new Locale("en"));
        mapaJezykow.put("Español", new Locale("es"));
    }

    private void ustawSpinner() {
        List<String> nazwyJezykow = new ArrayList<>(mapaJezykow.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nazwyJezykow);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJezyki.setAdapter(adapter);

        String aktualnaNazwa = pobierzNazweJezyka(wybranyKodJezyka);
        int pozycja = nazwyJezykow.indexOf(aktualnaNazwa);
        if (pozycja >= 0) spinnerJezyki.setSelection(pozycja);

        spinnerJezyki.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String nazwa = parent.getItemAtPosition(pos).toString();
                Locale locale = mapaJezykow.get(nazwa);
                if (locale != null) {
                    wybranyKodJezyka = locale.getLanguage();
                    ustawLocale(wybranyKodJezyka);
                    odswiezTekstyInterfejsu();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String pobierzNazweJezyka(String kod) {
        for (HashMap.Entry<String, Locale> entry : mapaJezykow.entrySet()) {
            if (entry.getValue().getLanguage().equals(kod)) {
                return entry.getKey();
            }
        }
        return "Polski";
    }

    private void odswiezTekstyInterfejsu() {
        tvWybierzJezyk.setText(R.string.wybierz_jezyk);
        btnGotowy.setText(R.string.gotowy);
    }

    private void ustawPrzyciskGotowy() {
        btnGotowy.setOnClickListener(v -> {
            zapiszJezyk(wybranyKodJezyka);
            Intent intencja = new Intent(LanguageSelectionActivity.this, MainActivity.class);
            startActivity(intencja);
            finish();
        });
    }
}