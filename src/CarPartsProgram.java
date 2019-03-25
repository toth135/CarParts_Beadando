import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.io.*;
import java.text.SimpleDateFormat;
import java.awt.*;

import java.util.Date;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;

public class CarPartsProgram extends JFrame {

	private JPanel contentPane;
	private JTextField fileName;
	private JTextField amountOfData;
	private String source = "Válasszon!";
	private File fLoad;
	private String message = "CarParts program üzenet";
	private CarPartsTM cptm;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	private JTextField destFileName;
	private String destination = "Válasszon!";
	private JTextField kulcs;
	private String kerkif = "kod";
	private CarPartsTM searchtm;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarPartsProgram frame = new CarPartsProgram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CarPartsProgram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 320);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Panel.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoad = new JButton("Betöltés");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (source.equals("Válasszon!"))
					JOptionPane.showMessageDialog(null, "Először válassza ki a forrást!", message, 0);
				if (source.equals("Helyi .csv fájl")) {
					FileDialog fd = new FileDialog(new Frame(), " ", FileDialog.LOAD);
					fd.setFile("*.csv");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fLoad = new File(fd.getDirectory(), fd.getFile());
						String loadedFileName = fd.getFile();
						fileName.setText(loadedFileName);
						FileManager.CsvReader(fLoad, cptm);
					}
				}
				if (source.equals("Helyi .json fájl")) {
					FileDialog fd = new FileDialog(new Frame(), " ", FileDialog.LOAD);
					fd.setFile("*.json");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fLoad = new File(fd.getDirectory(), fd.getFile());
						String befnev = fd.getFile();
						fileName.setText(befnev);
						FileManager.JsonReader(fLoad, cptm);
					}
				}
				if (source.equals("Helyi .xml fájl")) {
					FileDialog fd = new FileDialog(new Frame(), " ", FileDialog.LOAD);
					fd.setFile("*.xml");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fLoad = new File(fd.getDirectory(), fd.getFile());
						String befnev = fd.getFile();
						fileName.setText(befnev);
						FileManager.XMLReader(fLoad, cptm);
					}
				}
				amountOfData.setText("" + cptm.getRowCount());
			}
		});
		btnLoad.setBounds(20, 12, 116, 24);
		contentPane.add(btnLoad);
		
		JButton btnList = new JButton("Lista");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CarPartsList cpl = new CarPartsList(CarPartsProgram.this, cptm);
				cpl.setVisible(true);
			}
		});
		btnList.setBounds(20, 48, 116, 24);
		contentPane.add(btnList);
		
		JButton btnClose = new JButton("Bezár");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(436, 258, 100, 24);
		contentPane.add(btnClose);
		
		JLabel lblSource = new JLabel("Forrás:");
		lblSource.setBounds(154, 17, 70, 15);
		contentPane.add(lblSource);
		
		JLabel lblNumberOfData = new JLabel("Adatok száma:");
		lblNumberOfData.setBounds(154, 53, 115, 15);
		contentPane.add(lblNumberOfData);
		
		String elements[] = {"Válasszon!", "Helyi .csv fájl", "Helyi .xml fájl", "Helyi .json fájl"};
		
		JComboBox jcbSource = new JComboBox();
		jcbSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				source = (String)jcbSource.getSelectedItem();
				fileName.setText(source);
				amountOfData.setText("4");
			}
		});
		jcbSource.setBounds(242, 12, 141, 24);
		contentPane.add(jcbSource);
		
		fileName = new JTextField();
		fileName.setBounds(395, 13, 141, 24);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		amountOfData = new JTextField();
		amountOfData.setColumns(10);
		amountOfData.setBounds(273, 49, 49, 24);
		contentPane.add(amountOfData);
		
		JButton newdata = new JButton("Új adat");
		newdata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int kodv = 0;
				if (cptm.getRowCount() == 0) kodv = 20;
				else kodv = (int)cptm.getValueAt(cptm.getRowCount()-1, 1);
				CarPartsNew cpn = new CarPartsNew(CarPartsProgram.this, kodv);
				cpn.setVisible(true);
				int exit = cpn.Exit();
				if (exit ==1) {
					CarParts newCarParts = cpn.getCarParts();
					Date d = newCarParts.getGyartasd();
					String ddd = sdf.format(d).toString();
					cptm.addRow(new Object[] {new Boolean(false), newCarParts.getKod(), newCarParts.getMegnev(),
							newCarParts.getSzinkod(), ddd, newCarParts.getAuto(), newCarParts.getEvjarat()});
					amountOfData.setText(""+cptm.getRowCount());
				}
				
			}
		});
		newdata.setBounds(20, 84, 116, 24);
		contentPane.add(newdata);
		
		JButton modify = new JButton("Módosítás");
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cptm.getRowCount()==0) BasicClass.showMD("Nincs módosítható adat!", 0);
				else {
					CarPartsMod cpm = new CarPartsMod(CarPartsProgram.this, cptm);
					cpm.setVisible(true);
				}
			}
		});
		modify.setBounds(20, 120, 115, 24);
		contentPane.add(modify);
		
		JButton delete = new JButton("Törlés");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cptm.getRowCount()==0) BasicClass.showMD("Nincs törölhető adat!", 0);
				else {
					CarPartsDel cpd = new CarPartsDel(CarPartsProgram.this, cptm);
					cpd.setVisible(true);
					amountOfData.setText(""+cptm.getRowCount());
				}
			}
		});
		delete.setBounds(20, 160, 115, 24);
		contentPane.add(delete);
		
		JButton write = new JButton("Kiírás");
		write.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(destination.equals("Válasszon!")) SMD("Először válassza ki a Cél-t!");
				else if(cptm.getRowCount()==0) SMD("Nincs kiírható adat!");
				else if(destination.equals("Helyi .csv fájl")) {
					if(destFileName.getText().length()==0) SMD("Nincs megadva a fájl neve!");
					else {
						FileManager.CsvWriter(destFileName.getText().toString(), cptm);
					}
				}
					if(destination.equals("Helyi .json fájl")) {
					if(destFileName.getText().length()==0) SMD("Nincs megadva a fájl neve!");
					else {
							FileManager.JsonWriter(destFileName.getText().toString(), cptm);
					}
				}
					if(destination.equals("Helyi .xml fájl")) {
						if(destFileName.getText().length()==0) SMD("Nincs megadva a fájl neve!");
						else {
								FileManager.XMLWriter(destFileName.getText().toString(), cptm);
						}
				}
					else if(destination.equals(">>> Forrás") && source.equals("Helyi .csv fájl")) {
						String writeFileName = fileName.getText();
						destFileName.setText(writeFileName);
						FileManager.CsvWriter(writeFileName, cptm);
					}
				}
		});
		write.setBounds(20, 218, 115, 24);
		contentPane.add(write);
		
		JLabel lblCl = new JLabel("Cél:");
		lblCl.setBounds(154, 223, 34, 15);
		contentPane.add(lblCl);
		
		String elements2[] = {"Válasszon!",">>> Forrás", "Helyi .xml fájl", "Helyi .csv fájl", "Helyi .json fájl"};
		
		JComboBox jcbDestination = new JComboBox();
		for (String s : elements2) jcbDestination.addItem(s);
		jcbDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destination = (String)jcbDestination.getSelectedItem();
				if (destination.equals(">>> Forrás") && fileName.getText().equals("")) SMD("Nincs megadva a Forrás!");
				if (destination.equals(">>> Forrás") && !fileName.getText().equals("")) destFileName.setText(fileName.getText());
			}
		});
		jcbDestination.setBounds(191, 218, 141, 24);
		contentPane.add(jcbDestination);
		
		destFileName = new JTextField();
		destFileName.setColumns(10);
		destFileName.setBounds(361, 219, 141, 24);
		contentPane.add(destFileName);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(154, 81, 388, 125);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblKeress = new JLabel("Keresés");
		lblKeress.setBounds(12, 12, 70, 15);
		panel.add(lblKeress);
		
		JRadioButton jrbkod = new JRadioButton("Kód");
		jrbkod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbkod.isSelected()) kerkif = "kod";
			}
		});
		jrbkod.setSelected(true);
		jrbkod.setBounds(8, 35, 62, 23);
		panel.add(jrbkod);
		
		JRadioButton jrbmegnev = new JRadioButton("Megnevezés");
		jrbmegnev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbmegnev.isSelected()) kerkif = "megnev";
			}
		});
		jrbmegnev.setBounds(68, 35, 120, 23);
		panel.add(jrbmegnev);
		
		JRadioButton jrbszinkod = new JRadioButton("Színkód");
		jrbszinkod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbszinkod.isSelected()) kerkif = "szinkod";
			}
		});
		jrbszinkod.setBounds(195, 35, 86, 23);
		panel.add(jrbszinkod);
		
		JRadioButton jrbauto = new JRadioButton("Autó");
		jrbauto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbauto.isSelected()) kerkif = "auto";
			}
		});
		jrbauto.setBounds(298, 35, 70, 23);
		panel.add(jrbauto);
		
		JRadioButton jrbevjarat = new JRadioButton("Évjárat");
		jrbevjarat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbevjarat.isSelected()) kerkif = "evjarat";
			}
		});
		jrbevjarat.setBounds(8, 57, 86, 23);
		panel.add(jrbevjarat);
		
		JButton keres = new JButton("Keresés");
		keres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BasicClass.RF(amountOfData).equals("0")) BasicClass.showMD("Nincs betöltött adat!", 0);
				else if (!BasicClass.filled(kulcs)) BasicClass.showMD("A keresőkulcs (X=) nincs megadva!", 0);
				else if (!CarPartsSearch.KeyCheck(kerkif, BasicClass.RF(kulcs))) BasicClass.showMD("A keresőkulcs hibásan van megadva!"
						+ "Helyes forma pl. =667", 0);
				else {
					searchtm = CarPartsSearch.Select(cptm, kerkif, BasicClass.RF(kulcs));
					CarPartsSearchP cps = new CarPartsSearchP(CarPartsProgram.this, searchtm, kerkif, BasicClass.RF(kulcs));
					cps.setVisible(true);
				}
			}
		});
		keres.setBounds(18, 88, 100, 24);
		panel.add(keres);
		
		JLabel lblX = new JLabel("X = ");
		lblX.setBounds(174, 93, 38, 15);
		panel.add(lblX);
		
		kulcs = new JTextField();
		kulcs.setColumns(10);
		kulcs.setBounds(210, 91, 172, 24);
		panel.add(kulcs);
		for (String s : elements) jcbSource.addItem(s);
		
		Object cptmn[] = {"Jel", "Kód", "Megnevezés", "Színkód", "Gyártás dátuma", "Autó", "Évjárat"};
		cptm = new CarPartsTM(cptmn, 0);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbkod);
		bg.add(jrbmegnev);
		bg.add(jrbszinkod);
		bg.add(jrbauto);
		bg.add(jrbevjarat);
	}
	
	public void SMD(String s) {
		JOptionPane.showMessageDialog(null, s, message, 0);
	}
}
