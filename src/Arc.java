
public class Arc {
	private int distance;

	private Sommet sommet1;
	
	private Sommet sommet2;
	
	
	public Arc() {
		distance = 0;
		sommet1 = null;
		sommet2 = null;
	}
	
	public Arc(Sommet sommet1,Sommet sommet2,int distance) {
		
		this.sommet1 = sommet1;
		this.sommet2 = sommet2;
		this.distance = distance;
		
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Sommet getSommet1() {
		return sommet1;
	}

	public void setSommet1(Sommet sommet1) {
		this.sommet1 = sommet1;
	}

	public Sommet getSommet2() {
		return sommet2;
	}

	public void setSommet2(Sommet sommet2) {
		this.sommet2 = sommet2;
	}
	
	
}
