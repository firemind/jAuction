package jauctionclient.commands;

import jauctionclient.datamodel.Auction;

import org.json.*;


public class NewAuction extends ClientCommand {
	private JSONObject data;
	private Auction auction;
	
	public boolean parseJson(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			this.auction = new Auction(
				data.getLong("auction_id"), 
				data.getLong("user_id"), 
				data.getInt("amount"),
				data.getLong("resource_id"),
				data.getInt("price"), 
				data.getLong("timeleft_sec")
			);
		} catch(JSONException e) {
			System.out.println("Bad Request" + json_object.toString());
			return false;
		}
		return true;
	}
  
	public void run() {
		getModel().getInputCommands().newAuction(auction);
	}
}