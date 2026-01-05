//package com.example.hiraganaandkatakana.HiraganaBasic;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.hiraganaandkatakana.R;
//
//public class HiraganaBasicWords extends AppCompatActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.hiragana_basic_words);
//    }
//    private void setupKeyboard() {
//        Button buttonQ = findViewById(R.id.buttonQ);
//        Button buttonW = findViewById(R.id.buttonW);
//        Button buttonE = findViewById(R.id.buttonE);
//        Button buttonR = findViewById(R.id.buttonR);
//        Button buttonT = findViewById(R.id.buttonT);
//        Button buttonY = findViewById(R.id.buttonY);
//        Button buttonU = findViewById(R.id.buttonU);
//        Button buttonI = findViewById(R.id.buttonI);
//        Button buttonO = findViewById(R.id.buttonO);
//        Button buttonP = findViewById(R.id.buttonP);
//
//        Button buttonA = findViewById(R.id.buttonA);
//        Button buttonS = findViewById(R.id.buttonS);
//        Button buttonD = findViewById(R.id.buttonD);
//        Button buttonF = findViewById(R.id.buttonF);
//        Button buttonG = findViewById(R.id.buttonG);
//        Button buttonH = findViewById(R.id.buttonH);
//        Button buttonJ = findViewById(R.id.buttonJ);
//        Button buttonK = findViewById(R.id.buttonK);
//        Button buttonL = findViewById(R.id.buttonL);
//
//        Button buttonZ = findViewById(R.id.buttonZ);
//        Button buttonX = findViewById(R.id.buttonX);
//        Button buttonC = findViewById(R.id.buttonC);
//        Button buttonV = findViewById(R.id.buttonV);
//        Button buttonB = findViewById(R.id.buttonB);
//        Button buttonN = findViewById(R.id.buttonN);
//        Button buttonM = findViewById(R.id.buttonM);
//
//        buttonQ.setOnClickListener(v -> addCharacter("Q"));
//        buttonW.setOnClickListener(v -> addCharacter("W"));
//        buttonE.setOnClickListener(v -> addCharacter("E"));
//        buttonR.setOnClickListener(v -> addCharacter("R"));
//        buttonT.setOnClickListener(v -> addCharacter("T"));
//        buttonY.setOnClickListener(v -> addCharacter("Y"));
//        buttonU.setOnClickListener(v -> addCharacter("U"));
//        buttonI.setOnClickListener(v -> addCharacter("I"));
//        buttonO.setOnClickListener(v -> addCharacter("O"));
//        buttonP.setOnClickListener(v -> addCharacter("P"));
//
//
//        buttonA.setOnClickListener(v -> addCharacter("A"));
//        buttonS.setOnClickListener(v -> addCharacter("S"));
//        buttonD.setOnClickListener(v -> addCharacter("D"));
//        buttonF.setOnClickListener(v -> addCharacter("F"));
//        buttonG.setOnClickListener(v -> addCharacter("G"));
//        buttonH.setOnClickListener(v -> addCharacter("H"));
//        buttonJ.setOnClickListener(v -> addCharacter("J"));
//        buttonK.setOnClickListener(v -> addCharacter("K"));
//        buttonL.setOnClickListener(v -> addCharacter("L"));
//
//
//        buttonZ.setOnClickListener(v -> addCharacter("Z"));
//        buttonX.setOnClickListener(v -> addCharacter("X"));
//        buttonC.setOnClickListener(v -> addCharacter("C"));
//        buttonV.setOnClickListener(v -> addCharacter("V"));
//        buttonB.setOnClickListener(v -> addCharacter("B"));
//        buttonN.setOnClickListener(v -> addCharacter("N"));
//        buttonM.setOnClickListener(v -> addCharacter("M"));
//
//        Button buttonCheck = findViewById(R.id.buttonCheck);
//        buttonCheck.setOnClickListener(v -> sprawdzOdpowiedz());
//
//        TextView buttonRandom = findViewById(R.id.buttonRandom);
//        buttonRandom.setOnClickListener(v -> losujInnyZnak());
//
//        Button space = findViewById(R.id.buttonSpace);
//        space.setOnClickListener(v -> addCharacter(" "));
//
//        TextView delete = findViewById(R.id.buttonDelete);
//        delete.setOnClickListener(v -> {
//            if (input.length() > 0) {
//                input.deleteCharAt(input.length() - 1);
//                inputText.setText(input.toString());
//            }
//        });
//    }
//
//    private void addCharacter(String character) {
//        input.append(character);
//        inputText.setText(input.toString());
//    }
//
//}
