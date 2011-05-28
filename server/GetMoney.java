package server;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetMoney extends ServerCommand {
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("auth_key", "String");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("money", "Integer");
		return super.specificationMapper("request", data);
	}
	
	private String auth_key;
	
	GetMoney(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_money";
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("auth_key");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
    

	public void run(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap(); 
    		data.put("money", this.con.user.getMoney());
	  		con.respond(this.responseName(), data);
	  	}
	}
}