package FixedWindowCounter.Plain;

public class CounterManager {

    private final int maxRequests;

    private final int resetCounterAfter;

    private long lastRefillTime;

    private int currentCounter;

    public CounterManager(int maxRequests) {
        this.maxRequests = maxRequests;
        this.resetCounterAfter = 1000;
        resetCounter();
    }

    public synchronized void decrementCounter() {
        resetCounter();
        if (currentCounter <= 0) {
            System.out.println("Hey, hold on! Get a glass of water.");
            return;
        }
        --currentCounter;
        System.out.println("Request consumed");
    }

    private void resetCounter() {
        if (System.currentTimeMillis() - lastRefillTime > resetCounterAfter) {
            currentCounter = maxRequests;
            lastRefillTime = System.currentTimeMillis();
        }
    }
}
