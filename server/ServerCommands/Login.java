package server.ServerCommands;

import java.util.HashMap;

import server.Connection;



import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Login extends ServerCommand {
	
	private String username;
	private String password;
	
	Login(Connection con){
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
		this.con.user = con.jAuctionServer.authenticateUser(username, password);
		this.con.user.con = this.con;
		String auth_key = null;
		if(this.con.user != null){
			auth_key =  con.user.getAuthKey();
		}
		HashMap data = new HashMap();
		data.put("auth_key", auth_key);
		con.respond(responseName(), data);
	}
}