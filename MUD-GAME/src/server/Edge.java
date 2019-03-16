package server;

import java.io.Serializable;

public abstract class Edge implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Attribut prenant la valeur 0 ou 1 selon si le bord est franchissable
     * Porte 1
     * Mur 0
     */
	protected int franchissable;


}
