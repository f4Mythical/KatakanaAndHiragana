package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.hiraganaandkatakana.Dialog.LoginDialogFragment;
import com.example.hiraganaandkatakana.Dialog.UserProfileDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PoczatekHiraganaKatakana extends AppCompatActivity implements AuthCallback,
        UserProfileDialogFragment.OnProfileDialogListener,
        PremiumStatusTracker.OnPremiumStatusChangedListener {

    GridLayout container;
    boolean czyHiragana = true;
    String aktualnyTyp = "PODSTAWOWE";
    private ImageButton przyciskAuth;
    private FirebaseAuth autoryzacja;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FragmentManager fragmentManager;
    private boolean czyMaPremium = false;
    private Button zacznijNauke;
    private PremiumStatusTracker premiumTracker;

    String[][] hiraPodstawowe = {
            {"あ","a"}, {"い","i"}, {"う","u"}, {"え","e"}, {"お","o"},
            {"か","ka"}, {"き","ki"}, {"く","ku"}, {"け","ke"}, {"こ","ko"},
            {"さ","sa"}, {"し","shi"}, {"す","su"}, {"せ","se"}, {"そ","so"},
            {"た","ta"}, {"ち","chi"}, {"つ","tsu"}, {"て","te"}, {"と","to"},
            {"な","na"}, {"に","ni"}, {"ぬ","nu"}, {"ね","ne"}, {"の","no"},
            {"は","ha"}, {"ひ","hi"}, {"ふ","fu"}, {"へ","he"}, {"ほ","ho"},
            {"ま","ma"}, {"み","mi"}, {"む","mu"}, {"め","me"}, {"も","mo"},
            {"や","ya"}, {"   ","  "}, {"ゆ","yu"}, {"   ","  "}, {"よ","yo"},
            {"ら","ra"}, {"り","ri"}, {"る","ru"}, {"れ","re"}, {"ろ","ro"},
            {"わ","wa"}, {"   "," "}, {"   "," "}, {"   "," "}, {"を","wo"},
            {"   ","  "}, {"   ","  "}, {"   ","  "}, {"   ","  "}, {"ん","n"}
    };

    String[][] hiraDakuten = {
            {"が","ga"}, {"ぎ","gi"}, {"ぐ","gu"}, {"げ","ge"}, {"ご","go"},
            {"ざ","za"}, {"じ","ji"}, {"ず","zu"}, {"ぜ","ze"}, {"ぞ","zo"},
            {"だ","da"}, {"ぢ","ji"}, {"づ","zu"}, {"で","de"}, {"ど","do"},
            {"ば","ba"}, {"び","bi"}, {"ぶ","bu"}, {"べ","be"}, {"ぼ","bo"},
            {"ぱ","pa"}, {"ぴ","pi"}, {"ぷ","pu"}, {"ぺ","pe"}, {"ぽ","po"}
    };

    String[][] hiraKombinowane = {
            {"きゃ","kya"}, {" "," "},{"きゅ","kyu"},{" "," "}, {"きょ","kyo"},
            {"ぎゃ","gya"}, {" "," "}, {"ぎゅ","gyu"},{" "," "}, {"ぎょ","gyo"},
            {"しゃ","sha"}, {" "," "}, {"しゅ","shu"},{" "," "}, {"しょ","sho"},
            {"じゃ","ja"},{" "," "}, {"じゅ","ju"},{" "," "}, {"じょ","jo"},
            {"ちゃ","cha"},{" "," "}, {"ちゅ","chu"}, {" "," "},{"ちょ","cho"},
            {"ぢゃ","ja"}, {" "," "},{"ぢゅ","ju"},{" "," "}, {"ぢょ","jo"},
            {"にゃ","nya"},{" "," "}, {"にゅ","nyu"}, {" "," "},{"にょ","nyo"},
            {"ひゃ","hya"},{" "," "}, {"ひゅ","hyu"},{" "," "}, {"ひょ","hyo"},
            {"びゃ","bya"},{" "," "}, {"びゅ","byu"},{" "," "}, {"びょ","byo"},
            {"ぴゃ","pya"}, {" "," "},{"ぴゅ","pyu"},{" "," "}, {"ぴょ","pyo"},
            {"みゃ","mya"},{" "," "}, {"みゅ","myu"}, {" "," "}, {"みょ","myo"},
            {"りゃ","rya"},{" "," "}, {"りゅ","ryu"},{" "," "}, {"りょ","ryo"},
    };

    String[][] kataPodstawowe = {
            {"ア","a"}, {"イ","i"}, {"ウ","u"}, {"エ","e"}, {"オ","o"},
            {"カ","ka"}, {"キ","ki"}, {"ク","ku"}, {"ケ","ke"}, {"コ","ko"},
            {"サ","sa"}, {"シ","shi"}, {"ス","su"}, {"セ","se"}, {"ソ","so"},
            {"タ","ta"}, {"チ","chi"}, {"ツ","tsu"}, {"テ","te"}, {"ト","to"},
            {"ナ","na"}, {"ニ","ni"}, {"ヌ","nu"}, {"ネ","ne"}, {"ノ","no"},
            {"ハ","ha"}, {"ヒ","hi"}, {"フ","fu"}, {"ヘ","he"}, {"ホ","ho"},
            {"マ","ma"}, {"ミ","mi"}, {"ム","mu"}, {"メ","me"}, {"モ","mo"},
            {"ヤ","ya"}, {"  ","  "}, {"ユ","yu"}, {"  ","  "}, {"ヨ","yo"},
            {"ラ","ra"}, {"リ","ri"}, {"ル","ru"}, {"レ","re"}, {"ロ","ro"},
            {"ワ","wa"}, {"  ","  "}, {"  "," "}, {"  ","  "}, {"ヲ","wo"},
            {"  ","  "}, {"  ","  "}, {"  ","  "}, {"  ","  "}, {"ン","n"}
    };

    String[][] kataDakuten = {
            {"ガ","ga"}, {"ギ","gi"}, {"グ","gu"}, {"ゲ","ge"}, {"ゴ","go"},
            {"ザ","za"}, {"ジ","ji"}, {"ズ","zu"}, {"ゼ","ze"}, {"ゾ","zo"},
            {"ダ","da"}, {"ヂ","ji"}, {"ヅ","zu"}, {"デ","de"}, {"ド","do"},
            {"バ","ba"}, {"ビ","bi"}, {"ブ","bu"}, {"ベ","be"}, {"ボ","bo"},
            {"パ","pa"}, {"ピ","pi"}, {"プ","pu"}, {"ペ","pe"}, {"ポ","po"}
    };

    String[][] kataKombinowane = {
            {"キャ","kya"},{" "," "}, {"キュ","kyu"}, {" "," "},{"キョ","kyo"},
            {"ギャ","gya"}, {" "," "},{"ギュ","gyu"}, {" "," "},{"ギョ","gyo"},
            {"シャ","sha"},{" "," "}, {"シュ","shu"},{" "," "}, {"ショ","sho"},
            {"ジャ","ja"}, {" "," "},{"ジュ","ju"}, {" "," "}, {"ジョ","jo"},
            {"チャ","cha"},{" "," "}, {"チュ","chu"},{" "," "}, {"チョ","cho"},
            {"ヂャ","ja"}, {" "," "},{"ヂュ","ju"}, {" "," "}, {"ヂョ","jo"},
            {"ニャ","nya"},{" "," "}, {"ニュ","nyu"}, {" "," "}, {"ニョ","nyo"},
            {"ヒャ","hya"},{" "," "}, {"ヒュ","hyu"}, {" "," "}, {"ヒョ","hyo"},
            {"ビャ","bya"}, {" "," "}, {"ビュ","byu"}, {" "," "}, {"ビョ","byo"},
            {"ピャ","pya"}, {" "," "},{"ピュ","pyu"}, {" "," "}, {"ピョ","pyo"},
            {"ミャ","mya"}, {" "," "},{"ミュ","myu"}, {" "," "}, {"ミョ","myo"},
            {"リャ","rya"},{" "," "}, {"リュ","ryu"}, {" "," "}, {"リョ","ryo"}
    };

    String[][] aktualnyZestaw;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable[] pendingTasks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poczatek_hiragana_katakana);

        czyMaPremium = getIntent().getBooleanExtra("czyMaPremium", false);

        autoryzacja = FirebaseAuth.getInstance();
        premiumTracker = PremiumStatusTracker.getInstance();
        premiumTracker.setOnPremiumStatusChangedListener(this);
        fragmentManager = getSupportFragmentManager();

        container = findViewById(R.id.dynamicContainer);
        ImageButton przyciskWstecz = findViewById(R.id.buttonBack);
        przyciskAuth = findViewById(R.id.imageButtonAuth);
        zacznijNauke = findViewById(R.id.zacznijNauke);

        if (container == null || przyciskAuth == null || zacznijNauke == null) {
            Toast.makeText(this, "Błąd UI", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        zacznijNauke.setOnClickListener(v -> {
            Intent intent = new Intent(PoczatekHiraganaKatakana.this, Probny.class);
            intent.putExtra("czyMaPremium", premiumTracker.getCzyMaPremium());
            startActivity(intent);
        });

        przyciskWstecz.setOnClickListener(v -> finish());

        TextView zakladkaHiragana = findViewById(R.id.tabHiragana);
        TextView zakladkaKatakana = findViewById(R.id.tabKatakana);

        Button przyciskPodstawowe = findViewById(R.id.buttonPodstawowe);
        Button przyciskDakuten = findViewById(R.id.buttonDakuten);
        Button przyciskKombinowane = findViewById(R.id.buttonKombinowane);

        zakladkaHiragana.setOnClickListener(v -> {
            if (!czyHiragana) {
                czyHiragana = true;
                ustawStyleZakladek(zakladkaHiragana, zakladkaKatakana);
                ustawZestaw();
            }
        });

        zakladkaKatakana.setOnClickListener(v -> {
            if (czyHiragana) {
                czyHiragana = false;
                ustawStyleZakladek(zakladkaHiragana, zakladkaKatakana);
                ustawZestaw();
            }
        });

        przyciskPodstawowe.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("PODSTAWOWE")) {
                aktualnyTyp = "PODSTAWOWE";
                ustawStylePrzyciskow(przyciskPodstawowe, przyciskDakuten, przyciskKombinowane);
                ustawZestaw();
            }
        });

        przyciskDakuten.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("DAKUTEN")) {
                aktualnyTyp = "DAKUTEN";
                ustawStylePrzyciskow(przyciskDakuten, przyciskPodstawowe, przyciskKombinowane);
                ustawZestaw();
            }
        });

        przyciskKombinowane.setOnClickListener(v -> {
            if (!aktualnyTyp.equals("KOMBINOWANE")) {
                aktualnyTyp = "KOMBINOWANE";
                ustawStylePrzyciskow(przyciskKombinowane, przyciskPodstawowe, przyciskDakuten);
                ustawZestaw();
            }
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser uzytkownik = firebaseAuth.getCurrentUser();
            if (uzytkownik != null) {
                premiumTracker.aktualizujStatusDlaUzytkownika();
                przyciskAuth.setImageResource(R.drawable.konto1);
                przyciskAuth.setContentDescription("Mój profil");
            } else {
                czyMaPremium = false;
                premiumTracker.stopListening();
                przyciskAuth.setImageResource(R.drawable.login);
                przyciskAuth.setContentDescription("Zaloguj się");
            }
        };

        przyciskAuth.setOnClickListener(v -> obsluzPrzyciskAuth());

        aktualnyZestaw = hiraPodstawowe;
        wyswietlZestaw();
    }

    private void obsluzPrzyciskAuth() {
        FirebaseUser aktualnyUzytkownik = autoryzacja.getCurrentUser();
        if (aktualnyUzytkownik != null) {
            if (SessionTimer.getInstance().getElapsedSeconds() == 0) {
                SessionTimer.getInstance().start();
            }
            UserProfileDialogFragment dialog = UserProfileDialogFragment.newInstance(premiumTracker.getCzyMaPremium());
            dialog.show(fragmentManager, "profilUzytkownika");
        } else {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(fragmentManager, "logowanie");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoryzacja.addAuthStateListener(authStateListener);
        FirebaseUser uzytkownik = autoryzacja.getCurrentUser();
        if (uzytkownik != null) {
            premiumTracker.startListening();
            premiumTracker.aktualizujStatusDlaUzytkownika();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        premiumTracker.stopListening();
        autoryzacja.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pendingTasks != null) {
            for (Runnable r : pendingTasks) if (r != null) handler.removeCallbacks(r);
        }
        premiumTracker.stopListening();
    }

    @Override
    public void onUserAuthenticated() {
        SessionTimer.getInstance().start();
        premiumTracker.startListening();
        premiumTracker.aktualizujStatusDlaUzytkownika();
    }

    @Override
    public void onUserLoggedOut() {
        SessionTimer.getInstance().reset();
        finish();
    }

    @Override
    public void onPremiumStatusChanged(boolean czyMaPremium) {
        this.czyMaPremium = czyMaPremium;
    }

    private void ustawStyleZakladek(TextView zakladkaHiragana, TextView zakladkaKatakana) {
        if (czyHiragana) {
            zakladkaHiragana.setBackgroundColor(getResources().getColor(R.color.tlo_powierzchnia));
            zakladkaKatakana.setBackgroundColor(getResources().getColor(R.color.tlo_zakladki));
        } else {
            zakladkaHiragana.setBackgroundColor(getResources().getColor(R.color.tlo_zakladki));
            zakladkaKatakana.setBackgroundColor(getResources().getColor(R.color.tlo_powierzchnia));
        }
    }

    private void ustawStylePrzyciskow(Button aktywny, Button nieaktywny1, Button nieaktywny2) {
        aktywny.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.przycisk_glowny)));
        aktywny.setTextColor(getResources().getColor(R.color.tekst_na_glownym));

        nieaktywny1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.przycisk_dodatkowy)));
        nieaktywny1.setTextColor(getResources().getColor(R.color.tekst_na_dodatkowym));

        nieaktywny2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.przycisk_dodatkowy)));
        nieaktywny2.setTextColor(getResources().getColor(R.color.tekst_na_dodatkowym));
    }

    private void ustawZestaw() {
        if (czyHiragana) {
            if (aktualnyTyp.equals("PODSTAWOWE")) aktualnyZestaw = hiraPodstawowe;
            else if (aktualnyTyp.equals("DAKUTEN")) aktualnyZestaw = hiraDakuten;
            else aktualnyZestaw = hiraKombinowane;
        } else {
            if (aktualnyTyp.equals("PODSTAWOWE")) aktualnyZestaw = kataPodstawowe;
            else if (aktualnyTyp.equals("DAKUTEN")) aktualnyZestaw = kataDakuten;
            else aktualnyZestaw = kataKombinowane;
        }
        wyswietlZestaw();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void wyswietlZestaw() {
        if (pendingTasks != null) {
            for (Runnable zadanie : pendingTasks) if (zadanie != null) handler.removeCallbacks(zadanie);
        }
        container.removeAllViews();
        pendingTasks = new Runnable[aktualnyZestaw.length];
        int kolumny = 5;
        container.setColumnCount(kolumny);
        for (int i = 0; i < aktualnyZestaw.length; i++) {
            final String[] znak = aktualnyZestaw[i];
            final long opoznienie = i * 30L;
            Runnable zadanie = () -> {
                try {
                    CardView karta = new CardView(this);
                    GridLayout.LayoutParams parametryKarty = new GridLayout.LayoutParams();
                    parametryKarty.width = 0;
                    parametryKarty.height = dpToPx(140);
                    parametryKarty.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
                    parametryKarty.setMargins(8, 8, 8, 8);
                    karta.setLayoutParams(parametryKarty);
                    karta.setCardElevation(4f);
                    karta.setRadius(12f);
                    karta.setCardBackgroundColor(getResources().getColor(R.color.tlo_powierzchnia));

                    LinearLayout element = new LinearLayout(this);
                    element.setOrientation(LinearLayout.VERTICAL);
                    element.setPadding(16, 24, 16, 24);
                    element.setGravity(Gravity.CENTER);

                    TextView kana = new TextView(this);
                    kana.setText(znak[0]);
                    kana.setTextSize(32);
                    kana.setTextColor(getResources().getColor(R.color.tekst_glowny));
                    kana.setGravity(Gravity.CENTER);
                    element.addView(kana);

                    if (!znak[1].isEmpty()) {
                        TextView romaji = new TextView(this);
                        romaji.setText(znak[1]);
                        romaji.setTextSize(14);
                        romaji.setTextColor(getResources().getColor(R.color.tekst_dodatkowy));
                        romaji.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams parametryRomaji = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        parametryRomaji.setMargins(0, 8, 0, 0);
                        romaji.setLayoutParams(parametryRomaji);
                        element.addView(romaji);
                    }

                    karta.addView(element);
                    karta.setAlpha(0f);
                    karta.setTranslationY(50f);
                    karta.animate().alpha(1f).translationY(0f).setDuration(300).start();
                    container.addView(karta);
                } catch (Exception ignored) {}
            };
            pendingTasks[i] = zadanie;
            handler.postDelayed(zadanie, opoznienie);
        }
    }
}