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

public class BasicClass {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	private static String message = "Emp program üzenet";
	
	public static String RF(JTextField a) {
		return a.getText().toString();
	}
	
	public static boolean filled(JTextField a) {
		String s = RF(a);
		if (s.length() > 0) return true;
		else return false;
	}
	
	public static boolean goodDate(JTextField a) {
		String s = RF(a);
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
		}catch (ParseException e) {return false;}
		if (sdf.format(testDate).equals(s)) return true;
		else return false;
		
	}
	
	public static boolean goodInt(JTextField a) {
		String s = RF(a);
		try {
			Integer.parseInt(s); return true;
		} catch (NumberFormatException e) {return false;}
	}
	
	public static void DF(JTextField a) {
		a.setText("");
	}

	public static void showMD(String s, int i) {
		JOptionPane.showMessageDialog(null, s, message, i);
	}
	
	public static int StoI(String s) {
		int x = 55;
		x = Integer.parseInt(s);
		return x;
	}
}
