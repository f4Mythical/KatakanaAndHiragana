package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

public class GeneralSettingsDialogFragment extends DialogFragment {
    private static final String PREFS_NAME = "ustawienia_aplikacji";
    private static final String KEY_TRYB = "tryb_aplikacji";
    private static final String TRYB_JASNY = "jasny";
    private static final String TRYB_CIEMNY = "ciemny";

    private Button buttonJasny;
    private Button buttonCiemny;
    private Button buttonJezyk;
    private Button buttonGotowy;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_general_settings, container, false);

        prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        buttonJasny = view.findViewById(R.id.buttonJasny);
        buttonCiemny = view.findViewById(R.id.buttonCiemny);
        buttonJezyk = view.findViewById(R.id.buttonZmienJezyk);
        buttonGotowy = view.findViewById(R.id.buttonGotowy);

        String tryb = prefs.getString(KEY_TRYB, TRYB_JASNY);
        updateButtonColors(tryb);

        buttonJasny.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_TRYB, TRYB_JASNY);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            updateButtonColors(TRYB_JASNY);
        });

        buttonCiemny.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_TRYB, TRYB_CIEMNY);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            updateButtonColors(TRYB_CIEMNY);
        });

        buttonJezyk.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LanguageSelectionActivity.class);
            startActivity(intent);
            dismiss();
        });

        buttonGotowy.setOnClickListener(v -> dismiss());

        return view;
    }

    private void updateButtonColors(String aktualnyTryb) {
        if (TRYB_JASNY.equals(aktualnyTryb)) {
            buttonJasny.setBackgroundTintList(getResources().getColorStateList(R.color.pomaranczowy_zaznaczony));
            buttonCiemny.setBackgroundTintList(getResources().getColorStateList(R.color.szary_niezaznaczony));
        } else {
            buttonJasny.setBackgroundTintList(getResources().getColorStateList(R.color.szary_niezaznaczony));
            buttonCiemny.setBackgroundTintList(getResources().getColorStateList(R.color.pomaranczowy_zaznaczony));
        }
    }
}