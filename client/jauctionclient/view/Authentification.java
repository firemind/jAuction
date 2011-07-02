package jauctionclient.view;
import jauctionclient.*;
import jauctionclient.commands.Model;
import jauctionclient.datamodel.CurrentUser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class Authentification extends JDialog implements Observer {	
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblError;
	private Model model;

	/**
	 * Create the dialog.
	 */
	public Authentification(final Model model) {
		this.model = model;
		this.model.addObserver(this);
		
		setResizable(false);
		setAlwaysOnTop(true);
		setModal(true);
		setTitle("Authentification");
		setBounds(100, 100, 322, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
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
			JLabel lblUsername = new JLabel("username:");
			contentPanel.add(lblUsername, "2, 2, left, default");
		}
		{
			textField = new JTextField();
			contentPanel.add(textField, "4, 2, fill, default");
			textField.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("password:");
			contentPanel.add(lblPassword, "2, 4, left, default");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "4, 4, fill, default");
		}
		{
			lblError = new JLabel("");
			lblError.setForeground(Color.RED);
			contentPanel.add(lblError, "4, 6");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!textField.getText().isEmpty() && passwordField.getPassword().length > 0){
							lblError.setText("");
							((JButton) e.getSource()).setEnabled(false);
							textField.setEnabled(false);
							passwordField.setEnabled(false);
							
							repaint();
							model.getOutputCommands().login(textField.getText(), String.valueOf(passwordField.getPassword()));
						} else {
							lblError.setText("Please enter both fields");
							repaint();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if((arg.getClass() == CurrentUser.class) && (((CurrentUser)arg).isLogon())){
			setVisible(false);
		}
	}
}
