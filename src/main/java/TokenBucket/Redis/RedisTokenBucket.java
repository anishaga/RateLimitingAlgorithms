package TokenBucket.Redis;

import Utils.AbstractRedisConnector;

public class RedisTokenBucket extends AbstractRedisConnector {


    private static final String REFILL_TIME = ":refillTime";
    private static final String NUM_TOKENS_IN_BUCKET = ":numTokensInBucket";

    private final long newTokenAfter;

    private final int maxBucketCapacity;

    public RedisTokenBucket(int maxBucketCapacity) {
        super();
        this.maxBucketCapacity = maxBucketCapacity;
        this.newTokenAfter = 333;
    }

    @Override
    public void handleNewRequest(String user, String serviceName) {
        String userKey = getKey(user, serviceName);
        synchronized (userKey) {
            try {
                String lastRefillTime = asyncCommands.get(userKey + REFILL_TIME).get();
                String numTokensInBucket = asyncCommands.get(userKey + NUM_TOKENS_IN_BUCKET).get();
                long lastRefill = lastRefillTime != null ? Long.parseLong(lastRefillTime) : System.currentTimeMillis() - 1000;
                int numTokensInBucketOld = numTokensInBucket == null ? 0 : Integer.parseInt(numTokensInBucket);
                long numTokenInBucketNew = Math.min(maxBucketCapacity,
                        numTokensInBucketOld + ((System.currentTimeMillis() - lastRefill) / newTokenAfter));

                if (numTokenInBucketNew > 0) {
                    --numTokenInBucketNew;
                    asyncCommands.set(userKey + REFILL_TIME, String.valueOf(System.currentTimeMillis()));
                    asyncCommands.set(userKey + NUM_TOKENS_IN_BUCKET, String.valueOf(numTokenInBucketNew));
                    System.out.println("Request Accepted " + user + ", ServiceName: " + serviceName);
                    asyncCommands.expire(userKey + REFILL_TIME, (100 + (newTokenAfter * maxBucketCapacity)) / 1000);
                } else {
                    System.out.println("Hey, hold on! Get a glass of water. User: " + user + ", ServiceName: " + serviceName);
                }

            } catch (Exception e) {
                System.out.println("Exception Occurred: " + e.getMessage());
            }
        }

    }

    private String getKey(String user, String serviceName) {
        return serviceName + ":" + user;
    }

}
