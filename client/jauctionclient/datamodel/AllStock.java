package jauctionclient.datamodel;

import java.util.HashMap;

public class AllStock extends AbstractModelObject {
	private final HashMap<Long, Stock> all_stocks = new HashMap<Long, Stock>();
	
	public void addInstance(Stock stock) {
		all_stocks.put(stock.getResourceId(), stock);
		firePropertyChange("allStock", null, all_stocks);
	}
	public void addInstance(Long resource_id, Integer amount) {
		Stock stock = new Stock(resource_id, amount);
		all_stocks.put(stock.getResourceId(), stock);
		firePropertyChange("allStock", null, all_stocks);
	}
	

	public void removeInstance(Long resource_id) {
		all_stocks.remove(resource_id);
		firePropertyChange("allStock", null, all_stocks);
	}

	public HashMap<Long, Stock> getAll() { return all_stocks; }

}
