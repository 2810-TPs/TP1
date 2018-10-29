
public class Vehicule {
	
	private char type ;
	private double batterie ;
	private char risque;
	
	public Vehicule() {
		type = 'n'; //Ni-NH
		batterie = 100;
		risque = 'f';
	}
	
	public Vehicule(double batterie, char type , char risque) {
		this.batterie = batterie;
		this.type = type ;
		this.risque = risque;
	}
	
	public double consommerBatterie(int duree) {
		//NI-NH
		double consommation = 0;
		if (type == 'n') {
			switch (risque) {
			case 'f':
				consommation = (6 * duree)/60;
				break;
			case 'm':
				consommation = (12 * duree)/60;
				break;
			case 'h':
				consommation = (48 * duree)/60;
				break;
			}
		}
		else {
			switch (risque) {
			case 'f':
				consommation = (5 * duree)/60;
				break;
			case 'm':
				consommation = (10 * duree)/60;
				break;
			case 'h':
				consommation = (30 * duree)/60;
				break;
			}
		}
		return batterie - consommation;
	}
	
	public void rechargerBatterie() {
		batterie = 100;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public double getBatterie() {
		return batterie;
	}
	public void setBatterie(double batterie) {
		this.batterie = batterie;
	}
	public char getRisque() {
		return risque;
	}
	public void setRisque(char risque) {
		this.risque = risque;
	}
}
