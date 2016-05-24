package redisclient;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClientFactory {
	
	private static RedisClientFactory secooRedis = new RedisClientFactory();
	
	private HashMap<String, Object> redisServers = new HashMap<String, Object>();
	
	private HashMap<String, String> redisServersAuth = new HashMap<String, String>();
	
	private RedisClientFactory () {
		initConfig();
	}

	private void initConfig() {
		try {
			XMLConfiguration xmlConfiguration = new XMLConfiguration("redis-config.xml");
			List<Object> apps = xmlConfiguration.getList("app.id");
			for (int i = 0; i < apps.size(); i++) {
				RedisApp redisApp = new RedisApp();
				redisApp.setId(xmlConfiguration.getString("app(" + i  + ").id"));
				redisApp.setType(xmlConfiguration.getString("app(" + i  + ").type"));
				redisApp.setMaxIdle(xmlConfiguration.getInteger("app(" + i  + ").maxIdle", 10000));
				redisApp.setMaxTotal(xmlConfiguration.getInteger("app(" + i  + ").maxTotal", 100));
				redisApp.setAuth(xmlConfiguration.getString("app(" + i + ").auth"));
				redisApp.setServers(new ArrayList<RedisServer>());
				
				List<Object> redisServerList = xmlConfiguration.getList("app(" + i + ").servers.server[@host]");
				for (int j = 0; j < redisServerList.size(); j++) {
					String host = xmlConfiguration.getString("app(" + i + ").servers.server(" + j + ")[@host]");
					Integer port = xmlConfiguration.getInteger("app(" + i + ").servers.server(" + j + ")[@port]", 0);
					RedisServer redisServer = new RedisServer(host, port);
					redisApp.getServers().add(redisServer);
				}
				addRedisApp(redisApp);
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addRedisApp(RedisApp redisApp) {
		if (redisApp == null || redisApp.getServers() == null || redisApp.getServers().isEmpty()) return false;
		if (redisApp.getId() == null || redisApp.getId().trim().length() == 0) return false;
		if (redisServers.containsKey(redisApp.getId())) return false;
		
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(redisApp.getMaxIdle());
		genericObjectPoolConfig.setMaxTotal(redisApp.getMaxTotal());
		genericObjectPoolConfig.setTestWhileIdle(true);
		genericObjectPoolConfig.setMinEvictableIdleTimeMillis(60000L);
		genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(30000L);
		genericObjectPoolConfig.setNumTestsPerEvictionRun(-1);
		redisServersAuth.put(redisApp.getId(), redisApp.getAuth());
		if (redisApp.getType().equals(AppType.CLUSTER.getName())) {
			Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
			for (RedisServer redisServer : redisApp.getServers()) {
				if (redisServer.getHost() != null && !redisServer.getHost().trim().equals("") && redisServer.getPort() != 0) {
					hostAndPorts.add(new HostAndPort(redisServer.getHost(), redisServer.getPort()));
				}
			}
			if (hostAndPorts.isEmpty()) return false;
			JedisCluster redisCluster = new JedisCluster(hostAndPorts, genericObjectPoolConfig);
			redisServers.put(redisApp.getId(), redisCluster);
		} else if (redisApp.getType().equals(AppType.SHARD.getName())) {
			List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
			for (RedisServer redisServer : redisApp.getServers()) {
				if (redisServer.getHost() != null && !redisServer.getHost().trim().equals("") && redisServer.getPort() != 0) {
					jedisShardInfos.add(new JedisShardInfo(redisServer.getHost(), redisServer.getPort()));
				}
			}
			if (jedisShardInfos.isEmpty()) return false;
			ShardedJedisPool shardedJedisPool = new ShardedJedisPool(genericObjectPoolConfig, jedisShardInfos);
			redisServers.put(redisApp.getId(), shardedJedisPool);
		} else if (redisApp.getType().equals(AppType.SIMPLE.getName())) {
			RedisServer redisServer = redisApp.getServers().get(0);
			if (redisServer.getHost() != null && !redisServer.getHost().trim().equals("") && redisServer.getPort() != 0) {
				JedisPool jedisPool = new JedisPool(genericObjectPoolConfig, redisServer.getHost(), redisServer.getPort());
				redisServers.put(redisApp.getId(), jedisPool);
			} else {
				return false;
			}
		}
		return true;
	}
	
	public Set<String> getAllAppId() {
		return redisServers.keySet();
	}
	
	public void removeRedisApp(String app) {
		redisServers.remove(app);
		redisServersAuth.remove(app);
	}
	
	public IRedisClient getRedisClient(String app) {
		Object redisServer = redisServers.get(app);
		if (redisServer == null) {
			throw new RedisException("redis app is not exists, please check you app " + app + "'s config");
		}
		if (redisServer instanceof JedisCluster) {
			return new RedisCluster((JedisCluster)redisServer);
		} else if (redisServer instanceof ShardedJedisPool) {
			ShardedJedisPool shardedJedisPool = (ShardedJedisPool) redisServer;
			ShardedRedis shardedRedis = new ShardedRedis(shardedJedisPool);
			PoolInvocationHandler handler = new PoolInvocationHandler(shardedRedis);
			IRedisClient redisClient = (IRedisClient) Proxy.newProxyInstance(handler.getClass().getClassLoader(), shardedRedis.getClass().getInterfaces(), handler);
			return redisClient;
		} else if (redisServer instanceof JedisPool) {
			JedisPool jedisPool = (JedisPool) redisServer;
			SimpleRedis simpleRedis = new SimpleRedis(jedisPool);
			String auth = redisServersAuth.get(app);
			if (auth != null) {
				simpleRedis.setAuth(auth);
			}
			PoolInvocationHandler handler = new PoolInvocationHandler(simpleRedis);
			IRedisClient redisClient =  (IRedisClient) Proxy.newProxyInstance(handler.getClass().getClassLoader(), simpleRedis.getClass().getInterfaces(), handler);
			return redisClient;
		} else {
			throw new RedisException("redis app type is not exists, app id is " + app);
		}
	}
	
	public static RedisClientFactory getInstance() {
		return secooRedis;
	}

}
