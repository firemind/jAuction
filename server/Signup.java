package server;

import java.util.HashMap;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Signup extends ServerCommand {
	
	private String username;
	private String password;
	
	Signup(Connection con){
		super(con);
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("username", "String");
		data.put("password", "String");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("success", "Boolean");
		return super.specificationMapper("response", data);
	}
	
	public String name(){
		return "signup";
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.username = data.getString("username");
			this.password = data.getString("password");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
		User u = con.jAuctionServer.addUser(username, password);
		HashMap data = new HashMap();
		if(u != null){
			  data.put("success", true);
		}else{
			  data.put("success", false);
		}
		con.respond(responseName(), data);
	}
}