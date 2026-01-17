package com.example.hiraganaandkatakana;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UstawienieKlawiatury {
    public interface KeyboardCallbacks {
        void onCheckClicked();
        void onRandomClicked();
    }

    public static void setupKeyboard(Activity activity, TextView outputView, StringBuilder input, KeyboardCallbacks callbacks) {
        Button buttonQ = activity.findViewById(R.id.buttonQ);
        Button buttonW = activity.findViewById(R.id.buttonW);
        Button buttonE = activity.findViewById(R.id.buttonE);
        Button buttonR = activity.findViewById(R.id.buttonR);
        Button buttonT = activity.findViewById(R.id.buttonT);
        Button buttonY = activity.findViewById(R.id.buttonY);
        Button buttonU = activity.findViewById(R.id.buttonU);
        Button buttonI = activity.findViewById(R.id.buttonI);
        Button buttonO = activity.findViewById(R.id.buttonO);
        Button buttonP = activity.findViewById(R.id.buttonP);

        Button buttonA = activity.findViewById(R.id.buttonA);
        Button buttonS = activity.findViewById(R.id.buttonS);
        Button buttonD = activity.findViewById(R.id.buttonD);
        Button buttonF = activity.findViewById(R.id.buttonF);
        Button buttonG = activity.findViewById(R.id.buttonG);
        Button buttonH = activity.findViewById(R.id.buttonH);
        Button buttonJ = activity.findViewById(R.id.buttonJ);
        Button buttonK = activity.findViewById(R.id.buttonK);
        Button buttonL = activity.findViewById(R.id.buttonL);

        Button buttonZ = activity.findViewById(R.id.buttonZ);
        Button buttonX = activity.findViewById(R.id.buttonX);
        Button buttonC = activity.findViewById(R.id.buttonC);
        Button buttonV = activity.findViewById(R.id.buttonV);
        Button buttonB = activity.findViewById(R.id.buttonB);
        Button buttonN = activity.findViewById(R.id.buttonN);
        Button buttonM = activity.findViewById(R.id.buttonM);

        buttonQ.setOnClickListener(v -> addCharacter(outputView, input, "q"));
        buttonW.setOnClickListener(v -> addCharacter(outputView, input, "w"));
        buttonE.setOnClickListener(v -> addCharacter(outputView, input, "e"));
        buttonR.setOnClickListener(v -> addCharacter(outputView, input, "r"));
        buttonT.setOnClickListener(v -> addCharacter(outputView, input, "t"));
        buttonY.setOnClickListener(v -> addCharacter(outputView, input, "y"));
        buttonU.setOnClickListener(v -> addCharacter(outputView, input, "u"));
        buttonI.setOnClickListener(v -> addCharacter(outputView, input, "i"));
        buttonO.setOnClickListener(v -> addCharacter(outputView, input, "o"));
        buttonP.setOnClickListener(v -> addCharacter(outputView, input, "p"));

        buttonA.setOnClickListener(v -> addCharacter(outputView, input, "a"));
        buttonS.setOnClickListener(v -> addCharacter(outputView, input, "s"));
        buttonD.setOnClickListener(v -> addCharacter(outputView, input, "d"));
        buttonF.setOnClickListener(v -> addCharacter(outputView, input, "f"));
        buttonG.setOnClickListener(v -> addCharacter(outputView, input, "g"));
        buttonH.setOnClickListener(v -> addCharacter(outputView, input, "h"));
        buttonJ.setOnClickListener(v -> addCharacter(outputView, input, "j"));
        buttonK.setOnClickListener(v -> addCharacter(outputView, input, "k"));
        buttonL.setOnClickListener(v -> addCharacter(outputView, input, "l"));

        buttonZ.setOnClickListener(v -> addCharacter(outputView, input, "z"));
        buttonX.setOnClickListener(v -> addCharacter(outputView, input, "x"));
        buttonC.setOnClickListener(v -> addCharacter(outputView, input, "c"));
        buttonV.setOnClickListener(v -> addCharacter(outputView, input, "v"));
        buttonB.setOnClickListener(v -> addCharacter(outputView, input, "b"));
        buttonN.setOnClickListener(v -> addCharacter(outputView, input, "n"));
        buttonM.setOnClickListener(v -> addCharacter(outputView, input, "m"));

        Button buttonCheck = activity.findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(v -> {
            if (callbacks != null) callbacks.onCheckClicked();
        });

        Button space = activity.findViewById(R.id.buttonSpace);
        space.setOnClickListener(v -> addCharacter(outputView, input, " "));

        View delete = activity.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                outputView.setText(input.toString());
            }
        });

        View buttonRandom = activity.findViewById(R.id.buttonRandom);
        if (buttonRandom != null && callbacks != null) {
            buttonRandom.setOnClickListener(v -> callbacks.onRandomClicked());
        }
    }

    private static void addCharacter(TextView outputView, StringBuilder input, String character) {
        input.append(character);
        outputView.setText(input.toString());
    }
}