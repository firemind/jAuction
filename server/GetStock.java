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
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("user_id");
			this.resource_id 	= data.getInt("resource_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void call(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap(); 
	  		data.put("resource_id", resource_id);
	  		data.put("amount", this.con.user.getStock(resource_id));
	  		con.respond("get_stock", data);
	  	}
	}
}