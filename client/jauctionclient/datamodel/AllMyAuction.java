package jauctionclient.datamodel;


public class AllMyAuction extends DataObjectRecords<Auction> {
	private static AllMyAuction all;
	private static Object only_one_sync = new Object();
	
	private AllMyAuction(){	
	}

	public static AllMyAuction getInstance(){
		if(all == null) {
			synchronized (only_one_sync) {
				if(all == null) {
					all = new AllMyAuction();
				}
			}
		}
		return all;
	}
	
	@Override
	public synchronized void addDataObject(Auction data_object) {
		if (data_object.getUserId() == CurrentUser.getInstance().getId()) {
			super.addDataObject(data_object);
		} else {
			AllAuction.getInstance().addDataObject(data_object);
		}
	};
	
	public void addDataObject(Long id, Long user_id, Integer amount, Long resource_id, Integer price, Long timeleft_sec) {
		Auction auction = new Auction(id, user_id, amount, resource_id, price, timeleft_sec);
		addDataObject(auction);
	}
}