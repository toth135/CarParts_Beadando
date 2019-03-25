
public class CarPartsSearch {
	
	public static boolean KeyCheck(String mezo, String key) {
		
		boolean vi = false; Character fc = ' '; String fs = "";
		if (mezo.equals("kod")) {
			if (key.substring(0,1).equals("=")) key = key.substring(1,key.length());
			vi = goodStoInt(key);
		}
		if (mezo.equals("megnev")) {
			fs = key.substring(0,1);
			if (Character.isLetter(key.charAt(0)) || fs.equals(" ")) vi = true;
		}
		if(mezo.equals("szinkod")) {
			fs = key.substring(0,1);
			fc = key.charAt(0);
			if (fs.equals("<") || fs.equals(">") || fs.equals("=")) {
				if (key.length() > 1 && goodStoInt(key.substring(1,key.length()))) vi = true;
			  else vi = false;
			}
		}
		if (mezo.equals("auto")) {
			fs = key.substring(0,1);
			if (Character.isLetter(key.charAt(0)) || fs.equals(" ")) vi = true;
		}
		if(mezo.equals("evjarat")) {
			fs = key.substring(0,1);
			fc = key.charAt(0);
			if (fs.equals("<") || fs.equals(">") || fs.equals("=")) {
				if (key.length() > 1 && goodStoInt(key.substring(1,key.length()))) vi = true;
			  else vi = false;
			}
		}
		if (Character.isDigit(fc) && key.indexOf("..") > 0) {
			int x = key.indexOf("..");
			String k1 = key.substring(0, x);
			String k2 = key.substring(x+2, key.length());
			if (goodStoInt(k1) && goodStoInt(k2)) vi = true;
			  else vi = false;
		}
		return vi;
	}
	
	public static CarPartsTM Select(CarPartsTM cptm, String mezo, String key) {
		Object cptmn[] = {"Jel", "Kód", "Megnevezés", "Színkód", "Gyártás dátuma", "Autó", "Évjárat"};
		CarPartsTM searchtm = new CarPartsTM(cptmn, 0);
		String k = "", k1 = "", k2 = ""; int x = 0;
		if (mezo.equals("kod") && key.substring(0,1).equals("="))
			key = key.substring(1, key.length());
		if (mezo.equals("evjarat")) 
				k = key.substring(1, key.length());
		if (mezo.equals("szinkod")) 
			k = key.substring(1, key.length());
		if (mezo.equals("evjarat" ) && key.indexOf("..") > 0) {
			x = key.indexOf("..");
			k1 = key.substring(0, x);
			k2 = key.substring(x+2, key.length());
		}
		if (mezo.equals("szinkod" ) && key.indexOf("..") > 0) {
			x = key.indexOf("..");
			k1 = key.substring(0, x);
			k2 = key.substring(x+2, key.length());
		}
		
		for (int i = 0; i < cptm.getRowCount(); i++) {
			if (mezo.equals("kod") && key.equals(cptm.getValueAt(i, 1).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("megnev") && cptm.getValueAt(i, 2).toString().indexOf(key) >= 0) Pack(cptm, searchtm, i);
			if (mezo.equals("auto") && key.equals(cptm.getValueAt(i, 5).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("szinkod") && key.substring(0, 1).equals("=") && k.equals(cptm.getValueAt(i, 3).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("szinkod") && key.substring(0, 1).equals(">") && BasicClass.StoI(k) < BasicClass.StoI(cptm.getValueAt(i, 3).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("szinkod") && key.substring(0, 1).equals("<") && BasicClass.StoI(k) > BasicClass.StoI(cptm.getValueAt(i, 3).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("szinkod") && key.indexOf("..") > 0) {
				String s = cptm.getValueAt(i, 3).toString();
				if (BasicClass.StoI(k1) < BasicClass.StoI(s) && BasicClass.StoI(k2) > BasicClass.StoI(s)) Pack(cptm, searchtm, i);
			}
			if (mezo.equals("evjarat") && key.substring(0, 1).equals("=") && k.equals(cptm.getValueAt(i, 6).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("evjarat") && key.substring(0, 1).equals(">") && BasicClass.StoI(k) < BasicClass.StoI(cptm.getValueAt(i, 6).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("evjarat") && key.substring(0, 1).equals("<") && BasicClass.StoI(k) > BasicClass.StoI(cptm.getValueAt(i, 6).toString())) Pack(cptm, searchtm, i);
			if (mezo.equals("evjarat") && key.indexOf("..") > 0) {
				String s = cptm.getValueAt(i, 6).toString();
				if (BasicClass.StoI(k1) < BasicClass.StoI(s) && BasicClass.StoI(k2) > BasicClass.StoI(s)) Pack(cptm, searchtm, i);
			}
		}
		return searchtm;
	}
	
	public static void Pack(CarPartsTM cptm, CarPartsTM searchtm, int row) {
		searchtm.addRow(new Object[] {new Boolean(false),
				BasicClass.StoI(cptm.getValueAt(row, 1).toString()),
				cptm.getValueAt(row, 2).toString(),
				BasicClass.StoI(cptm.getValueAt(row, 3).toString()),
				cptm.getValueAt(row, 4).toString(),
				cptm.getValueAt(row, 5).toString(),
				BasicClass.StoI(cptm.getValueAt(row, 6).toString())});
	}
	
	public static boolean goodStoInt(String s) {
		try {
			Integer.parseInt(s); return true;
		} catch (NumberFormatException e) {return false;}
	}
	
}
