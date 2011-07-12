package server;

import java.util.Date;
import java.util.HashMap;
import net.sf.json.JSONObject;

public class Auction {
  private long id;
  private int amount;
  private Resource resource;
  private int duration;
  private int price;
  private User user;
  private Date created_at;
  private User highest_bidder = null;
  private long highest_bid = 0;
  AuctionEnder ender;
  
  Auction( long id, int amount, Resource resource, int duration, User user, int price){
	  this.id = id;
	  this.amount = amount;
	  this.resource = resource;  
	  this.created_at = new Date();
	  this.duration = duration;
	  this.user = user; 
	  this.price = price;
	  
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
  
  public long getTimeleftSec(){
	  long now = (new Date().getTime() / 1000);
	  long endtime = (this.created_at.getTime() / 1000) + this.duration;
	  return endtime - now;
  }

  public long getId(){
	  return this.id;
  }
  
  public void bid(User bidder, long bid){
	  if(this.hasBidder())
		  this.highest_bidder.releaseBid(this, this.highest_bid);
	  
	  this.highest_bidder = bidder;
	  this.highest_bid = bid;
  }
  
  public boolean hasBidder(){
	  return (this.highest_bidder != null);
  }
  
  public User getHighestBidder(){
	  return this.highest_bidder;
  }
  
  public long getNextHigherBid(){
	return highest_bid + 1;  
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

}
