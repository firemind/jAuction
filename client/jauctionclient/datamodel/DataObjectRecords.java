package jauctionclient.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import javax.swing.event.EventListenerList;

public abstract class DataObjectRecords<T extends DataObject> extends Observable {
	private final Map<Long, T> all = Collections.synchronizedMap(new HashMap<Long, T>());
	private EventListenerList listeners = new EventListenerList(); 
	
	public DataObjectRecords() {
	}
	
	public void addDataObjectListener(DataObjectListener listener) { listeners.add(DataObjectListener.class, listener); }
	
	public void removeDataObjectListener(DataObjectListener listener) { listeners.remove(DataObjectListener.class, listener); }
	
	protected void requestDataObject(DataObjectEvent e){
		for ( DataObjectListener listener : listeners.getListeners( DataObjectListener.class ) ) 
		      listener.getDataObject( e );
	}
	
	public void addDataObject(T data_object) {
		all.put(data_object.getId(), data_object);			
	}
	
	public void addAllDataObjects(Object[] all_objects) {
		all.clear();
		for(int i = 0; i < all_objects.length; i++) {
			all.put(((T)all_objects[i]).getId(), (T)all_objects[i]);
		}
	}

	public void removeDataObject(Long id) {
		all.remove(id);
	}
	
	
	public Integer size(){
		return all.size();
	}
	
	public T get(Long id) {
		if(all.containsKey(id)){
			return all.get(id);
		} else {
			//requestDataObject(new DataObjectEvent(this, id, "User"));
			return null;
		}
	}

	public HashMap<Long, T> getAll() {
		HashMap m = new HashMap<Long, T>(all);
		return (HashMap<Long, T>) m.clone(); 
	}
}
