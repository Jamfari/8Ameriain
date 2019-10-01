package MoteurDeJeu;

import java.util.*;

public class Partie {

	// ATTUBUTS


	private List<Joueur> joueursPresents;
	private String variante;
	private int cartesParJ;
	private final int nombreDeCartes;
	private final int nombreDeManches;
	private int manchesJouees=0;
	private Scanner s;


	// CONSTRUCTEUR


	public Partie(String variante, int nbCarte, int nbManche, int cartesParJ, List<Joueur> joueursPresents, Scanner s) {
		this.variante=variante;
		this.nombreDeCartes=nbCarte;
		this.nombreDeManches=nbManche;
		this.cartesParJ=cartesParJ;
		this.joueursPresents=joueursPresents;
		this.s=s;
		

	}




	// METHODES

	public void joueursALaTable(){

		System.out.println("\nVoici les joueurs presents ");	//affiche le nom des joueurs
		for (Joueur j : joueursPresents) {
			System.out.println(j);
		}
	}

	public String getVariante(){
		return variante;
	}

	public int getCartesParJ(){
		return cartesParJ;
	}

	public List<Joueur> getJoueursPresents(){
		return joueursPresents;
	}

	public void partie() throws InterruptedException{
		for (int i=0;i<nombreDeManches;i++){
			manchesJouees++;
			Pioche pioche = new Pioche(this);
			pioche.melangerLesCartes();
			Talon talon = new Talon(pioche);
			Collections.shuffle(joueursPresents);
			Manche m=new Manche(manchesJouees, joueursPresents, pioche, talon, this, s);
			for (Joueur j : joueursPresents) {
				j.viderMain();
			}
			m.distribuerLesCartes();
			m.jouerManche();
			

			//Recap des scores et annonce de victoire
			for(Joueur j : joueursPresents) {
				if(j.getMain().size()==0) {
					if (j instanceof JoueurVirtuel) {
						System.out.println("\n\n\nL'adversaire " +j+ " a gagne la manche numero " +(i+1));
					}
					else {
						System.out.println("\n\n\nVous avez gagne la manche numero " +(i+1)+ " !!");
					}
				}
				
			}
		}
	}
}






