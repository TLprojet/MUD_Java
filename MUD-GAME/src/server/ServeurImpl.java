package server;

/* Cette classe correspond à l'objet distant. Elle doit 
donc implémenter l'interface définie et contenir le code nécessaire. */

/* Comme indiqué dans l'interface, toutes les méthodes distantes, 
mais aussi le constructeur de la classe, doivent indiquer qu'elles 
peuvent lever l'exception RemoteException. Ainsi, même si le constructeur 
ne contient pas de code il doit être redéfini pour inhiber la génération du 
constructeur par défaut qui ne lève pas cette exception. */ 


/* Cette classe doit obligatoirement hériter de la classe UnicastRemoteObject 
qui contient les différents traitements élémentaires
pour un objet distant dont l'appel par le stub du client est unique. */
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class ServeurImpl extends UnicastRemoteObject implements ServeurInterface {

 	
	private static final long serialVersionUID = 1L;
	private String serverName;
	private ArrayList<Joueur> playersInGame;
	
	protected ServeurImpl(String serverName, ArrayList<Joueur> playersInGame) throws RemoteException {
    	super();
    	this.serverName = serverName;
		this.playersInGame = playersInGame;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String afficherGrille(int currentRoom) throws RemoteException {
		GrilleDonjon dj= new GrilleDonjon();
		return(dj.afficherGrille(currentRoom));
	}
	
	public ArrayList<Joueur> getPlayersInGame() {
		return playersInGame;
	}
	
	public void setPlayersInGame(ArrayList<Joueur> playersInGame) {
		this.playersInGame = playersInGame;
	}
	
	public void addPlayer(String nom){
		playersInGame.add(new Joueur(playersInGame.size(), 8, nom));
	}
}