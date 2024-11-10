package LeakingBucket.Redis;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        RedisLeakingBucket leakingBucket = new RedisLeakingBucket(3);

        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedIndex");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedIndex");
        leakingBucket.handleNewRequest("anish", "SearchPinnedIndex");
        leakingBucket.handleNewRequest("anish", "SearchPinnedIndex");
        Thread.sleep(900);
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        Thread.sleep(10000);
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handleNewRequest("anish", "SearchPinnedStocks");
    }
}
