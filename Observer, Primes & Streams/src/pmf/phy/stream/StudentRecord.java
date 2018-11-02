package pmf.phy.stream;



public class StudentRecord {


	private String jmbag;
	
	private String prezime;
	
	private String ime;
	
	private double bodoviMI;
	
	private double bodoviZI;
	
	private double bodoviLA;
	
	private int ocjena;
	
	

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", prezime=" + prezime
				+ ", ime=" + ime + ", bodoviMI=" + bodoviMI + ", bodoviZI="
				+ bodoviZI + ", bodoviLA=" + bodoviLA + ", ocjena=" + ocjena
				+ "]";
	}

	public StudentRecord(String jmbag, String prezime, String ime,
			double bodoviMI, double bodoviZI, double bodoviLA, int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviMI = bodoviMI;
		this.bodoviZI = bodoviZI;
		this.bodoviLA = bodoviLA;
		this.ocjena = ocjena;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getIme() {
		return ime;
	}

	public double getBodoviMI() {
		return bodoviMI;
	}

	public double getBodoviZI() {
		return bodoviZI;
	}

	public double getBodoviLA() {
		return bodoviLA;
	}

	public int getOcjena() {
		return ocjena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	

	

	
}
