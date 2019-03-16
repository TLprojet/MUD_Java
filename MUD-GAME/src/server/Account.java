package server;

import java.io.Serializable;

public class Account implements Serializable{
	private static Player player;
	private static int status;
	private static String name;
	
	public Account(Player player, int status, String name){
		this.player = player;
		this.status = status;
		this.name = name;
	}

	public Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Account.player = player;
	}

	public static int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		Account.status = status;
	}

	public String getName() {
		return name;
	}

	public static void setName(String name) {
		Account.name = name;
	}
}
