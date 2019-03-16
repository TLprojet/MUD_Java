package server;

public class Wall extends Edge {
	
	private static final long serialVersionUID = -618262362219794394L;

	 /**
     * Constructeur d'un mur qui n'est pas franchissable
     */
	public Wall () {
		franchissable=0;
	}

	/**
     * Méthode toString de la classe Wall
     * @return
     */
	public String toString() {
		return "mur";
	}
	
	
	
}
