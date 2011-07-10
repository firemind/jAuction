package jauctionclient.view;
import jauctionclient.commands.Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class JDialogLogin extends JDialog implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6029250077518363485L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblMessage;
	private Model model;
	private JLabel lblHost;
	private JTextField txtHost;
	private JFormattedTextField txtPort;
	private JLabel lblNewLabel;
	private MaskFormatter formatter;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;


	/**
	 * Create the dialog.
	 */	
	public JDialogLogin(JFrame jframe, Model model) {
		super(jframe, true);
		this.model = model;
		model.addObserver(this);
		setMinimumSize(new Dimension(295, 170));
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("login");
		setModal(true);
		setBounds(100, 100, 295, 237);
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
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			lblMessage = new JLabel("");
			lblMessage.setVisible(false);
			{
				lblHost = new JLabel("host");
				contentPanel.add(lblHost, "2, 2");
			}
			{
				txtHost = new JTextField();
				txtHost.setText("zeira.ch");
				contentPanel.add(txtHost, "4, 2, fill, default");
				txtHost.setColumns(10);
			}
			{
				lblNewLabel = new JLabel("port");
				contentPanel.add(lblNewLabel, "2, 4");
			}
			{
				txtPort = new JFormattedTextField(getNumberFormatter());
				txtPort.setText("4444");
				contentPanel.add(txtPort, "4, 4, fill, default");
				txtPort.setColumns(10);
			}
			{
				lblUsername = new JLabel("username:");
				contentPanel.add(lblUsername, "2, 6");
			}
			{
				this.txtUsername = new JTextField();
				contentPanel.add(this.txtUsername, "4, 6, fill, default");
				this.txtUsername.setColumns(10);
			}
			{
				lblPassword = new JLabel("password:");
				contentPanel.add(lblPassword, "2, 8");
			}
			{
				this.pwdPassword = new JPasswordField();
				contentPanel.add(this.pwdPassword, "4, 8, fill, default");
			}
			lblMessage.setForeground(Color.RED);
			contentPanel.add(lblMessage, "4, 10");
		}
		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsername, lblMessage, lblPassword, lblUsername}));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Login");
				okButton.addActionListener(new OkButtonActionListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsername, pwdPassword, okButton, cancelButton, txtHost, txtPort, contentPanel, lblHost, lblNewLabel, lblUsername, lblPassword, lblMessage, buttonPane}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsername, pwdPassword, okButton, cancelButton, txtHost, txtPort, lblUsername, lblPassword, lblHost, lblNewLabel, getContentPane(), contentPanel, lblMessage, buttonPane}));
	}
	
	protected MaskFormatter getNumberFormatter() {
	    if(formatter == null){
	        try {
				formatter = new MaskFormatter("####");
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    }
	    return formatter;
	}


	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!txtUsername.getText().isEmpty() && pwdPassword.getPassword().length > 0 && !txtHost.getText().isEmpty() && !txtPort.getText().isEmpty()){
				lblMessage.setVisible(false);
				
				((JButton) arg0.getSource()).setEnabled(false);
				txtUsername.setEnabled(false);
				pwdPassword.setEnabled(false);
				txtHost.setEnabled(false);
				txtPort.setEnabled(false);
				lblMessage.setVisible(true);
				
				repaint();
				
				if(model.setConnection(txtHost.getText(), new Integer(txtPort.getText()) )) {
					model.getOutputCommands().login(txtUsername.getText(), String.valueOf(pwdPassword.getPassword()));
				} else {
					txtUsername.setEnabled(true);
					pwdPassword.setEnabled(true);
					txtHost.setEnabled(true);
					txtPort.setEnabled(true);
					((JButton) arg0.getSource()).setEnabled(true);
					lblMessage.setText("Connection failed");
					lblMessage.setVisible(true);

					repaint();
				}
			} else {
				lblMessage.setText("Please enter all fields");
				lblMessage.setVisible(true);
				
				repaint();
			}
		}
	}
	
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("login")){
			if((Boolean) model.getCurrentUser().isLogon()) {
				setVisible(false);
			} else {
				lblMessage.setText("login failed");
				lblMessage.setVisible(true);
			}
		}
	}
}
