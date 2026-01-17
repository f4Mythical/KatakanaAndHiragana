package com.example.hiraganaandkatakana.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.MainActivity;
import com.example.hiraganaandkatakana.R;
import com.example.hiraganaandkatakana.SessionTimer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileDialogFragment extends DialogFragment {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateTimerRunnable;
    private TextView sessionTextView;
    private OnProfileDialogListener mListener;

    public interface OnProfileDialogListener {
        void onUserLoggedOut();
    }

    public static UserProfileDialogFragment newInstance(boolean czyMaPremium) {
        UserProfileDialogFragment fragment = new UserProfileDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("czyMaPremium", czyMaPremium);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        try {
            mListener = (OnProfileDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnProfileDialogListener");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_user_profile, null);

        TextView emailTextView     = view.findViewById(R.id.emailTextView);
        TextView premiumTextView   = view.findViewById(R.id.premiumTextView);
        sessionTextView            = view.findViewById(R.id.sessionTextView);
        TextView settingsText      = view.findViewById(R.id.settingsText);
        TextView logoutText        = view.findViewById(R.id.logoutText);
        TextView deleteAccountText = view.findViewById(R.id.deleteAccountText);
        deleteAccountText.setOnClickListener(v -> showDeleteAccountConfirmationDialog());
        FirebaseUser uzytkownik = FirebaseAuth.getInstance().getCurrentUser();
        if (uzytkownik != null) {
            emailTextView.setText(getString(R.string.email) + ": " + uzytkownik.getEmail());
            boolean premium = requireArguments().getBoolean("czyMaPremium");
            premiumTextView.setText(getString(R.string.premiumBol) + ": " +
                    (premium ? getString(R.string.premiumTrue) : getString(R.string.premiumFalse)));
        }

        settingsText.setOnClickListener(v -> {
            dismiss();
            GeneralSettingsDialogFragment settingsDialog = new GeneralSettingsDialogFragment();
            settingsDialog.show(requireActivity().getSupportFragmentManager(), "GeneralSettingsDialogFragment");
        });

        logoutText.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            if (mListener != null && !(requireActivity() instanceof MainActivity)) {
                mListener.onUserLoggedOut();
            }
            dismiss();
        });

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        builder.setView(view);
        Dialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        startTimer();
        return dialog;
    }

    private void startTimer() {
        updateTimerRunnable = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {
                long elapsed = SessionTimer.getInstance().getElapsedSeconds();
                int h = (int)(elapsed / 3600);
                int m = (int)((elapsed % 3600) / 60);
                int s = (int)(elapsed % 60);
                sessionTextView.setText(getString(R.string.czas) + " " +
                        (h > 0 ? String.format("%02d:%02d:%02d", h, m, s)
                                : String.format("%02d:%02d", m, s)));
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateTimerRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (updateTimerRunnable != null) handler.removeCallbacks(updateTimerRunnable);
    }
    private void showDeleteAccountConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.usun_konto)
                .setMessage(R.string.jestes_pewny)
                .setPositiveButton(R.string.tak, (dialog, which) -> deleteUserAccount())
                .setNegativeButton(R.string.nie, null)
                .show();
    }


        private void deleteUserAccount() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;

            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            ReauthDialogFragment reauthDialog = new ReauthDialogFragment();
            reauthDialog.setCallback(() -> {
                db.collection("users").document(uid)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            user.delete()
                                    .addOnSuccessListener(bVoid -> {
                                        if (mListener != null) mListener.onUserLoggedOut();
                                        dismiss();
                                    })
                                    .addOnFailureListener(e -> {
                                        new MaterialAlertDialogBuilder(requireActivity())
                                                .setTitle("Błąd")
                                                .setMessage("Nie udało się usunąć konta Firebase: " + e.getLocalizedMessage())
                                                .setPositiveButton("OK", null)
                                                .show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            new MaterialAlertDialogBuilder(requireActivity())
                                    .setTitle("Błąd")
                                    .setMessage("Nie udało się usunąć danych użytkownika: " + e.getLocalizedMessage())
                                    .setPositiveButton("OK", null)
                                    .show();
                        });
            });
            reauthDialog.show(getParentFragmentManager(), "reauth");
        }
}


