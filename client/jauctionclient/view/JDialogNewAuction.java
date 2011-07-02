package jauctionclient.view;
import jauctionclient.commands.Model;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;


public class JDialogNewAuction extends JDialog implements Observer {
	private final JPanel contentPanel = new JPanel();
	private JComboBox cmbResource;
	private JSpinner spinAmount;
	private JSpinner spinPrice;
	private JSpinner spinEndAuction;
	private Model model;

	/**
	 * Create the dialog.
	 */
	public JDialogNewAuction(JFrame jframe, Model model) {
		super(jframe, true);
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
			JLabel lblEndAuctionOn = new JLabel("Seconds Left:");
			contentPanel.add(lblEndAuctionOn, "2, 8, right, default");
		}
		{
			spinEndAuction = new JSpinner();
			contentPanel.add(spinEndAuction, "4, 8");
		}
		{
			JLabel lblLblmessage = new JLabel("");
			lblLblmessage.setVisible(false);
			contentPanel.add(lblLblmessage, "4, 10");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton CreateButton = new JButton("Create Auction");
				CreateButton.addActionListener(new CreateButtonActionListener());
				CreateButton.setActionCommand("CreateAuction");
				buttonPane.add(CreateButton);
				getRootPane().setDefaultButton(CreateButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private class CreateButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
