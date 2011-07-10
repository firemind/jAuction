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
	
	public Auction get(Long id) {
		if(!all_auctions.containsKey(id)){
			requestDataObject(new DataObjectEvent(this, id, "Auction"));
			return null;
		}
		return all_auctions.get(id);	
	}
	
	public Integer size(){
		return all_auctions.size();
	}
	
	public Object[][] getAllTable() {
		Object[][] all = new Object[all_auctions.size()][6];
		
		int i = 0;
		for(Auction auc : all_auctions.values()){
			all[i][0] = auc.getId();
			all[i][1] = auc.getUserId();
			all[i][2] = auc.getAmount();
			all[i][3] = auc.getResourceId();
			all[i][4] = auc.getPrice();
			all[i][5] = auc.getTimeleftSec();
			i++;
		}
		
		return all; 
	}
}