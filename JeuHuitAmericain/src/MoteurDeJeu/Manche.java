package MoteurDeJeu;

import java.util.Scanner;
import java.util.*;



public class Manche {

	// ATTUBUTS

	
	private boolean sensDeJeu = true;
	private List<Joueur> joueurs;
	private final int numeroManche;
	private Pioche pioche;
	private Talon talon;
	private Scanner s;
	private int nbGagne=0;
	private Partie partie;
	private final List<String> couleur =  Arrays.asList("P","CO","CA","T");
	private final List<String> valeur = Arrays.asList("A","2","3","4","5","6","7","8","9","10","V","D","R","P");

	// CONSTRUCTEUR
	public Manche(int nManche, List<Joueur> joueurs, Pioche pioche, Talon talon, Partie partie, Scanner s) {
		this.pioche=pioche;
		this.pioche.melangerLesCartes();
		this.talon=talon;
		this.numeroManche=nManche;
		this.joueurs=joueurs;
		this.partie = partie;
		this.s = s;
	}

	// METHODES


	public void changerSens(){
		sensDeJeu=!sensDeJeu; 
	}



	public void distribuerLesCartes(){
		for (int i =0;i<partie.getCartesParJ();i++){
			for (Joueur j : joueurs){
				j.addCarteMain(pioche.distribuer1Carte(talon));
			}
		}
	}

	public void jouerTour() throws InterruptedException {
		CarteEffet carte = new CarteEffet("3-P", partie.getVariante()); // Carte au hasard, va changer de valeur



		for (Joueur j : joueurs){			//On parcourt les joueurs participant a la manche
			talon.afficherCarteEnCours();	//On affiche la carte en cours sur le talon a chaque tour
			Thread.sleep(1000);
			if (j.getGagne()){		//On verifie si le joueur dont c'est le tour a gagne, si oui on on continue notre boucle et on procede a son tour
				continue;	//on peut retourner dans al boucle for

			}
			if (!j.getTourJouable()){
				j.retablirTour();
				continue;
			}
			if (j instanceof JoueurVirtuel){ 		//Tour d'un joueur virtuel
				Thread.sleep(2000);
				System.out.println("\n\nC'est au tour de l'adversaire " +j+ " de jouer.");
				Thread.sleep(2000);
				if (((JoueurVirtuel) j).getDifficulte() == 1 ) {
				carte = ((JoueurVirtuel) j).moveNiveau1(talon);
				System.out.println("L'adversaire " +j+ " a joue un(e) " +carte);
				}
				else {
					carte = ((JoueurVirtuel) j).moveNiveau2(talon);
					System.out.println("L'adversaire " +j+ " a joue un(e) " +carte);
				}
				
				System.out.println("Il reste " +(j.getMain().size())+ " cartes a " +j);
			}
			
			else{
				boolean coupValide = false;
				while(!coupValide){
					//Tour du joueur utilisateur on affiche les cartes de sa main et on lui demande quelle carte il veut jouer en lui montrant les propositions


					Thread.sleep(2000);
					System.out.println("\nC'est a votre tour ! Quelle carte voulez-vous jouer ?"); 
					System.out.println("\nVeuillez entrer une valeur de carte : (A, 2, 3, 4, 5, 6, 7, 8, 9, 10, V, D, R) et une couleur : (P, CO, CA, T)");
					System.out.println("\nExemple d'un format de carte standard : 5-CA (Pour 5 de Carreau)");
					System.out.println("\nSi vous devez piocher veuillez saisir 'P-P' ");
					System.out.println("\n\n\n\nVoici vos cartes : " +j.getMain());
					//s.nextLine();   
					String nomCarte = s.nextLine();	

					String[] strCarte = nomCarte.split("-");

					if (strCarte.length==2){				//On verifie que le format de la carte est correct
						String val=strCarte[0].toUpperCase();		
						String coul=strCarte[1].toUpperCase();
						if ((valeur.contains(val) && couleur.contains(coul))||(val.equals("J") && coul.equals("J"))){	//On verifie que la carte existe 
							carte= new CarteEffet(nomCarte, partie.getVariante());
							if(j.getMain().contains(carte) || carte.getValeur().equalsIgnoreCase("Piocher")){			//On verifie que la carte existe dans la main du joueur
								if(carte.jouable(talon)){   //On verifie que cette carte est jouable
									coupValide=true;
									System.out.println("\nVous avez joue un(e) " +carte);	

								}
							}
						}
					}
					else{
						System.out.println("Ce n'est pas un coup valide !");
					}
				}

			}
			j.jouerCarte(carte,talon); 
			carte.appliquerEffetCarte(partie, this, talon, j, pioche, s);		//La carte jouee par le joueur applique son effet 
			if (j.getMain().size()==0){										//Le cas ou le joueur pose sa derniere carte
				j.setGagne(true);
				this.incrGagne();								//On incremente le nombre de manche gagnee par ce joueurV
				switch (nbGagne){						//On lui attribue son score en fonction de son classement
				case 1:j.score(50);break;
				case 2:j.score(30);break;
				case 3:j.score(10);break;
				default:break;
				}
			}

		}
	}

	public void jouerManche() throws InterruptedException{		//Tant qu'il ne reste pas 1 seul joueur avec des cartes la manche n'est pas terminee et les tours continuent
		while (nbGagne<joueurs.size()-1){
			jouerTour();
		}
	}

	public void incrGagne(){
		nbGagne++;
	}
	public int getNbGagne(){
		return nbGagne;
	}

	public int getNumeroManche() {
		return numeroManche;
	}


}



