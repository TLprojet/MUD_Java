package server;

import java.io.Serializable;

public abstract class Bord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected int franchissable;
	protected char direction;
	
}
