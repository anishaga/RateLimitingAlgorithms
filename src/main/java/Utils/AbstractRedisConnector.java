package Utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

public abstract class AbstractRedisConnector {

    protected RedisAsyncCommands<String, String> asyncCommands;

    public AbstractRedisConnector() {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        this.asyncCommands = connection.async();
    }

    public abstract void handleNewRequest(String user, String serviceName);
}
