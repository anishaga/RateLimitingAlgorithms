package TokenBucket.Plain;

public class CounterManager {

    private int counter;

    private final int maxCounter;

    public CounterManager(int maxCounter) {
        this.maxCounter = maxCounter;
    }

    public synchronized void incrementCounter() {
        if (counter < maxCounter) {
            ++counter;
        }
    }

    public synchronized void decrementCounter() {
        if (counter > 0) {
            --counter;
        } else {
            System.out.println("Hey, hold on! Get a glass of water");
        }
    }

}
