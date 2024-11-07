package TokenBucket.Plain;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        PlainTokenBucket obj = new PlainTokenBucket(3);
        obj.handleNewRequest("s1", "u1");
        obj.handleNewRequest("s1", "u1");
        obj.handleNewRequest("s1", "u1");
        obj.handleNewRequest("s2", "u1");
        obj.handleNewRequest("s2", "u1");
        obj.handleNewRequest("s1", "u2");
        obj.handleNewRequest("s2", "u2");
        obj.handleNewRequest("s1", "u2");
        obj.handleNewRequest("s1", "u2");
        obj.handleNewRequest("s1", "u2");
        obj.handleNewRequest("s1", "u2");
        Thread.sleep(10000);
        obj.handleNewRequest("s1", "u1");
        obj.shutdown();
    }
}
