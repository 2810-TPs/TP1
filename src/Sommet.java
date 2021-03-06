import java.util.ArrayList;

public class Sommet {
	private int identifiant;
	
	private boolean estRechargeable;
	
	private ArrayList<Arc> chemins;
	
<<<<<<< HEAD
	private int distancePlusCourte;
=======
	private Integer distancePlusCourte;
>>>>>>> c791f7d56124298277d7da0b2a8decddf4bcbece
	
	private ArrayList<Sommet> cheminPlusCourt;

	
	public Sommet() {
		identifiant = 0;
		estRechargeable = false;
		chemins = new ArrayList<Arc>();
		distancePlusCourte = Integer.MAX_VALUE;
		cheminPlusCourt = new ArrayList<Sommet>();
	}
	public Sommet(int identifiant, boolean estRechargeable) {
		this.identifiant = identifiant;
		this.estRechargeable = estRechargeable;
		chemins = new ArrayList<Arc>();
		distancePlusCourte = Integer.MAX_VALUE;
		cheminPlusCourt = new ArrayList<Sommet>();
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
<<<<<<< HEAD
	public int getDistancePlusCourte() {
		return distancePlusCourte;
	}
	public void setDistancePlusCourte(int distancePlusCourte) {
=======
	public Integer getDistancePlusCourte() {
		return distancePlusCourte;
	}
	public void setDistancePlusCourte(Integer distancePlusCourte) {
>>>>>>> c791f7d56124298277d7da0b2a8decddf4bcbece
		this.distancePlusCourte = distancePlusCourte;
	}
	public ArrayList<Sommet> getCheminPlusCourt() {
		return cheminPlusCourt;
	}
	public void setCheminPlusCourt(ArrayList<Sommet> cheminPlusCourt) {
<<<<<<< HEAD
		this.cheminPlusCourt = new ArrayList<Sommet>();
		for(int i = 0; i < cheminPlusCourt.size(); i++){
			this.cheminPlusCourt.add(cheminPlusCourt.get(i));
		}
		
	}
	public String afficher(){
		
		String resultat = "("+identifiant+", " +estRechargeable+ ", (";
		if (chemins != null)
		{
			for (Arc arc : chemins)
			{
				resultat += "(" + arc.getOtherSommet(this).identifiant+ ", " + arc.getDistance() + "), ";
			}
		}
		else
			resultat = resultat.substring(0, resultat.length() - 2);
		resultat = resultat.substring(0, resultat.length() - 2);
		resultat += "))";
		return resultat;
		
		
		
	}
}
=======
		this.cheminPlusCourt = cheminPlusCourt;
	}
}
>>>>>>> c791f7d56124298277d7da0b2a8decddf4bcbece
