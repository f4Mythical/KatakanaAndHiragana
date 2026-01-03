package com.example.hiraganaandkatakana;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import java.util.Locale;

public class Aplikacja extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences prefs = getSharedPreferences("ustawienia_aplikacji", MODE_PRIVATE);
        String kod = prefs.getString("jezyk_aplikacji", "pl");
        Locale.setDefault(new Locale(kod));
        Configuration config = getResources().getConfiguration();
        config.setLocale(new Locale(kod));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}