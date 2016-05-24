package redisclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class PoolInvocationHandler implements InvocationHandler {
	
	private Object target;
	
	public PoolInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (target instanceof ShardedRedis) {
			ShardedRedis shardedRedis = (ShardedRedis) target;
			ShardedJedisPool shardedJedisPool = shardedRedis.getShardedJedisPool();
			ShardedJedis shardedJedis = null;
			Object result = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				shardedRedis.setShardedJedis(shardedJedis);
				result = method.invoke(target, args);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (shardedJedis != null) {
					shardedJedis.close();
				}
			}
			return result;
		} else if (target instanceof SimpleRedis) {
			SimpleRedis simpleRedis = (SimpleRedis) target;
			JedisPool jedisPool = simpleRedis.getJedisPool();
			Jedis jedis = null;
			Object result = null;
			try {
				jedis = jedisPool.getResource();
				simpleRedis.setJedis(jedis);
				String auth = simpleRedis.getAuth();
				if (auth != null && auth.trim().length() != 0) {
					jedis.auth(auth);
				}
				result = method.invoke(target, args);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
			return result;
		}
		return null;
	}

}
