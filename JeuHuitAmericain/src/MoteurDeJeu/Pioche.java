package MoteurDeJeu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Pioche {

	// ATTUBUTS

	private List<CarteEffet> pioche = new LinkedList<CarteEffet>();
	private Partie partie;
	

	// CONSTRUCTEUR
	public Pioche(Partie partie) {
		this.partie=partie;
		
		
		String[] couleur = new String[] { "Pique", "Trefle", "Coeur", "Carreau" };
		String[] valeur = new String[] {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};

		for (int i = 0; i < couleur.length; i++) { 
			for (int j = 0; j < valeur.length; j++) { 
				CarteEffet carte = new CarteEffet(couleur[i], valeur[j], partie.getVariante());
				
				this.pioche.add(carte);
			}
		}
	}

	
	

	public List<CarteEffet> getPioche() {
		return pioche;
	}
	
	
	public CarteEffet distribuer1Carte(Talon t){	
		if (pioche.size()==0) {					//Cas ou la pioche est vide
			refairePioche(t);
			melangerLesCartes();
		}

		CarteEffet c = pioche.get(0);	//pioche la carte du haut de la pioche
		pioche.remove(0);				//enleve cette carte de la pioche
		return c;						//retourne la carte piochee

	}


	public void refairePioche(Talon t){
		List<CarteEffet> T=t.getTalon();
		for (int i =3; i<T.size(); i++){
			pioche.add(T.get(i));
			t.refaireTalon();
			this.melangerLesCartes();
			}

	}

	public void melangerLesCartes() {
		Collections.shuffle(pioche);
	}

}
