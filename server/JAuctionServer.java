/**
 * Java Auctionhouse
 *
 * @author Michael Gerber
 * @version 0.1.0
 */

package server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;

import server.ServerCommands.ServerCommandFactory;

public class JAuctionServer {
    private static int port = 4444;
    private static int maxConnections = 0;

    private MutationStore mutationStore;
    private AuctionEnder auctionEnder;
    public ServerCommandFactory serverCommandFactory = new ServerCommandFactory();
    
    private HashMap<Long, Resource> resources = new HashMap<Long, Resource>();
    private HashMap<Long, Auction> auctions = new HashMap<Long, Auction>();
    private HashMap<Long, User> users = new HashMap<Long, User>();
    
    long nextUserId = 0;
    long nextAuctionId = 0;
    long nextResourceId = 0;
    
    
    JAuctionServer(){

    	this.mutationStore = new MutationStore();
    	this.auctionEnder = new AuctionEnder(this);
		synchronized (this.auctionEnder){
    	  this.auctionEnder.start();
		}
    	Resource r1 = addResource("Gold");
    	Resource r2 = addResource("Silver");
    	Resource r3 = addResource("Iron");
/*
    	User u1 = addUser("mark", "secret");
    	User u2 = addUser("jeff", "ladybug");

    	addAuction( 5, r2, 3000, u1, 92);
    	addAuction( 20, r3, 5000, u2, 55);
    	addAuction( 100, r3, 7000, u2, 100);
    	addAuction( 77, r1, 10000, u1, 34);
 */   	
    }
    
    /**
     * Tries to let user buy auction                           
     *
     * @param  user_id Id of the user trying to buy the auction
     * @param  auction_id Id of the auction to be bought
     * @return boolean indicating weather buying worked
     */
    public synchronized boolean buyAuction(long user_id, long auction_id){
    	if(!this.auctions.containsKey(auction_id))
    	  return false;
    	if(!this.users.containsKey(user_id))
      	  return false;
    	Auction auction = this.auctions.get(auction_id);
    	User buyer = this.users.get(user_id);
    	if(buyer.getMoney() < auction.getPrice())
    	  return false;
    	
    	auction.bid(buyer, auction.getPrice());
    	
    	sellAuction(auction.getId());
    	
    	return true;
    }

    public void endAuction(Auction auction){
  	  if(auction.hasBidder()){
  		sellAuction(auction.getId());
  	  }else{
  	    cancelAuction(auction.getId());
  	  }
    }
    
    public synchronized boolean sellAuction(long auction_id){
    	Auction auction = this.auctions.get(auction_id);
    	User buyer = auction.getHighestBidder();
    	
    	buyer.buyAuction(auction);

    	User seller = auction.getUser();
    	seller.sellAuction(auction);
    	
    	auction.getUser().send("auction_sold", auction.soldJson());
    	this.removeAuction(auction_id);
    	
    	return true;
    }
    
    /**
     * Tries to let user bid on auction                           
     *
     * @param  user_id Id of the user trying to bid on the auction
     * @param  auction_id Id of the auction to be bid on
     * @return boolean indicating weather bidding worked
     */
    public synchronized boolean bid(long user_id, long bid, long auction_id){
    	if(!this.auctions.containsKey(auction_id))
    	  return false;
    	if(!this.users.containsKey(user_id))
      	  return false;
    	Auction auction = this.auctions.get(auction_id);
    	User buyer = this.users.get(user_id);
    	if(buyer.getMoney() < bid)
      	  return false;
    	if(bid < auction.getNextHigherBid())
    	  return false;
    	
    	buyer.bidOn(auction, bid);
    	
    	return true;
    }    
    
    /**
     * Tries to cancel the auction                           
     *
     * @param  user_id Id of the user trying to buy the auction
     * @param  auction_id Id of the auction to be bought
     * @return boolean indicating weather buying worked
     */
    public synchronized boolean cancelAuction(long auction_id){
    	if(!this.auctions.containsKey(auction_id))
    	  return false;
    	Auction auction = this.auctions.get(auction_id);
    	
    	User seller = auction.getUser();
    	seller.cancelAuction(auction);
    	
    	this.removeAuction(auction_id);
    	
    	return true;
    }
    
    private synchronized boolean removeAuction(long auction_id){
    	if(!this.auctions.containsKey(auction_id))
      	  return false;
      	Auction auction = this.auctions.get(auction_id);
    	this.mutationStore.addMutation("auction_removed", auction.removeJson());
    	this.auctions.remove(auction.getId());
    	this.auctionEnder.removeAuction(auction);
    	return true;
    }
    
    
    /**
     * Creates new auction and adds it to the list of active auctions. Adds mutation. 
     * Takes stock from user who created auction and adds auction to that users auctions.
     *
     * @param  amount amount of resource sold in this auction
     * @param  resource resource that is to be sold
     * @param duration how long the auction will be running
     * @param user user that owns this auction
     * @param price amount of money for which the auction is to be sold
     * @return Auction Object
     */
    public Auction addAuction(int amount, Resource resource, int duration, User user, int price){

    	Auction auc = new Auction(nextAuctionId, amount, resource, duration, user, price);
    	this.auctions.put(nextAuctionId++, auc);
    	synchronized (this.auctionEnder) {
    	  auctionEnder.notify();
    	}
    	auctionEnder.addAuction(auc);
    	user.createAuction(auc);
    	this.mutationStore.addMutation("new_auction", auc.toJson());
    	return auc;
    }
    
    /**
     * Creates new resource and adds it to the list of resources.            
     *
     * @param  name Name of resource to be created
     * @return Resource Object
     */
    public Resource addResource(String name){
    	Resource res = new Resource(nextResourceId, name);
    	this.resources.put(nextResourceId++, res);
    	return res;
    }  
    
    /**
     * Creates new user and adds it to the list of users.                 
     *
     * @param  username Userame of user to be created
     * @param  password Password of user to be created
     * @return User Object
     */
    public User addUser(String username, String password){
    	User user = new User(nextUserId, username, password);
    	this.users.put(nextUserId++, user);
    	return user;
    }  

    /**
     * Authenticates User by username and password            
     *
     * @param  username Userame of user to be authenticated
     * @param  password Password of user to be authenticated
     * @return User Object or null
     */
    public User authenticateUser(String username, String password){
    	User user = null;
    	Iterator<Long> it = this.users.keySet().iterator();
    	while(it.hasNext()) {
    		User tmp_user = this.users.get(it.next());
    		if(tmp_user.authenticate(username, password)){
    			user = tmp_user;
    		}
    	}
    	return user;
    }  
    
    
    /**            
     *
     * @return HashMap of all resources.
     */
    public HashMap<Long, Resource> getResources(){
    	return this.resources;
    }

    /**              
     *
     * @param resource_id The id of the resource to be returned
     * @return Resouce Object associated with resource_id 
     */
    public Resource getResource(long resource_id){
    	return this.resources.get(resource_id);
    }
    
    /**               
     *
     * @return HashMap of all users.
     */
    public HashMap<Long, User> getUsers(){
    	return this.users;
    }
    
    /**
     * @return Hashtable of all ServerCommands that can be created by ServerCommandFactory
     */
    public Hashtable<String, String> getServerCommands(){
    	return this.serverCommandFactory.getServerCommands();
    }
    
    /**
     * 
     * @param auction_id
     * @return
     */
    public Auction getAuctionById(long auction_id){
    	return this.auctions.get(auction_id);
    }
    
    /**
     * Filters all active auctions by resource_id and returns ArrayList of matches                
     *
     * @param  resource_id Id of resource to be filtered by.
     * @return ArrayList of auctions
     */
    public ArrayList<Auction> getAuctionsByResourceId(long resource_id){
    	ArrayList<Auction> filtered = new ArrayList<Auction>();
    	Iterator<Long> it = this.auctions.keySet().iterator();
    	while(it.hasNext()) {
    		Auction auc = this.auctions.get(it.next());
    		if(auc.getResource().getId() == resource_id){
    			filtered.add(auc);
    		}		    
    	}

    	return filtered;
    }
    
    /**      
     * @return ArrayList of all active auctions
     */
    public HashMap<Long, Auction> getAuctions(){
    	  return this.auctions;
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
