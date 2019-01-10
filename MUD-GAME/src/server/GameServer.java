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

public class GameServer extends UnicastRemoteObject implements GameServerIF {

 	
	private static final long serialVersionUID = 1L;
	private String serverName;
	private ArrayList<Player> players;
	
	protected GameServer(String serverName, ArrayList<Player> players) throws RemoteException {
    	super();
    	this.serverName = serverName;
		this.players = players;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String displayGrid(int currentRoom) throws RemoteException {
		Grid dj= new Grid();
		return(dj.displayGrid(currentRoom));
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public Player addPlayer(String name){
		Player toAdd = new Player(8, name, 1);
		players.add(toAdd);
		System.out.println("Le joueur " + toAdd.getPlayerName() + " a créé un compte.");
		return toAdd;
	}
	
	public void delPlayer(String name, int room) {
		players.remove(players.indexOf(new Player(room, name, 1)));
		System.out.println("Le joueur " + name + " a été supprimé de la base.");
	}
	
	public void logOut(int playerNum) {
		Player curPlayer = players.get(playerNum);
		players.set(playerNum, new Player(curPlayer.getRoom(), curPlayer.getPlayerName(), 0));
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est déconnecté.");
	}
	
	public Player logIn(int playerNum) {
		Player curPlayer = players.get(playerNum);
		curPlayer.setStatus(1);
		players.set(playerNum, curPlayer);
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est connecté.");
		return curPlayer;
	}
	
	public int findByName(String name) {
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).getPlayerName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
}