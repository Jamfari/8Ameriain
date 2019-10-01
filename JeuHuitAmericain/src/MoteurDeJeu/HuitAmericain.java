package MoteurDeJeu;

import java.util.ArrayList;
import java.util.Scanner;

public class HuitAmericain {


	public static void main(String[] args) throws InterruptedException {


		int cartesParJ = 0;
		ArrayList<Joueur> joueursPresents = new ArrayList<Joueur>();
		String variante = "Monclar";
		int nbCartes = 52;
		int nbManches = 1;
		int nbJVirtuel = 1;
		int diff = 1;
		System.out.println("Bonjour !\n");
		Scanner s = new Scanner(System.in);	



		boolean entreeCorrecte = false;
		while (!entreeCorrecte) {
			System.out.println("\nQuelle variante du Huit Americain voulez-vous jouer?");
			System.out.println("\nVous avez le choix entre : Monclar     Minimale      et     Variante 1 ");
			variante = s.nextLine();
			if(variante.equalsIgnoreCase("Monclar")){
				entreeCorrecte = true;
			}
			else if(variante.equalsIgnoreCase("Minimale")){
				entreeCorrecte = true;
			}
			else if(variante.equalsIgnoreCase("Variante 1")) {
				entreeCorrecte = true;
			}
			else {
				System.out.println("\nCe n'est pas un nom de variante valable. Veuillez reesayer. ");
			}
		}

		
		boolean entreeCorrecte2 = false;
		while (!entreeCorrecte2) {
			System.out.println("\nCombien de manches voulez-vous jouer ?");
			nbManches = s.nextInt();
			if(1 <= nbManches && nbManches <= 5) {
				entreeCorrecte2 = true;
			}
			else {
				System.out.println("\nCe nombre de manches n'est pas pris en compte. Veuillez reesayer. ");
			}

		}


		System.out.println("\nSaisissez votre nom");
		s.nextLine();
		String nom= s.nextLine();

		boolean entreeCorrecte3 = false;
		while (!entreeCorrecte3) {
			System.out.println("\nAvec combien de joueurs ordinateur voulez-vous jouer " +nom+ " ?");
			nbJVirtuel = s.nextInt();	
			if(1 <= nbJVirtuel && nbJVirtuel <= 10 ) {
				entreeCorrecte3 = true;
			}
			else {
				System.out.println("\nCe nombre de joueurs n'est pas pris en compte. Veuillez reesayer. ");
			}

		}


		joueursPresents.add(new Joueur(nom,cartesParJ));
		for (int n = 0; n < nbJVirtuel; n++) {  
			boolean entreeCorrecte4 = false;
			while (!entreeCorrecte4) {
				System.out.println("\nChoisissez la difficulte pour le joueur Virtuel numero " + (n+1) + " : 1  ou  2 ");
				diff=s.nextInt();	
				if(1 <= diff && diff <= 3 ) {
					entreeCorrecte4 = true;
				}
				else {
					System.out.println("\nCe niveau de difficulte de joueur virtuel n'est pas pris en compte. Veuillez reesayer. ");
				}
			}
			JoueurVirtuel joueurV = new JoueurVirtuel("Joueur Virtuel "+n, cartesParJ, diff);  //joueur(s) virtuel
			joueursPresents.add(joueurV);
		}

		System.out.println("\nMerci ! La partie commence \n\n\n");

		if (nbJVirtuel>=1 && nbJVirtuel<=3){ //Au pif, peut etre modifie
			cartesParJ=8;

		}
		if (nbJVirtuel>=4 && nbJVirtuel<=6){ //Au pif, peut etre modifier
			cartesParJ=6;
		}
		if (nbJVirtuel>=7 && nbJVirtuel<=10){
			cartesParJ=4;
		}
		Thread.sleep(2000);
		Partie p = new Partie(variante, nbCartes, nbManches, cartesParJ, joueursPresents, s);
		p.joueursALaTable();
		s.nextLine();
		p.partie();
		s.close();		//Derniere ligne executee du code



	}
}
