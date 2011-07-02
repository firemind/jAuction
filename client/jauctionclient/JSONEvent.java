package jauctionclient;

import java.util.EventObject;
import org.json.JSONObject;

public class JSONEvent extends EventObject { 
	private JSONObject json; 

	public JSONEvent( Object source, JSONObject json ) { 
		super( source ); 
		this.json = json; 
	} 
 
	public JSONObject getJson() { return this.json; }
}
