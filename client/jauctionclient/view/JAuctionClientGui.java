package jauctionclient.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JSplitPane;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class JAuctionClientGui {

	private MainView frmAuctionClient;

	/**
	 * Create the application.
	 */
	public JAuctionClientGui() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//this.frmAuctionClient = new MainView(); 
	}
	

	public MainView getFrmAuctionClient() {
		return frmAuctionClient;
	}
}