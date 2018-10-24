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
	
	public void plusCourtChemin(Sommet source, Sommet destination){
		source.setDistancePlusCourte(0); // la distance de la source est initialise a 0
		source.getCheminPlusCourt().add(source);
		HashMap<Integer,Sommet> sommetsVisites = new HashMap<Integer,Sommet>();
		ArrayList<Sommet> sommetsNonVisites = new ArrayList<Sommet>(this.sommets.values());
		Sommet sommetCourant = source;
		while(!sommetsVisites.containsValue(destination)){ // tant qu'on a pas atteint la destination
			sommetsVisites.put(sommetCourant.getIdentifiant(),sommetCourant); // on ajoute les sommets courants au sommets visites
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
}
