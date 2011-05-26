package server;

import junit.framework.TestCase;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.lang.ClassNotFoundException;
import java.io.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

public class JAuctionServerTest extends TestCase {
	//private ServerThread server;
	//private Thread t;
    
	InetAddress host;
	Socket socket;
	PrintWriter printWriter;
	protected void setUp() throws Exception {
		//server = new ServerThread();
		//t = new Thread(server);
		host = InetAddress.getLocalHost();
		socket = new Socket(host.getHostName(), 4444);
		printWriter =
            new PrintWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()));
	}

	protected void tearDown() throws Exception {
		//t.stop();
		printWriter.close();
		super.tearDown();
		//System.out.println("Test finished");
	}
	
	public void testMain() {

		assertFalse(socket.isClosed());
		sendQuit();
		jsonParse(readResponse());
		
	}
	
	public boolean jsonParse(String jsonString){
		  try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonString);
			  assert(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  return true;
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_commands");
			  return false;
		  }
	}
	
	public void testGetCommands() {
		
        printWriter.println("{command:'get_commands'}");
        printWriter.flush();

	
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse());
			  assert(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assert(json.containsKey("commands"));
			  JSONArray commands = json.getJSONArray("commands");
			  for( int i = 0; i < commands.size(); i++){
				  json = commands.getJSONObject(i);
				  JSONObject response = json.getJSONObject("response");
				  JSONObject request = json.getJSONObject("request");
			  }
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_commands");
		  }
		sendQuit();
		in.close();
		printWriter.close();
		
	}

	public String readResponse(){
        In in = new In (socket);
		String message;
		message = in.readLine();
		in.close();
		return  message;
	}
	
	private void sendQuit(){
        printWriter.println("{command:'quit'}");
        printWriter.flush();
		
	}
}

class ServerThread implements Runnable {
    private static int port = 4444;
    private static int maxConnections = 0;
    public static Hashtable serverCommands = new Hashtable();
    
	public void run(){
		
    	serverCommands.put("login", Login.class);
    	serverCommands.put("get_money", GetMoney.class);
    	serverCommands.put("get_stock", GetStock.class);
    	serverCommands.put("quit", Quit.class);
    	
		
	    System.err.println("Started server on port " + port);
	
	    int con_counter=0;
	
	    try{
	      ServerSocket serverSocket = new ServerSocket(port);
	      Socket clientSocket;
	
	      while((con_counter++ < maxConnections) || (maxConnections == 0)){
	
	        clientSocket = serverSocket.accept();
	        System.err.println("Accepted connection from client");
	        
	        Connection conn_c= new Connection(clientSocket, serverCommands);
	        Thread t = new Thread(conn_c);
	        t.start();
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }  
	}    
}