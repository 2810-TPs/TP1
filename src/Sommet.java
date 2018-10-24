import java.util.ArrayList;


public class Sommet {
	private int identifiant;
	
	private String type;

	private boolean estRechargeable;
	
	private ArrayList<Arc> chemins;
	
	private Integer distancePlusCourte;
	
	private ArrayList<Sommet> cheminPlusCourt;
	
	public Sommet() {
		identifiant = 0;
		type = "";
		estRechargeable = false;
		chemins = new ArrayList<Arc>();
		distancePlusCourte.equals(Integer.MAX_VALUE); // distance la plus courte d'une source
		cheminPlusCourt = new ArrayList<Sommet>();
	}
	public Sommet(int identifiant, boolean estRechargeable) {
		type = "";
		this.identifiant = identifiant;
		this.estRechargeable = estRechargeable;
		chemins = new ArrayList<Arc>();
		distancePlusCourte.equals(Integer.MAX_VALUE); // distance la plus courte d'une source
		cheminPlusCourt = new ArrayList<Sommet>();
	}
	
	
	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isEstRechargeable() {
		return estRechargeable;
	}

	public void setEstRechargeable(boolean estRechargeable) {
		this.estRechargeable = estRechargeable;
	}
	
	public ArrayList<Arc> getChemins() {
		return chemins;
	}
	public void setChemins(ArrayList<Arc> chemins) {
		this.chemins = chemins;
	}
	
	public int getDistancePlusCourte(){
		return distancePlusCourte;
	}
	
	public void setDistancePlusCourte(int distance){
		this.distancePlusCourte.equals(distance);
		
	}
	public ArrayList<Sommet> getCheminPlusCourt() {
		return cheminPlusCourt;
	}
	public void setCheminPlusCourt(ArrayList<Sommet> cheminPlusCourt) {
		this.cheminPlusCourt = cheminPlusCourt;
	}
}
