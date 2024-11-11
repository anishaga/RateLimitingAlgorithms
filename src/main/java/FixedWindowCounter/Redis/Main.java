package FixedWindowCounter.Redis;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RedisFixedWindowCounter fixedWindowCounter = new RedisFixedWindowCounter(3);
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedIndex");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedIndex");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedIndex");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedIndex");
        Thread.sleep(900);
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        Thread.sleep(10000);
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
        fixedWindowCounter.handleNewRequest("anish", "SearchPinnedStocks");
    }
}
