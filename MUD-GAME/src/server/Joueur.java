package server;

public class Joueur {
	private int numJoueur;
	private int piece;
	private String nomJoueur;
	public Joueur(int numJoueur, int piece, String nomJoueur){
		super();
		this.numJoueur = numJoueur;
		this.piece = piece;
		this.nomJoueur = nomJoueur;
	}
	public int getNumJoueur() {
		return numJoueur;
	}
	public void setNumJoueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}
	public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
	}
	public String getNomJoueur() {
		return nomJoueur;
	}
	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}
}
