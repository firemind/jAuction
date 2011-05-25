package server;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetMoney extends ServerCommand {
	
	private String auth_key;
	
	GetMoney(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock";
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("user_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
    

	public void call(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap(); 
    		data.put("money", this.con.user.getMoney());
	  		con.respond("get_stock", data);
	  	}
	}
}