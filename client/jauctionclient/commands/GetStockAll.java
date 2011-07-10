package jauctionclient.commands;

import java.util.LinkedList;

import jauctionclient.datamodel.AllStock;
import jauctionclient.datamodel.Stock;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetStockAll extends ClientCommand {	
	private LinkedList<Stock> stocks = new LinkedList<Stock>();

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			JSONArray array = getData().getJSONArray("stocks");
			for (int i = 0; i < array.length(); ++i) {
			    JSONObject rec = array.getJSONObject(i);
			    stocks.add (new Stock(
			    	rec.getLong("resource_id"),
			    	rec.getInt("amount")
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
		AllStock all = m.getAllStock();
		for (Stock stock: stocks) {
            all.addInstance(stock);
        }
		m.notifyObservers("allStock");
	}
}