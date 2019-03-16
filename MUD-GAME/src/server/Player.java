package server;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private int room;
	private int healthPoints;
	private int id;
	private String name;
	
	public Player(int piece, int id, int healthPoints, String name){
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
