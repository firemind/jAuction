package jauctionclient.datamodel;

public class Resource extends DataObject {
	private String name = "";

	public Resource() {
	}
	
	public Resource(Long id, String name) {
		setId(id);
		this.name = name;
	}
	
	public String getName()	{
		return name; 
	}
	
	public void setName(String name) {
		this.name = name; 
	}
	
	@Override
	public String toString() {
		return name;
	}
}
