package jauctionclient.commands;

import org.json.JSONException;
import org.json.JSONObject;

public class AuctionSold extends ClientCommand {
	private JSONObject data;
	private Long auction_id;
	
	public boolean parseJson(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			this.auction_id = this.data.getLong("auth_id");
		} catch(JSONException e) {
			System.out.println("Bad Request" + json_object.toString());
			return false;
		}
		return true;
	}
  
	public void run() {
		getModel().getInputCommands().auctionSold(auction_id);
	}
}
