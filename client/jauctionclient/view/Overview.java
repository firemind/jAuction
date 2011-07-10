package jauctionclient.view;

import jauctionclient.commands.Model;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Overview extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6224907092214880827L;
	private JTabbedPane tabbedPane;
	private JPanel paneAuctions;
	private JPanel paneMyAuctions;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tblAuctions;
	private JButton btnBuy;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JTable tblMyAuctions;
	private JButton btnCreateNewAuction;
	private JButton btnCancelAuction;
	private JDialogNewAuction diaNewAuction;
	private JOptionPane buyAuctionDialog;
	private Model model;

	/**
	 * Create the panel.
	 */
	public Overview(Model model) {
		this.model = model;
		model.addObserver(this);
		setLayout(new BorderLayout(0, 0));
		add(getTabbedPane());

	}
	public JTable getTblAuctions() {
		if (tblAuctions == null) {
			tblAuctions = new JTable();
			tblAuctions.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			tblAuctions.setFillsViewportHeight(true);
		}
		return tblAuctions;
	}
	public JButton getBtnBuy() {
		if (btnBuy == null) {
			btnBuy = new JButton("buy");
			btnBuy.addActionListener(new BtnBuyActionListener());
			btnBuy.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return btnBuy;
	}
	public JTable getTblMyAuctions() {
		if (tblMyAuctions == null) {
			tblMyAuctions = new JTable();
			tblMyAuctions.setFillsViewportHeight(true);
			tblMyAuctions.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		}
		return tblMyAuctions;
	}
	public JButton getBtnCreateNewAuction() {
		if (btnCreateNewAuction == null) {
			btnCreateNewAuction = new JButton("create new auction");
			btnCreateNewAuction.addActionListener(new BtnCreateNewAuctionActionListener());
		}
		return btnCreateNewAuction;
	}
	public JButton getBtnCancelAuction() {
		if (btnCancelAuction == null) {
			btnCancelAuction = new JButton("cancel auction");
			btnCancelAuction.addActionListener(new BtnCancelAuctionActionListener());
		}
		return btnCancelAuction;
	}
	public JDialogNewAuction getDiaNewAuction() {
		if(diaNewAuction == null){
			diaNewAuction = new JDialogNewAuction((JFrame)SwingUtilities.getWindowAncestor(this), model);
			diaNewAuction.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		}
		return diaNewAuction;
	}
	private JPanel getPanel_1_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_1.add(getBtnCreateNewAuction());
			panel_1.add(getBtnCancelAuction());
		}
		return panel_1;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTblMyAuctions());
		}
		return scrollPane_1;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Auctions", null, getPaneAuctions(), null);
			tabbedPane.addTab("MyAuctions", null, getPanel_1(), null);
		}
		return tabbedPane;
	}
	private JPanel getPaneAuctions() {
		if (paneAuctions == null) {
			paneAuctions = new JPanel();
			paneAuctions.setLayout(new BorderLayout(0, 0));
			paneAuctions.add(getPanel(), BorderLayout.SOUTH);
			paneAuctions.add(getScrollPane(), BorderLayout.CENTER);
		}
		return paneAuctions;
	}
	private JPanel getPanel_1() {
		if (paneMyAuctions == null) {
			paneMyAuctions = new JPanel();
			paneMyAuctions.setLayout(new BorderLayout(0, 0));
			paneMyAuctions.add(getPanel_1_1(), BorderLayout.SOUTH);
			paneMyAuctions.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return paneMyAuctions;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel.add(getBtnBuy());
		}
		return panel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTblAuctions());
		}
		return scrollPane;
	}
	
	private JOptionPane getBuyAuctionDialog() {
		if (buyAuctionDialog == null) {
			buyAuctionDialog = new JOptionPane(
				    "The only way to close this dialog is by\n"
				    + "pressing one of the following buttons.\n"
				    + "Do you understand?",
				    JOptionPane.QUESTION_MESSAGE,
				    JOptionPane.YES_NO_OPTION);	
		}
		return buyAuctionDialog;
	}
	
	private class BtnBuyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = getTblAuctions().getSelectedRow();

			if(row != 1){
				model.getOutputCommands().buyAuction((Long)getTblAuctions().getValueAt(row, 0));
			}
		}
	}	
	
	private class BtnCreateNewAuctionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getDiaNewAuction().setVisible(true);
		}
	}
	private class BtnCancelAuctionActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = getTblMyAuctions().getSelectedRow();
			if(row != 1){
				model.getOutputCommands().cancelAuction((Long)getTblMyAuctions().getValueAt(row, 0));
			}
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("allAuction")) {
			getTblAuctions().setModel(model.getAllAuctionTable());
			repaint();
		}else if(arg.equals("myAuction")){
			getTblMyAuctions().setModel(model.getAllMyAuctionTable());
			repaint();
		}
	}
}
