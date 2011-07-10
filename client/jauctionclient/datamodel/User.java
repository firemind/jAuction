package jauctionclient.datamodel;

public class User extends DataObject {
	private String name = "";
	
	public User() {
	}
	
	public User(Long id, String name) {
		setId(id);
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
