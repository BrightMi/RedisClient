package redisclient;

public class RedisServer {
	
	public RedisServer() {}
	
	public RedisServer(String host, Integer port) {
		this.host = host;
		this.port = port;
	}
	
	private String host;
	private Integer port;
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
}
