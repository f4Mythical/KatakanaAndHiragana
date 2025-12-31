package com.example.hiraganaandkatakana;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginDialogFragment extends DialogFragment {

    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_login, null);

        EditText poleEmail = view.findViewById(R.id.poleEmail);
        EditText poleHaslo = view.findViewById(R.id.poleHaslo);
        Button przyciskLogowanie = view.findViewById(R.id.przyciskLogowanie);
        TextView tekstRejestracja = view.findViewById(R.id.tekstRejestracja);

        przyciskLogowanie.setOnClickListener(v -> {
            String emailUzytkownika = poleEmail.getText().toString().trim();
            String hasloUzytkownika = poleHaslo.getText().toString().trim();

            if (emailUzytkownika.isEmpty() || hasloUzytkownika.isEmpty()) return;

            autoryzacja.signInWithEmailAndPassword(emailUzytkownika, hasloUzytkownika)
                    .addOnSuccessListener(authResult -> {
                        if (getActivity() instanceof AuthCallback) {
                            ((AuthCallback) getActivity()).onUserAuthenticated();
                        }
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                    });
        });

        tekstRejestracja.setOnClickListener(v -> {
            dismiss();
            new RegisterDialogFragment().show(getParentFragmentManager(), "rejestracja");
        });

        builder.setView(view);
        return builder.create();
    }
}