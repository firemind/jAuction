package server;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class Login extends ServerCommand {
	
	private String username;
	private String password;
	
	Login(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock";
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
	
	public void call(){
		this.con.user = new User(username, password);
		HashMap data = new HashMap();
		data.put("user_id", con.user.getUserId());
		con.respond("login", data);
	}
}