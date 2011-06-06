package server;
/*************************************************************************
 
*  Compilation:  javac In.java
 *  Execution:    java In
 *
 *  Reads in data of various types from: stdin, file, URL.
 *
 *  % java In
 *
 *  Remarks
 *  -------
 *    - isEmpty() returns true if there is no more input or
 *      it is all whitespace. This might lead to surprising behavior
 *      when used with readChar()
 *
 *************************************************************************/


import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Locale;


public final class In {
    private Scanner scanner;

    private String charsetName = "ISO-8859-1";

    private Locale usLocale = new Locale("en", "US");

   /**
     * Create an input stream from a socket.
     */
    public In(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), charsetName);
            scanner.useLocale(usLocale);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }

    /**
     * Does the input stream exist?
     */
    public boolean exists()  {
        return scanner != null;
    }

   /**
     * Is the input stream empty?
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

   /**
     * Does the input stream have a next line?
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

   /**
     * Read and return the next line.
     */
    public String readLine() {
        String line = null;
        try                 { line = scanner.nextLine(); }
        catch (Exception e) {                            }
        return line;
    }
    public void close() { scanner.close();  }
}
