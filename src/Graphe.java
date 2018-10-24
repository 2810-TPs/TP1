import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author sesarb
 *
 */
public class Graphe {
	private HashMap<Integer,Sommet> sommets;
	
	public Graphe() {
		sommets = new HashMap<Integer,Sommet>();
	}
	public Graphe(HashMap<Integer,Sommet> sommets) {
		this.sommets = sommets;
	}
	public HashMap<Integer,Sommet> getSommets() {
		return sommets;
	}

	public void setSommets(HashMap<Integer,Sommet> sommets) {
		this.sommets = sommets;
	}
	
	public void creerGraphe(BufferedReader lecteur) throws IOException {
		
		String ligne = lecteur.readLine();
		if (ligne == null)
			return ;
		if (ligne.length() > 0) {
			
			if (ligne.length() == 3 || ligne.length() == 4) {
				//Creer sommet
				creerSommet(ligne);
			}
			else {
				//Relier sommet avec arc
				relierSommet(ligne);
			}
			
		}
		creerGraphe(lecteur);
	}
	
	public void creerSommet(String ligne) {
		String identifiantStr = "";
		if (ligne.length() == 4)
			identifiantStr = ligne.substring(0,2);
		else
			identifiantStr = ligne.substring(0,1);
		
		int identifiant = Integer.parseInt(identifiantStr);
		boolean estRechargeable = (ligne.charAt(ligne.length()-1) == '0') ? false : true;
		Sommet nouveauSommet = new Sommet(identifiant,estRechargeable);
		sommets.put(identifiant, nouveauSommet);
	}
	
	public void relierSommet(String ligne) {
		
		String sommetStr1 = "";
		String sommetStr2 = "";
		String distance = "";
		int i = 0;
		
		while (ligne.charAt(i) != ',') {
			sommetStr1 += ligne.charAt(i++);
		}
		i++;
		while (ligne.charAt(i) != ',') {
			sommetStr2 += ligne.charAt(i++);
		}
		i++;
		while (i < ligne.length()) {
			distance += ligne.charAt(i++);
		}
		//ajouter un arc aux 2 sommets
		Sommet sommet1 = sommets.get(Integer.parseInt(sommetStr1));
		Sommet sommet2 = sommets.get(Integer.parseInt(sommetStr2));
		Arc arc = new Arc(sommet1,sommet2,Integer.parseInt(distance));
		sommet1.ajouterChemin(arc);
		sommet2.ajouterChemin(arc);
	}
	
	public Graphe extraireSousGraphe(Sommet source, Vehicule vehicule) {
		
		Graphe graphe = new Graphe();
		/*graphe.getSommets().put(sommet.getIdentifiant(), sommet);
		creerTrajetLePlusLong(graphe ,sommet , vehicule);*/
		creerParcours(graphe,source,vehicule);
		return graphe;
	}
	
	public boolean verifierChemin(Arc arc, Vehicule vehicule) {
		if (vehicule.consommerBatterie(arc.getDistance()) >= 20)
			return true;
		return false;
	}
	
	public boolean verifierReccurence(Graphe graphe , Sommet source) {
		if (graphe.getSommets().get(source.getIdentifiant()) == null)
			return true;
		return false;
	}
	public void creerParcours(Graphe graphe, Sommet source, Vehicule vehicule) {
		ArrayList<Arc> chemins = source.getChemins();
		graphe.getSommets().put(source.getIdentifiant(), source);
		for (Arc chemin : chemins) {
			if (verifierChemin(chemin,vehicule) && verifierReccurence(graphe, chemin.getOtherSommet(source))) {
				creerParcours(graphe, chemin.getOtherSommet(source) ,new Vehicule(vehicule.consommerBatterie(chemin.getDistance()),vehicule.getType(),vehicule.getRisque()));
			}
		}
	}
	//fonction qui trouve le chemin le plus long a chaque fois
	/*
	public void creerTrajetLePlusLong(Graphe graphe ,Sommet sommet , Vehicule vehicule) {		
		Arc cheminSuivant = trouverCheminSuivant(graphe, sommet, vehicule);
		if (cheminSuivant == null )
			return ;
		
		Sommet destinationSuivante = (sommet == cheminSuivant.getSommet1()) ? cheminSuivant.getSommet2() : cheminSuivant.getSommet1();
		
		vehicule.setBatterie(vehicule.consommerBatterie(cheminSuivant.getDistance()));	//consommer essence
		
		if (destinationSuivante.isEstRechargeable())
			vehicule.rechargerBatterie();
		
		graphe.sommets.put( destinationSuivante.getIdentifiant(),  destinationSuivante);
		creerTrajetLePlusLong(graphe ,destinationSuivante , vehicule);
	}
	
	//1 seul mouvement
	public Arc trouverCheminSuivant(Graphe graphe, Sommet sommet, Vehicule vehicule) {
		
		//trouvers chemins rechargeables
		ArrayList<Arc> chemins = enleverCheminsInvalides(graphe,sommet, sommet.trouverSommetsRechargeables() , vehicule);
		//si il y en a pas, on se base sur la duree de chaque chemins pour trouver le plus long
		if (chemins.size() == 0) {
			chemins = enleverCheminsInvalides(graphe,sommet, sommet.getChemins() , vehicule);	
		}
		if (chemins.size() == 0)
			return null;
		
		return trouverCheminPlusLong(graphe,sommet, chemins, vehicule);
	}
	
	public Arc trouverCheminPlusLong(Graphe graphe,Sommet sommet, ArrayList<Arc> chemins , Vehicule vehicule) {
		ArrayList<Arc> cheminsPossibles = new ArrayList<>(chemins);
		//verifier si le chemin fait pas baisser la batterie a moins de 20%		
		//si il y a juste un chemin possible...
		if (cheminsPossibles.size() == 1)
			return cheminsPossibles.get(0);
		
		int indexCheminPlusLong = 0;
		for (int i = 1 ; i < chemins.size() ; i++) {
			Arc chemin = chemins.get(i);
			if (chemin.getDistance() > chemins.get(indexCheminPlusLong).getDistance()) {
				indexCheminPlusLong = i;
			}
						
		}
		
		return chemins.get(indexCheminPlusLong);
	}
	
	
	public ArrayList<Arc> enleverCheminsInvalides(Graphe graphe,Sommet sommet, ArrayList<Arc> chemins , Vehicule vehicule){
		ArrayList<Arc> cheminsPossiblesEtValides = new ArrayList<>();
		for (int i = 0 ; i < chemins.size() ; i++) {
			if (verifierChemin(vehicule,  chemins.get(i)) && !verifierDestinationReccurente(graphe,sommet,chemins.get(i))) {
				 cheminsPossiblesEtValides.add(chemins.get(i));
			}
		}
		return cheminsPossiblesEtValides;
	}
	
	public boolean verifierDestinationReccurente(Graphe graphe,Sommet sommet, Arc chemin) {
		Sommet destination = (sommet == chemin.getSommet1()) ? chemin.getSommet2() : chemin.getSommet1();
		
		if (graphe.getSommets().get(destination.getIdentifiant()) == destination)
			return true; //recurrente
		return false;
			
	}
	public boolean verifierChemin(Vehicule vehicule , Arc arc) {
		
		if (vehicule.consommerBatterie(arc.getDistance()) < 20)
			return false;
		return true;
	}
	
	*/
}
