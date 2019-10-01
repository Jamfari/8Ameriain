package MoteurDeJeu;
import java.util.*;

public class Joueur {

	// ATTUBUTS

	protected LinkedList<CarteEffet> main = new LinkedList<CarteEffet>();
	protected final String nom;
	protected int nombreDeCartes;
	protected int score = 0;
	protected boolean gagne = false;
	protected boolean tourJouable=true;

	
	// CONSTRUCTEUR
	
	public Joueur(String nom, int nbDeCartes) { 
		this.nom=nom;
		this.nombreDeCartes=nbDeCartes;

	}
	
	// METHODES
 
	public void compterPoints() {

	}

	public int jouerCarte(CarteEffet c,Talon t) {
		if (gagne){
			return CarteEffet.RIEN;
		}
		if (c.equals(new CarteEffet("P-P", "Monclar")) 
				|| c.equals(new CarteEffet("P-P", "Minimale")) 
				|| c.equals(new CarteEffet("P-P", "Variante 1"))) {
			return CarteEffet.CHOISIR_DE_PIOCHER;
		}
		
		if (c.equals(new CarteEffet("J-J", "Monclar")) || c.equals(new CarteEffet("J-J", "Monclar"))) {
			return CarteEffet.JOKER;
		}
		
		if (!main.contains(c)){
			throw new RuntimeException(this+" ne possede pas le "+c);	
		}
		//Dans le cas ou le joueur fait n'importe quoi on arrete tout et le compilateur me dit ou le joueur a fait n'importe quoi
		if (!c.jouable(t)){
			throw new RuntimeException(c+" ne peut pas etre jouee dan cette situation.");
			
		}
		else{
			String couleur=c.couleur;
			if (!couleur.equalsIgnoreCase(t.getCouleur())){
				t.changerCouleurTalon(couleur);
			}
			int effet=c.getEffet();
			main.remove(c);
			nombreDeCartes--;
			t.add(c);
			return effet;		
		}
	}
	
	
	public void viderMain(){
		this.main = new LinkedList<>();
		}
	

	public void score(int s){
		score+=s;
	}

	//Acces a la main du joueur
	public LinkedList<CarteEffet> getMain() {
	 
		return main;
	}

	public int getNombreDeCartes() {
		return nombreDeCartes;
		
	}
	
	public void addCarteMain(CarteEffet c){
		main.add(c);
	}
	
	public String getNom() {
		return nom;
	}
	
	public boolean getGagne(){
		return gagne;
	}
	public void setGagne(boolean t){
		gagne=t;
	}
	
	
	@Override
	public String toString(){
		return nom;
	}
	public boolean getTourJouable(){
		return tourJouable;
	}
	public void retablirTour(){  //On aurait pu creer une seule methode setTourJouable pour les effets de retablir/passerTour
		tourJouable=true;		//Mais le code est plus clair de cette façon, donc plus facile a manipuler
	}
	public void passerTour(){
		tourJouable=false;
	}


}
