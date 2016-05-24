package redisclient;

public enum AppType {
	CLUSTER("cluster"),
	SHARD("shard"),
	SIMPLE("simple");
	
	AppType(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
