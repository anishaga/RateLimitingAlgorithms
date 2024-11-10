package FixedWindowCounter.Plain;

public class Main {

    public static void main(String[] args) {
        PlainFixedWindowCounter fixedWindowCounter = new PlainFixedWindowCounter(3);
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
    }
}
