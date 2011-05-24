package server;

import java.net.Socket;
import java.net.ServerSocket;
import net.sf.json.*;
import org.apache.commons.io.*;
import java.io.InputStream;

public class JAuctionServer {

    public static void main(String[] args) throws Exception {

        // create socket
        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // repeatedly wait for connections, and process
        while (true) {

            // a "blocking" call which waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");
            // open up IO streams
            In  in  = new In (clientSocket);
            Out out = new Out(clientSocket);
            // waits for data and reads it in until connection dies
            // readLine() blocks until the server receives a new line from client
            String s;
            String command = null;
            JSONObject json;
            /*System.err.println("trying to read file");
            InputStream is = JAuctionServer.class.getResourceAsStream("/src/example.txt");
            System.err.println("file read");
            String jsonTxt = IOUtils.toString( is );
            System.err.println("stringed it");
            JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );
            System.err.println("json parsed it");
            */
            while ((s = in.readLine()) != null) {
                try {
                  json = (JSONObject) JSONSerializer.toJSON(s);
                  command = json.getString("command");
                }catch(net.sf.json.JSONException e){
                	out.println("bad request: "+s);
                }
                if(command != null){
	                out.println("Command: " + command);
	                if (command.equals("get_money")){ 
	                  out.println("{command: '"+command+"', response: { money: 2343} }");
	                }else if (command.equals("quit")){
	                	out.println("goodbye!");
	                	break;
	                }else{
	                  out.println("bad command: "+command);
	                }
                }
            }

            // close IO streams, then socket
            System.err.println("Closing connection with client");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
