package jauctionclient.datamodel;

public abstract class DataObject extends AbstractModelObject {
	private Long id;

	public Long getId() {
		return this.id; 
	}
	protected void setId(Long id) {
		this.id = id; 
	}
}