package MoteurDeJeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class Test {  //Teste la methode d'affichage toString de Carte

	public static void main (String[] args) {
		
		Scanner s = new Scanner(System.in);
		Joueur joueurV = new JoueurVirtuel("joueurV", 8, 1);
		List<Joueur>list = Arrays.asList(joueurV);
		Partie partie = new Partie("Monclar", 52, 1, 8, list, s);
		Pioche pioche = new Pioche(partie);
		Talon talon = new Talon(pioche);
		Manche manche = new Manche(1, list, pioche, talon, partie, s);
		CarteEffet c = new CarteEffet ("10-P", "Monclar");
		joueurV.addCarteMain(new CarteEffet ("10-P", "Monclar"));
		talon.add(new CarteEffet("A-P", "Monclar"));
		(new CarteEffet("10-CA", "Monclar")).appliquerEffetCarte(partie, manche, talon, joueurV, pioche, s);
	}
	
	
	
}
