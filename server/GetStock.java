package server;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetStock extends ServerCommand {
	
	private String auth_key;
	private int resource_id;
	
	GetStock(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("resource_id", "Integer");
		data.put("auth_key", "String");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("resource_id", "Integer");
		data.put("amount", "Integer");
		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("auth_key");
			this.resource_id 	= data.getInt("resource_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap(); 
	  		data.put("resource_id", resource_id);
	  		data.put("amount", this.con.user.getStock(resource_id));
	  		con.respond(this.responseName(), data);
	  	}
	}
}