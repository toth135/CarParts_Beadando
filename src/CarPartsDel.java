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
import javax.swing.JCheckBox;

public class CarPartsDel extends JDialog {
	private JTable table;
	private CarPartsTM cptm;
	private boolean md = false;

	/**
	 * Create the dialog.
	 */
	public CarPartsDel(JFrame f, CarPartsTM bcptm) {
		super(f, "Alkatrészek törlése", true);
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
		scrollPane.setBounds(12, 10, 713, 168);
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
		
		JCheckBox jcb = new JCheckBox("Legyen több rekord is törölhető egyszerre");
		jcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				md = jcb.isSelected();
			}
		});
		jcb.setBounds(22, 192, 333, 23);
		getContentPane().add(jcb);
		
		JButton delete = new JButton("Töröl");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < cptm.getRowCount(); x++)
					if ((Boolean) cptm.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				if (db == 0)
					BasicClass.showMD("Nincs kijelölve a módosítandó rekord!", 0);
				else if (!md) {
					if (db > 1)
						BasicClass.showMD("Több rekord van kijelölve!\n Egyszerre csak egy rekord módosítható!", 0);
					if (db == 1) {
						cptm.removeRow(jel);
						BasicClass.showMD("A rekord törölve", 1);
					}
				} else {
					for (int i = 0; i < cptm.getRowCount(); i++)
						if ((Boolean) cptm.getValueAt(i, 0)) {
							cptm.removeRow(i);
							i--;
						}
					BasicClass.showMD("Rekord(ok) törölve", 1);
				}
			}
		});
		delete.setBounds(467, 237, 117, 25);
		getContentPane().add(delete);
		TableRowSorter<CarPartsTM> trs = (TableRowSorter<CarPartsTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}
}
