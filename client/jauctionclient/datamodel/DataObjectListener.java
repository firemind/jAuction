package jauctionclient.datamodel;
import java.util.EventListener;

public interface DataObjectListener extends EventListener {
	public void getDataObject(DataObjectEvent e);
}
