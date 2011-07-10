package jauctionclient;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ConnectException;

import jauctionclient.commands.Model;
import jauctionclient.connection.Connection;
import jauctionclient.view.*;

public class Client {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			final Model model = new Model();
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainView mainWindow = new MainView(model);
						mainWindow.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});			
	}	
}
