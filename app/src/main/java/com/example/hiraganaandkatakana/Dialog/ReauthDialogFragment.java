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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReauthDialogFragment extends DialogFragment {

    public interface ReauthCallback {
        void onReauthSuccess();
    }

    private ReauthCallback callback;

    public void setCallback(ReauthCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_reauth, null);

        EditText poleHaslo = view.findViewById(R.id.poleHasloReauth);
        Button przyciskPotwierdz = view.findViewById(R.id.przyciskPotwierdzReauth);
        TextView tekstBlad = view.findViewById(R.id.tekstBladReauth);

        przyciskPotwierdz.setOnClickListener(v -> {
            String haslo = poleHaslo.getText().toString().trim();
            if (haslo.isEmpty()) {
                tekstBlad.setText(R.string.haslo_puste);
                tekstBlad.setVisibility(View.VISIBLE);
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null || user.getEmail() == null) {
                tekstBlad.setText(R.string.blad_uzytkownika);
                tekstBlad.setVisibility(View.VISIBLE);
                return;
            }

            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), haslo);

            user.reauthenticate(credential)
                    .addOnSuccessListener(a -> {
                        if (callback != null) callback.onReauthSuccess();
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        tekstBlad.setText(R.string.zle_haslo);
                        tekstBlad.setVisibility(View.VISIBLE);
                    });
        });

        builder.setView(view);
        return builder.create();
    }
}