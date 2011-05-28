package server;

import java.net.Socket;
import java.net.ServerSocket;
import net.sf.json.*;
import org.apache.commons.io.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;

public class JAuctionServer {
    private static int port = 4444;
    private static int maxConnections = 0;
    public Hashtable<String, Class<?>> serverCommands = new Hashtable<String, Class<?>>();

    private MutationStore mutationStore;
    
    private HashMap<Long, Resource> resources = new HashMap();
    private HashMap<Long, Auction> auctions = new HashMap();
    private HashMap<Long, User> users = new HashMap();
    
    long nextUserId = 0;
    long nextAuctionId = 0;
    long nextResourceId = 0;
    
    
    JAuctionServer(){
    	this.addServerCommands();
    	
    	Resource r1 = addResource("Gold");
    	Resource r2 = addResource("Silver");
    	Resource r3 = addResource("Iron");

    	User u1 = addUser("mark", "secret");
    	User u2 = addUser("jeff", "ladybug");

    	addAuction( 5, r2, 5000, u1, 92);
    	addAuction( 20, r3, 5000, u2, 55);
    	addAuction( 100, r3, 5000, u2, 100);
    	addAuction( 77, r1, 5000, u1, 34);
    	
    	this.mutationStore = new MutationStore();
    }
    
    
    protected Auction addAuction(int amount, Resource resource, int duration, User user, int price){
    	Auction auc = new Auction(nextAuctionId, amount, resource, duration, user, price);
    	this.auctions.put(nextAuctionId++, auc);
    	return auc;
    }
    
    protected Resource addResource(String name){
    	Resource res = new Resource(nextResourceId, name);
    	this.resources.put(nextResourceId++, res);
    	return res;
    }  
    
    protected User addUser(String username, String password){
    	User user = new User(nextUserId, username, password);
    	this.users.put(nextUserId++, user);
    	return user;
    }  

    
    protected HashMap getResources(){
    	return this.resources;
    }
    
    protected Resource getResource(long resource_id){
    	return this.resources.get(resource_id);
    }
    
    protected HashMap getUsers(){
    	return this.users;
    }
    
    protected Hashtable getServerCommands(){
    	return this.serverCommands;
    }
    
    protected ArrayList<Auction> getAuctionsByResourceId(long resource_id){
    	ArrayList<Auction> filtered = new ArrayList();
    	Iterator it = this.auctions.keySet().iterator();
    	while(it.hasNext()) {
    		Auction auc = this.auctions.get(it.next());
    		if(auc.getResource().getId() == resource_id){
    			filtered.add(auc);
    		}		    
    	}

    	return filtered;
    }
    
    protected HashMap getAuctions(){
    	  return this.auctions;
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
    	serverCommands.put("create_auction", CreateAuction.class);
    	serverCommands.put("quit", Quit.class);
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
	        jAuctionServer.mutationStore.addObserver(conn_c);
	        Thread t = new Thread(conn_c);
	        t.start();
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }   
        
    }

}
