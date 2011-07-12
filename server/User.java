package server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class User {
  private long id;
  private long money;
  private HashMap<Long, Long> stock = new HashMap<Long, Long>();
  private HashMap<Long, Auction> auctions = new HashMap<Long, Auction>();
  private byte[] auth_key;
  private String username;
  private String password;
  public Connection con;
  
  
  User(long id, String username, String password){
    try {
	  MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	  digest.update((username + password).getBytes());
	  this.auth_key = digest.digest();
	 }catch(NoSuchAlgorithmException e){
       System.out.println("No Such Algorithm");
  	 }
	 this.username = username;
	 this.password = password;
	 this.id = id;
	 this.money = 5000;
	 long r_id1 = 0;
	 long r_id2 = 1;
	 long r_id3 = 2;
	 long v_id1 = 150;
	 long v_id2 = 50;
	 long v_id3 = 100;
	 this.stock.put(r_id1, v_id1);
	 this.stock.put(r_id2, v_id2);
	 this.stock.put(r_id3, v_id3);
  }
  
  public long getMoney(){
	  return this.money;
  }
  
  public long getStock(long resource_id){
	  if(this.stock.containsKey(resource_id)){
		  return this.stock.get(resource_id);
	  }else{
		  System.out.println("don't have stock "+resource_id);
		  return 0;
	  }
  }
  
  private void inform_update_money(){
	  HashMap data = new HashMap(); 
  	  data.put("money_amount", this.money);
	  this.send("update_money", data);
  }
  
  public void addMoney(long amount){
	  this.money += amount;
	  this.inform_update_money();
  }
  
  public void loseMoney(long amount){
	  this.money -= amount;
	  this.inform_update_money();
  }

  public void addStock(long res_id, long amount){
	  if(this.stock.containsKey(res_id)){
		  long prev_amount = this.stock.get(res_id);
		  this.stock.put(res_id, prev_amount+amount);
	  }else{
		  this.stock.put(res_id, amount);
	  }
  }
  
  public void loseStock(long res_id, long amount){
	  if(this.stock.containsKey(res_id)){
		  long prev_amount = this.stock.get(res_id);
		  this.stock.put(res_id, prev_amount-amount);
	  }else{
		  this.stock.put(res_id, -amount);
	  }
	  if(this.stock.get(res_id) < 0){
	    System.out.println("user lost more stock than he has");
	  }
  }
  
  public boolean authenticate(String u, String p){
	  if(u.equals(this.username) && p.equals(this.password)){
		  return true;
	  }else{
		  return false;
	  }
	  
  }
  
  public void createAuction(Auction auc){
  	addAuction(auc);
	loseStock(auc.getResource().getId(), auc.getAmount());
  }
  
  public void buyAuction(Auction auc){
  	this.loseMoney(auc.getPrice());
	this.addStock(auc.getResource().getId(), auc.getAmount());
  }
  
  
  public void sellAuction(Auction auc){
  	addMoney(auc.getPrice());
	removeAuction(auc);
  }
  
  public void bidOn(Auction auction, long bid){
	this.loseMoney(bid);
  	auction.bid(this, bid);
  }
  
  public void releaseBid(Auction auction, long bid){
	  this.addMoney(bid);
  }
  
  public void cancelAuction(Auction auc){
	this.addStock(auc.getResource().getId(), auc.getAmount());
	removeAuction(auc);
  }
  
  public void addAuction(Auction auc){
	  this.auctions.put(auc.getId(), auc);
  }
  
  public void removeAuction(Auction auc){
	  this.auctions.remove(auc.getId());
  }
  
  public String getAuthKey(){
	  return this.auth_key.toString();
  }

  public long getId(){
	  return this.id;
  }
  
  public void send(String command, JSONObject data){
	  if(this.con != null){
		  this.con.respond(command, data);
	  }
  }
  
  public void send(String command, HashMap data){
	  if(this.con != null){
		  this.con.respond(command, data);
	  }
  }
}
