//package com.example.hiraganaandkatakana.WyborPoziomu;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.graphics.Color;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.hiraganaandkatakana.Litery.LiteryHiragana;
//import com.example.hiraganaandkatakana.R;
//
//public class WyborPoziomuHiragana extends AppCompatActivity {
//
//    private Button btnPodstawowe, btnDakuten, btnKombinowane, buttonStart;
//    private boolean p = false, d = false, k = false;
//    private ImageButton Powrot;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wybor_poziomu_hiragana);
//
//        btnPodstawowe = findViewById(R.id.btnPodstawowe);
//        btnDakuten = findViewById(R.id.btnDakuten);
//        btnKombinowane = findViewById(R.id.btnKombinowane);
//        buttonStart = findViewById(R.id.buttonStart);
//
//        btnPodstawowe.setOnClickListener(v -> {
//            p = !p;
//            btnPodstawowe.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
//                    p ? Color.parseColor("#FFE0B2") : Color.parseColor("#FFFFFF")
//            ));
//        });
//
//        btnDakuten.setOnClickListener(v -> {
//            d = !d;
//            btnDakuten.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
//                    d ? Color.parseColor("#FFE0B2") : Color.parseColor("#FFFFFF")
//            ));
//        });
//
//        btnKombinowane.setOnClickListener(v -> {
//            k = !k;
//            btnKombinowane.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
//                    k ? Color.parseColor("#FFE0B2") : Color.parseColor("#FFFFFF")
//            ));
//        });
//
//        buttonStart.setOnClickListener(v -> {
//            Intent i = new Intent(this, LiteryHiragana.class);
//            i.putExtra("PODSTAWOWE", p);
//            i.putExtra("DAKUTEN", d);
//            i.putExtra("KOMBINOWANE", k);
//            startActivity(i);
//        });
//        Powrot = findViewById(R.id.buttonBack);
//        Powrot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//}
