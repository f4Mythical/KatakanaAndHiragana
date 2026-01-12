package com.example.hiraganaandkatakana.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hiraganaandkatakana.MainActivity;
import com.example.hiraganaandkatakana.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileDialogFragment extends DialogFragment {

    private FirebaseAuth autoryzacja;
    private FirebaseFirestore bazaDanych;
    private long czasZalogowania;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateTimerRunnable;
    private TextView sessionTextView;
    private boolean czyMaPremium;
    private OnProfileDialogListener mListener;

    public interface OnProfileDialogListener {
        void onUserLoggedOut();
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

    public static UserProfileDialogFragment newInstance(long czasZalogowania, boolean czyMaPremium) {
        UserProfileDialogFragment fragment = new UserProfileDialogFragment();
        Bundle args = new Bundle();
        args.putLong("czasZalogowania", czasZalogowania);
        args.putBoolean("czyMaPremium", czyMaPremium);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            czasZalogowania = getArguments().getLong("czasZalogowania");
            czyMaPremium = getArguments().getBoolean("czyMaPremium");
        } else {
            czasZalogowania = System.currentTimeMillis();
            czyMaPremium = false;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        autoryzacja = FirebaseAuth.getInstance();
        bazaDanych = FirebaseFirestore.getInstance();

        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_user_profile, null);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView premiumTextView = view.findViewById(R.id.premiumTextView);
        sessionTextView = view.findViewById(R.id.sessionTextView);
        TextView settingsText = view.findViewById(R.id.settingsText);
        TextView logoutText = view.findViewById(R.id.logoutText);

        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
        if (uzytkownik != null) {
            String emailLabel = getString(R.string.email) + ": " + uzytkownik.getEmail();
            emailTextView.setText(emailLabel);

            String premiumLabel = getString(R.string.premiumBol) + ": " +
                    (czyMaPremium ? getString(R.string.premiumTrue) : getString(R.string.premiumFalse));
            premiumTextView.setText(premiumLabel);
        }

        startTimer();

        settingsText.setOnClickListener(v -> {
            dismiss();
            GeneralSettingsDialogFragment settingsDialog = new GeneralSettingsDialogFragment();
            settingsDialog.show(requireActivity().getSupportFragmentManager(), "GeneralSettingsDialogFragment");
        });

        logoutText.setOnClickListener(v -> {
            autoryzacja.signOut();

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

        return dialog;
    }

    private void startTimer() {
        updateTimerRunnable = new Runnable() {
            @Override
            public void run() {
                aktualizujCzasSesji();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateTimerRunnable);
    }

    private void aktualizujCzasSesji() {
        if (sessionTextView == null) return;

        long czasTrwania = System.currentTimeMillis() - czasZalogowania;
        int godziny = (int) (czasTrwania / 3600000);
        int minuty = (int) ((czasTrwania % 3600000) / 60000);
        int sekundy = (int) ((czasTrwania % 60000) / 1000);

        String czasLabel = getString(R.string.czas) + " ";
        if (godziny > 0) {
            sessionTextView.setText(czasLabel + String.format("%02d:%02d:%02d", godziny, minuty, sekundy));
        } else {
            sessionTextView.setText(czasLabel + String.format("%02d:%02d", minuty, sekundy));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (updateTimerRunnable != null) {
            handler.removeCallbacks(updateTimerRunnable);
        }
    }
}