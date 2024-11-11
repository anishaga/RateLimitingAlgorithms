package FixedWindowCounter.Redis;

import Utils.AbstractRedisConnector;

import java.util.concurrent.ExecutionException;

public class RedisFixedWindowCounter extends AbstractRedisConnector {

    private final int maxRequestsPerSecond;

    public RedisFixedWindowCounter(int maxRequestsPerSecond) {
        super();
        this.maxRequestsPerSecond = maxRequestsPerSecond;
    }

    @Override
    public void handleNewRequest(String user, String serviceName) {
        String userKey = getKey(user, serviceName);
        synchronized (userKey) {
            try {
                String lastRefillTimeStr = asyncCommands.lindex(userKey, 0).get();
                long lastRefillTime = lastRefillTimeStr != null ? Long.parseLong(lastRefillTimeStr) : 0;
                int oldCounter = lastRefillTimeStr != null ? Integer.parseInt(asyncCommands.lindex(userKey, 1).get()) : 0;
                long currentTime = System.currentTimeMillis();
                boolean isEligibleForRefill = currentTime - lastRefillTime >= 1000;
                int newCounter = isEligibleForRefill ? maxRequestsPerSecond : oldCounter;

                if (newCounter > 0) {
                    --newCounter;
                    if (isEligibleForRefill) {
                        asyncCommands.lpop(userKey).get();
                        asyncCommands.lpush(userKey, String.valueOf(currentTime)).get();
                    }
                    if (asyncCommands.lindex(userKey, 1).get() != null) {
                        asyncCommands.rpop(userKey).get();
                    }
                    asyncCommands.rpush(userKey, String.valueOf(newCounter)).get();
                    asyncCommands.expire(userKey, 1).get();
                    System.out.println("Request Accepted " + user + ", ServiceName: " + serviceName);
                } else {
                    System.out.println("Hey, hold on! Get a glass of water. User: " + user + ", ServiceName: " + serviceName);
                }

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String getKey(String user, String serviceName) {
        return serviceName + ":" + user;
    }
}
