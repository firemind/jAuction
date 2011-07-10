package jauctionclient.datamodel;

import java.util.HashMap;

public class AllStock extends AbstractModelObject {
	private final HashMap<Long, Stock> all_stocks = new HashMap<Long, Stock>();
	
	public synchronized void addInstance(Stock stock) {
		all_stocks.put(stock.getResourceId(), stock);
		firePropertyChange("allStock", null, all_stocks);
	}
	public synchronized void addInstance(Long resource_id, Integer amount) {
		Stock stock = new Stock(resource_id, amount);
		all_stocks.put(stock.getResourceId(), stock);
		firePropertyChange("allStock", null, all_stocks);
	}
	
	public synchronized void removeInstance(Long resource_id) {
		all_stocks.remove(resource_id);
		firePropertyChange("allStock", null, all_stocks);
	}
	
	public Stock get(Long id) {
		if(!all_stocks.containsKey(id)){
			requestDataObject(new DataObjectEvent(this, id, "Stock"));
			return null;
		}
		return all_stocks.get(id);
	}
	
	public Object[][] getAllTable() {
		Object[][] all = new Object[all_stocks.size()][5];
		
		int i = 0;
		for(Stock s : all_stocks.values()){
			all[i][0] = s.getResourceId();
			all[i][1] = s.getAmount();
			i++;
		}
		
		return all; 
	}
	
	public Integer size(){
		return all_stocks.size();
	}
	

	public HashMap<Long, Stock> getAll() { return all_stocks; }
}
