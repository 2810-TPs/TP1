import java.io.BufferedReader;
import java.io.IOException;
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
		if (ligne.length() > 0) {
			
			if (ligne.length() == 3 || ligne.length() == 4) {
				//Creer sommet
				creerSommet(ligne);
			}
			else {
				//Relier sommet avec arc
				
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
		boolean estRechargeable = (ligne.charAt(2) == '0') ? false : true;
		Sommet nouveauSommet = new Sommet(identifiant,estRechargeable);
		sommets.put(identifiant, nouveauSommet);
	}
	
	public void relierSommet(String ligne) {
		
		String sommetStr = ligne.substring(0, 2);
		//Si sommetStr contient une virgule
		if (sommetStr.charAt(sommetStr.length()-1) == ',')
			//decouper
			sommetStr = sommetStr.substring(0, 1);
	
	}
}
