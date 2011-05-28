package server;

import junit.framework.TestCase;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.lang.ClassNotFoundException;
import java.io.*;
import net.sf.json.JsonConfig;

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

        In in = new In (socket);
		jsonParse(readResponse(in));
		in.close();
		
	}
	
	public boolean jsonParse(String jsonString){
		  try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonString);
			  assert(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  return true;
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON");
			  return false;
		  }
	}
	
	public void testGetCommands() {
		
        printWriter.println("{command:'get_commands'}");
        printWriter.flush();


        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("commands"));
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
		in.close();
		sendQuit();
		printWriter.close();
		
	}

	public void testLoginAndGetMoney(){
        printWriter.println("{command:'login', data: {username: 'mark', password: 'secret'}}");
        printWriter.flush();
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auth_key"));
			  String auth_key = json.getString("auth_key");
		
			  printWriter.println("{command:'get_money', data: {auth_key: '"+auth_key+"'}}");
		      printWriter.flush();
		      json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("money"));
			  int money = json.getInt("money");
			  assertEquals(money, 5000);
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON");
		  }
		in.close();
		sendQuit();
	}


	public void testLoginAndGetStock(){
        sendJson("{command:'login', data: {username: 'mark', password: 'secret'}}");
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auth_key"));
			  String auth_key = json.getString("auth_key");
			  
			  
			  sendJson("{command:'get_stock', data: {resource_id: 1, auth_key: '"+auth_key+"'}}");
		      json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("amount"));
			  int amount = json.getInt("amount");
			  assertEquals(amount, 50);
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON");
		  }
		in.close();
		sendQuit();
	}	

	public void testLoginAndGetStockAll(){
        sendJson("{command:'login', data: {username: 'mark', password: 'secret'}}");
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auth_key"));
			  String auth_key = json.getString("auth_key");
			  
			  
			  sendJson("{command:'get_stock_all', data: {auth_key: '"+auth_key+"'}}");
		      json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  JSONArray stocks = json.getJSONArray("stocks");
			  assertTrue(stocks.size() > 1);
			  for( int i = 0; i < stocks.size(); i++){
				  json = stocks.getJSONObject(i);
				  assertTrue(json.containsKey("amount"));
				  int amount = json.getInt("amount");
			  };
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON");
		  }
		in.close();
		sendQuit();
	}	
	
	public void testGetResourceAll() {

        printWriter.println("{command:'get_resource_all'}");
        printWriter.flush();

	
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("resources"));
			  JSONArray stocks = json.getJSONArray("resources");
			  assertTrue(stocks.size() > 2);
			  for( int i = 0; i < stocks.size(); i++){
				  json = stocks.getJSONObject(i);
				  assertTrue(json.containsKey("name"));
				  assertTrue(json.containsKey("resource_id"));
			  }
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_resource_all");
		  }
		sendQuit();
		in.close();
		printWriter.close();
		
	}	

	public void testGetAuctions() {

        sendJson("{command:'get_auctions', data: {resource_id: 2}}");
	
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auctions"));
			  JSONArray auctions = json.getJSONArray("auctions");
			  assertTrue(auctions.size() == 2);
			  for( int i = 0; i < auctions.size(); i++){
				  json = auctions.getJSONObject(i);
				  assertTrue(json.containsKey("resource_id"));
				  assertTrue(json.containsKey("user_id"));
				  assertTrue(json.containsKey("auction_id"));
				  assertTrue(json.containsKey("price"));
				  assertTrue(json.containsKey("amount"));
				  assertTrue(json.containsKey("timeleft_sec"));
			  }
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_auctions");
		  }
		sendQuit();
		in.close();
		printWriter.close();
		
	}	

	public void testGetAuctionsAll() {

        printWriter.println("{command:'get_auctions_all'}");
        printWriter.flush();

	
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auctions"));
			  JSONArray auctions = json.getJSONArray("auctions");
			  assertTrue(auctions.size() > 0);
			  for( int i = 0; i < auctions.size(); i++){
				  json = auctions.getJSONObject(i);
				  assertTrue(json.containsKey("resource_id"));
				  assertTrue(json.containsKey("user_id"));
				  assertTrue(json.containsKey("auction_id"));
				  assertTrue(json.containsKey("price"));
				  assertTrue(json.containsKey("amount"));
				  assertTrue(json.containsKey("timeleft_sec"));
			  }
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_auctions");
		  }
		sendQuit();
		in.close();
		printWriter.close();
		
	}		
	
	public void testLoginAndCreateAuction() {

		sendJson("{command:'login', data: {username: 'mark', password: 'secret'}}");
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auth_key"));
			  String auth_key = json.getString("auth_key");
			  
		      sendJson("{command:'create_auction', data:{resource_id : 2,amount: 10,price: 89,duration: 80000,auth_key: '"+auth_key+"'}}");
		      json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("auction_id"));
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON");
		  }
		
		in.close();
		printWriter.close();
		
	}		
	
	public void testGetResource() {

        sendJson("{command:'get_resource', data: {resource_id: 1}}");

	
        In in = new In (socket);
		try {
			  JSONObject json = (JSONObject) JSONSerializer.toJSON(readResponse(in));
			  assertTrue(json.containsKey("data"));
			  json = json.getJSONObject("data");
			  assertTrue(json.containsKey("name"));
			  assertTrue(json.containsKey("resource_id"));
			  String n = json.getString("name");
			  assertEquals(n, "Silver");
		  }catch(net.sf.json.JSONException e){
			  e.printStackTrace();
			  fail("Invalid JSON by get_resource");
		  }
		sendQuit();
		in.close();
		printWriter.close();
		
	}		
	
	public String readResponse(In in){
		String message;
		message = in.readLine();
		System.err.println(message);
		return  message;
	}
	
	public void sendJson(String m){
		System.out.println(m);
		  printWriter.println(m);
	      printWriter.flush();
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
		
		JAuctionServer jAuctionServer = new JAuctionServer();
    	
		
	    System.err.println("Started server on port " + port);
	
	    int con_counter=0;
	
	    try{
	      ServerSocket serverSocket = new ServerSocket(port);
	      Socket clientSocket;
	
	      while((con_counter++ < maxConnections) || (maxConnections == 0)){
	
	        clientSocket = serverSocket.accept();
	        System.err.println("Accepted connection from client");
	        
	        Connection conn_c= new Connection(clientSocket, jAuctionServer);
	        Thread t = new Thread(conn_c);
	        t.start();
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }  
	}    
}