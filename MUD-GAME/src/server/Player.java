package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private int room;
	private int healthPoints;
	private int maxHp;
	private int id;
	private String name;
	private int[] sins;
	
	public Player(int piece, int id, int healthPoints, String name, int[] sins, int maxHp){
		super();
		this.room = piece;
		this.healthPoints = healthPoints;
		this.id = id;
		this.name = name;
		this.sins = sins;
		this.maxHp = maxHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int[] getSins() {
		return sins;
	}

	public void setSins(int[] sins) {
		this.sins = sins;
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
