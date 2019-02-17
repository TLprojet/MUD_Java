package server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Sin implements Serializable {
	
	
	private static final long serialVersionUID = -705396228447824018L;
	private int room;
	private String name;
	private int status;
	
	/**
     * Constructeur d'un p�ch�, ce sont les monstres
     * @param room
     *          Num�ro de la pi�ce o� se trouve le p�ch�
     * @param name
     * 			Nom du p�ch�
     * @param status
     * 			Status du p�ch�, en combat ou non
     * @throws RemoteException
     */
	public Sin(int room, String name, int status) {
		super();
		this.room = room;
		this.name = name;
		this.status = status;
	}


	public void Attack () {
		
	}
	
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getName() {
		return name;
	}
	public void setSName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
