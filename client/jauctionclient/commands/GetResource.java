package jauctionclient.commands;

import org.json.JSONObject;

public class GetResource extends ClientCommand {
	private long resource_id;
	private String name;

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			resource_id = getData().getLong("resource_id");
			name = getData().getString("name");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		m.getAllResource().addInstance(resource_id, name);
		m.notifyObservers("allResource");
	}
}