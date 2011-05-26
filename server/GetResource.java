package server;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetResource extends ServerCommand {
	
	private int resource_id;
	
	GetResource(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_resource";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("resource_id", "Integer");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("resource_id", "Integer");
		data.put("name", "String");
		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		try {;
			this.resource_id 	= data.getInt("resource_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
	  		HashMap data = new HashMap();
	  		Resource resource;
	  		if((con.getResources().size() > resource_id )&& (resource_id >= 0) && 
	  				(resource = con.getResources().get(resource_id)) != null){
	  			data.put("resource_id", resource_id);
	  			data.put("name", resource.getName());
	  		}
	  		con.respond(this.responseName(), data);
	}
}