package server;

import java.io.Serializable;

public class Sin implements Serializable {
	
	private int room;
	private String sinName;
	private int status;
	
	
	public Sin(int room, String sinName, int status) {
		super();
		this.room = room;
		this.sinName = sinName;
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
	public String getSinName() {
		return sinName;
	}
	public void setSinName(String sinName) {
		this.sinName = sinName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
