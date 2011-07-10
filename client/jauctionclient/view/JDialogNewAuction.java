package jauctionclient.view;
import jauctionclient.commands.Model;
import jauctionclient.datamodel.Resource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class JDialogNewAuction extends JDialog implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 431502548094942119L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox cmbResource;
	private JSpinner spinAmount;
	private JSpinner spinPrice;
	private JComboBox cmbEndAuction;
	private JLabel lblMessage;
	private Model model;
	private JButton createButton;
	private JButton cancelButton;
	private enum duration { SHORT, MEDIUM, LONG };

	/**
	 * Create the dialog.
	 */
	public JDialogNewAuction(JFrame jframe, Model model) {
		super(jframe, true);
		addWindowListener(new ThisWindowListener());
		this.model = model;
		model.addObserver(this);
		setMinimumSize(new Dimension(292, 252));
		setTitle("New Auction");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 292, 252);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblResource = new JLabel("Resource:");
			contentPanel.add(lblResource, "2, 2");
		}
		{
			cmbResource = new JComboBox();
			cmbResource.addItemListener(new CmbResourceItemListener());
			contentPanel.add(cmbResource, "4, 2, fill, default");
		}
		{
			JLabel lblAmount = new JLabel("Amount:");
			contentPanel.add(lblAmount, "2, 4");
		}
		{
			spinAmount = new JSpinner();
			contentPanel.add(spinAmount, "4, 4");
		}
		{
			JLabel lblPrice = new JLabel("Price:");
			contentPanel.add(lblPrice, "2, 6");
		}
		{
			spinPrice = new JSpinner();
			contentPanel.add(spinPrice, "4, 6");
		}
		{
			JLabel lblEndAuctionOn = new JLabel("Duration:");
			contentPanel.add(lblEndAuctionOn, "2, 8");
		}
		{
			cmbEndAuction = new JComboBox();
			cmbEndAuction.setModel(new DefaultComboBoxModel(duration.values()));
			contentPanel.add(cmbEndAuction, "4, 8");
		}
		{
			lblMessage = new JLabel("");
			this.lblMessage.setForeground(Color.RED);
			lblMessage.setVisible(false);
			contentPanel.add(lblMessage, "4, 10");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				createButton = new JButton("Create Auction");
				createButton.addActionListener(new CreateButtonActionListener());
				createButton.setActionCommand("CreateAuction");
				buttonPane.add(createButton);
				getRootPane().setDefaultButton(createButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public JButton getCreateButton() {
		return createButton;
	}
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	protected void updateResourceAmount() {
		if(cmbResource.getSelectedIndex() == -1){
			spinAmount.setModel(new SpinnerNumberModel(0,0,0,10));
			spinAmount.setEnabled(false);
		} else {
			long resource_id = ((Resource)cmbResource.getSelectedItem()).getId();
			int max = model.getAllStock().get(resource_id).getAmount();
			
			spinAmount.setModel(new SpinnerNumberModel(1,1,max,10));
		}
	}
	
	private class CreateButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(cmbResource.getSelectedIndex() != -1) {
				long resource_id = ((Resource)cmbResource.getSelectedItem()).getId();
				int amount = ((SpinnerNumberModel)spinAmount.getModel()).getNumber().intValue();
				int price = ((SpinnerNumberModel)spinPrice.getModel()).getNumber().intValue();
				long time = 1000000000L;
				if(duration.SHORT.equals(cmbEndAuction.getSelectedItem())) {
					time = 1000000000L;
				} else if (duration.MEDIUM.equals(cmbEndAuction.getSelectedItem())) {
					time = 5000000000L;
				} else if (duration.LONG.equals(cmbEndAuction.getSelectedItem())) {
					time = 10000000000L;
				}
				
				model.getOutputCommands().createAuction(resource_id, amount, price, time);
				setVisible(false);
			} else {
				lblMessage.setText("please select a resource");
				lblMessage.setVisible(true);
			}
		}
	}	
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	private class CmbResourceItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			updateResourceAmount();
		}
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowOpened(WindowEvent arg0) {
			cmbResource.setModel(new DefaultComboBoxModel(model.getAllStockArray()));
			updateResourceAmount();
			spinPrice.setModel(new SpinnerNumberModel(1, 1, model.getMaximalAuctionPrice(), 10));
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}
}
