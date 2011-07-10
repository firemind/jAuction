package jauctionclient.view;

import jauctionclient.commands.Model;
import jauctionclient.datamodel.CurrentUser;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

public class MyData extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5506129463852649454L;
	private JLabel lblMyData;
	private JLabel lblNameDisplay;
	private JLabel lblMoneyDisplay;
	private JLabel lblName;
	private JLabel lblMoney;
	private Model model;

	/**
	 * Create the panel.
	 */
	public MyData(Model model) {
		this.model = model;
		this.model.addObserver(this);
		setMaximumSize(new Dimension(32767, 81));
		setPreferredSize(new Dimension(178, 81));
		setMinimumSize(new Dimension(20, 81));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		add(getLblMyData(), "2, 2");
		add(getLblNameDisplay(), "2, 4");
		add(getLblName(), "4, 4");
		add(getLblMoneyDisplay(), "2, 6");
		add(getLblMoney(), "4, 6");

	}

	private JLabel getLblMyData() {
		if (lblMyData == null) {
			lblMyData = new JLabel("My Data");
		}
		return lblMyData;
	}
	private JLabel getLblNameDisplay() {
		if (lblNameDisplay == null) {
			lblNameDisplay = new JLabel("username:");
			lblNameDisplay.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return lblNameDisplay;
	}
	private JLabel getLblMoneyDisplay() {
		if (lblMoneyDisplay == null) {
			lblMoneyDisplay = new JLabel("money:");
			lblMoneyDisplay.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return lblMoneyDisplay;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("-");
		}
		return lblName;
	}
	private JLabel getLblMoney() {
		if (lblMoney == null) {
			lblMoney = new JLabel("-");
		}
		return lblMoney;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals("login")){
			CurrentUser cu = model.getCurrentUser();
			if(cu.isLogon()) {
				lblName.setText(cu.getName());
				repaint();
			}
		}else if(arg.equals("updateMoney")){
			CurrentUser cu = model.getCurrentUser();
			if(cu.isLogon()) {
				lblMoney.setText(Integer.toString(cu.getMoney()));
				repaint();
			}
		}
	}
}
