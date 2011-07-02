package jauctionclient.datamodel;

import java.util.HashMap;

public class AllResource extends AbstractModelObject {
	private final HashMap<Long, Resource> all_resources = new HashMap<Long, Resource>();
	
	public void addInstance(Resource resource) {
		all_resources.put(resource.getId(), resource);
		firePropertyChange("allResource", null, all_resources);
	}
	public void addInstance(Long id, String name) {
		Resource resource = new Resource(id, name);
		all_resources.put(resource.getId(), resource);
		firePropertyChange("allResource", null, all_resources);
	}

	public void removeInstance(Long id) {
		all_resources.remove(id);
		firePropertyChange("allResource", null, all_resources);
	}

	public HashMap<Long, Resource> getAll() { return all_resources; }
}
