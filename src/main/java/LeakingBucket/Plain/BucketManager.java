package LeakingBucket.Plain;

import java.util.PriorityQueue;
import java.util.Queue;

public class BucketManager {

    private final Queue<Long> bucket;

    private final int maxBucketCapacity;

    public BucketManager(int maxBucketCapacity) {
        this.maxBucketCapacity = maxBucketCapacity;
        bucket = new PriorityQueue<>();
    }

    public void consumeEventFromBucket() {
        if (!bucket.isEmpty() && System.currentTimeMillis() - bucket.peek() >= 333) {
            System.out.println("Event Consumed");
            bucket.poll();
        } else if (!bucket.isEmpty()) {
//            System.out.println("No event in bucket to consume");
        }
    }

    public void insertInBucket() {
        if (bucket.size() < maxBucketCapacity) {
            bucket.add(System.currentTimeMillis());
            System.out.println("Event added in bucket");
        } else {
            System.out.println("Hey, hold on! Get a glass of water");
        }
    }

}
