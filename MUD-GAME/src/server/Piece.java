package server;

import java.io.Serializable;

public class Piece implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int idPiece;
	private Cote nord;
	private Cote sud;
	private Cote ouest;
	private Cote est;

	
	public Piece(int idPiece, Cote nord, Cote sud, Cote ouest, Cote est) {
		super();
		this.idPiece = idPiece;
		this.nord = nord;
		this.sud = sud;
		this.ouest = ouest;
		this.est = est;
	}

	public Cote getNord() {
		return nord;
	}

	public void setNord(Cote nord) {
		this.nord = nord;
	}

	public Cote getSud() {
		return sud;
	}

	public void setSud(Cote sud) {
		this.sud = sud;
	}

	public Cote getOuest() {
		return ouest;
	}

	public void setOuest(Cote ouest) {
		this.ouest = ouest;
	}

	public Cote getEst() {
		return est;
	}

	public void setEst(Cote est) {
		this.est = est;
	}

	public int getIdPiece() {
		return idPiece;
	}
	
	
}
