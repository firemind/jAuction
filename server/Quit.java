package server;

import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class Quit extends ServerCommand {
	
	Quit(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock";
	}
	
	public boolean parseJson(JSONObject data){
		return true;
	}
	
	public void call(){
		con.close();
	}
}