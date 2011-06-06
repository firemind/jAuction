package server.ServerCommands;

import java.io.IOException;
import java.util.HashMap;

import server.Connection;

import net.sf.json.JSONObject;

public class Quit extends ServerCommand {
	
	public JSONObject requestSpecification(){
		return super.specificationMapper("request", null);
	}
	
	public JSONObject responseSpecification(){
		return super.specificationMapper("response", null);
	}
	
	Quit(server.Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock";
	}
	
	public boolean parseJson(JSONObject data){
		return true;
	}
	
	public void run(){
  		HashMap data = new HashMap(); 
  		con.respond("quit", (HashMap) null);
		con.close();
	}
}