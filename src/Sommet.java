import java.util.ArrayList;

public class Sommet {
	private int identifiant;
	
	private String type;

	private boolean estRechargeable;
	
	private ArrayList<Arc> chemins;
	
	public Sommet() {
		identifiant = 0;
		type = "";
		estRechargeable = false;
		chemins = new ArrayList<Arc>();
	}
	public Sommet(int identifiant, boolean estRechargeable) {
		type = "";
		this.identifiant = identifiant;
		this.estRechargeable = estRechargeable;
		chemins = new ArrayList<Arc>();
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
	
	
}
