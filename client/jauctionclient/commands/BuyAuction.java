package jauctionclient.commands;

import org.json.JSONObject;

public class BuyAuction extends ClientCommand{
	private long auction_id;
	private boolean success;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			auction_id = getData().getLong("auction_id");
			success = getData().getBoolean("success");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  

	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		if(success){
			m.setMessage("Auction "+auction_id+" bouth.");
		}else{
			m.setMessage("Could not buy auction "+auction_id+"!");
		}
		m.notifyObservers("auction");
	}
}
