package MoteurDeJeu;

import java.util.Arrays;


public class Carte {
	// ATTUBUTS

	protected String valeur;
	protected String couleur;
	protected final String[] couleurs = {"Pique","Trefle","Coeur","Carreau"};
	protected final String[] valeurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi", "Piocher"};


	// CONSTRUCTEUR

	public Carte(String couleur, String valeur) {
		this.couleur=couleur;
		this.valeur=valeur;

	}

	public Carte(String carte) {


		this(associerCouleur(carte.split("-")[1]),associerValeur(carte.split("-")[0]));
	}


	//on associe les couleurs a une lettre les représentant entree par l'utilisateur
	public static String associerCouleur(String couleur){
		String coul=new String(couleur);

		if (couleur.equalsIgnoreCase("P")){
			coul = "Pique";		
		}
		else if (couleur.equalsIgnoreCase("T")) {
			coul = "Trefle";
		}
		else if (couleur.equalsIgnoreCase("Co")) {
			coul = "Coeur";
		}
		else if (couleur.equalsIgnoreCase("Ca")) {
			coul = "Carreau";
		}
		return coul;
	}

	//On associe les valeurs a ce que l'utilisateur doit saisir
	public static String associerValeur(String valeur){
		String val = new String();
		if (Arrays.asList("2","3","4","5","6","7","8","9","10").contains(valeur)){
			return valeur;
		}
		if (valeur.equalsIgnoreCase("A")){
			val="As";
		}
		if (valeur.equalsIgnoreCase("V")){
			val="Valet";
		}
		if (valeur.equalsIgnoreCase("D")){
			val="Dame";
		}
		if (valeur.equalsIgnoreCase("R")){
			val="Roi";
		}
		if (valeur.equalsIgnoreCase("P")) {
			val="Piocher";
		}
		if (valeur.equalsIgnoreCase("J")){
			val="Joker";
		}
		return val;
	}
	// METHODES 

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	@Override   	//Dire au compilateur qu'on réécrit une methode
	public String toString() {
		if(valeur.equalsIgnoreCase("Joker")) {	
			return "Joker";
		}
		else {
			return valeur + " de " + couleur;
		}

	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Carte){
			Carte c=(Carte) o;
			return this.valeur.equalsIgnoreCase(c.valeur) && this.couleur.equalsIgnoreCase(c.couleur);}
		else{
			return false;
		}
	}



}
