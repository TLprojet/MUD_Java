package server;

import java.io.Serializable;

public class Door extends Edge implements Serializable {

	private static final long serialVersionUID = 3836007156848573600L;
	public Door() {
		franchissable = 1;
	}

	public String toString() {
		return "porte";
	}
}
