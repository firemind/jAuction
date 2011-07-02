package jauctionclient.view;

import jauctionclient.commands.Model;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
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
import java.util.Observable;
import java.util.Observer;

public class MainView extends JFrame implements Observer {
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenuItem mntmVersion;
	private JSplitPane splitPane;
	private MyStock paneMyStock;
	private MyData paneMyData;
	private Overview tabpaneOverview;
	private JSplitPane verticalSplitPane;
	private JDialogLogin diaLogin;
	private Model model;
	/*
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public MainView(Model model) {
		this.model = model;
		this.model.addObserver(this);
		this.addWindowListener(new FrmAuctionClientWindowListener());
		this.setTitle("jAuction Client");
		this.setBounds(100, 100, 716, 475);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			this.menuBar = new JMenuBar();
			this.setJMenuBar(this.menuBar);
			{
				this.mnHelp = new JMenu("Help");
				this.menuBar.add(this.mnHelp);
				{
					this.mntmVersion = new JMenuItem("Version");
					this.mnHelp.add(this.mntmVersion);
				}
			}
		}
		this.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		{
			splitPane = new JSplitPane();
			splitPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			splitPane.setOneTouchExpandable(true);
			this.getContentPane().add(splitPane);
			{
				verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				verticalSplitPane.setOneTouchExpandable(true);
				{
					paneMyStock = new MyStock(this.model);
					paneMyStock.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					verticalSplitPane.setBottomComponent(paneMyStock);
				}
				{
					paneMyData= new MyData(this.model);
					verticalSplitPane.setTopComponent(paneMyData);
				}
				splitPane.setLeftComponent(verticalSplitPane);
			}
			{
				tabpaneOverview = new Overview(this.model);
				tabpaneOverview.getTblAuctions();
				tabpaneOverview.getTblMyAuctions();
				splitPane.setRightComponent(tabpaneOverview);
			}
		}
	}
	
	private class FrmAuctionClientWindowListener extends WindowAdapter {
		@Override
		public void windowOpened(WindowEvent e) {
			getDiaLogin().setVisible(true);
		}
	}	

	public JDialogLogin getDiaLogin() {
		if(diaLogin == null){
			diaLogin = new JDialogLogin(this, model);
			diaLogin.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		}
		return diaLogin;
	}

	public Model getModel() {
		return model;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}