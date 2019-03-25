import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class CarPartsNew extends JDialog {
	private JTextField kod;
	private JTextField megnev;
	private JTextField szinkod;
	private JTextField gyartasd;
	private JTextField auto;
	private JTextField evjarat;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	private String message = "Emp program üzenet";
	private CarParts data;
	private int exit = 0;

	/**
	 * Create the dialog.
	 */
	public CarPartsNew(JFrame f, int maxKod) {
		super(f, true);
		setTitle("Új alkatrészek felvitele");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblKd = new JLabel("Kód:");
		lblKd.setBounds(12, 12, 46, 15);
		getContentPane().add(lblKd);
		
		JLabel lblMegnevezs = new JLabel("Megnevezés:");
		lblMegnevezs.setBounds(12, 39, 104, 15);
		getContentPane().add(lblMegnevezs);
		
		JLabel lblSznkd = new JLabel("Színkód:");
		lblSznkd.setBounds(12, 66, 104, 15);
		getContentPane().add(lblSznkd);
		
		JLabel lblGyrtsDtuma = new JLabel("Gyártás dátuma:");
		lblGyrtsDtuma.setBounds(12, 93, 125, 15);
		getContentPane().add(lblGyrtsDtuma);
		
		JLabel lblAut = new JLabel("Autó:");
		lblAut.setBounds(12, 120, 54, 15);
		getContentPane().add(lblAut);
		
		JLabel lblvjrat = new JLabel("Évjárat:");
		lblvjrat.setBounds(12, 147, 104, 15);
		getContentPane().add(lblvjrat);
		
		kod = new JTextField();
		kod.setEditable(false);
		kod.setBounds(141, 10, 54, 19);
		getContentPane().add(kod);
		kod.setColumns(10);
		
		megnev = new JTextField();
		megnev.setColumns(10);
		megnev.setBounds(141, 37, 213, 19);
		getContentPane().add(megnev);
		
		szinkod = new JTextField();
		szinkod.setColumns(10);
		szinkod.setBounds(141, 64, 80, 19);
		getContentPane().add(szinkod);
		
		gyartasd = new JTextField();
		gyartasd.setColumns(10);
		gyartasd.setBounds(141, 91, 131, 19);
		getContentPane().add(gyartasd);
		
		auto = new JTextField();
		auto.setColumns(10);
		auto.setBounds(141, 118, 269, 19);
		getContentPane().add(auto);
		
		evjarat = new JTextField();
		evjarat.setColumns(10);
		evjarat.setBounds(141, 145, 92, 19);
		getContentPane().add(evjarat);
		
		JButton btnLekr = new JButton("Lekér");
		btnLekr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kod.setText(""+(maxKod+1));
			}
		});
		btnLekr.setBounds(220, 7, 92, 25);
		getContentPane().add(btnLekr);
		
		JButton btnBeszr = new JButton("Beszúr");
		btnBeszr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!filled(kod)) kod.setText(""+(maxKod+1));
				if(!filled(megnev)) showMD("A Megnevezés mező üres!", 0);
				else if(!goodInt(szinkod)) showMD("A Színkód mezőben hibás adat van!", 0);
				else if(!filled(szinkod)) showMD("A Színkód mező üres!", 0);
				else if(!goodDate(gyartasd)) showMD("A Gyártás dátuma mezőben hibás adat van!", 0);
				else if(!filled(auto)) showMD("Az Autó mező üres!", 0);
				else if(!goodInt(evjarat)) showMD("Az Évjárat mezőben hibás adat van!", 0);
				else if(!filled(evjarat)) showMD("Az évjárat mező üres!", 0);
				else { 
					data = new CarParts(StoI(RF(kod)), RF(megnev), StoI(RF(szinkod)), StoD(RF(gyartasd)), RF(auto), StoI(RF(evjarat)));
					showMD("Adat beszúrva!", 1);
					exit = 1;
					dispose();
					setVisible(false);
				}
			}
		});
		btnBeszr.setBounds(72, 216, 104, 25);
		getContentPane().add(btnBeszr);
		
		JButton btnBeszrbezr = new JButton("Bezár");
		btnBeszrbezr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBeszrbezr.setBounds(271, 216, 104, 25);
		getContentPane().add(btnBeszrbezr);

	}
	
	public String RF(JTextField a) {
		return a.getText().toString();
	}
	
	public boolean filled(JTextField a) {
		String s = RF(a);
		if (s.length() > 0) return true;
		else return false;
	}
	
	public boolean goodDate(JTextField a) {
		String s = RF(a);
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {return false;}
		if (sdf.format(testDate).equals(s)) return true;
		else return false;
	}
	
	public boolean goodInt(JTextField a) {
		String s = RF(a);
		try {
			Integer.parseInt(s); return true;
		} catch (NumberFormatException e) {return false;}
	}
	
	public void showMD(String s, int i) {
		JOptionPane.showMessageDialog(null, s, message, i);
	}
	
	public Date StoD(String s) {
		Date testDate = null, vid = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {return vid;}
		if (!sdf.format(testDate).equals(s)) {return vid;}
		return testDate;
	}
	
	public int StoI(String s) {
		int x = 55;
		x = Integer.parseInt(s);
		return x;
	}
	
	public CarParts getCarParts() {
		return data;
	}
	
	public int Exit() {
		return exit;
	}
	
}
