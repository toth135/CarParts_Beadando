import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.*;


public class FileManager {

	private static String message = "CarParts program üzenet";
	
	public static void CsvReader(File fileName, CarPartsTM cptm) {
		String kod = "", megnev = "", szinkod = "", gyartasd = "", auto = "", evj = "", s ="";
		
		try {
			FileInputStream f=new FileInputStream(fileName);
			LineNumberReader in=new LineNumberReader(new
			InputStreamReader(f));
			s=in.readLine(); //=== mezőnevek az első sorból
			s=in.readLine(); //=== adatsor
			while(s!=null) {
				String[] splitS = s.split(";");
				kod = splitS[0];
				megnev = splitS[1];
				szinkod = splitS[2];
				gyartasd = splitS[3];
				auto = splitS[4];
				evj = splitS[5];
				
				cptm.addRow(new Object[]{new Boolean(false), StoI(kod), megnev, szinkod, gyartasd, auto, StoI(evj)});
				
				s=in.readLine();
			}
			in.close();
			JOptionPane.showMessageDialog(null, "Adatok beolvasva!", message, 1);
		} catch (IOException ioe) {JOptionPane.showMessageDialog(
		null, "CsvReader: "+ioe.getMessage(), message, 0);}
	}
	
	public static void CsvWriter(String fileName, CarPartsTM cptm) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			out.println("Kód;Megnevezés;Színkód;Gyártás dátuma;Autó;Évjárat");
			int rdb = cptm.getRowCount();
			int cdb = cptm.getColumnCount();
			for (int i = 0; i < rdb; i++) {
			for(int j = 1; j < cdb - 1; j++) {
				out.print(""+cptm.getValueAt(i, j) + ";");
			}
			out.println(""+cptm.getValueAt(i, cdb-1));
			}
			out.close();
			JOptionPane.showMessageDialog(null, "Adatok kiírva", message, 1);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "CsvWriter: " + ioe.getMessage(), message, 0);
		}
	}
	
	public static void JsonReader(File fileName, CarPartsTM cptm) {
		StringBuilder jsonData = new StringBuilder();
		try {
			FileInputStream f = new FileInputStream(fileName);
			LineNumberReader in = new LineNumberReader(new InputStreamReader(f));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				jsonData.append(inputLine);
			in.close();
		} catch (IOException e) {System.out.println(e.getMessage());}
		JOptionPane.showMessageDialog(null, "Adatok beolvasva!", message, 1);
		try {
			JSONObject jRootObj = new JSONObject(jsonData.toString());
			JSONObject jObj = jRootObj.getJSONObject("carparts");
			JSONArray jArr = jObj.getJSONArray("carpart");
			for (int i = 0; i < jArr.length(); ++i) {
				JSONObject rec = jArr.getJSONObject(i);
				String kod = rec.getString("Kód");
				String megnev = rec.getString("Megnevezés");
				String szinkod = rec.getString("Színkód");
				String gyartasd = rec.getString("Gyártás dátuma");
				String auto = rec.getString("Autó");
				String evjarat = rec.getString("Évjárat");
				
				cptm.addRow(new Object[] {new Boolean(false), StoI(kod), megnev, StoI(szinkod), gyartasd, auto, StoI(evjarat)});
			}
		} catch (Exception e) {JOptionPane.showMessageDialog(null, "JsonReader: " + e.getMessage(), message, 0);}
	}
	
	public static void JsonWriter(String fileName, CarPartsTM cptm) {
		int rdb = cptm.getRowCount();
		int cdb = cptm.getColumnCount();
		
		JSONObject jRootObj = new JSONObject();
		JSONObject obj = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		try {
			for (int i = 0; i < rdb; i++) {
				JSONObject jo = new JSONObject();
				for (int j = 1; j < cdb -1; j++) {
					jo.put("Kód", cptm.getValueAt(i, j));
					j++;
					jo.put("Megnevezés", cptm.getValueAt(i, j));
					j++;
					jo.put("Színkód", cptm.getValueAt(i, j));
					j++;
					jo.put("Gyártás dátuma", cptm.getValueAt(i, j));
					j++;
					jo.put("Autó", cptm.getValueAt(i, j));
					j++;
					jo.put("Évjárat", cptm.getValueAt(i, j));
					//jArray.put(j, jo);
				}
				jArray.put(i, jo);
			}
			obj.put("carpart", jArray);
			jRootObj.put("carparts", obj);
		} catch (Exception e) {System.out.println(e.getMessage());}
		
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jRootObj.toString());
			String prettyJS = gson.toJson(je);
			
			PrintStream out  = new PrintStream(new FileOutputStream(fileName));
			String[] arrSplit = prettyJS.split("\n");
			for (String x : arrSplit) out.println(x);
			out.close();
			JOptionPane.showMessageDialog(null, "Adatok kiírva", message, 1);
		} catch (Exception e) {JOptionPane.showMessageDialog(null, "JsonWriter: " + e.getMessage(), message, 0);}
	}
	
	public static void XMLReader(File fileName, CarPartsTM cptm){
		String kod="", megnev="", szinkod="", gyartasd="", auto="", evjarat="";
		Document dom;
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(fileName);
			NodeList nodeList = dom.getElementsByTagName("carpart");
			for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element element = (Element) node;
			kod = element.getElementsByTagName("Kód").item(0).getTextContent();
			megnev = element.getElementsByTagName("Megnevezés").item(0).getTextContent();
			szinkod = element.getElementsByTagName("Színkód").item(0).getTextContent();
			gyartasd = element.getElementsByTagName("Gyártás_dátuma").item(0).getTextContent();
			auto = element.getElementsByTagName("Autó").item(0).getTextContent();
			evjarat = element.getElementsByTagName("Évjárat").item(0).getTextContent();
			
			cptm.addRow(new Object[]{new Boolean(false), StoI(kod), megnev, StoI(szinkod), gyartasd, auto, StoI(evjarat)});
			}
			JOptionPane.showMessageDialog(null, "Adatok beolvasva!", message, 1);
			} catch (ParserConfigurationException pce)
			{System.out.println("XmlReader: "+pce.getMessage());}
			catch (SAXException se)
			{System.out.println("XmlReader: "+se.getMessage());}
			catch (IOException ioe)
			{System.out.println("XmlReader: "+ioe.getMessage());}
	}
	
	public static void XMLWriter(String fileName, CarPartsTM cptm) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			Element rootEle = dom.createElement("Carparts");
			Element e = null;
			Element a = null;
			
			int rdb = cptm.getRowCount();
			int cdb = cptm.getColumnCount();
			for (int i = 0; i < rdb; i++) {
				
				a=dom.createElement("carpart");

				rootEle.appendChild(a);

				for(int j = 1; j < cdb - 1; j++) {
				
					e=dom.createElement("kod");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
					j++;
					
					e=dom.createElement("megnev");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
					j++;
					
					e=dom.createElement("szinkod");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
					j++;
					
					e=dom.createElement("gyartasd");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
					j++;
					
					e=dom.createElement("auto");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
					j++;
					
					e=dom.createElement("evjarat");
					e.appendChild(dom.createTextNode(cptm.getValueAt(i, j).toString()));
					a.appendChild(e);
				}
			}
			
			dom.appendChild(rootEle);
			
			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt} indent-amount", "4");
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(fileName)));
				JOptionPane.showMessageDialog(null, "Adatok kiírva", message, 1);
				} catch (TransformerException te)
				{System.out.println("XmlWriter: "+te.getMessage());}
				catch (IOException ioe)
				{System.out.println("XmlWriter: "+ioe.getMessage());}
				} catch (ParserConfigurationException pce)
				{System.out.println("XmlWriter: "+pce.getMessage());}
	}

	public static int StoI(String s){
		int x=-55;
		x = Integer.parseInt(s);
		return x;
	
	}
}