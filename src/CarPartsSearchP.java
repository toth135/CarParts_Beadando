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
import javax.swing.SwingConstants;

public class CarPartsSearchP extends JDialog {
	private JTable table;
	private CarPartsTM cptm;
	private JTextField megnev;
	private JTextField szinkod;
	private JTextField auto;
	private JTextField evjarat;
	private JTextField kod;

	/**
	 * Create the dialog.
	 */
	public CarPartsSearchP(JFrame f, CarPartsTM bcptm, String mezo, String kulcs) {	
		super(f, "Alkatrészek keresése", true);
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
		btnBezr.setBounds(362, 237, 117, 25);
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
		
		megnev = new JTextField(kulcs);
		megnev.setBounds(126, 191, 140, 19);
		getContentPane().add(megnev);
		megnev.setColumns(10);
		
		szinkod = new JTextField(kulcs);
		szinkod.setColumns(10);
		szinkod.setBounds(278, 191, 50, 19);
		getContentPane().add(szinkod);
		
		auto = new JTextField(kulcs);
		auto.setColumns(10);
		auto.setBounds(511, 191, 154, 19);
		getContentPane().add(auto);
		
		evjarat = new JTextField(kulcs);
		evjarat.setHorizontalAlignment(SwingConstants.RIGHT);
		evjarat.setColumns(10);
		evjarat.setBounds(671, 191, 56, 19);
		getContentPane().add(evjarat);
		
		kod = new JTextField(kulcs);
		kod.setHorizontalAlignment(SwingConstants.RIGHT);
		kod.setColumns(10);
		kod.setBounds(66, 191, 56, 19);
		getContentPane().add(kod);
		TableRowSorter<CarPartsTM> trs = (TableRowSorter<CarPartsTM>)table.getRowSorter();
		trs.setSortable(0, false);
		
		kod.setVisible(false); megnev.setVisible(false);
		szinkod.setVisible(false); auto.setVisible(false);
		evjarat.setVisible(false);
		
		if(mezo.equals("kod")) kod.setVisible(true);
		if(mezo.equals("megnev")) megnev.setVisible(true);
		if(mezo.equals("szinkod")) szinkod.setVisible(true);
		if(mezo.equals("auto")) auto.setVisible(true);
		if(mezo.equals("evjarat")) evjarat.setVisible(true);

	}
	
}
