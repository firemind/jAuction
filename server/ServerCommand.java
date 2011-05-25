package server;

import java.net.Socket;
import java.util.HashMap;

import net.sf.json.JSONObject;

abstract class ServerCommand implements ServerCommandRules{
	
  ServerCommand(Connection con){
	  this.con = con;	  
  }
	  
  public Connection con;
  
  protected boolean authenticate(String user_id){
  	if (this.con.user != null && this.con.user.getUserId().equals(user_id)){
  		return true;
  	}else{
  		this.con.notify("wrong user_id");
  		return false;
  	}
  }

}
