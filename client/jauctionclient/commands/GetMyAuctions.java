package jauctionclient.commands;

import jauctionclient.datamodel.AllAuction;
import jauctionclient.datamodel.Auction;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetMyAuctions extends ClientCommand {	
	private LinkedList<Auction> auctions = new LinkedList<Auction>();

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			JSONArray array = getData().getJSONArray("auctions");
			for (int i = 0; i < array.length(); ++i) {
			    JSONObject rec = array.getJSONObject(i);
			    auctions.add (new Auction(
			    	rec.getLong("auction_id"), 
			    	rec.getLong("user_id"), 
			    	rec.getInt("amount"),
			    	rec.getLong("resource_id"),
			    	rec.getInt("price"), 
			    	rec.getLong("timeleft_sec")
			    ));
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		AllAuction all = m.getAllMyAuction();
		for (Auction auction: auctions) {
            all.addInstance(auction);
        }
		m.notifyObservers("allMyAuction");
	}
}
