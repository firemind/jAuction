package jauctionclient.connection;

import jauctionclient.JSONEvent;
import jauctionclient.JSONListener;

import java.net.*;
import java.io.*;

public class Connection implements JSONListener {
	private String host;
	private Integer port;
	private Socket server;
	private Input input;
	private Output output;
	
	public Connection(String host, Integer port){
		this.host = host;
		this.port = port;
		
		try {
			this.server = new Socket( this.host, this.port );
			System.out.println("Connect to host: " + this.host);
			this.input = new Input(this.server.getInputStream());
			this.input.start();
			this.output = new Output(this.server.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize() throws Throwable
	{
	  close();
	  super.finalize();
	} 
	
	
	public void addJSONListener(JSONListener listener) { input.addJSONListener(listener); }
	
	public void removeJSONListener(JSONListener listener) { input.removeJSONListener(listener); }
	
	@Override
	public void jsonReceived(JSONEvent e) { output.jsonReceived(e); }
	
	
	public String getHost() { return this.host; }

	public int getPort() { return this.port; }

	public Input getInput() { return this.input; }

	public Output getOutput() { return this.output; }

	public void close() {
		try {
			server.close();
			input.close();
			output.close();
			System.out.println("Connection closed");
		} catch (IOException e) {}
	}
}
