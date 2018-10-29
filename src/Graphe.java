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
	
	 public void lireGraphe(){
    	 
	    	for(java.util.Map.Entry<Integer, Sommet> entry :sommets.entrySet()) {
				Sommet sommet = entry.getValue();
				sommet.afficher();
				System.out.println(sommet.afficher());
						
		}
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
		System.out.println("Vehicule batterie:" + vehicule.getBatterie());
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
	
<<<<<<< HEAD
	*/
=======
>>>>>>> c791f7d56124298277d7da0b2a8decddf4bcbece
	public void plusCourtChemin(Sommet source, Sommet destination){
		source.setDistancePlusCourte(0); // la distance de la source est initialise a 0
		source.getCheminPlusCourt().add(source);
		HashMap<Integer,Sommet> sommetsVisites = new HashMap<Integer,Sommet>();
		ArrayList<Sommet> sommetsNonVisites = new ArrayList<Sommet>(this.sommets.values());
		Sommet sommetCourant = source;
		while(!sommetsVisites.containsValue(destination)){ // tant qu'on a pas atteint la destination
			sommetsVisites.put(sommetCourant.getIdentifiant(),sommetCourant); // on ajoute les sommets courants au sommets visites
<<<<<<< HEAD
			for(int i = 0; i < sommetCourant.getChemins().size(); i++ ){ // on verifie tous les voisins du sommets courant
				if(sommetCourant.getDistancePlusCourte() + sommetCourant.getChemins().get(i).getDistance() < sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant).getDistancePlusCourte() && !sommetsVisites.containsValue(sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant))){
					//si on a une distance inferieure, on met a jour la distance et le chemin le plus court du sommet adjacent 
					sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant).setDistancePlusCourte(sommetCourant.getDistancePlusCourte() + sommetCourant.getChemins().get(i).getDistance());
					sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant).setCheminPlusCourt(sommetCourant.getCheminPlusCourt());
					sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant).getCheminPlusCourt().add(sommetCourant.getChemins().get(i).getOtherSommet(sommetCourant));
				}
			}
			sommetsNonVisites.remove(sommetCourant);
			if(sommetsNonVisites.size() > 0)
				sommetCourant = sommetsNonVisites.get(0);
			else
				return;
			for(int i = 1; i < sommetsNonVisites.size();i++){
				if(sommetCourant.getDistancePlusCourte()>sommetsNonVisites.get(i).getDistancePlusCourte()){ 
					sommetCourant = sommetsNonVisites.get(i); //le sommet courant est celui dont la distance est la plus petite
				}
			}
		}
	}

	
			
	public Vehicule parcoursChemin(Sommet destination, char risque){
		Vehicule vehicule = new Vehicule(100,'n',risque);
		int distanceTotale = 0;
		switch(risque){
			case 'f':
				distanceTotale = verificationSecuriteParcours("f",destination,vehicule);
				break;
			case 'm':
				distanceTotale = verificationSecuriteParcours("m",destination,vehicule);
				break;
			case 'h':
				distanceTotale = verificationSecuriteParcours("h",destination,vehicule);
					break;
		}
		destination.setDistancePlusCourte(distanceTotale); //on ajoute les temps de rechargement a la duree minimale;
		return vehicule;
	}
	
	public int verificationSecuriteParcours(String risque, Sommet destination,Vehicule vehicule){
		vehicule.setBatterie(vehicule.consommerBatterie(destination.getDistancePlusCourte())); //on verifie l'etat final de la batterie apres le trajet
		int dureeTotale = parcoursCheminSommetParSommet(destination,vehicule);

		if (vehicule.getBatterie() <= 20){ //si apres avoir parcouru tous les sommets, la batterie est encore sous 20%, on essaye avec le type L
			vehicule.setType('l');
			vehicule.setBatterie(100);
			vehicule.setBatterie(vehicule.consommerBatterie(destination.getDistancePlusCourte()));
			dureeTotale = parcoursCheminSommetParSommet(destination,vehicule);
		}	
		return dureeTotale;
	}
	public int parcoursCheminSommetParSommet(Sommet destination,Vehicule vehicule){
		int dureeTotale = destination.getDistancePlusCourte();
		boolean parcoursImpossible = false;
		while(vehicule.getBatterie() <= 20 && !parcoursImpossible){
			vehicule.setBatterie(100);
			for(int i = 1; i < destination.getCheminPlusCourt().size() && !parcoursImpossible;i++){
				//on parcoure le chemin tant qu'une station de recharge n'est pas rencontrer
				vehicule.setBatterie(vehicule.consommerBatterie(destination.getCheminPlusCourt().get(i).getDistancePlusCourte() - destination.getCheminPlusCourt().get(i-1).getDistancePlusCourte()));
				if(vehicule.getBatterie() <= 20){
					parcoursImpossible = true;
				}
				if(destination.getCheminPlusCourt().get(i).isEstRechargeable() && !parcoursImpossible){ //sommet actuel est celui qui contient une station de recharge
					dureeTotale += 120; //2 heures de chargement
					vehicule.setBatterie(100);					
				}	
			}
		}
	return dureeTotale;
	}
	
	
	public void afficherParcours(Vehicule vehicule,Sommet destination){
		if(vehicule.getBatterie() <= 20){
			System.out.println("Le transport est refuser par manque de batterie");
			return;
		}
		if(vehicule.getType() == 'n')
			System.out.println("Type de vehicule utilise : NI-NH");
		else
			System.out.println("Type de vehicule utilise : Li-ion");
		System.out.println("Pourcentage de batterie : " + vehicule.getBatterie());
		System.out.println("Parcours suivi:");
		for(int i = 0; i < destination.getCheminPlusCourt().size();i++){
			System.out.print(" -> "+destination.getCheminPlusCourt().get(i).getIdentifiant());
		}
		System.out.println();
		System.out.println("Duree Totale du trajet : " + destination.getDistancePlusCourte());
	}
	
	public void clearSommets(){
		for(Sommet sommet:sommets.values()){
			sommet.setDistancePlusCourte(Integer.MAX_VALUE);
			sommet.setCheminPlusCourt(new ArrayList<Sommet>());
		}
	}

=======
			sommetsNonVisites.remove(sommetCourant);
			for(int i = 0; i < sommetCourant.getChemins().size(); i++ ){ // on verifie tous les voisins du sommets courant
				if(sommetCourant.getDistancePlusCourte() + sommetCourant.getChemins().get(i).getDistance() < sommetCourant.getChemins().get(i).getSommet2().getDistancePlusCourte() && !sommetsVisites.containsValue(sommetCourant.getChemins().get(i).getSommet2())){
					//si on a une distance inferieure, on met a jour la distance et le chemin le plus court du sommet adjacent 
					sommetCourant.getChemins().get(i).getSommet2().setDistancePlusCourte(sommetCourant.getDistancePlusCourte() + sommetCourant.getChemins().get(i).getDistance());
					sommetCourant.getChemins().get(i).getSommet2().setCheminPlusCourt(sommetCourant.getCheminPlusCourt());
					sommetCourant.getChemins().get(i).getSommet2().getCheminPlusCourt().add(sommetCourant.getChemins().get(i).getSommet2());
				}
			}
			sommetCourant = sommetsNonVisites.get(0);
			for(int i = 1; i < sommetsNonVisites.size();i++){
				if(sommetCourant.getDistancePlusCourte() > sommetsNonVisites.get(i).getDistancePlusCourte()); 
					sommetCourant = sommetsNonVisites.get(i); //le sommet courant est celui dont la distance est la plus petite
				}
		}
	}
	
			
	public void parcoursChemin(Sommet destination, char risque){
		Vehicule vehicule = new Vehicule();
		int distanceTotale = 0;
		switch(risque){
			case 'F':
				distanceTotale = verificationSecuriteParcours('F',destination,vehicule);
				break;
			case 'M':
				distanceTotale = verificationSecuriteParcours('M',destination,vehicule);
				break;
			case 'H':
				distanceTotale = verificationSecuriteParcours('H',destination,vehicule);
					break;
		}
		destination.setDistancePlusCourte(distanceTotale); //on ajoute les temps de rechargement a la duree minimale;
	}
	
	public int verificationSecuriteParcours(char risque, Sommet destination,Vehicule vehicule){
		vehicule.consommerBatterie(destination.getDistancePlusCourte()); //on verifie l'etat final de la batterie apres le trajet
		int dureeTotale = 0;
		int sommetActuel = 0;
		dureeTotale = destination.getDistancePlusCourte(); // duree minimale sans les rechargement
		while(vehicule.getBatterie() <= 20 || sommetActuel != destination.getCheminPlusCourt().size()-1){ //tant que la batterie est decharger et qu'on a pas fini le parcours
			boolean stationRecharge = false;
			for(int i = sommetActuel; i < destination.getCheminPlusCourt().size() && !stationRecharge;i++){ //on parcoure le chemin tant qu'une station de recharge n'est pas rencontrer
				if(destination.getCheminPlusCourt().get(i).isEstRechargeable()){
					sommetActuel = i; //sommet actuel est celui qui contient une station de recharge
					dureeTotale += 120; //2 heures de chargement
					vehicule.setBatterie(100);
					//on veriife l'etat de la batterie lors du trajet entre la station actuel et la destination
					vehicule.consommerBatterie(destination.getDistancePlusCourte() - destination.getCheminPlusCourt().get(i).getDistancePlusCourte());
					stationRecharge=true; //on quitte la boucle for
				}
			}
		}
		if (vehicule.getBatterie() <= 20) //si apres avoir parcouru tous les sommets, la batterie est encore sous 20%, on essaye avec le type L
			vehicule = new Vehicule(100,'L',risque);
			vehicule.consommerBatterie(destination.getDistancePlusCourte());
			sommetActuel = 0;
			dureeTotale = destination.getDistancePlusCourte();
			while(vehicule.getBatterie() <= 20 || sommetActuel != destination.getCheminPlusCourt().size()-1){
				boolean stationRecharge = false;
				for(int i = sommetActuel; i < destination.getCheminPlusCourt().size() && !stationRecharge;i++){
					if(destination.getCheminPlusCourt().get(i).isEstRechargeable()){
						sommetActuel = i;
						dureeTotale += 120;
						vehicule.setBatterie(100);
						vehicule.consommerBatterie(destination.getDistancePlusCourte() - destination.getCheminPlusCourt().get(i).getDistancePlusCourte());
						stationRecharge=true;
				}
			}
		}
		return dureeTotale;
	}
	
	public void afficherParcours(Vehicule vehicule,Sommet destination){
		if(vehicule.getBatterie() <= 20){
			System.out.print("Le transport est refuser par manque de batterie");
		}
		else if(vehicule.getType() == 'N'){
			System.out.println("Type de vehicule utilise : NI-NH");
			System.out.println("Pourcentage de batterie : " + vehicule.getBatterie());
			for(int i = 0; i < destination.getCheminPlusCourt().size();i++){
				System.out.print(destination.getCheminPlusCourt().get(i).getIdentifiant() + "-> ");
			}
			System.out.println("Duree Totale du trajet : " + destination.getDistancePlusCourte());
		}
		else{
			System.out.println("Type de vehicule utilise : NI-NH");
			System.out.println("Pourcentage de batterie : " + vehicule.getBatterie());
			for(int i = 0; i < destination.getCheminPlusCourt().size();i++){
				System.out.print(destination.getCheminPlusCourt().get(i).getIdentifiant() + "-> ");
			}
			System.out.println("Duree Totale du trajet : " + destination.getDistancePlusCourte());
		}
	}
>>>>>>> c791f7d56124298277d7da0b2a8decddf4bcbece
}

