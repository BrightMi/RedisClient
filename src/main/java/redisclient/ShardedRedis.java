package redisclient;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

public class ShardedRedis implements IRedisClient {

	private ShardedJedisPool shardedJedisPool;
	
	private ShardedJedis shardedJedis;

	public void setShardedJedis(ShardedJedis shardedJedis) {
		this.shardedJedis = shardedJedis;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public ShardedRedis(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	@Override
	public Long append(String key, String value) {
		return shardedJedis.append(key, value);
	}

	@Override
	public Long bitcount(String key) {
		return shardedJedis.bitcount(key);
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		return shardedJedis.bitcount(key, start, end);
	}

	@Override
	public Long bitpos(String key, boolean value) {
		return shardedJedis.bitpos(key, value);
	}

	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		return shardedJedis.bitpos(key, value);
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		return shardedJedis.blpop(timeout, key);
	}

	@Override
	public String set(String key, String value) {
		return shardedJedis.set(key, value);
	}

	@Override
	public String get(String key) {
		return shardedJedis.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return shardedJedis.exists(key);
	}

	@Override
	public Long persist(String key) {
		return shardedJedis.persist(key);
	}

	@Override
	public String type(String key) {
		return shardedJedis.type(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return shardedJedis.expire(key, seconds);
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		return shardedJedis.pexpire(key, milliseconds);
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		return shardedJedis.expireAt(key, unixTime);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		return shardedJedis.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long ttl(String key) {
		return shardedJedis.ttl(key);
	}

	@Override
	public Long pttl(String key) {
		return shardedJedis.pttl(key);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		return shardedJedis.setbit(key, offset, value);
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		return shardedJedis.setbit(key, offset, value);
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return shardedJedis.getbit(key, offset);
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return shardedJedis.setrange(key, offset, value);
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return shardedJedis.getrange(key, startOffset, endOffset);
	}

	@Override
	public String getSet(String key, String value) {
		return shardedJedis.getSet(key, value);
	}

	@Override
	public Long setnx(String key, String value) {
		return shardedJedis.setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return shardedJedis.setex(key, seconds, value);
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {
		return shardedJedis.psetex(key, milliseconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return shardedJedis.decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {
		return shardedJedis.decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		return shardedJedis.incrBy(key, integer);
	}

	@Override
	public Double incrByFloat(String key, double value) {
		return shardedJedis.incrByFloat(key, value);
	}

	@Override
	public Long incr(String key) {
		return shardedJedis.incr(key);
	}

	@Override
	public String substr(String key, int start, int end) {
		return shardedJedis.substr(key, start, end);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return shardedJedis.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return shardedJedis.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return shardedJedis.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return shardedJedis.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return shardedJedis.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return shardedJedis.hincrBy(key, field, value);
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) {
		return shardedJedis.hincrByFloat(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {
		return shardedJedis.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return shardedJedis.hdel(key, field);
	}

	@Override
	public Long hlen(String key) {
		return shardedJedis.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return shardedJedis.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return shardedJedis.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return shardedJedis.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... string) {
		return shardedJedis.rpush(key, string);
	}

	@Override
	public Long lpush(String key, String... string) {
		return shardedJedis.lpush(key, string);
	}

	@Override
	public Long llen(String key) {
		return shardedJedis.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return shardedJedis.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return shardedJedis.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return shardedJedis.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		return shardedJedis.lset(key, index, value);

	}

	@Override
	public Long lrem(String key, long count, String value) {
		return shardedJedis.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		return shardedJedis.lpop(key);
	}

	@Override
	public String rpop(String key) {
		return shardedJedis.rpop(key);
	}

	@Override
	public Long sadd(String key, String... member) {
		return shardedJedis.sadd(key, member);
	}

	@Override
	public Set<String> smembers(String key) {
		return shardedJedis.smembers(key);
	}

	@Override
	public Long srem(String key, String... member) {
		return shardedJedis.srem(key, member);
	}

	@Override
	public String spop(String key) {
		return shardedJedis.spop(key);
	}

	@Override
	public Set<String> spop(String key, long count) {
		return shardedJedis.spop(key, count);
	}

	@Override
	public Long scard(String key) {
		return shardedJedis.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return shardedJedis.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return shardedJedis.srandmember(key);
	}

	@Override
	public List<String> srandmember(String key, int count) {
		return shardedJedis.srandmember(key, count);
	}

	@Override
	public Long strlen(String key) {
		return shardedJedis.strlen(key);
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return shardedJedis.zadd(key, score, member);
	}

	@Override
	public Long zadd(String key, double score, String member, ZAddParams params) {
		return shardedJedis.zadd(key, score, member, params);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return shardedJedis.zadd(key, scoreMembers);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
		return shardedJedis.zadd(key, scoreMembers, params);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return shardedJedis.zrange(key, start, end);
	}

	@Override
	public Long zrem(String key, String... member) {
		return shardedJedis.zrem(key, member);
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		return shardedJedis.zincrby(key, score, member);
	}

	@Override
	public Double zincrby(String key, double score, String member, ZIncrByParams params) {
		return shardedJedis.zincrby(key, score, member, params);
	}

	@Override
	public Long zrank(String key, String member) {
		return shardedJedis.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return shardedJedis.zrevrank(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return shardedJedis.zrevrange(key, start, end);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return shardedJedis.zrangeWithScores(key, start, end);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return shardedJedis.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Long zcard(String key) {
		return shardedJedis.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) {
		return shardedJedis.zscore(key, member);
	}

	@Override
	public List<String> sort(String key) {
		return shardedJedis.sort(key);
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		return shardedJedis.sort(key, sortingParameters);
	}

	@Override
	public Long zcount(String key, double min, double max) {
		return shardedJedis.zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return shardedJedis.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return shardedJedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return shardedJedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return shardedJedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return shardedJedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return shardedJedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return shardedJedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return shardedJedis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return shardedJedis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		return shardedJedis.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return shardedJedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		return shardedJedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zlexcount(String key, String min, String max) {
		return shardedJedis.zlexcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {
		return shardedJedis.zrangeByLex(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		return shardedJedis.zrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		return shardedJedis.zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		return shardedJedis.zrevrangeByLex(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) {
		return shardedJedis.zremrangeByLex(key, min, max);
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		return shardedJedis.linsert(key, where, pivot, value);
	}

	@Override
	public Long lpushx(String key, String... string) {
		return shardedJedis.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String... string) {
		return shardedJedis.rpushx(key, string);
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		return shardedJedis.brpop(timeout, key);
	}

	@Override
	public Long del(String key) {
		return shardedJedis.del(key);
	}

	@Override
	public String echo(String string) {
		return shardedJedis.echo(string);
	}

	@Override
	public Long move(String key, int dbIndex) {
		return shardedJedis.move(key, dbIndex);
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		return shardedJedis.hscan(key, cursor);
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
		return shardedJedis.hscan(key, cursor, params);
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		return shardedJedis.sscan(key, cursor);
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		return shardedJedis.zscan(key, cursor);
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		return shardedJedis.zscan(key, cursor, params);
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		return shardedJedis.sscan(key, cursor, params);
	}

	@Override
	public Long pfadd(String key, String... elements) {
		return shardedJedis.pfadd(key, elements);
	}

	@Override
	public long pfcount(String key) {
		return shardedJedis.pfcount(key);
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, String member) {
		return shardedJedis.geoadd(key, longitude, latitude, member);
	}

	@Override
	public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		return shardedJedis.geoadd(key, memberCoordinateMap);
	}

	@Override
	public Double geodist(String key, String member1, String member2) {
		return shardedJedis.geodist(key, member1, member2);
	}

	@Override
	public Double geodist(String key, String member1, String member2, GeoUnit unit) {
		return shardedJedis.geodist(key, member1, member2, unit);
	}

	@Override
	public List<String> geohash(String key, String... members) {
		return shardedJedis.geohash(key, members);
	}

	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		return shardedJedis.geopos(key, members);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
		return shardedJedis.georadius(key, longitude, latitude, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
		return shardedJedis.georadius(key, longitude, latitude, radius, unit, param);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
		return shardedJedis.georadiusByMember(key, member, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
		return shardedJedis.georadiusByMember(key, member, radius, unit, param);
	}

	@Override
	public Long publish(String channel, String message) {
		throw new RedisException("sharded redis does not supply pubsub funciton.");
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		throw new RedisException("sharded redis does not supply pubsub funciton.");
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		throw new RedisException("sharded redis does not supply pubsub funciton.");
	}

}