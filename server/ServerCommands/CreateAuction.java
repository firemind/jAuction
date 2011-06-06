package server.ServerCommands;

import java.util.ArrayList;
import java.util.HashMap;

import server.Auction;
import server.Connection;
import server.Resource;
import server.User;

import net.sf.json.JSONObject;

public class CreateAuction extends ServerCommand {

	private String auth_key;
	private int resource_id;
	private int amount;
	private int duration;
	private int price;
	
	CreateAuction(Connection con){
		super(con);
	}
	
	public String name(){
		return "create_auction";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("auction_id", "Integer");
		data.put("resource_id", "Integer");
		data.put("amount", "Integer");
		data.put("price", "Integer");
		data.put("duration", "Integer");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("auction_id", "Integer");
		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key		= data.getString("auth_key");
			this.resource_id 	= data.getInt("resource_id");
			this.amount 		= data.getInt("amount");
			this.price	 		= data.getInt("price");
			this.duration 		= data.getInt("duration");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	private boolean checkValues(){
		if(duration < 0)
			return false;
		try {
		  con.jAuctionServer.getResource(resource_id);
		}catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
		if(0 > amount || con.user.getStock(resource_id) <= amount)
			return false;
		if(price < 0)
			return false;
		return true;
	}
	
	
	public void run(){
	  if(authenticate(auth_key)){
			Auction auction = null;
			int auction_id;
	  		HashMap data = new HashMap();
			if (checkValues()){
				Resource resource = con.jAuctionServer.getResource(resource_id);
				User user = con.user;
				Auction auc = con.jAuctionServer.addAuction(amount,resource, duration, user, price);
				data.put("auction_id", auc.getId());
			}
	  		con.respond(this.responseName(), data);
	  }
	}
}