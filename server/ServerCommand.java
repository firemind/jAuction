package server;

import java.net.Socket;
import java.util.HashMap;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

abstract class ServerCommand implements ServerCommandRules, Runnable{
	
  ServerCommand(Connection con){
	  this.con = con;	  
  }
	  
  protected JSONObject specificationMapper(String type, HashMap data){
		HashMap request = new HashMap();
		if(type.equals("request")){
			request.put("command", this.name());
		}else if(type.equals("response")){
			request.put("command", this.responseName());
		}
		if(data != null){
		  request.put("data", data);
		}
		JSONObject json = (JSONObject) JSONSerializer.toJSON(request);
		return json;
  }
  
  public String responseName(){
	  return this.name();
  }
  
  public Connection con;
  
  protected boolean authenticate(String auth_key){
  	if (this.con.user != null && this.con.user.getAuthKey().equals(auth_key)){
  		return true;
  	}else{
  		this.con.notify("wrong auth_key");
  		return false;
  	}
  }

}
