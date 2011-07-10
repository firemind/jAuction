package jauctionclient.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.event.EventListenerList;

import org.json.JSONException;
import org.json.JSONObject;

public class Input extends Thread {
	private BufferedReader stream;
	private EventListenerList listeners = new EventListenerList(); 
	private static Object wait_for_command = new Object();
	
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
				new NotifyJSON(input).start(); 
			} catch (IOException e) {
				close();
				e.printStackTrace();
			}
		}
		System.out.println("Listening finished");
	}
	
	
	public void addJSONListener(JSONListener listener) { listeners.add(JSONListener.class, listener); }
	
	public void removeJSONListener(JSONListener listener) { listeners.remove(JSONListener.class, listener); }
	
	
	class NotifyJSON extends Thread {
		String input;
		JSONEvent event;
		
		public NotifyJSON(String input) {
			this.input = input;
		}
		
		public void run(){				
			try {
				event = new JSONEvent(this, new JSONObject(input));
				for ( JSONListener listener : listeners.getListeners( JSONListener.class ) )
					listener.jsonReceived( event );
			} catch (JSONException e){
				System.out.println("Bad Request: "+ input);
			}
		}
	}

	public void close() {
		interrupt();
		try {
			stream.close();
		} catch (IOException e) {}
	}
}