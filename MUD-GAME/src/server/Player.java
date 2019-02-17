package server;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private int room;
	private String playerName;
	private int status;
	
	
	/**
     * Constructeur d'un joueur
     * @param piece
     *          Numéro de pièce où le joueur commencera (par défaut 8)
     * @param nomJoueur
     * 			Chaine de caractère décrivant le nom du joueur
     * @param status
     * 			En combat ou non
     */
	public Player(int piece, String nomJoueur, int status){
		super();
		this.status = status;
		this.room = piece;
		this.playerName = nomJoueur;
	}
	
	
	public void attack() {
		
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int piece) {
		this.room = piece;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String nomJoueur) {
		this.playerName = nomJoueur;
	}
}
