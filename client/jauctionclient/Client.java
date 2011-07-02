package jauctionclient;

import jauctionclient.commands.Model;
import jauctionclient.connection.Connection;
import jauctionclient.view.*;

public class Client {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Model model = new Model();
			Connection connection = new Connection("localhost", 4444);
			
			connection.addJSONListener(model.getInputCommands());
			model.getOutputCommands().addJSONListener(connection);
			
			//
			MainView mainWindow = new MainView(model);
			mainWindow.setVisible(true);
			//
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
