import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CarPartsList extends JDialog {
	private JTable table;
	private CarPartsTM cptm;

	/**
	 * Create the dialog.
	 */
	public CarPartsList(JFrame f, CarPartsTM bcptm) {
		super(f, "Alkatrészek listája", true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		cptm = bcptm;
		setBounds(100, 100, 750, 300);
		getContentPane().setLayout(null);
		
		JButton btnBezr = new JButton("Bezár");
		btnBezr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose(); setVisible(false);
			}
		});
		btnBezr.setBounds(197, 237, 117, 25);
		getContentPane().add(btnBezr);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 713, 215);
		getContentPane().add(scrollPane);
		
		table = new JTable(cptm);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 7; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1 || i ==3 || i == 6) tc.setPreferredWidth(100);
			  else {tc.setPreferredWidth(400);} 
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<CarPartsTM> trs = (TableRowSorter<CarPartsTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}
	
}
