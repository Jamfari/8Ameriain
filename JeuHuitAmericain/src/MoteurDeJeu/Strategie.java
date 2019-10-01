package MoteurDeJeu;



public interface Strategie {

	public abstract CarteEffet moveNiveau1(Talon talon);	
	//Abstract pour preciser au compilateur que c'est un modele et pas une vraie methode
	
	public abstract CarteEffet moveNiveau2(Talon talon);
	
}
