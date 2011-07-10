package jauctionclient.commands;

import jauctionclient.datamodel.AllResource;
import jauctionclient.datamodel.Resource;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetResourceAll extends ClientCommand {	
	private LinkedList<Resource> resources = new LinkedList<Resource>();

	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			JSONArray array = getData().getJSONArray("resources");
			for (int i = 0; i < array.length(); ++i) {
			    JSONObject rec = array.getJSONObject(i);
			    resources.add (new Resource(
			    		rec.getLong("resource_id"),
						rec.getString("name")
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
		AllResource all = m.getAllResource();
		for (Resource resource: resources) {
            all.addInstance(resource);
        }
		m.notifyObservers("allResource");
		m.setModelChanged();
		m.notifyObservers("allStock");
	}
}