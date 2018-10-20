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
}
