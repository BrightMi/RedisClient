package redisclient;

import java.util.List;

public class RedisApp {
	
	private String id;
	private String Type;
	private int maxIdle;
	private int maxTotal;
	private String auth;
	private List<RedisServer> servers;
	public String getId() {
		return id;
	}
	public String getType() {
		return Type;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public String getAuth() {
		return auth;
	}
	public List<RedisServer> getServers() {
		return servers;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setType(String type) {
		Type = type;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public void setServers(List<RedisServer> servers) {
		this.servers = servers;
	}
}
