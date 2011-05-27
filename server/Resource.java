package server;

public class Resource {
	private String name;
	private long id;
	
	public long getId(){
		return this.id;
	}
	
	Resource(long id, String n){
		this.id = id;
		this.name = n;
	}
	
	public String getName(){
		return this.name;
	}
}
