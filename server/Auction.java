package server;

import java.util.HashMap;
import net.sf.json.JSONObject;

public class Auction {
  private long id;
  private int amount;
  private Resource resource;
  private int duration;
  private int price;
  private User user;
  AuctionEnder ender;
  JAuctionServer jAuctionServer;
  
  Auction(JAuctionServer jAuctionServer, long id, int amount, Resource resource, int duration, User user, int price){
	  this.id = id;
	  this.amount = amount;
	  this.resource = resource;
	  this.duration = duration;
	  this.user = user; 
	  this.price = price;
	  this.jAuctionServer = jAuctionServer;
	  
	  ender = new AuctionEnder(this);
	  ender.start();
  }
  
  public Resource getResource(){
	  return this.resource;
  }
  
  public User getUser(){
	  return this.user;
  }
  
  public int getDuration(){
	  return this.duration;
  }
  
  public int getAmount(){
	  return this.amount;
  }
  
  public int getPrice(){
	  return this.price;
  }
  
  public int getTimeleftSec(){
	  return this.duration;
  }

  public long getId(){
	  return this.id;
  }
  
  public JSONObject soldJson(){
	  	HashMap data = new HashMap();
		data.put("auction_id", this.id);
		JSONObject jsonObject = JSONObject.fromObject( data );
		return jsonObject;
	  }

  public JSONObject removeJson(){
	  	HashMap<String, Long> data = new HashMap<String, Long>();
		data.put("auction_id", this.id);
		JSONObject jsonObject = null;
		try{
		  jsonObject = JSONObject.fromObject( data );
		}catch(org.apache.commons.lang.exception.NestableRuntimeException e){
		  e.printStackTrace();
		}
		return jsonObject;
	  } 
  
  public JSONObject toJson(){
  	HashMap data = new HashMap();
	data.put("auction_id", this.id);
	data.put("price", this.price);
	data.put("amount", this.amount);
	data.put("timeleft_sec", this.getTimeleftSec());
	data.put("user_id", this.user.getId());
	data.put("resource_id", this.resource.getId());
	JSONObject jsonObject = JSONObject.fromObject( data );
	return jsonObject;
  }
  public void end(){
	  this.jAuctionServer.cancelAuction(this.getId());
	  System.out.println("Auction Ended");
  }
  private class AuctionEnder extends Thread{
	  Auction auc;
	  AuctionEnder(Auction auc){
		  this.auc = auc;
	  }
	  @Override
	public void run(){
		  try {
			  sleep(auc.getDuration());
		  }catch(InterruptedException e) {}
		  auc.end();
	  }
  }
}
