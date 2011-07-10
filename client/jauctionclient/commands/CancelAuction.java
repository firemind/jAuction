package jauctionclient.commands;

import org.json.JSONObject;

public class CancelAuction extends ClientCommand{
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
		if(success){
			m.setModelChanged();
			m.setMessage("Canceled auction "+auction_id+"");
			m.getAllMyAuction().removeInstance(auction_id);
			m.notifyObservers("myAuction");
		}else{
			m.setModelChanged();
			m.setMessage("Could not cancel auction "+auction_id+"!");
			m.getOutputCommands().getAuctionsAll();
			m.notifyObservers("notify");
		}
	}
}
