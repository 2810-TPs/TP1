import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	/**QUESTIONS:
	 * Affichage objet1 pas sure?? 
	 * Faut il faire a chaque fois une verification pour tous les entreees?*/
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graphe graphe = new Graphe();
		
		boolean quitter = false;
		Scanner scanner = new Scanner(System.in);
		
		while (!quitter) {		
			String choix = proposerChoixMenu(scanner);
			quitter = executerChoix(scanner,choix,graphe);
		}
		scanner.close();
	}
	
	public static boolean executerChoix(Scanner scanner,String choix, Graphe graphe) throws IOException {
		switch (choix) {
		case "a": 
			mettreAJourCarte(scanner,graphe);
			break;
		case "b":
			plusCourtChemin(scanner,graphe);
			break;
		case "c":
			extraireSousGraphe(scanner,graphe);
			break;
		case "d":
			return true;			// quitter
		}
		return false;
	}
	
	public static void mettreAJourCarte(Scanner scanner,Graphe graphe) throws IOException {
		System.out.print("Entrer le nom du fichier a lire: ");
		File fichier = new File(scanner.nextLine());
		BufferedReader lecteur;
		try {
			lecteur = new BufferedReader(new FileReader(fichier));
			graphe.creerGraphe(lecteur);
			lecteur.close();
			//afficher fichier
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier introuvable.");
			e.printStackTrace();
		}
		
		
	}
	public static void plusCourtChemin(Scanner scanner,Graphe graphe) {
		//fonction de chemhi
		int identifiant =  0;
		
		System.out.print("Entrer le point de depart: ");
		identifiant =  Integer.parseInt(scanner.nextLine());
		Sommet source = graphe.getSommets().get(identifiant);
		
		System.out.print("Entrer la destination: ");
		identifiant =  Integer.parseInt(scanner.nextLine());
		System.out.println();
		Sommet destination = graphe.getSommets().get(identifiant);
		
		System.out.println("Entrer type de transport: ");
		System.out.println("(f) Faible Risque.");
		System.out.println("(m) Moyen  Risque.");
		System.out.println("(h) Haut   Risque.");
		System.out.print("Entrer votre choix: ");
		String typeTransport = scanner.nextLine();
		
		//graphe.plusCourtChemin(source,destination,typeTransport);
		
	}
	public static void extraireSousGraphe(Scanner scanner,Graphe graphe) {
		System.out.println("Entrer type de vehicule: ");
		System.out.println("(n) NI-NH. ");
		System.out.println("(l) LI-ion.");
		System.out.print("Entrer votre choix: ");
		String typeVehicule = scanner.nextLine();
		
		System.out.println("Entrer type de transport: ");
		System.out.println("    (f) Faible Risque.");
		System.out.println("    (m) Moyen  Risque.");
		System.out.println("    (h) Haut   Risque.");
		System.out.print("Entrer votre choix: ");
		String typeTransport = scanner.nextLine();
		Vehicule vehicule = new Vehicule(100,typeVehicule.charAt(0),typeTransport.charAt(0));
		
		System.out.print("Entrer le point de depart: ");
		int identifiant =  Integer.parseInt(scanner.nextLine());
		System.out.println();
		Sommet source = graphe.getSommets().get(identifiant);
		
		graphe = graphe.extraireSousGraphe(source,vehicule);
	}

	public static String proposerChoixMenu(Scanner scanner) {
		afficherMenu();
		System.out.print("Entrer votre choix: ");
		String choix = scanner.nextLine();
		if (choix.equals("a") || choix.equals("b") || choix.equals("c") || choix.equals("d"))
			return choix;
		else  return proposerChoixMenu(scanner);
		
	}
	public static void afficherMenu() {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("(a) Mettre à jour la carte.");
		System.out.println("(b) Déterminer le plus court chemin sécuritaire.");
		System.out.println("(c) Extraire un sous-graphe.");
		System.out.println("(d) Quitter.");
		System.out.println("-----------------------------------------------------------------");
		
	}
}
