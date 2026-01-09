package com.example.hiraganaandkatakana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterSelectionActivity extends AppCompatActivity {

    private GridLayout kontener;
    private String aktualnySystem = "HIRAGANA";
    private String aktualnaKategoria = "PODSTAWOWE";
    private TextView tvPodstawowe, tvDakuten, tvKombinowane, tvKatakanaPodstawowe, tvKatakanaDakuten, tvKatakanaKombinowane;
    private Button btnZacznijNauke, btnDodajKatakana;
    private ImageButton btnPomoc;
    private LinearLayout kontenerPrzyciskowKolumn, kategorieContainer, kategorieKatakanaContainer;
    private ScrollView scrollView;
    private ImageButton buttonBack;

    private int zapisanaPozycjaScroll = 0;
    private boolean katakanaWlaczone = false;

    private Map<Integer, Boolean> czyZaznaczony = new HashMap<>();
    private Map<Integer, String[]> mapaZnakow = new HashMap<>();
    private Map<Integer, Integer> licznikiKolumn = new HashMap<>();
    private Map<Integer, Integer> licznikPowtorzen = new HashMap<>();

    private static final int DOUBLE_CLICK_TIME_DELTA = 300;
    private Map<Integer, Long> lastClickTimes = new HashMap<>();

    private String[][] hiraganaPodstawowe = {
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

    private String[][] hiraganaDakuten = {
            {"が","ga"}, {"ぎ","gi"}, {"ぐ","gu"}, {"げ","ge"}, {"ご","go"},
            {"ざ","za"}, {"じ","ji"}, {"ず","zu"}, {"ぜ","ze"}, {"ぞ","zo"},
            {"だ","da"}, {"ぢ","ji"}, {"づ","zu"}, {"で","de"}, {"ど","do"},
            {"ば","ba"}, {"び","bi"}, {"ぶ","bu"}, {"べ","be"}, {"ぼ","bo"},
            {"ぱ","pa"}, {"ぴ","pi"}, {"ぷ","pu"}, {"ぺ","pe"}, {"ぽ","po"}
    };

    private String[][] hiraganaKombinowane = {
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
            {"みゃ","mya"},{" "," "}, {"みゅ","myu"}, {" "," "},{"みょ","myo"},
            {"りゃ","rya"},{" "," "}, {"りゅ","ryu"},{" "," "}, {"りょ","ryo"},
    };

    private String[][] katakanaPodstawowe = {
            {"ア","a"}, {"イ","i"}, {"ウ","u"}, {"エ","e"}, {"オ","o"},
            {"カ","ka"}, {"キ","ki"}, {"ク","ku"}, {"ケ","ke"}, {"コ","ko"},
            {"サ","sa"}, {"シ","shi"}, {"ス","su"}, {"セ","se"}, {"ソ","so"},
            {"タ","ta"}, {"チ","chi"}, {"ツ","tsu"}, {"テ","te"}, {"ト","to"},
            {"ナ","na"}, {"ニ","ni"}, {"ヌ","nu"}, {"ネ","ne"}, {"ノ","no"},
            {"ハ","ha"}, {"ヒ","hi"}, {"フ","fu"}, {"ヘ","he"}, {"ホ","ho"},
            {"マ","ma"}, {"ミ","mi"}, {"ム","mu"}, {"メ","me"}, {"モ","mo"},
            {"ヤ","ya"}, {"   ","  "}, {"ユ","yu"}, {"   ","  "}, {"ヨ","yo"},
            {"ラ","ra"}, {"リ","ri"}, {"ル","ru"}, {"レ","re"}, {"ロ","ro"},
            {"ワ","wa"}, {"   "," "}, {"   "," "}, {"   "," "}, {"ヲ","wo"},
            {"   ","  "}, {"   ","  "}, {"   ","  "}, {"   ","  "}, {"ン","n"}
    };

    private String[][] katakanaDakuten = {
            {"ガ","ga"}, {"ギ","gi"}, {"グ","gu"}, {"ゲ","ge"}, {"ゴ","go"},
            {"ザ","za"}, {"ジ","ji"}, {"ズ","zu"}, {"ゼ","ze"}, {"ゾ","zo"},
            {"ダ","da"}, {"ヂ","ji"}, {"ヅ","zu"}, {"デ","de"}, {"ド","do"},
            {"バ","ba"}, {"ビ","bi"}, {"ブ","bu"}, {"ベ","be"}, {"ボ","bo"},
            {"パ","pa"}, {"ピ","pi"}, {"プ","pu"}, {"ペ","pe"}, {"ポ","po"}
    };

    private String[][] katakanaKombinowane = {
            {"キャ","kya"}, {" "," "},{"キュ","kyu"},{" "," "}, {"キョ","kyo"},
            {"ギャ","gya"}, {" "," "}, {"ギュ","gyu"},{" "," "}, {"ギョ","gyo"},
            {"シャ","sha"}, {" "," "}, {"シュ","shu"},{" "," "}, {"ショ","sho"},
            {"ジャ","ja"},{" "," "}, {"ジュ","ju"},{" "," "}, {"ジョ","jo"},
            {"チャ","cha"},{" "," "}, {"チュ","chu"}, {" "," "},{"チョ","cho"},
            {"ヂャ","ja"}, {" "," "},{"ヂュ","ju"},{" "," "}, {"ヂョ","jo"},
            {"ニャ","nya"},{" "," "}, {"ニュ","nyu"}, {" "," "},{"ニョ","nyo"},
            {"ヒャ","hya"},{" "," "}, {"ヒュ","hyu"},{" "," "}, {"ヒョ","hyo"},
            {"ビャ","bya"},{" "," "}, {"ビュ","byu"},{" "," "}, {"ビョ","byo"},
            {"ピャ","pya"}, {" "," "},{"ピュ","pyu"},{" "," "}, {"ピョ","pyo"},
            {"ミャ","mya"},{" "," "}, {"ミュ","myu"}, {" "," "},{"ミョ","myo"},
            {"リャ","rya"},{" "," "}, {"リュ","ryu"},{" "," "}, {"リョ","ryo"},
    };

    private String[][] aktualnyZestaw;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable[] zadania = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premiumhiraganaznakiwybranenicnierobie);

        kontener = findViewById(R.id.gridCharacters);
        kontenerPrzyciskowKolumn = findViewById(R.id.kontenerPrzyciskowKolumn);
        scrollView = findViewById(R.id.scrollView);
        buttonBack = findViewById(R.id.buttonBack);
        btnDodajKatakana = findViewById(R.id.btnDodajKatakana);
        btnPomoc = findViewById(R.id.btnPomoc);
        kategorieContainer = findViewById(R.id.kategorieContainer);
        kategorieKatakanaContainer = findViewById(R.id.kategorieKatakanaContainer);
        tvPodstawowe = findViewById(R.id.tvPodstawowe);
        tvDakuten = findViewById(R.id.tvDakuten);
        tvKombinowane = findViewById(R.id.tvKombinowane);
        tvKatakanaPodstawowe = findViewById(R.id.tvKatakanaPodstawowe);
        tvKatakanaDakuten = findViewById(R.id.tvKatakanaDakuten);
        tvKatakanaKombinowane = findViewById(R.id.tvKatakanaKombinowane);
        btnZacznijNauke = findViewById(R.id.btnZacznijNauke);

        inicjalizujMapy();

        buttonBack.setOnClickListener(v -> finish());

        btnPomoc.setOnClickListener(v -> pokazOknoPomoc());

        btnDodajKatakana.setOnClickListener(v -> {
            katakanaWlaczone = !katakanaWlaczone;
            btnDodajKatakana.setText(katakanaWlaczone ? R.string.ukryj_katakana : R.string.dodaj_katakana);
            kategorieKatakanaContainer.setVisibility(katakanaWlaczone ? View.VISIBLE : View.GONE);
            inicjalizujMapy();
            wyswietlZestaw(true);
        });

        tvPodstawowe.setTag("PODSTAWOWE");
        tvDakuten.setTag("DAKUTEN");
        tvKombinowane.setTag("KOMBINOWANE");
        tvKatakanaPodstawowe.setTag("PODSTAWOWE");
        tvKatakanaDakuten.setTag("DAKUTEN");
        tvKatakanaKombinowane.setTag("KOMBINOWANE");

        ustawSluchaczyKategorii();
        aktualnyZestaw = hiraganaPodstawowe;
        wyswietlZestaw(true);
    }

    private void pokazOknoPomoc() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pomoc);
        builder.setMessage(R.string.premium_info);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void inicjalizujMapy() {
        czyZaznaczony.clear();
        mapaZnakow.clear();
        licznikPowtorzen.clear();
        lastClickTimes.clear();

        int offset = 0;
        for (int i = 0; i < hiraganaPodstawowe.length; i++) {
            mapaZnakow.put(i + offset, hiraganaPodstawowe[i]);
            czyZaznaczony.put(i + offset, false);
            licznikPowtorzen.put(i + offset, 0);
        }

        offset = 100;
        for (int i = 0; i < hiraganaDakuten.length; i++) {
            int indeks = i + offset;
            mapaZnakow.put(indeks, hiraganaDakuten[i]);
            czyZaznaczony.put(indeks, false);
            licznikPowtorzen.put(indeks, 0);
        }

        offset = 200;
        for (int i = 0; i < hiraganaKombinowane.length; i++) {
            int indeks = i + offset;
            mapaZnakow.put(indeks, hiraganaKombinowane[i]);
            czyZaznaczony.put(indeks, false);
            licznikPowtorzen.put(indeks, 0);
        }

        offset = 300;
        for (int i = 0; i < katakanaPodstawowe.length; i++) {
            mapaZnakow.put(i + offset, katakanaPodstawowe[i]);
            czyZaznaczony.put(i + offset, false);
            licznikPowtorzen.put(i + offset, 0);
        }

        offset = 400;
        for (int i = 0; i < katakanaDakuten.length; i++) {
            int indeks = i + offset;
            mapaZnakow.put(indeks, katakanaDakuten[i]);
            czyZaznaczony.put(indeks, false);
            licznikPowtorzen.put(indeks, 0);
        }

        offset = 500;
        for (int i = 0; i < katakanaKombinowane.length; i++) {
            int indeks = i + offset;
            mapaZnakow.put(indeks, katakanaKombinowane[i]);
            czyZaznaczony.put(indeks, false);
            licznikPowtorzen.put(indeks, 0);
        }

        for (int i = 0; i < 5; i++) {
            licznikiKolumn.put(i, 0);
        }
    }

    private void ustawSluchaczyKategorii() {
        View.OnClickListener listener = v -> {
            TextView aktywna = (TextView) v;
            String tag = aktywna.getTag().toString();

            aktualnySystem = (v == tvPodstawowe || v == tvDakuten || v == tvKombinowane) ? "HIRAGANA" : "KATAKANA";

            if (!aktualnaKategoria.equals(tag)) {
                aktualnaKategoria = tag;
                resetujWszystkieZakladki();
                aktywna.setBackgroundColor(getResources().getColor(R.color.przycisk_glowny));
                aktywna.setTextColor(getResources().getColor(R.color.tekst_na_glownym));
                zresetujLicznikiKolumn();
                wyswietlZestaw(true);
            }
        };

        tvPodstawowe.setOnClickListener(listener);
        tvDakuten.setOnClickListener(listener);
        tvKombinowane.setOnClickListener(listener);
        tvKatakanaPodstawowe.setOnClickListener(listener);
        tvKatakanaDakuten.setOnClickListener(listener);
        tvKatakanaKombinowane.setOnClickListener(listener);

        btnZacznijNauke.setOnClickListener(v -> {
            ArrayList<String> wszystkieZaznaczone = new ArrayList<>();
            ArrayList<Integer> powtorzeniaList = new ArrayList<>();
            HashMap<String, String> mapaZnakowRomaji = new HashMap<>();

            for (Map.Entry<Integer, Boolean> entry : czyZaznaczony.entrySet()) {
                if (entry.getValue()) {
                    String[] znakData = mapaZnakow.get(entry.getKey());
                    String znak = znakData[0];
                    String romaji = znakData[1];

                    if (!znak.trim().isEmpty() && !romaji.trim().isEmpty()) {
                        wszystkieZaznaczone.add(znak);
                        int powtorzenia = licznikPowtorzen.get(entry.getKey());
                        powtorzeniaList.add(powtorzenia);
                        mapaZnakowRomaji.put(znak, romaji);
                    }
                }
            }

            if (wszystkieZaznaczone.isEmpty()) {
                Toast.makeText(this, getString(R.string.wybierz_znak), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, WybraneZnakiPremium.class);
            intent.putStringArrayListExtra("znaki", wszystkieZaznaczone);
            intent.putIntegerArrayListExtra("powtorzenia", powtorzeniaList);
            intent.putExtra("mapaZnakow", mapaZnakowRomaji);
            startActivity(intent);
        });
    }

    private void pokazOknoPowtorzen(int indeksTablicy) {
        int realnyIndeks = getRealnyIndeks(indeksTablicy);
        String[] znak = mapaZnakow.get(realnyIndeks);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.ustaw_powtorzenia) + ": " + znak[0]);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        int aktualneLiczby = licznikPowtorzen.getOrDefault(realnyIndeks, 0);
        if (aktualneLiczby == 0) {
            input.setText("1");
        } else {
            input.setText(String.valueOf(aktualneLiczby));
        }
        input.setSelection(input.getText().length());

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(50, 20, 50, 20);
        container.addView(input);

        builder.setView(container);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            try {
                int liczba = Integer.parseInt(input.getText().toString());
                if (liczba >= 1 && liczba <= 99) {
                    licznikPowtorzen.put(realnyIndeks, liczba);
                    wyswietlZestaw(false);
                } else {
                    Toast.makeText(this, "Wybierz liczbę od 1 do 99", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Nieprawidłowa wartość", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.anuluj, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void resetujWszystkieZakladki() {
        TextView[] wszystkie = {tvPodstawowe, tvDakuten, tvKombinowane, tvKatakanaPodstawowe, tvKatakanaDakuten, tvKatakanaKombinowane};
        for (TextView tv : wszystkie) {
            tv.setBackgroundColor(getResources().getColor(R.color.przycisk_dodatkowy));
            tv.setTextColor(getResources().getColor(R.color.tekst_na_dodatkowym));
        }
    }

    private void zresetujLicznikiKolumn() {
        for (int i = 0; i < 5; i++) {
            licznikiKolumn.put(i, 0);
        }
    }

    private int dpNaPx(int dp) {
        float gestosc = getResources().getDisplayMetrics().density;
        return Math.round(dp * gestosc);
    }

    private void wyswietlZestaw(boolean zAnimacja) {
        if (aktualnaKategoria.equals("PODSTAWOWE")) {
            aktualnyZestaw = aktualnySystem.equals("HIRAGANA") ? hiraganaPodstawowe : katakanaPodstawowe;
        } else if (aktualnaKategoria.equals("DAKUTEN")) {
            aktualnyZestaw = aktualnySystem.equals("HIRAGANA") ? hiraganaDakuten : katakanaDakuten;
        } else if (aktualnaKategoria.equals("KOMBINOWANE")) {
            aktualnyZestaw = aktualnySystem.equals("HIRAGANA") ? hiraganaKombinowane : katakanaKombinowane;
        }

        zapisanaPozycjaScroll = scrollView.getScrollY();

        if (zadania != null) {
            for (Runnable zadanie : zadania) {
                if (zadanie != null) {
                    handler.removeCallbacks(zadanie);
                }
            }
        }

        kontener.removeAllViews();
        kontenerPrzyciskowKolumn.removeAllViews();

        zadania = new Runnable[aktualnyZestaw.length];
        int kolumny = 5;
        kontener.setColumnCount(kolumny + 1);

        stworzPrzyciskiKolumn(kolumny);

        int iloscRzedow = (int) Math.ceil((double) aktualnyZestaw.length / 5);

        for (int i = 0; i < aktualnyZestaw.length; i++) {
            int indeksTablicy = i;
            long opoznienie = zAnimacja ? i * 30L : 0L;

            Runnable zadanie = () -> {
                if (indeksTablicy % 5 == 0) {
                    int rzad = indeksTablicy / 5;
                    Button przyciskWiersza = new Button(this);
                    GridLayout.LayoutParams parametryWiersza = new GridLayout.LayoutParams();
                    parametryWiersza.width = dpNaPx(36);
                    parametryWiersza.height = dpNaPx(88);
                    parametryWiersza.rowSpec = GridLayout.spec(rzad, 1);
                    parametryWiersza.columnSpec = GridLayout.spec(0, 1);
                    parametryWiersza.setMargins(2, 2, 2, 2);
                    przyciskWiersza.setLayoutParams(parametryWiersza);

                    przyciskWiersza.setBackgroundColor(getResources().getColor(R.color.przycisk_glowny));
                    przyciskWiersza.setStateListAnimator(null);
                    przyciskWiersza.setText("");

                    int nrRzedu = rzad;
                    przyciskWiersza.setOnClickListener(v -> {
                        toggleRzad(nrRzedu);
                        wyswietlZestaw(false);
                    });

                    kontener.addView(przyciskWiersza);
                }

                CardView karta = new CardView(this);
                GridLayout.LayoutParams parametry = new GridLayout.LayoutParams();
                parametry.width = 0;
                parametry.height = dpNaPx(88);
                parametry.columnSpec = GridLayout.spec((indeksTablicy % 5) + 1, 1, 1f);
                parametry.rowSpec = GridLayout.spec(indeksTablicy / 5, 1);
                parametry.setMargins(2, 2, 2, 2);
                karta.setLayoutParams(parametry);
                karta.setCardElevation(2f);
                karta.setRadius(6f);

                int realnyIndeks = getRealnyIndeks(indeksTablicy);
                boolean jestZaznaczony = czyZaznaczony.getOrDefault(realnyIndeks, false);
                int kolorTla = jestZaznaczony ?
                        getResources().getColor(R.color.przycisk_glowny) :
                        getResources().getColor(R.color.tlo_powierzchnia);

                karta.setCardBackgroundColor(kolorTla);

                LinearLayout element = new LinearLayout(this);
                element.setOrientation(LinearLayout.VERTICAL);
                element.setPadding(6, 8, 6, 8);
                element.setGravity(Gravity.CENTER);

                TextView kana = new TextView(this);
                kana.setText(aktualnyZestaw[indeksTablicy][0]);
                kana.setTextSize(28);
                kana.setTextColor(jestZaznaczony ?
                        getResources().getColor(R.color.tekst_na_glownym) :
                        getResources().getColor(R.color.tekst_glowny));
                kana.setGravity(Gravity.CENTER);
                element.addView(kana);

                TextView romaji = null;
                if (!aktualnyZestaw[indeksTablicy][1].trim().isEmpty()) {
                    romaji = new TextView(this);
                    romaji.setText(aktualnyZestaw[indeksTablicy][1]);
                    romaji.setTextSize(12);
                    romaji.setTextColor(jestZaznaczony ?
                            getResources().getColor(R.color.tekst_na_glownym) :
                            getResources().getColor(R.color.tekst_dodatkowy));
                    romaji.setGravity(Gravity.CENTER);
                    element.addView(romaji);
                }

                int powtorzenia = licznikPowtorzen.getOrDefault(realnyIndeks, 0);
                if (powtorzenia > 0) {
                    TextView tvPowtorzenia = new TextView(this);
                    tvPowtorzenia.setText("×" + powtorzenia);
                    tvPowtorzenia.setTextSize(10);
                    tvPowtorzenia.setTextColor(jestZaznaczony ?
                            getResources().getColor(R.color.tekst_na_glownym) :
                            getResources().getColor(R.color.tekst_dodatkowy));
                    tvPowtorzenia.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.topMargin = 4;
                    tvPowtorzenia.setLayoutParams(params);
                    element.addView(tvPowtorzenia);
                }

                karta.addView(element);

                if (!aktualnyZestaw[indeksTablicy][0].trim().isEmpty()) {
                    karta.setOnClickListener(v -> {
                        long clickTime = System.currentTimeMillis();
                        Long lastClickTime = lastClickTimes.get(indeksTablicy);

                        if (lastClickTime != null && (clickTime - lastClickTime) < DOUBLE_CLICK_TIME_DELTA) {
                            pokazOknoPowtorzen(indeksTablicy);
                        } else {
                            togglePojedynczyZnak(indeksTablicy);
                            wyswietlZestaw(false);
                        }

                        lastClickTimes.put(indeksTablicy, clickTime);
                    });
                } else {
                    karta.setOnClickListener(v -> {
                        Animation animacja = new TranslateAnimation(0, 15, 0, 0);
                        animacja.setDuration(150);
                        animacja.setInterpolator(new CycleInterpolator(4));
                        v.startAnimation(animacja);
                    });
                    karta.setClickable(true);
                    karta.setAlpha(0.5f);
                }

                if (zAnimacja) {
                    karta.setAlpha(0f);
                    karta.setTranslationY(50f);
                    karta.animate()
                            .alpha(1f)
                            .translationY(0f)
                            .setDuration(300)
                            .setStartDelay(0)
                            .start();
                } else {
                    karta.setAlpha(1f);
                    karta.setTranslationY(0f);
                }

                kontener.addView(karta);

                if (indeksTablicy == aktualnyZestaw.length - 1) {
                    scrollView.post(() -> {
                        scrollView.scrollTo(0, zapisanaPozycjaScroll);
                    });
                }
            };

            zadania[indeksTablicy] = zadanie;
            handler.postDelayed(zadanie, opoznienie);
        }
    }

    private void stworzPrzyciskiKolumn(int kolumny) {
        kontenerPrzyciskowKolumn.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams parametryKontenera = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        parametryKontenera.bottomMargin = dpNaPx(8);
        kontenerPrzyciskowKolumn.setLayoutParams(parametryKontenera);

        Button pusteMiejsce = new Button(this);
        LinearLayout.LayoutParams parametryPuste = new LinearLayout.LayoutParams(dpNaPx(36), dpNaPx(42));
        parametryPuste.leftMargin = dpNaPx(2);
        parametryPuste.rightMargin = dpNaPx(2);
        pusteMiejsce.setLayoutParams(parametryPuste);
        pusteMiejsce.setBackgroundColor(getResources().getColor(R.color.tlo_glowne));
        pusteMiejsce.setClickable(false);
        kontenerPrzyciskowKolumn.addView(pusteMiejsce);

        for(int kolumna = 0; kolumna < kolumny; kolumna++) {
            Button przyciskKolumny = new Button(this);
            LinearLayout.LayoutParams parametry = new LinearLayout.LayoutParams(0, dpNaPx(42), 1f);
            parametry.leftMargin = dpNaPx(2);
            parametry.rightMargin = dpNaPx(2);
            przyciskKolumny.setLayoutParams(parametry);

            przyciskKolumny.setText("");
            przyciskKolumny.setTextSize(18);
            przyciskKolumny.setAllCaps(true);

            przyciskKolumny.setBackgroundColor(getResources().getColor(R.color.przycisk_glowny));
            przyciskKolumny.setTextColor(getResources().getColor(R.color.tekst_na_glownym));
            przyciskKolumny.setStateListAnimator(null);

            int nrKolumny = kolumna;
            przyciskKolumny.setOnClickListener(v -> {
                int licznik = licznikiKolumn.getOrDefault(nrKolumny, 0);
                licznik++;
                licznikiKolumn.put(nrKolumny, licznik);

                if (licznik % 2 == 1) {
                    zaznaczKolumne(nrKolumny);
                } else {
                    odznaczKolumne(nrKolumny);
                }

                wyswietlZestaw(false);
            });

            kontenerPrzyciskowKolumn.addView(przyciskKolumny);
        }
    }

    private void togglePojedynczyZnak(int indeksTablicy) {
        int realnyIndeks = getRealnyIndeks(indeksTablicy);
        boolean obecnyStan = czyZaznaczony.getOrDefault(realnyIndeks, false);
        czyZaznaczony.put(realnyIndeks, !obecnyStan);
    }

    private void toggleRzad(int nrRzedu) {
        int startIndeks = nrRzedu * 5;
        int koniecIndeks = Math.min(startIndeks + 5, aktualnyZestaw.length);

        boolean wszystkieZaznaczone = true;
        for (int i = startIndeks; i < koniecIndeks; i++) {
            String znak = aktualnyZestaw[i][0];
            if (!znak.trim().isEmpty()) {
                int realnyIndeks = getRealnyIndeks(i);
                if (!czyZaznaczony.getOrDefault(realnyIndeks, false)) {
                    wszystkieZaznaczone = false;
                    break;
                }
            }
        }

        for (int i = startIndeks; i < koniecIndeks; i++) {
            String znak = aktualnyZestaw[i][0];
            if (!znak.trim().isEmpty()) {
                int realnyIndeks = getRealnyIndeks(i);
                czyZaznaczony.put(realnyIndeks, !wszystkieZaznaczone);
            }
        }
    }

    private void zaznaczKolumne(int kolumna) {
        int iloscRzedow = (int) Math.ceil((double) aktualnyZestaw.length / 5);
        for (int rzad = 0; rzad < iloscRzedow; rzad++) {
            int indeksWTablicy = rzad * 5 + kolumna;
            if (indeksWTablicy < aktualnyZestaw.length) {
                String znak = aktualnyZestaw[indeksWTablicy][0];
                if (!znak.trim().isEmpty()) {
                    int realnyIndeks = getRealnyIndeks(indeksWTablicy);
                    czyZaznaczony.put(realnyIndeks, true);
                }
            }
        }
    }

    private void odznaczKolumne(int kolumna){
        int iloscRzedow = (int) Math.ceil((double) aktualnyZestaw.length / 5);
        for (int rzad = 0; rzad < iloscRzedow; rzad++) {
            int indeksWTablicy = rzad * 5 + kolumna;
            if (indeksWTablicy < aktualnyZestaw.length) {
                String znak = aktualnyZestaw[indeksWTablicy][0];
                if (!znak.trim().isEmpty()) {
                    int realnyIndeks = getRealnyIndeks(indeksWTablicy);
                    czyZaznaczony.put(realnyIndeks, false);
                }
            }
        }
    }

    public int getRealnyIndeks(int indeksTablicy) {
        int offset = 0;

        if (aktualnySystem.equals("KATAKANA")) {
            offset = 300;
        }

        if (aktualnaKategoria.equals("DAKUTEN")) {
            offset += 100;
        } else if (aktualnaKategoria.equals("KOMBINOWANE")) {
            offset += 200;
        }

        return offset + indeksTablicy;
    }
}