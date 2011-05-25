package server;
import java.net.Socket;
import java.io.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class Connection implements Runnable {
    private Socket server;
    private String line,input;
    public In in;
    public Out out;
    private User user;
    Connection(Socket clientSocket) {
      this.server=clientSocket;
      this.in  = new In (clientSocket);
      this.out = new Out(clientSocket);
    }

    public void run () {
		String s;
		JSONObject json ;
		String command = null;

		while ((s = in.readLine()) != null) {
			try {
				json = (JSONObject) JSONSerializer.toJSON(s);
				command = json.getString("command");
			}catch(net.sf.json.JSONException e){
				out.println("bad request: "+s);
				json = null;
				command = null;
			}
			try {
				if(json != null && command != null){
					JSONObject data = json.getJSONObject("data");
					if (command.equals("get_money")){ 
						getMoney(data.getString("user_id"));
				    }else if (command.equals("get_stock")){
				    	getStock(data.getString("user_id"), data.getInt("resource_id"));
				    }else if (command.equals("login")){
					  login(data.getString("username"), data.getString("password"));
					}else if (command.equals("quit")){
						out.println("goodbye!");
						break;
					}else{
						out.println("bad command: "+command);
					}
				}
			}catch(net.sf.json.JSONException e){
				out.println("bad request: "+s);
				json = null;
				command = null;
			}
		}
        System.err.println("Closing connection with client");
        out.close();
        in.close();
        try {
        	server.close();
	    } catch (IOException ioe) {
		      System.out.println("IOException on socket listen: " + ioe);
		      ioe.printStackTrace();
		}  
    }
    
    private boolean authenticate(String user_id){
    	if (this.user != null && this.user.getUserId().equals(user_id)){
    		return true;
    	}else{
    		notify("wrong user_id");
    		return false;
    	}
    }
    
    private void getStock(String user_id, int resource_id){
    	if(authenticate(user_id)){
    		HashMap data = new HashMap(); 
    		data.put("resource_id", resource_id);
    		data.put("amount", this.user.getStock(resource_id));
    		respond("get_stock", data);
    	}
    }
    
    private void getMoney(String user_id){
    	if(authenticate(user_id)){
    		HashMap data = new HashMap(); 
    		data.put("money", this.user.getMoney());
    		respond("get_money", data);
    	}
    }
    
    private void notify(String text){
		HashMap data = new HashMap();
		data.put("text", text);
		respond("notify", data);
    }
    
    private void respond(String command, HashMap data){
		Map response = new HashMap(); 
		response.put("command", command);
		response.put("data", data);
		JSONObject jsonObject = JSONObject.fromObject( response );
		out.println(jsonObject.toString());
    }
    
    private void login(String username, String password){	
		this.user = new User(username, password);
		HashMap data = new HashMap();
		data.put("user_id", user.getUserId());
		respond("login", data);
    }
}
