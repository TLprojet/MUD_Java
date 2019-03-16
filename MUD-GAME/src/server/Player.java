package server;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private int room;
	private int healthPoints;
	private int id;
	private String name;
	
<<<<<<< HEAD
	public Player(int piece, int id, int healthPoints, String name){
=======
	
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
>>>>>>> refs/remotes/origin/master
		super();
		this.room = piece;
		this.healthPoints = healthPoints;
		this.id = id;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHealthPoints() {
		return healthPoints;
	}
	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int piece) {
		this.room = piece;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
