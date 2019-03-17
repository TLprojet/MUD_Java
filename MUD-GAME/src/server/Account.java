package server;

import java.io.Serializable;

public class Account implements Serializable{
	private static final long serialVersionUID = 5716718161294494154L;
	private Player player;
	private int status;
	private String name;
	
	public Account(Player player, int status, String name){
		this.player = player;
		this.status = status;
		this.name = name;
	}

	public Player getPlayer() {
		return player;
	}

	public int getStatus() {
		return status;
	}
	
	public String getName() {
		return name;
	}

}
