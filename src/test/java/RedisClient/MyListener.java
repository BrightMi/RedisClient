package RedisClient;

import redisclient.RedisPubsub;

public class MyListener extends RedisPubsub {
	// 取得订阅的消息后的处理  
	@Override
	public void onMessage(String channel, String message) {
		super.onMessage(channel, message);
		System.out.println(channel + "=" + message); 
	}
	// 取得按表达式的方式订阅的消息后的处理 
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		super.onPMessage(pattern, channel, message);
		System.out.println(pattern + "=" + channel + "=" + message);  
	}
	// 初始化订阅时候的处理  
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		super.onSubscribe(channel, subscribedChannels);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		super.onUnsubscribe(channel, subscribedChannels);
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		super.onPUnsubscribe(pattern, subscribedChannels);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		super.onPSubscribe(pattern, subscribedChannels);
	}

}
