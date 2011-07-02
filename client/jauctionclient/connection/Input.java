package jauctionclient.connection;

import jauctionclient.JSONEvent;
import jauctionclient.JSONListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import javax.swing.event.EventListenerList;

import org.json.JSONException;
import org.json.JSONObject;

public class Input extends Thread {
	private BufferedReader stream;
	private EventListenerList listeners = new EventListenerList(); 
	
	public Input(InputStream stream) {
		setDaemon(true);
		this.stream = new BufferedReader (new InputStreamReader(stream));
	}	
	
	public void run() {
		System.out.println("Listening to server");
		while(!isInterrupted()) {
			try {
				String input = this.stream.readLine();
				System.out.println("Received Data: "+ input);
				try {
					notifyJSON(new JSONEvent(this, new JSONObject(input))); 
				} catch (JSONException e){
					System.out.println("Bad Request: "+ input);
				}
			} catch (SocketException e){
			} catch (IOException e) {
				close();
				e.printStackTrace();
			}
		}
		System.out.println("Listening finished");
	}
	
	
	public void addJSONListener(JSONListener listener) { listeners.add(JSONListener.class, listener); }
	
	public void removeJSONListener(JSONListener listener) { listeners.remove(JSONListener.class, listener); }
	
	protected void notifyJSON(JSONEvent event){
		for ( JSONListener listener : listeners.getListeners( JSONListener.class ) ) 
		      listener.jsonReceived( event );
	}


	public void close() {
		interrupt();
		try {
			stream.close();
		} catch (IOException e) {}
	}
}