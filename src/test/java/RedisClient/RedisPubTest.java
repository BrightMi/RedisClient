package RedisClient;

import redisclient.IRedisClient;
import redisclient.RedisClientFactory;

public class RedisPubTest {
	
	public static void main(String[] args) {
		IRedisClient shardRedisClient = RedisClientFactory.getInstance().getRedisClient("shardRedis");
		shardRedisClient.set("", "");
		
		IRedisClient redisClient = RedisClientFactory.getInstance().getRedisClient("simpleRedis");
		MyListener myListener = new MyListener();
		redisClient.psubscribe(myListener, new String[] { "hello_*" });
	}
}
