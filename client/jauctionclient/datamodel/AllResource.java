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
	
	public Resource get(Long id) {
		if(!all_resources.containsKey(id)){
			requestDataObject(new DataObjectEvent(this, id, "Resource"));
			return null;
		}
		return all_resources.get(id);
	}
	
	public Integer size(){
		return all_resources.size();
	}
	
	public Object[][] getAllTable() {
		Object[][] all = new Object[all_resources.size()][5];
		
		int i = 0;
		for(Resource r : all_resources.values()){
			all[i][0] = r.getId();
			all[i][1] = r.getName();
			i++;
		}
		
		return all; 
	}

	public HashMap<Long, Resource> getAll() { return all_resources; }
}
