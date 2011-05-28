package server;
import java.net.Socket;
import java.io.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

public class Connection implements Runnable, Observer {
    protected Socket server;
    public In in;
    public Out out;
    protected User user;
    protected JAuctionServer jAuctionServer;
    private long last_mutation = 0;
    
    Connection(Socket clientSocket, JAuctionServer jAuctionServer) {
      this.jAuctionServer = jAuctionServer;
      this.server=clientSocket;
      this.in  = new In (clientSocket);
      this.out = new Out(clientSocket);
    }

    public void update(Observable obs, Object obj){
    	MutationStore mutSto = (MutationStore) obs;
    	for (MutationStore.Mutation m : mutSto.getMutations(last_mutation)){
    		last_mutation = m.getId();
    		send(m.getJson());
    	}
    }

    
    public void run () {

        System.err.println("Accepted connection from client");
		String s;
		JSONObject json ;
		String command = null;
		ServerCommand sc = null;
		while ((s = in.readLine()) != null) {
			try {
				json = (JSONObject) JSONSerializer.toJSON(s);
				command = json.getString("command");
				if(json != null && command != null){
					if (jAuctionServer.getServerCommands().containsKey(command) == true){ 
						Class cla = (Class) jAuctionServer.getServerCommands().get(command);
						try {
						  sc = (ServerCommand) cla.getDeclaredConstructor(this.getClass()).newInstance(this);
						}catch(Exception e){
							System.out.println("Dynamic ServerCommand loading failed");
							e.printStackTrace();
						}
					}else{
						badRequest();
					}
					JSONObject data = json.getJSONObject("data");
					if(sc != null){
						if(sc.parseJson(data)){
						   Thread t = new Thread(sc);
				           t.start();
						}else{
							badRequest();
						}
						json = null;
						command = null;
						sc = null;
					}
				}
			}catch(net.sf.json.JSONException e){
				badRequest();
				System.err.println("bad request: "+s);
				json = null;
				command = null;
				sc = null;
			}
		}
		close();
        

    }
    
    public void close(){
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
    public void badRequest(){
		out.println("bad request");
    }
    
    public void notify(String text){
		HashMap data = new HashMap();
		data.put("text", text);
		respond("notify", data);
    }
    
    
    public void send(JSONObject json){
		out.println(json.toString());
    }
    
    public void respond(String command, HashMap data){
		Map response = new HashMap();
		response.put("command", command);
		if(data != null){
		  response.put("data", data);
		}
		JSONObject jsonObject = JSONObject.fromObject( response );
		send(jsonObject);
    }
    

}
