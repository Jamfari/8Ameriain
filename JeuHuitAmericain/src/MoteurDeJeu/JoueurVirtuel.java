package MoteurDeJeu;



public class JoueurVirtuel extends Joueur implements Strategie{

	// ATTUBUTS

	private int difficulte;



	// CONSTRUCTEUR

	public JoueurVirtuel(String nom, int nombreDeCartes, int diff) {

		super(nom, nombreDeCartes);
		difficulte=diff;			

	}

	// METHODES

	//La methode move qui va faire jouer le joueurV  

	public CarteEffet moveNiveau1(Talon t) {

		for (CarteEffet c: main) {  // on parcourt la main du joueurV

			if (c.jouable(t)) {  //Et on regarde si une carte dans sa main est jouable

				return c;				//On retourne la carte jouee par le joueur V
			}

		}

		return new CarteEffet("P-P", "Monclar");

	}


	@SuppressWarnings("unlikely-arg-type")
	public CarteEffet moveNiveau2(Talon t) {

		for (CarteEffet c : main) {

			if (c.jouable(t)) {

				return c;
			}

			int rejouerCarte = CarteEffet.REJOUER_CARTE;
			if (main.contains(rejouerCarte)) {
				return c;
			}

			int piocher3Cartes = CarteEffet.PIOCHER_TROIS_CARTES;
			if (main.contains(piocher3Cartes)) {
				return c;
			}
			
			int sauterTourJoueur = CarteEffet.SAUTER_TOUR_JOUEUR;
			if (main.contains(sauterTourJoueur)) {
				return c;
			}
		}



		return new CarteEffet("P-P", "Monclar");		

	}


	public int getDifficulte() {
		return difficulte;
	}


}
