package server;

import java.io.Serializable;

public class Piece implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int idPiece;
	private Bord nord;
	private Bord sud;
	private Bord ouest;
	private Bord est;

	
	public Piece(int idPiece, Bord nord, Bord sud, Bord ouest, Bord est) {
		super();
		this.idPiece = idPiece;
		this.nord = nord;
		this.sud = sud;
		this.ouest = ouest;
		this.est = est;
	}

	public Bord getNord() {
		return nord;
	}

	public void setNord(Bord nord) {
		this.nord = nord;
	}

	public Bord getSud() {
		return sud;
	}

	public void setSud(Bord sud) {
		this.sud = sud;
	}

	public Bord getOuest() {
		return ouest;
	}

	public void setOuest(Bord ouest) {
		this.ouest = ouest;
	}

	public Bord getEst() {
		return est;
	}

	public void setEst(Bord est) {
		this.est = est;
	}

	public int getIdPiece() {
		return idPiece;
	}
	
	
}
