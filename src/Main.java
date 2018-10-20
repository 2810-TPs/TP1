import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graphe graphe = new Graphe();
		
		File fichier = new File("centresLocaux.txt");
		
		BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
		
		graphe.creerGraphe(lecteur); //NOM FICHIER PAS EN PARAMETRE
		lecteur.close();
	}

}
