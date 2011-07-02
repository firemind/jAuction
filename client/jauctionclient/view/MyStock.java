package jauctionclient.view;

import jauctionclient.commands.Model;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

public class MyStock extends JPanel implements Observer {
	private JLabel lblMyStock;
	private JTable tblMyStock;
	private JScrollPane scrlpnMyStrock;
	private Model model;

	/**
	 * Create the panel.
	 */
	public MyStock(Model model) {
		this.model = model;
		model.addObserver(this);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("2dlu"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,}));
		add(getLblMyStock(), "2, 2");
		add(getScrlpnMyStrock(), "2, 4, fill, fill");	
	}

	
	private JLabel getLblMyStock() {
		if (lblMyStock == null) {
			lblMyStock = new JLabel("My Stock");
		}
		return lblMyStock;
	}
	
	private JTable getTblMyStock() {
		if (tblMyStock == null) {
			tblMyStock = new JTable();
			tblMyStock.setPreferredScrollableViewportSize(new Dimension(178, 400));
			tblMyStock.setFillsViewportHeight(true);
		}
		return tblMyStock;
	}
	
	private JScrollPane getScrlpnMyStrock() {
		if (scrlpnMyStrock == null) {
			scrlpnMyStrock = new JScrollPane();
			scrlpnMyStrock.setViewportView(getTblMyStock());
		}
		return scrlpnMyStrock;
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
