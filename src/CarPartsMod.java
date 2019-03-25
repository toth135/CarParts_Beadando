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
import javax.swing.JTextField;

public class CarPartsMod extends JDialog {
	private JTable table;
	private CarPartsTM cptm;
	private JTextField megnev;
	private JTextField szinkod;
	private JTextField gyartasd;
	private JTextField auto;
	private JTextField evjarat;

	/**
	 * Create the dialog.
	 */
	public CarPartsMod(JFrame f, CarPartsTM bcptm) {	
		super(f, "Alkatrészek módosítása", true);
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
		scrollPane.setBounds(12, 10, 713, 169);
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
		
		megnev = new JTextField();
		megnev.setBounds(126, 191, 140, 19);
		getContentPane().add(megnev);
		megnev.setColumns(10);
		
		szinkod = new JTextField();
		szinkod.setColumns(10);
		szinkod.setBounds(278, 191, 50, 19);
		getContentPane().add(szinkod);
		
		gyartasd = new JTextField();
		gyartasd.setColumns(10);
		gyartasd.setBounds(336, 191, 163, 19);
		getContentPane().add(gyartasd);
		
		auto = new JTextField();
		auto.setColumns(10);
		auto.setBounds(511, 191, 154, 19);
		getContentPane().add(auto);
		
		evjarat = new JTextField();
		evjarat.setColumns(10);
		evjarat.setBounds(671, 191, 56, 19);
		getContentPane().add(evjarat);
		
		JButton modosit = new JButton("Módosít");
		modosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!BasicClass.filled(megnev) && !BasicClass.filled(szinkod) && !BasicClass.filled(gyartasd) && !BasicClass.filled(auto) && !BasicClass.filled(evjarat))
					BasicClass.showMD("Egyetlen módosító adat sincs beírva!", 0);
				else if(BasicClass.filled(szinkod) && !BasicClass.goodInt(szinkod)) BasicClass.showMD("A Színkód mezőben hibás adat van!", 0);
				else if (BasicClass.filled(gyartasd) && !BasicClass.goodDate(gyartasd)) BasicClass.showMD("A Gyártás dátum mezőben hibás adat van!", 0);
				else if(BasicClass.filled(evjarat) && !BasicClass.goodInt(evjarat)) BasicClass.showMD("Az Évjárat mezőben hibás adat van!", 0);
				else {
					int db = 0, jel = 0, x = 0;
					for(x = 0; x < cptm.getRowCount(); x++)
						if((Boolean)cptm.getValueAt(x, 0)) {db++; jel = x;}
					if(db == 0) BasicClass.showMD("Nincs kijelölve a módosítandó rekord!", 0);
					if(db > 1) BasicClass.showMD("Több rekord van kijelölve!\n Egyszerre csak egy rekord módosítható!", 0);
					if(db == 1) {
						if (BasicClass.filled(megnev)) cptm.setValueAt(BasicClass.RF(megnev), jel, 2);
						if (BasicClass.filled(szinkod)) cptm.setValueAt(BasicClass.RF(szinkod), jel, 3);
						if (BasicClass.filled(gyartasd)) cptm.setValueAt(BasicClass.RF(gyartasd), jel, 4);
						if (BasicClass.filled(auto)) cptm.setValueAt(BasicClass.RF(auto), jel, 5);
						if (BasicClass.filled(evjarat)) cptm.setValueAt(BasicClass.RF(evjarat), jel, 5);
						BasicClass.showMD("A rekord módosítva", 1);
						BasicClass.DF(megnev); BasicClass.DF(szinkod); BasicClass.DF(gyartasd); BasicClass.DF(auto); BasicClass.DF(evjarat);
						cptm.setValueAt(new Boolean(false), jel, 0);
						
					}
				}
			}
		});
		modosit.setBounds(429, 237, 117, 25);
		getContentPane().add(modosit);
		TableRowSorter<CarPartsTM> trs = (TableRowSorter<CarPartsTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}
	
}
