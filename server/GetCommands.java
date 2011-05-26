package server;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

public class GetCommands extends ServerCommand {
	
	
	public JSONObject requestSpecification(){
		return super.specificationMapper("request", null);
	}
	
	public JSONObject responseSpecification(){
	  HashMap data = new HashMap();
	  JSONArray commands = new JSONArray();
	  HashMap quitCommand = new HashMap();
	  quitCommand.put("command", new Quit(con).name());
	  quitCommand.put("request", new Quit(con).requestSpecification());
	  quitCommand.put("response", new Quit(con).responseSpecification());
	  commands.add(quitCommand);
	  data.put("auth_key", "String");
	  return super.specificationMapper("response", data);
	}
	
	private int resource_id;
	
	GetCommands(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_commands";
	}
	
	public boolean parseJson(JSONObject data){
		return true;
	}
	
	public void run(){
		HashMap data = new HashMap();
		List commands = new ArrayList();
  		Enumeration keys = con.serverCommands.keys();
  	    while (keys.hasMoreElements())
  	    {
  	       HashMap command = new HashMap();
  	        ServerCommand c = null;
			Class cla = (Class) con.serverCommands.get(keys.nextElement());
			try {
				c = (ServerCommand) cla.getDeclaredConstructor(con.getClass()).newInstance(con);
			}catch(Exception e){
				System.out.println("Dynamic ServerCommand loading failed");
				e.printStackTrace();
			}
  	       command.put("command", c.name());
  	       command.put("request", c.requestSpecification());
  	       command.put("response", c.responseSpecification());
  		   commands.add(command );
  		   data.put("commands", commands);
  		}
  		con.respond(this.responseName(), data);
	}
}