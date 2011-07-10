package jauctionclient.commands;

import org.json.JSONObject;

public class CreateAuction extends ClientCommand{
	private long auction_id;
	

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			auction_id = getData().getLong("auction_id");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  

	@Override
	public void run() {
		Model m = getModel();
		m.setMessage("Auction "+auction_id+" successfully created.");
		m.getOutputCommands().getStockAll();
	}
}
