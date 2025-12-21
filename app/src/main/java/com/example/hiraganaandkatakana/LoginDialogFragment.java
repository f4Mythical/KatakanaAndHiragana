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

public class LoginDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity()
                .getLayoutInflater()
                .inflate(R.layout.dialog_login, null);

        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);
        Button btnLogin = view.findViewById(R.id.btnLoginDialog);
        TextView tvRegister = view.findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String pass = etPassword.getText().toString();

            // u później Firebase Auth
        });
// TODO ZROBIC FRAGMENT
//        tvRegister.setOnClickListener(v -> {
//            dismiss();
//            new RegisterDialogFragment()
//                    .show(getParentFragmentManager(), "registerDialog");
//        });

        builder.setView(view);
        return builder.create();
    }
}
