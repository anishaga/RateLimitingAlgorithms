package TokenBucket.Redis;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RedisTokenBucket tokenBucket = new RedisTokenBucket(3);
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedIndex");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedIndex");
        tokenBucket.handleNewRequest("anish", "SearchPinnedIndex");
        tokenBucket.handleNewRequest("anish", "SearchPinnedIndex");
        Thread.sleep(900);
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        Thread.sleep(10000);
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");
        tokenBucket.handleNewRequest("anish", "SearchPinnedStocks");

    }
}
