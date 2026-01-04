package com.example.hiraganaandkatakana;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class Utils {
    public static void ustawJezyk(Context context, String jezyk) {
        Locale locale = new Locale(jezyk);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
 // to jest akurat z ai, bo nie mam pojecia jak narpawic blad z tlumaceniem, zobaczy sie pozniej czy jakos inaczej sie nie da
    }
}