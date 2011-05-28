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
		data.put("auth_key", "String");
		return super.specificationMapper("response", data);
	}
	
	public String name(){
		return "login";
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
		this.con.user = con.jAuctionServer.addUser(username, password);
		HashMap data = new HashMap();
		data.put("auth_key", con.user.getAuthKey());
		con.respond(responseName(), data);
	}
}