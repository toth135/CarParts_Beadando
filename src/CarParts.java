import java.io.Serializable;
import java.util.Date;

public class CarParts implements Serializable {
	
	private int kod;
	private String megnev;
	private int szinkod;
	private Date gyartasd;
	private String auto;
	private int evjarat;
	
	public CarParts(int kod, String megnev, int szinkod, Date gyartasd, String auto, int evjarat) {
		super();
		this.kod = kod;
		this.megnev = megnev;
		this.szinkod = szinkod;
		this.gyartasd = gyartasd;
		this.auto = auto;
		this.evjarat = evjarat;
	}

	public int getKod() {
		return kod;
	}

	public String getMegnev() {
		return megnev;
	}

	public int getSzinkod() {
		return szinkod;
	}

	public Date getGyartasd() {
		return gyartasd;
	}

	public String getAuto() {
		return auto;
	}

	public int getEvjarat() {
		return evjarat;
	}
	
}
