package com.example.hiraganaandkatakana;

public class WordData {
    private String hiragana;
    private String romaji;
    private String polski;
    private String angielski;
    private String hiszpanski;
    private String kategoria;

    public WordData(String hiragana, String romaji, String polski, String angielski, String hiszpanski, String kategoria) {
        this.hiragana = hiragana;
        this.romaji = romaji;
        this.polski = polski;
        this.angielski = angielski;
        this.hiszpanski = hiszpanski;
        this.kategoria = kategoria;
    }

    public String getHiragana() { return hiragana; }
    public String getRomaji() { return romaji; }
    public String getPolski() { return polski; }
    public String getAngielski() { return angielski; }
    public String getHiszpanski() { return hiszpanski; }
    public String getKategoria() { return kategoria; }

    public String getTlumaczenieDlaJezyka(String kod) {
        if ("en".equals(kod)) return angielski;
        if ("es".equals(kod)) return hiszpanski;
        return polski;
    }
}