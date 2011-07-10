package jauctionclient.datamodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.EventListenerList;

public abstract class AbstractModelObject {
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private EventListenerList listeners = new EventListenerList(); 

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public void addDataObjectListener(DataObjectListener listener) { listeners.add(DataObjectListener.class, listener); }
	
	public void removeDataObjectListener(DataObjectListener listener) { listeners.remove(DataObjectListener.class, listener); }
	
	protected void requestDataObject(DataObjectEvent e){
		for ( DataObjectListener listener : listeners.getListeners( DataObjectListener.class ) ) 
		      listener.getDataObject( e );
	}
}