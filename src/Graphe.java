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
	
	public void initialiserListe(Sommet source, Sommet destination){
		ArrayList<Sommet> listeSommets = new ArrayList<Sommet>();
			listeSommets.add(source);
			plusCourtChemin(source,destination,listeSommets);
	}
	
	public void plusCourtChemin(Sommet source, Sommet destination,ArrayList<Sommet> listeSommets){
		while(!listeSommets.contains(destination)){ // tant que la destination n'est pas ajoute			
			for(int i = 0; i < source.getChemins().size();i++){
				if(!listeSommets.contains(source.getChemins().get(i).getSommet2()))
					listeSommets.add(source.getChemins().get(i).getSommet2());
				plusCourtChemin(source.getChemins().get(i).getSommet2(),destination, listeSommets);
					//on ajoute les voisins
				}
		}
	}
}
