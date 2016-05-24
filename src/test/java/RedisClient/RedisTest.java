package RedisClient;

import static org.junit.Assert.*;

import org.junit.Test;

import redisclient.IRedisClient;
import redisclient.RedisClientFactory;

public class RedisTest {

	@Test
	public void testCluster() {
		
		IRedisClient redisClusterClient = RedisClientFactory.getInstance().getRedisClient("redisCluster");
		redisClusterClient.set("testCluster", "true");
		
//		for (int j = 0; j < 10; j++) {
//			System.out.println((j + 1) + "st...............");
//			long startTime = new java.util.Date().getTime();
//			System.out.println("start time is " + startTime);
//			for (int i = 0; i < 50000; i++) {
//				redisClusterClient.set("testCluster11", "13");
//			}
//			long endTime = new java.util.Date().getTime();
//			System.out.println("end time is " + endTime + ", using " + (endTime - startTime)); 
//		}
	}
	
	@Test
	public void testShard() {
		IRedisClient shardRedisClient = RedisClientFactory.getInstance().getRedisClient("shardRedis");
		shardRedisClient.set("testShard", "true");
		
		System.out.println(shardRedisClient.get("testShard"));
	}

	@Test
	public void testSimple() {
		IRedisClient simpleRedisClient = RedisClientFactory.getInstance().getRedisClient("simpleRedis");
		//simpleRedisClient.set("testSimple", "true");
		//System.out.println(simpleRedisClient.lpush("list", new String[] { "1", "2", "3" }));
		System.out.println(simpleRedisClient.lpush("list", "4"));
	}
	
	@Test
	public void testPublish() {
		IRedisClient redisClient = RedisClientFactory.getInstance().getRedisClient("simpleRedis");
		redisClient.publish("hello_test", "aaaabbbb");
	}
}
