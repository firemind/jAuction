package jauctionclient.datamodel;

import java.util.HashMap;

public class AllAuction extends AbstractModelObject {
	private final HashMap<Long, Auction> all_auctions = new HashMap<Long, Auction>();
	
	public void addInstance(Auction auction) {
		all_auctions.put(auction.getId(), auction);
		firePropertyChange("allAuction", null, all_auctions);
	}
	public void addInstance(Long id, Long user_id, Integer amount, Long resource_id, Integer price, Long timeleft_sec) {
		Auction auction = new Auction(id, user_id, amount, resource_id, price, timeleft_sec);
		all_auctions.put(auction.getId(), auction);
		firePropertyChange("allAuction", null, all_auctions);
	}

	public void removeInstance(Long id) {
		all_auctions.remove(id);
		firePropertyChange("allAuction", null, all_auctions);
	}

	public HashMap<Long, Auction> getAll() {
		return all_auctions; 
	}
}