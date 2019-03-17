package server;

import java.io.Serializable;

public class Sin implements Serializable {
	
	private String sinName;
	private int hp;
	
	public Sin(String sinName) {
		super();
		this.sinName = sinName;
		this.hp = 5;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getSinName() {
		return sinName;
	}
	public void setSinName(String sinName) {
		this.sinName = sinName;
	}	
	
}
