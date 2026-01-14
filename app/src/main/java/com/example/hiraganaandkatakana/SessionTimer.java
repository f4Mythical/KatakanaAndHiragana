package com.example.hiraganaandkatakana;

public class SessionTimer {
    private static volatile SessionTimer INSTANCE;
    private long startTime = 0;

    private SessionTimer() {}

    public static SessionTimer getInstance() {
        if (INSTANCE == null) {
            synchronized (SessionTimer.class) {
                if (INSTANCE == null) INSTANCE = new SessionTimer();
            }
        }
        return INSTANCE;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void reset() {
        startTime = 0;
    }

    public long getElapsedSeconds() {
        return startTime == 0 ? 0 : (System.currentTimeMillis() - startTime) / 1000;
    }
}