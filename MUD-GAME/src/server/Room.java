package server;

import java.io.Serializable;

public class Room implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int idPiece;
	private Edge nord;
	private Edge sud;
	private Edge ouest;
	private Edge est;

	
	public Room(int idPiece, Edge nord, Edge sud, Edge ouest, Edge est) {
		super();
		this.idPiece = idPiece;
		this.nord = nord;
		this.sud = sud;
		this.ouest = ouest;
		this.est = est;
	}

	public Edge getNord() {
		return nord;
	}

	public void setNord(Edge nord) {
		this.nord = nord;
	}

	public Edge getSud() {
		return sud;
	}

	public void setSud(Edge sud) {
		this.sud = sud;
	}

	public Edge getOuest() {
		return ouest;
	}

	public void setOuest(Edge ouest) {
		this.ouest = ouest;
	}

	public Edge getEst() {
		return est;
	}

	public void setEst(Edge est) {
		this.est = est;
	}

	public int getIdPiece() {
		return idPiece;
	}
	
	
}
