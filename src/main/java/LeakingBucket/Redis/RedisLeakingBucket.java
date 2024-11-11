package LeakingBucket.Redis;

import Utils.AbstractRedisConnector;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisLeakingBucket extends AbstractRedisConnector {

    private final long processRequestAfter;

    private static final String REQUEST = ":Request";

    private static final String LEAKING_BUCKET_PATTERN = "*:LeakingBucket";

    private final int maxBucketCapacity;

    private final ExecutorService executor;

    public RedisLeakingBucket(int maxBucketCapacity) {
        super();
        this.maxBucketCapacity = maxBucketCapacity;
        this.processRequestAfter = 333;
        executor = Executors.newSingleThreadExecutor();
        initLeaking();
    }

    @Override
    public void handleNewRequest(String user, String serviceName) {
        String userKey = getUserKey(user, serviceName);
        synchronized (userKey) {
            try {
                if (asyncCommands.llen(userKey).get() < maxBucketCapacity) {
                    asyncCommands.rpush(userKey, String.valueOf(System.currentTimeMillis())).get();
                    asyncCommands.rpush(userKey + REQUEST, "User: " + user + ", ServiceName: " + serviceName).get();
                } else {
                    System.out.println("Hey, hold on! Get a glass of water. User: " + user + ", ServiceName: " + serviceName);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getUserKey(String user, String serviceName) {
        return serviceName + ":" + user + ":LeakingBucket";
    }

    private void initLeaking() {
        executor.submit(() -> {
            while (true) {
                asyncCommands.keys(LEAKING_BUCKET_PATTERN).get().forEach(userKey ->
                {
                    try {
                        if (asyncCommands.llen(userKey).get() > 0 &&
                                System.currentTimeMillis() - Long.parseLong(asyncCommands.lindex(userKey, 0).get()) >= processRequestAfter) {
                            String requestMetaData = asyncCommands.lpop(userKey + REQUEST).get();
                            asyncCommands.lpop(userKey).get();
                            // Process the request from it's metadata
                            System.out.println("requestMetaData: " + requestMetaData);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

}
