/*************************************************************************
 *  Compilation:  javac Out.java
 *  Execution:    java Out
 *
 *  Writes data of various types to: stdout, file, or socket.
 *
 *************************************************************************/

package server;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Out {
    private PrintWriter out;

    // for Socket output
    public Out(Socket socket) {
        try                     { out = new PrintWriter(socket.getOutputStream(), true); }
        catch (IOException ioe) { ioe.printStackTrace();                                 }
    }

    public void close() { out.close(); }

    public void println(Object x)  { out.println(x); out.flush(); }
    
    public void print(Object x)    { out.print(x);   out.flush(); }

}
