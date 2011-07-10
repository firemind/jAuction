package jauctionclient.commands;

import jauctionclient.datamodel.Auction;

import org.json.JSONException;
import org.json.JSONObject;


public class NewAuction extends ClientCommand {
	private Auction auction;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			JSONObject data = getData();
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
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		if(auction.getUserId() == m.getCurrentUser().getId()) {
			m.getAllMyAuction().addInstance(auction);
			m.notifyObservers("myAuction");
		} else {
			m.getAllAuction().addInstance(auction);
			m.notifyObservers("allAuction");
		}
	}
}