package server;

import java.net.Socket;
import java.net.ServerSocket;
import net.sf.json.*;
import org.apache.commons.io.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class JAuctionServer {
    private static int port = 4444;
    private static int maxConnections = 0;
    public static Hashtable serverCommands = new Hashtable();
    
    
    public static void main(String[] args) throws Exception {

    	serverCommands.put("login", Login.class);
    	serverCommands.put("get_commands", GetCommands.class);
    	serverCommands.put("get_money", GetMoney.class);
    	serverCommands.put("get_stock", GetStock.class);
    	serverCommands.put("quit", Quit.class);
    	
	    System.err.println("Started server on port " + port);
	
	    int con_counter=0;
	
	    try{
	      ServerSocket serverSocket = new ServerSocket(port);
	      Socket clientSocket;
	
	      while((con_counter++ < maxConnections) || (maxConnections == 0)){
	
	        clientSocket = serverSocket.accept();
	        System.err.println("Accepted connection from client");
	        
	        Connection conn_c= new Connection(clientSocket, serverCommands);
	        Thread t = new Thread(conn_c);
	        t.start();
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }   
        
    }

}
