package jauctionclient;

import jauctionclient.commands.Model;
import jauctionclient.view.MainView;

import java.awt.EventQueue;

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
