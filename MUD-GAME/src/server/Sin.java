package server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Sin implements Serializable {
	
<<<<<<< HEAD
	private String sinName;
	private int hp;
=======
	
	private static final long serialVersionUID = -705396228447824018L;
	private int room;
	private String name;
	private int status;
>>>>>>> refs/remotes/origin/master
	
<<<<<<< HEAD
	public Sin(String sinName, int hp) {
=======
	/**
     * Constructeur d'un pêché, ce sont les monstres
     * @param room
     *          Numéro de la pièce où se trouve le pêché
     * @param name
     * 			Nom du pêché
     * @param status
     * 			Status du pêché, en combat ou non
     * @throws RemoteException
     */
	public Sin(int room, String name, int status) {
>>>>>>> refs/remotes/origin/master
		super();
<<<<<<< HEAD
		this.sinName = sinName;
		this.hp = hp;
=======
		this.room = room;
		this.name = name;
		this.status = status;
>>>>>>> refs/remotes/origin/master
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
<<<<<<< HEAD

	public String getSinName() {
		return sinName;
=======
	
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getName() {
		return name;
>>>>>>> refs/remotes/origin/master
	}
<<<<<<< HEAD
	public void setSinName(String sinName) {
		this.sinName = sinName;
	}	
=======
	public void setSName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
>>>>>>> refs/remotes/origin/master
	
}
