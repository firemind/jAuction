package jauctionclient.connection;

import java.util.EventListener;

public interface JSONListener extends EventListener {
	  void jsonReceived( JSONEvent e );
}
