package MoteurDeJeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CarteEffet extends Carte{

	// ATTUBUTS


	private final List<String> couleurs =  Arrays.asList("P","CO","CA","T");
	private final List<String> valeurs = Arrays.asList("A","2","3","4","5","6","7","8","9","10","V","D","R","P");

	public static final int SAUTER_TOUR_JOUEUR = 0;
	public static final int CHANGER_SENS=1;
	public static final int REJOUER_CARTE=2;
	public static final int PIOCHER_TROIS_CARTES=3;
	public static final int CHOISIR_COULEUR=4;
	public static final int POSER_PARTOUT =5;
	public static final int PIOCHER_CARTE=6;
	public static final int CHOISIR_DE_PIOCHER=7;
	public static final int JOKER=8;
	public static final int CONTRER =9;
	public static final int RIEN=10;

	private int effet = RIEN;

	private Scanner s;



	// CONSTRUCTEUR

	public CarteEffet(String couleur, String valeur, String variante) {

		super(couleur, valeur);

		{

			//-----------------------------------------------------------------------------------------------------------------------------------------------------
			if (variante.equals("Monclar")) // On definit les effets pour la variante de Monclar

			{

				if (valeur.equals("10")) {
					effet = REJOUER_CARTE;
				}

				if (valeur.equals("7")) {
					effet = SAUTER_TOUR_JOUEUR;
				}

				if (valeur.equals("Valet")) {
					effet = CHANGER_SENS;
				}

				if (valeur.equals("9")) {
					effet = PIOCHER_CARTE;
				}

				if (valeur.equals("As")) {
					effet = PIOCHER_TROIS_CARTES;
				}

				if (valeur.equals("8")) {
					effet = CHOISIR_COULEUR;
				}

			}
			//-----------------------------------------------------------------------------------------------------------------------------------------------------			
			if (variante.equals("Minimale")) // On definit les effets pour la variante de Minimale

			{
				if (valeur.equals("8"))
				{
					effet = CHOISIR_COULEUR;
				}
			}
			//-----------------------------------------------------------------------------------------------------------------------------------------------------
			if (variante.equals("Variante 1")) // On definit les effets pour la variante 1

			{


				if (valeur.equals("7")) {
					effet = SAUTER_TOUR_JOUEUR;
				}

				if (valeur.equals("Valet")) {
					effet = CHANGER_SENS;
				}

				if (valeur.equals("6")) {
					effet = PIOCHER_CARTE;
				}

				if (valeur.equals("8") || valeur.equals("5")) {
					effet = CHOISIR_COULEUR;
				}
			}

			//-----------------------------------------------------------------------------------------------------------------------------------------------------		
			if(valeur.equals("P")){
				effet=CHOISIR_DE_PIOCHER;				
			}	

			if (valeur.equalsIgnoreCase("Piocher")) 
			{
				effet = CHOISIR_DE_PIOCHER;
			}
		}

	}


	/*Autre constructeur qui associe separement les chaines de caracteres liees a 
		 la valeur et a la couleur pour les saisies de l'utilisateur*/

	public CarteEffet(String carte, String variante){
		this(associerCouleur(carte.split("-")[1]),associerValeur(carte.split("-")[0]), variante);
	}


	// METHODES

	public boolean jouable(Talon talon){  //Verifie qu'uune carte est jouable sur le talon
		return this.valeur.equals(talon.getCarteJouee().valeur) 
				|| this.couleur.equals(talon.getCouleur()) 
				|| effet == POSER_PARTOUT
				|| (effet == CONTRER && talon.getCarteJouee().effet == JOKER)
				|| effet==CHOISIR_COULEUR
				|| effet==CHOISIR_DE_PIOCHER;
				
		//A modifier pour rajouter tous les effets contrables
	}


	public int getEffet(){
		return effet;
	}

	public void appliquerEffetCarte(Partie partie, Manche m, Talon t, Joueur j, Pioche p, Scanner s) { //A modifier pour inclure les effets restants. Syntaxe switch tres pratique pour ce genre de fonctions
		this.s = s;
		Joueur jvise;
		List<Joueur> players;
		int indx;
		switch (effet)
		{

		case SAUTER_TOUR_JOUEUR:

			System.out.println("Le prochain joueur saute son tour !");
			List<Joueur> joueurs=partie.getJoueursPresents();
			int index = joueurs.indexOf(j);  //~~Numero des joueurs
			if(index < joueurs.size()-1){	//On se cale sur joueur qui pose la carte sautant un tour
				joueurs.get(index+1).passerTour();	//On saute le tour du joueur suivant
			}
			else{
				joueurs.get(0).passerTour();
			}

			break; 

		case CHANGER_SENS:

			System.out.println("On change de sens !");
			m.changerSens();
			break;

		case REJOUER_CARTE :	
			CarteEffet c = new CarteEffet("A-Co", partie.getVariante()); // Carte au hasard, va changer de valeur
			boolean tour=true;
			t.afficherCarteEnCours();			//On affiche la carte en cours sur le talon


			if (j.getGagne()){		//On verifie si le joueur dont c'est le tour a gagne, si oui on on continue notre boucle et on procede a son tour
				tour=false;			//on peut retourner dans la boucle for

			}

			if (j instanceof JoueurVirtuel && tour){//On rejoue le Tour d'un joueur virtuel
				
				System.out.println("\n\nL'adversaire " +j+ " peut rejouer.");

				c = ((JoueurVirtuel) j).moveNiveau1(t);
				System.out.println("\nL'adversaire " +j+ " joue un(e) " +c  );

			}
			else if(tour){		//On rejoue le tour de l'utilisateur
				boolean coupValide=false;
				while(!coupValide){
					//Tour du joueur utilisateur on affiche les cartes de sa main et on lui demande quelle carte il veut jouer en lui montrant les propositions
					
					System.out.println("\nVous pouvez rejouer ! Quelle carte voulez-vous jouer ?"); 
					System.out.println("\nVeuillez entrer une valeur de carte : (A, 2, 3, 4, 5, 6, 7, 8, 9, 10, V, D, R) et une couleur : (P, CO, CA, T)");
					System.out.println("\nExemple d'un format de carte standard : 5-CA (Pour 5 de Carreau)");
					System.out.println("\nSi vous devez piocher veuillez saisir 'P-P' ");
					System.out.println("\n\n\n\nVoici vos cartes : " +j.getMain());

					String nomCarte = s.nextLine();	

					String[] strCarte=nomCarte.split("-");

					if (strCarte.length==2){				//On verifie que le format de la carte est correct
						String val=strCarte[0].toUpperCase();		
						String coul=strCarte[1].toUpperCase();
						if ((valeurs.contains(val) && couleurs.contains(coul))||(val.equals("J") && coul.equals("J"))){	//On verifie que la carte existe 
							c= new CarteEffet(nomCarte, partie.getVariante());
							if(j.getMain().contains(c) || c.getValeur().equalsIgnoreCase("Piocher")){			//On verifie que la carte existe dans la main du joueur
								if(c.jouable(t)){   //On verifie que cette carte est jouable
									coupValide=true;
								}
							}
						}
					}
					else{
						System.out.println("Ce n'est pas un coup valide !");
					}
				}

			}
			j.jouerCarte(c,t); 
			c.appliquerEffetCarte(partie, m, t, j, p, s);		//La carte jouee par le joueur applique son effet 
			if (j.getMain().size()==0){							//Le cas ou le joueur pose sa derniere carte
				j.setGagne(true);
				m.incrGagne();								//On incremente le nombre de joueur ayant gagne a cette manche
				switch (m.getNbGagne()){					//On lui attribue son score en fonction de son classement
				case 1:j.score(50);break;
				case 2:j.score(30);break;
				case 3:j.score(10);break;
				default:break;
				}
			}


			break;

		case PIOCHER_TROIS_CARTES :

			players=partie.getJoueursPresents();
			indx=players.indexOf(j);
			if (indx<players.size()-1){		//On se cale sur le joueur attaquant
				jvise=players.get(indx+1);	//On se cale sur le joueur recevant l'attaque
			}
			else{
				jvise=players.get(0);
			}
			if (jvise instanceof JoueurVirtuel) {	//Dans le cas ou le joueur vise est un joueurV
				System.out.println("\n\nL'adversaire " +jvise+ " pioche trois cartes.");
				if((p.getPioche().size() < 3)) {
					System.out.println("Il n'y a plus de carte dans la pioche ... ! La pioche est renouvellée !");
					p.refairePioche(t);

				}

				jvise.addCarteMain(p.distribuer1Carte(t));
				jvise.addCarteMain(p.distribuer1Carte(t));
				jvise.addCarteMain(p.distribuer1Carte(t));

				System.out.println("\nL'adversaire " +jvise+ " a pioche trois cartes.");
			}


			else { //Dans le cas ou le joueur vise est l'utilisateur
				System.out.println("Aie ! Vous piochez trois cartes !");
				if((p.getPioche().size() < 3)) {
					System.out.println("Il n'y a plus de carte dans la pioche ... ! La pioche est renouvellée !");
					p.refairePioche(t);

				}
				ArrayList<CarteEffet> l=new ArrayList<CarteEffet>();
				for (int i=0;i<3;i++){
					CarteEffet card=p.distribuer1Carte(t);
					jvise.addCarteMain(card);
					l.add(card);


				}
				System.out.println("Vous avez pioche " +l);
			}

			break;

		case CHOISIR_COULEUR :
			if (j instanceof JoueurVirtuel) {

				System.out.println("\nL'adversaire " +j+ " choisit la couleur");
				//On appelle la methode changerCouleurTalon de la classe Talon
				t.changerCouleurTalon(couleur);
				System.out.println("\nL'adversaire " +j+ " a choisi " +t.getCouleur());
			}
			else {
				System.out.println("\nQuelle couleur voulez-vous choisir ? (P, CO, CA, T) ");
				String nouvelleCouleur = s.nextLine();
				//On appelle la methode changerCouleurTalon de la classe Talon
				t.changerCouleurTalon(associerCouleur(nouvelleCouleur));
				System.out.println("\nVous avez choisi " +t.getCouleur());

			}
			break;


		case POSER_PARTOUT : 		
			break;

		case PIOCHER_CARTE : 
			players=partie.getJoueursPresents();
			indx=players.indexOf(j);
			if (indx<players.size()-1){
				jvise=players.get(indx+1);
			}
			else{
				jvise=players.get(0);
			}
			if (jvise instanceof JoueurVirtuel) {
				System.out.println("\nL'adversaire " +jvise+ " pioche une carte.");
				if((p.getPioche().size() < 1)) {
					System.out.println("\n\nIl n'y a plus de carte dans la pioche ... ! La pioche est renouvellée !");
					p.refairePioche(t);

				}
				jvise.addCarteMain(p.distribuer1Carte(t));
				System.out.println("\n\nL'adversaire " +jvise+ " a pioche une carte.");
			}
			else {
				System.out.println("Aie ! Vous piochez une carte !");
				if((p.getPioche().size() < 1)) {
					System.out.println("\n\nIl n'y a plus de carte dans la pioche ... ! La pioche est renouvellée !");
					p.refairePioche(t);

				}
				CarteEffet card=p.distribuer1Carte(t);
				jvise.addCarteMain(card);
				System.out.println("\nVous avez pioche " +card);
			}
			break;


		case CHOISIR_DE_PIOCHER :

			if (j instanceof JoueurVirtuel) {
				System.out.println("L'adversaire " +j+ " pioche.");
				if ((p.getPioche().size() == 0)) {
					System.out.println("\n\nIl n'y a plus de carte dans la pioche ... ! ");
					p.refairePioche(t);	


				}
				j.addCarteMain(p.distribuer1Carte(t));
			}
			else {
				if ((p.getPioche().size() == 0)) {
					System.out.println("\n\nIl n'y a plus de carte dans la pioche ... ! ");
					p.refairePioche(t);	


				}
				j.addCarteMain(p.distribuer1Carte(t));

			}
			break;


		case JOKER :
			if (j instanceof JoueurVirtuel) {
				System.out.println("\nL'adversaire " +j+ " a joue un joker");
				CarteEffet j1 = new CarteEffet("J-J", partie.getVariante());
			}

			else {
				System.out.println("\nVous avez joue un joker ");
				CarteEffet j2 = new CarteEffet("J-J", partie.getVariante());
			}


		case RIEN : 

			break;
		
		default:
			break;

		}
	}


}
