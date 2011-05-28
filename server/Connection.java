package server;
import java.net.Socket;
import java.io.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.util.HashMap;
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
			System.out.println("Got command: "+s);
			try {
				json = (JSONObject) JSONSerializer.toJSON(s);
				command = json.getString("command");
				if(json != null && command != null){
					sc = this.jAuctionServer.serverCommandFactory.getCommand(command, this);
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
    
    
    /** 
     * Helper method that sends standardized JSON notification.
     * 
     * Sent string looks like this: { command: 'notify', data: {'text': 'mytext' } }
     *    
     * @param text Text to be sent
     */
    public void notify(String text){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("text", text);
		respond("notify", data);
    }
    
    /** 
     * Formats JSON Object and sends it through the connection
     * 
     *    
     * @param json Instance of net.sf.json.JSONObject;
     */
    public void send(JSONObject json){
		System.err.println("Sending Message: "+json.toString());
		out.println(json.toString());
    }
    
    /**
     * Formats command name and data hash to JSON and sends it through the socket.
     *
     *
     * Format should look like this: 
     * { command: 'command_name', data: { ... } }
     *
     * @param command Name of the command
     * @param data HashMap that is converted to JSON and sent as data attribute
     */
    @SuppressWarnings("unchecked")
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
