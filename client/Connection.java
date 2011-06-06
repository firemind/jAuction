/*
 Author: Victor
*/

import java.net.*;
import java.io.*;

public class Connection {
  private String _host = "zeira.ch";
  private int _port = 4444;
  private Input _input;
  private Output _output;
  private Model _model;

  public Connection(Model model) throws IOException {
	_model = model;
	Socket server = new Socket( _host, _port );
    _input = new Input(server.getInputStream());
    _output = new Output(server.getOutputStream());
  }

  public void request(String json) {
    _output.request(json);
  }

  protected class Input extends Thread {
    private BufferedReader _stream;

    public Input(InputStream stream) {
      _stream = new BufferedReader (new InputStreamReader(stream));
    }

    public void run() {
      while(true) {
        try {
		  _model.extractJson(_stream.readLine());
	    } catch (IOException e) {
		  e.printStackTrace();
	    }
      }
    }
  }

  protected class Output {
    private PrintStream _stream;

    public Output(OutputStream stream) {
      _stream = new PrintStream(stream);
    }

    public void request(String json) {
      _stream.println(json);
      _stream.flush();
    }
  }
}
