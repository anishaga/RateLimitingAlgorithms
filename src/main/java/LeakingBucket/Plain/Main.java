package LeakingBucket.Plain;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        PlainLeakingBucket leakingBucket = new PlainLeakingBucket(3);
        leakingBucket.handeleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handeleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handeleNewRequest("anish", "SearchPinnedStocks");
        leakingBucket.handeleNewRequest("anish", "SearchPinnedStocks");
        Thread.sleep(2000);
        leakingBucket.unRegister();
    }
}
