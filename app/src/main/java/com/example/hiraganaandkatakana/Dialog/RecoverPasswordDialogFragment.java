package com.example.hiraganaandkatakana.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecoverPasswordDialogFragment extends DialogFragment {

    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private TextView tekstBladEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_recover_password, null);

        EditText poleEmail = view.findViewById(R.id.poleEmailRecover);
        Button przyciskWyslij = view.findViewById(R.id.przyciskWyslij);
        tekstBladEmail = view.findViewById(R.id.tekstBladEmail);

        przyciskWyslij.setOnClickListener(v -> {
            String email = poleEmail.getText().toString().trim();

            if (email.isEmpty()) {
                tekstBladEmail.setText(R.string.nie_moze_byc_pusty);
                tekstBladEmail.setTextColor(getResources().getColor(R.color.tekst_blad));
                tekstBladEmail.setVisibility(View.VISIBLE);
                return;
            }

            tekstBladEmail.setVisibility(View.GONE);

            bazaDanych.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (!querySnapshot.isEmpty()) {
                            autoryzacja.sendPasswordResetEmail(email)
                                    .addOnSuccessListener(aVoid -> {
                                        dismiss();
                                        new LoginDialogFragment().show(getParentFragmentManager(), "logowanie");
                                    })
                                    .addOnFailureListener(e -> {
                                        tekstBladEmail.setText("Błąd: " + e.getMessage());
                                        tekstBladEmail.setTextColor(getResources().getColor(R.color.tekst_blad));
                                        tekstBladEmail.setVisibility(View.VISIBLE);
                                    });
                        } else {
                            tekstBladEmail.setText(R.string.nie_ma_emaila);
                            tekstBladEmail.setTextColor(getResources().getColor(R.color.tekst_blad));
                            tekstBladEmail.setVisibility(View.VISIBLE);
                        }
                    })
                    .addOnFailureListener(e -> {
                        tekstBladEmail.setText("Błąd sprawdzania emaila: " + e.getMessage());
                        tekstBladEmail.setTextColor(getResources().getColor(R.color.tekst_blad));
                        tekstBladEmail.setVisibility(View.VISIBLE);
                    });
        });

        builder.setView(view);
        return builder.create();
    }
}