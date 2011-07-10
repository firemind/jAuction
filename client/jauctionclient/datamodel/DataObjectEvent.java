package jauctionclient.datamodel;

import java.util.EventObject;

public class DataObjectEvent extends EventObject {
	private Long id; 
	private String class_name;

	public DataObjectEvent ( Object source, Long id, String class_name) { 
		super( source );
		this.class_name = class_name;
		this.id = id; 
	} 
 
	public Long getId() { return id; }

	public String getClassName() { return class_name; }
}
