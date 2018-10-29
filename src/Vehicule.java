
public class Vehicule {
	
	private String type ;
	private double batterie ;
	private String risque;
	
	public Vehicule() {
		type = "n"; //Ni-NH
		batterie = 100;
		risque = "f";
	}
	
	public Vehicule(double batterie, String type , String risque) {
		this.batterie = batterie;
		this.type = type ;
		this.risque = risque;
	}
	
	public double consommerBatterie(int duree) {
		//NI-NH
		double consommation = 0;
		if (type == "n") {
			switch (risque) {
			case "f":
				consommation = (double)(6 * duree)/60;
				break;
			case "m":
				consommation = (double)(12 * duree)/60;
				break;
			case "h":
				consommation = (double)(48 * duree)/60;
				break;
			}
		}
		else {
			switch (risque) {
			case "f":
				consommation = (double)(5 * duree)/60;
				break;
			case "m":
				consommation = (double)(10 * duree)/60;
				break;
			case "h":
				consommation = (double)(30 * duree)/60;
				break;
			}
		}
		return batterie - consommation;
	}
	
	public void rechargerBatterie() {
		batterie = 100;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getBatterie() {
		return batterie;
	}
	public void setBatterie(double batterie) {
		this.batterie = batterie;
	}
	public String getRisque() {
		return risque;
	}
	public void setRisque(String risque) {
		this.risque = risque;
	}
}
