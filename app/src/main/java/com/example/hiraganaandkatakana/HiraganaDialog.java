package com.example.hiraganaandkatakana;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HiraganaDialog extends AppCompatActivity {

    private final String FINAL_TEXT = "きのう、がっこうに　いました。";
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private Button button;
    private ImageButton Cofnij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);
        Cofnij = findViewById(R.id.buttonBack);
        button = findViewById(R.id.button);
        buttonBack();

        handler.post(letterAdder);
    }

    private final Runnable letterAdder = new Runnable() {
        private long DELAY_MS = 300;

        @Override
        public void run() {
            if (currentIndex <= FINAL_TEXT.length()) {
                button.setText(FINAL_TEXT.substring(0, currentIndex));
                currentIndex++;
                handler.postDelayed(this, DELAY_MS);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(letterAdder);
    }

    private void buttonBack() {
        Cofnij.setOnClickListener(v -> finish());
    }
}