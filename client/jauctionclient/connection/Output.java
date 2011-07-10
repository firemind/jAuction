package jauctionclient.connection;

import java.io.OutputStream;
import java.io.PrintStream;

public class Output implements JSONListener {
	private PrintStream stream;
	
	public Output(OutputStream stream) {
		this.stream = new PrintStream(stream);
	}

	@Override
	public void jsonReceived(JSONEvent e) {
		if(stream != null){
			this.stream.println(e.getJson().toString());
			this.stream.flush();
			System.out.println("Send Data: " + e.getJson().toString());
		}
	}

	public void close() {
		stream.close();
		stream = null;
	}
}