package jauctionclient.connection;


import java.io.IOException;
import java.net.Socket;

public class Connection implements JSONListener {
	private String host;
	private Integer port;
	private Socket server = null;
	private Input input = null;
	private Output output = null;
	
	public Connection(String host, Integer port) throws IOException {
		this.host = host;
		this.port = port;
		
		try {
			this.server = new Socket( this.host, this.port );
			System.out.println("Connect to host: " + this.host);
			this.input = new Input(this.server.getInputStream());
			this.input.start();
			this.output = new Output(this.server.getOutputStream());
		} catch (IOException e) {
			this.close();
			throw e;
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

	public Integer getPort() { return this.port; }

	public Input getInput() { return this.input; }

	public Output getOutput() { return this.output; }

	public void close() {
		try {
			if (server != null)
				server.close();
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			System.out.println("Connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
