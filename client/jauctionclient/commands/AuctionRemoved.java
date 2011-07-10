package jauctionclient.commands;

import org.json.JSONException;
import org.json.JSONObject;

public class AuctionRemoved extends ClientCommand {
	private Long auction_id;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			auction_id = getData().getLong("auction_id");
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
		m.getAllAuction().removeInstance(auction_id);
		m.notifyObservers("allAuction");
	}
}
