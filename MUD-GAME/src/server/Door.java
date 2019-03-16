package server;

import java.io.Serializable;

public class Door extends Edge implements Serializable {

	private static final long serialVersionUID = 3836007156848573600L;
	
	 /**
     * Constructeur d'une porte qui est un bord franchissable
     */
	public Door() {
		franchissable = 1;
	}

	/**
     * Méthode toString de la classe Door
     * @return
     */
	public String toString() {
		return "porte";
	}
}
