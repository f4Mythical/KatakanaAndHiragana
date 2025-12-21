package com.example.hiraganaandkatakana;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class RegisterDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(requireActivity());

        View view = requireActivity()
                .getLayoutInflater()
                .inflate(R.layout.dialog_register, null);

        Button btnRegister = view.findViewById(R.id.btnRegisterDialog);
        TextView tvLogin = view.findViewById(R.id.tvRegister);

        btnRegister.setOnClickListener(v -> {
            // TODO: Firebase createUserWithEmailAndPassword
        });

        tvLogin.setOnClickListener(v -> {
            dismiss();
            new LoginDialogFragment()
                    .show(getParentFragmentManager(), "loginDialog");
        });

        builder.setView(view);
        return builder.create();
    }
}
