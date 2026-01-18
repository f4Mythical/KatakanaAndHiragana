package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Test extends AppCompatActivity {

    private final String[] OPTIONS = {"Hiragana", "Katakana"};
    private Intent[] intents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probny);

        View root = findViewById(R.id.main);
        root.setScaleX(0.85f);
        root.setScaleY(0.85f);
        root.setAlpha(0f);
        root.animate()
                .scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(600)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        intents = new Intent[]{
                new Intent(this, CharacterSelectionActivity.class),
                new Intent(this, HiraganaDialog.class)
        };

        NumberPicker picker = findViewById(R.id.picker);
        picker.setMinValue(0);
        picker.setMaxValue(OPTIONS.length - 1);
        picker.setDisplayedValues(OPTIONS);

        findViewById(R.id.btnOk).setOnClickListener(v ->
                startActivity(intents[picker.getValue()])
        );
    }
}