package server.ServerCommands;

import java.util.HashMap;

import server.Connection;



import net.sf.json.JSONObject;

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
		data.put("user_id", "long");
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
		String auth_key = null;
		HashMap data = new HashMap();
		if(this.con.user != null){
			this.con.user.con = this.con;
			auth_key =  con.user.getAuthKey();
			data.put("user_id", con.user.getId());
		}
		data.put("auth_key", auth_key);
		con.respond(responseName(), data);
	}
}