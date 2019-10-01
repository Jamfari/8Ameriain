package MoteurDeJeu;


import java.util.LinkedList;
import java.util.List;

public class Talon {

	// ATTUBUTS
	private final String[] couleurs = {"Pique","Trefle","Coeur","Carreau"};
	private CarteEffet carteJouee;
	private LinkedList<CarteEffet> talon = new LinkedList<CarteEffet>();
	private String couleur;
		
	
	// CONSTRUCTEUR

	public Talon(Pioche pioche) {

		//carteJouee = talon.get(0)  ;
		talon = new LinkedList<CarteEffet>();


		this.carteJouee=pioche.distribuer1Carte(this);
		talon.add(carteJouee);
		couleur=carteJouee.couleur;
		
	}

	// METHODES 

	public boolean changerCouleurTalon(String couleur) {
		for (String c:couleurs){
			if(c.equalsIgnoreCase(Carte.associerCouleur(couleur))){
				this.couleur=Carte.associerCouleur(couleur);
				return true;
			}
		}
		throw new RuntimeException(couleur + " n'est pas une couleur valide.");
	}
	
	
	
	public void refaireTalon(){
		CarteEffet c1=talon.get(0);
		CarteEffet c2=talon.get(1);
		CarteEffet c3=talon.get(2);
		this.talon=new LinkedList<CarteEffet>();
		talon.add(c1);
		talon.add(c2);
		talon.add(c3);
	}
	
	public String getCouleur(){
		return couleur;
	}
	
	
	public CarteEffet getCarteJouee() {
		return talon.getLast();
	}

	public void add(CarteEffet carteJouee) { 
		this.carteJouee = carteJouee;
		this.talon.add(carteJouee);
		
	}

	public List<CarteEffet> getTalon(){
		return talon;
	}
	
	public void afficherCarteEnCours() {
	      System.out.println("\n\nVoici la carte du talon en cours : " + talon.getLast());
	}

	

}
