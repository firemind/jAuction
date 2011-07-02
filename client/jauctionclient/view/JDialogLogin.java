package jauctionclient.view;
import jauctionclient.commands.Model;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;


public class JDialogLogin extends JDialog implements Observer {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblMessage;
	private Model model;


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
		setBounds(100, 100, 295, 170);
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
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			lblUsername = new JLabel("username:");
			contentPanel.add(lblUsername, "2, 2, right, default");
		}
		{
			this.txtUsername = new JTextField();
			contentPanel.add(this.txtUsername, "4, 2, fill, default");
			this.txtUsername.setColumns(10);
		}
		{
			lblPassword = new JLabel("password:");
			contentPanel.add(lblPassword, "2, 4, right, default");
		}
		{
			this.pwdPassword = new JPasswordField();
			contentPanel.add(this.pwdPassword, "4, 4, fill, default");
		}
		{
			lblMessage = new JLabel("");
			lblMessage.setVisible(false);
			lblMessage.setForeground(Color.RED);
			contentPanel.add(lblMessage, "4, 6");
		}
		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{this.lblUsername, this.txtUsername, this.lblPassword, this.pwdPassword, this.lblMessage}));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Login");
				okButton.addActionListener(new OkButtonActionListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!txtUsername.getText().isEmpty() && pwdPassword.getPassword().length > 0){
				lblMessage.setVisible(false);
				
				((JButton) arg0.getSource()).setEnabled(false);
				txtUsername.setEnabled(false);
				pwdPassword.setEnabled(false);
				
				model.getOutputCommands().login(txtUsername.getText(), String.valueOf(pwdPassword.getPassword()));
				
				repaint();
			} else {
				lblMessage.setText("Please enter both fields");
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
