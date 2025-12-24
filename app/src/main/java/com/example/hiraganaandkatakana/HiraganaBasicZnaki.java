package com.example.hiraganaandkatakana;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HiraganaBasicZnaki extends AppCompatActivity {

    String[] alfabet = {
            "Q","W","E","R","T","Y","U","I","O","P",
            "A","S","D","F","G","H","J","K","L",
            "Z","X","C","V","B","N","M"
    };

    StringBuilder input = new StringBuilder();
    TextView inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana_basic_znaki);

        ImageButton back = findViewById(R.id.buttonBack);
        back.setOnClickListener(v -> finish());

        inputText = findViewById(R.id.inputText);
        GridLayout keyboard = findViewById(R.id.keyboardContainer);

        keyboard.setColumnCount(10);

        for (String litera : alfabet) {
            Button key = new Button(this);
            key.setText(litera);
            key.setAllCaps(false);
            key.setTextSize(16f);
            key.setTextColor(0xFFE65100);
            key.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(0xFFFFCCBC)
            );

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(
                    GridLayout.UNDEFINED, 1f
            );
            params.setMargins(6, 6, 6, 6);
            key.setLayoutParams(params);

            key.setOnClickListener(v -> {
                input.append(litera);
                inputText.setText(input.toString());
            });

            keyboard.addView(key);
        }

        Button space = findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> {
            input.append(" ");
            inputText.setText(input.toString());
        });

        Button delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                inputText.setText(input.toString());
            }
        });
    }
}
