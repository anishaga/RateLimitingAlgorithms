package LeakingBucket.Plain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlainLeakingBucket {

    private final int maxBucketCapacity;

    private final Map<String, BucketManager> userServiceCounters;

    private final ExecutorService executorService;

    private volatile boolean isRunning;

    public PlainLeakingBucket(int maxBucketCapacity) {
        this.maxBucketCapacity = maxBucketCapacity;
        userServiceCounters = new HashMap<>();
        executorService = Executors.newSingleThreadExecutor();
        init();
    }

    public void handeleNewRequest(String user, String serviceName) {
        BucketManager bucketManager;
        if (!userServiceCounters.containsKey(serviceName + ":" + user)) {
            userServiceCounters.put(serviceName + ":" + user, new BucketManager(maxBucketCapacity));
        }
        bucketManager = userServiceCounters.get(serviceName + ":" + user);
        bucketManager.insertInBucket();
    }

    public void unRegister() {
        System.out.println("Shutting down executor service");
        isRunning = false;
        executorService.shutdownNow();
        System.out.println("Executor service is stopped successfully");
    }

    private void init() {
        isRunning = true;
        executorService.submit(() -> {
            while (isRunning) {
                userServiceCounters.values().forEach(BucketManager::consumeEventFromBucket);
            }
        });
    }
}
