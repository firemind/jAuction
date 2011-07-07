package server.ServerCommands;

import java.util.HashMap;

import server.Connection;

import net.sf.json.JSONObject;

public class CancelAuction extends ServerCommand {
	
	private String auth_key;
	private long auction_id;
	
	CancelAuction(Connection con){
		super(con);
	}
	
	public String name(){
		return "cancel_auction";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("auction_id", "Integer");
		data.put("auth_key", "String");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		data.put("auction_id", "Integer");
		data.put("success", "Boolean");
		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("auth_key");
			this.auction_id 	= data.getLong("auction_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap(); 
	  		data.put("auction_id", auction_id);
	  		data.put("success", this.con.jAuctionServer.cancelAuction(auction_id));
	  		con.respond(this.responseName(), data);
	  	}
	}
}