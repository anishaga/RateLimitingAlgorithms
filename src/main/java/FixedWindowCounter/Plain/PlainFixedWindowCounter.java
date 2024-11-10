package FixedWindowCounter.Plain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlainFixedWindowCounter {

    private final int maxRequestsPerSecond;

    private final Map<String, CounterManager> userServiceCounters;

    public PlainFixedWindowCounter(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
        userServiceCounters = new ConcurrentHashMap<>();
    }

    public void handleNewRequest(String user, String serviceName) {
        String userKey = getKey(user, serviceName);
        userServiceCounters.computeIfAbsent(userKey, k -> new CounterManager(maxRequestsPerSecond));
        userServiceCounters.get(userKey).decrementCounter();
    }

    private String getKey(String user, String serviceName) {
        return serviceName + ":" + user;
    }
}
