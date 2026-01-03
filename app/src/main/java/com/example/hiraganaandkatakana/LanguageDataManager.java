package com.example.hiraganaandkatakana;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LanguageDataManager {
    private static LanguageDataManager instancja;
    private HashMap<String, WordData> slownik = new HashMap<>();

    private LanguageDataManager() {}

    public static synchronized LanguageDataManager getInstance() {
        if (instancja == null) instancja = new LanguageDataManager();
        return instancja;
    }

    public void wczytajDaneZCSV(Context kontekst) {
        slownik.clear();
        try {
            BufferedReader czytnik = new BufferedReader(new InputStreamReader(kontekst.getAssets().open("hiragana_dane.csv")));
            czytnik.readLine();
            String linia;
            while ((linia = czytnik.readLine()) != null) {
                String[] dane = linia.split(",");
                if (dane.length >= 6) {
                    WordData slowo = new WordData(dane[0], dane[1], dane[2], dane[3], dane[4], dane[5]);
                    slownik.put(dane[0], slowo);
                }
            }
            czytnik.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WordData pobierzSlowo(String hiragana) {
        return slownik.get(hiragana);
    }

    public HashMap<String, WordData> pobierzSlownik() {
        return slownik;
    }
}