import java.util.ArrayList;

public class Sommet {
	private int identifiant;
	
	private boolean estRechargeable;
	
	private ArrayList<Arc> chemins;
	
	public Sommet() {
		identifiant = 0;
		estRechargeable = false;
		chemins = new ArrayList<Arc>();
	}
	public Sommet(int identifiant, boolean estRechargeable) {
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
	
	public void ajouterChemin(Arc arc) {
		
		chemins.add(arc);
	}
	
	public void rechargerVehicule(Vehicule vehicule) {
		vehicule.rechargerBatterie();
	}
	//fonction qui donne lindex des chemins ayant un sommet destination rechargeable
	public ArrayList<Arc> trouverSommetsRechargeables(){
		
		//contient les index de chaque chemins qui sont relies a un sommet rechargeable
		ArrayList<Arc> list = new ArrayList<>();
		
		for (Arc chemin : chemins) {
			//trouver lautre sommet
			Sommet destination = (this == chemin.getSommet1()) ? chemin.getSommet2() : chemin.getSommet1();
			if (destination.estRechargeable) {
				list.add(chemin);
			}
					
		}
		return list;
	}
}
