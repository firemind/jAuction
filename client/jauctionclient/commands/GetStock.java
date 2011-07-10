package jauctionclient.commands;

import org.json.JSONObject;

public class GetStock extends ClientCommand {
	private long resource_id;
	private int amount;

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			resource_id = getData().getLong("resource_id");
			amount = getData().getInt("amount");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		m.getAllStock().addInstance(resource_id, amount);
		m.notifyObservers("allStock");
	}
}