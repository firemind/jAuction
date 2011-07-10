package jauctionclient.commands;

import java.util.LinkedList;

import jauctionclient.datamodel.AllAuction;
import jauctionclient.datamodel.AllStock;
import jauctionclient.datamodel.Auction;
import jauctionclient.datamodel.Stock;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetAuctionsAll extends ClientCommand {	
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
		AllAuction all = m.getAllAuction();
		AllAuction my = m.getAllMyAuction();
		Long my_id = m.getCurrentUser().getId();
		
		for (Auction auction: auctions) {
			if (auction.getUserId() == my_id) {
				my.addInstance(auction);
			} else {
				all.addInstance(auction);
			}
        }
		m.notifyObservers("allAuction");
		m.setModelChanged();
		m.notifyObservers("myAuction");
	}
}