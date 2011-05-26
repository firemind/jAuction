package server;

import java.net.Socket;
import java.net.ServerSocket;
import net.sf.json.*;
import org.apache.commons.io.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class JAuctionServer {
    private static int port = 4444;
    private static int maxConnections = 0;
    public Hashtable serverCommands = new Hashtable();
    public ArrayList<Resource> resources = new ArrayList();
    public ArrayList<Auction> auctions = new ArrayList();
    public ArrayList<User> users = new ArrayList();
    
    JAuctionServer(){
    	this.addServerCommands();
    	this.addResources();
    	this.addUsers();	
    	this.addAuctions();	
    	
    }
    
    private void addServerCommands(){
    	serverCommands.put("login", Login.class);
    	serverCommands.put("get_commands", GetCommands.class);
    	serverCommands.put("get_money", GetMoney.class);
    	serverCommands.put("get_stock", GetStock.class);
    	serverCommands.put("get_stock_all", GetStockAll.class);
    	serverCommands.put("get_resource", GetResource.class);
    	serverCommands.put("get_resource_all", GetResourceAll.class);
    	serverCommands.put("get_auctions", GetAuctions.class);
    	serverCommands.put("get_auctions_all", GetAuctionsAll.class);
    	serverCommands.put("quit", Quit.class);
    }

    private void addResources(){
    	resources.add(new Resource("Gold"));
    	resources.add(new Resource("Silver"));
    	resources.add(new Resource("Iron"));
    }
    
    private void addUsers(){
    	users.add(new User("mark", "secret"));
    	users.add(new User("jeff", "ladybug"));
    }
    
    private void addAuctions(){
    	auctions.add(new Auction(5, resources.get(2), 5000, users.get(0), 92));
    	auctions.add(new Auction(20, resources.get(1), 5000, users.get(1), 55));
    	auctions.add(new Auction(100, resources.get(1), 5000, users.get(0), 100));
    	auctions.add(new Auction(77, resources.get(0), 5000, users.get(1), 34));
    }
    
    public static void main(String[] args) throws Exception {

    	JAuctionServer jAuctionServer = new JAuctionServer();

	    System.err.println("Started server on port " + port);
	
	    int con_counter=0;
	
	    try{
	      ServerSocket serverSocket = new ServerSocket(port);
	      Socket clientSocket;
	
	      while((con_counter++ < maxConnections) || (maxConnections == 0)){
	
	        clientSocket = serverSocket.accept();
	        
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
