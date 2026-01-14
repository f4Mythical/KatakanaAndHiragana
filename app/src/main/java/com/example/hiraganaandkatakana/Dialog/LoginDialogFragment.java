package com.example.hiraganaandkatakana.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.AuthCallback;
import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginDialogFragment extends DialogFragment {

    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private TextView tekstBladLogowanie;

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
        TextView tekstZapomnialemHasla = view.findViewById(R.id.tekstZapomnialemHasla);
        tekstBladLogowanie = view.findViewById(R.id.tekstBladLogowanie);

        przyciskLogowanie.setOnClickListener(v -> {
            String emailUzytkownika = poleEmail.getText().toString().trim();
            String hasloUzytkownika = poleHaslo.getText().toString().trim();

            if (emailUzytkownika.isEmpty() || hasloUzytkownika.isEmpty()) return;

            tekstBladLogowanie.setVisibility(View.GONE);

            autoryzacja.signInWithEmailAndPassword(emailUzytkownika, hasloUzytkownika)
                    .addOnSuccessListener(authResult -> {
                        if (!isAdded()) return;
                        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
                        if (uzytkownik == null) return;

                        String uid = uzytkownik.getUid();
                        long teraz = System.currentTimeMillis();

                        Map<String, Object> daneStart = new HashMap<>();
                        daneStart.put("sessionStart", teraz);

                        bazaDanych.collection("users").document(uid)
                                .update(daneStart)
                                .addOnFailureListener(e -> {
                                    if (!isAdded()) return;
                                    if (e.getMessage() != null && e.getMessage().contains("NO_DOCUMENT")) {
                                        bazaDanych.collection("users").document(uid)
                                                .set(daneStart);
                                    }
                                });

                        bazaDanych.collection("users").document(uid)
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!isAdded()) return;
                                    if (documentSnapshot.exists()) {
                                        String jezykZFirestore = documentSnapshot.getString("jezyk");
                                        if (jezykZFirestore != null) {
                                            SharedPreferences prefs = requireActivity()
                                                    .getSharedPreferences("ustawienia_aplikacji", requireContext().MODE_PRIVATE);
                                            String aktualnyJezyk = prefs.getString("jezyk_aplikacji", "en");
                                            if (!jezykZFirestore.equals(aktualnyJezyk)) {
                                                prefs.edit().putString("jezyk_aplikacji", jezykZFirestore).apply();
                                                Utils.ustawJezyk(requireActivity(), jezykZFirestore);
                                                requireActivity().recreate();
                                            }
                                        }
                                    }
                                });

                        if (getActivity() instanceof AuthCallback) {
                            ((AuthCallback) getActivity()).onUserAuthenticated();
                        }
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        if (isAdded()) tekstBladLogowanie.setVisibility(View.VISIBLE);
                    });
        });

        tekstRejestracja.setOnClickListener(v -> {
            dismiss();
            new RegisterDialogFragment().show(getParentFragmentManager(), "rejestracja");
        });

        tekstZapomnialemHasla.setOnClickListener(v -> {
            dismiss();
            new RecoverPasswordDialogFragment().show(getParentFragmentManager(), "odzyskiwanieHasla");
        });

        builder.setView(view);
        return builder.create();
    }
}