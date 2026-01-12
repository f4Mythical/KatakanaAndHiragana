package com.example.hiraganaandkatakana.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.AuthCallback;
import com.example.hiraganaandkatakana.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterDialogFragment extends DialogFragment {

    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private EditText poleEmail, poleHaslo;
    private TextView tekstBladEmail, tekstPodpowiedzHaslo;
    private Button przyciskRejestracja;
    private TextView tekstLogowanie;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_register, null);

        poleEmail = view.findViewById(R.id.poleEmail);
        poleHaslo = view.findViewById(R.id.poleHaslo);
        przyciskRejestracja = view.findViewById(R.id.przyciskRejestracja);
        tekstLogowanie = view.findViewById(R.id.tekstLogowanie);
        tekstBladEmail = view.findViewById(R.id.tekstBladEmail);
        tekstPodpowiedzHaslo = view.findViewById(R.id.tekstPodpowiedzHaslo);

        ustawWeryfikacjeEmail();
        ustawWeryfikacjeHasla();

        przyciskRejestracja.setOnClickListener(v -> {
            String emailUzytkownika = poleEmail.getText().toString().trim();
            String hasloUzytkownika = poleHaslo.getText().toString().trim();

            if (emailUzytkownika.isEmpty() || hasloUzytkownika.isEmpty()) return;

            autoryzacja.createUserWithEmailAndPassword(emailUzytkownika, hasloUzytkownika)
                    .addOnSuccessListener(authResult -> {
                        String identyfikatorUzytkownika = autoryzacja.getCurrentUser().getUid();

                        SharedPreferences prefs = requireActivity().getSharedPreferences("ustawienia_aplikacji", requireContext().MODE_PRIVATE);
                        String jezyk = prefs.getString("jezyk_aplikacji", "en");

                        Map<String, Object> daneUzytkownika = new HashMap<>();
                        daneUzytkownika.put("email", emailUzytkownika);
                        daneUzytkownika.put("czyMaPremium", false);
                        daneUzytkownika.put("jezyk", jezyk);

                        bazaDanych.collection("users").document(identyfikatorUzytkownika).set(daneUzytkownika)
                                .addOnSuccessListener(unused -> {
                                    if (getActivity() instanceof AuthCallback) {
                                        ((AuthCallback) getActivity()).onUserAuthenticated();
                                    }
                                    dismiss();
                                })
                                .addOnFailureListener(e -> {
                                    tekstBladEmail.setVisibility(View.VISIBLE);
                                });
                    })
                    .addOnFailureListener(e -> {
                        tekstBladEmail.setVisibility(View.VISIBLE);
                    });
        });

        tekstLogowanie.setOnClickListener(v -> {
            dismiss();
            new LoginDialogFragment().show(getParentFragmentManager(), "loginDialog");
        });

        builder.setView(view);
        return builder.create();
    }

    private void ustawWeryfikacjeEmail() {
        poleEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String emailUzytkownika = s.toString().trim();
                if (emailUzytkownika.isEmpty()) {
                    tekstBladEmail.setVisibility(View.GONE);
                    return;
                }
                sprawdzCzyEmailIstnieje(emailUzytkownika);
            }
        });
    }

    private void sprawdzCzyEmailIstnieje(String emailUzytkownika) {
        autoryzacja.fetchSignInMethodsForEmail(emailUzytkownika)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        boolean czyIstnieje = task.getResult().getSignInMethods() != null
                                && !task.getResult().getSignInMethods().isEmpty();
                        tekstBladEmail.setVisibility(czyIstnieje ? View.VISIBLE : View.GONE);
                    }
                });
    }

    private void ustawWeryfikacjeHasla() {
        poleHaslo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tekstPodpowiedzHaslo.setText(sprawdzPoprawnoscHasla(s.toString()));
            }
        });
    }

    private String sprawdzPoprawnoscHasla(String hasloUzytkownika) {
        StringBuilder podpowiedz = new StringBuilder();
        boolean ma6Znakow = hasloUzytkownika.length() >= 6;
        boolean maCyfre = false;
        boolean maWielkaLitere = false;
        boolean maMalaLitere = false;

        String cyfry = "1234567890";
        for (char znak : hasloUzytkownika.toCharArray()) {
            if (cyfry.contains(String.valueOf(znak))) maCyfre = true;
            if (Character.isUpperCase(znak)) maWielkaLitere = true;
            if (Character.isLowerCase(znak)) maMalaLitere = true;
        }

        podpowiedz.append(ma6Znakow ? "✅" : "❌").append(getString(R.string.znaki6));
        podpowiedz.append(maCyfre ? "✅" : "❌").append(getString(R.string.cyfra));
        podpowiedz.append(maWielkaLitere ? "✅" : "❌").append(getString(R.string.wielka));
        podpowiedz.append(maMalaLitere ? "✅" : "❌").append(getString(R.string.mala));
        return podpowiedz.toString();
    }
}