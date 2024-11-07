package TokenBucket.Plain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlainTokenBucket {

    private Map<String, CounterManager> userServiceCounters;

    private ExecutorService executorService;

    private final int rateLimit;

    public PlainTokenBucket(int rateLimit) {
        executorService = Executors.newSingleThreadExecutor();
        this.rateLimit = rateLimit;
        userServiceCounters = new HashMap<>();
        init();
    }

    public void handleNewRequest(String serviceName, String user){
        CounterManager manager = null;
        if (!userServiceCounters.containsKey(serviceName + ":" + user)){
            registerServiceForUser(serviceName, user);
        }
        manager = userServiceCounters.get(serviceName + ":" + user);
        manager.decrementCounter();
    }

    public void shutdown() {
        executorService.shutdownNow();
    }

    private void registerServiceForUser(String serviceName, String user) {
        userServiceCounters.put(serviceName + ":" + user, new CounterManager(rateLimit));
    }

    private void init() {

        executorService.submit((Runnable) () -> {
            while (true) {
                userServiceCounters.values().forEach(CounterManager::incrementCounter);
                sleep();
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(333);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
