package server;

import junit.framework.TestCase;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.ClassNotFoundException;
import java.io.*;

public class JAuctionServerTest extends TestCase {
	private ServerThread server;
	private Thread t;
	protected void setUp() throws Exception {
		server = new ServerThread();
		t = new Thread();
	}

	protected void tearDown() throws Exception {
		t.stop();
		super.tearDown();
		System.out.println("Test finished");
	}
	
	public void testMain() {
		try {
		InetAddress host = InetAddress.getLocalHost();
		Socket socket = new Socket(host.getHostName(), 4444);
		
		assertFalse(socket.isClosed());
		
		PrintWriter printWriter =
            new PrintWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()));
		
        printWriter.println("{command:'quit'}");

        printWriter.flush();

	
        In in = new In (socket);
		String message = (String) in.readLine();
		System.out.println("Message: " + message);
		
		printWriter.close();
		in.close();
		
		
		assertEquals(true, true);
		
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
			fail("Unknown Host");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IO Exception");
		}
	}

}

class ServerThread implements Runnable {
    private static int port = 4444;
    private static int maxConnections = 0;
	public void run(){
		
	    System.err.println("Started server on port " + port);
	
	    int con_counter=0;
	
	    try{
	      ServerSocket serverSocket = new ServerSocket(port);
	      Socket clientSocket;
	
	      while((con_counter++ < maxConnections) || (maxConnections == 0)){
	
	        clientSocket = serverSocket.accept();
	        System.err.println("Accepted connection from client");
	        
	        Connection conn_c= new Connection(clientSocket);
	        Thread t = new Thread(conn_c);
	        t.start();
	      }
	    } catch (IOException ioe) {
	      System.out.println("IOException on socket listen: " + ioe);
	      ioe.printStackTrace();
	    }  
	}    
}