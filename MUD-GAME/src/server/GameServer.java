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
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import client.ChatClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GameServer extends UnicastRemoteObject implements GameServerIF {

 	
	private static final long serialVersionUID = 1L;
	private String serverName;
	private ArrayList<Player> players;
	private Sin sin;
	private ChatClient chatClient;
	private ChatServerIF chatServer;
	private String chatServerURL;
	
	// Constructeur du serveur de jeu
	protected GameServer(String serverName, ArrayList<Player> players, Sin sin) throws RemoteException, MalformedURLException, NotBoundException {
    	super();
    	this.serverName = serverName;
    	this.players = players;
    	this.sin = sin;
    	
    	// Le serveur de jeu se connecte au serveur de chat correspondant
    	chatServerURL = "rmi://localhost:1099/" + this.getServerName().substring(1, serverName.length());
    	chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
    	chatClient = new ChatClient("Serveur", chatServer);
    	Thread t = new Thread(chatClient);
    	t.start();
    	
	}

	public Sin getSin() throws RemoteException {
		return sin;
	}

	public void setSin(Sin sin) {
		this.sin = sin;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	// Récupère le numéro de serveur de jeu (donc numéro de salle)
	public int getServerNum(){
		return Integer.parseInt(serverName.substring(1, serverName.length()));
	}
	
	public void addPlayer(Player p) throws RemoteException{
		if(getServerNum()==9) {
			players.add(new Player(p.getRoom(), p.getId(), p.getHealthPoints() + 2, p.getName(), p.getSins(), p.getMaxHp() + 2));
		} else {
			players.add(p);
		}
	}
	
	public void delPlayer(int i){
		players.remove(i);
	}
	
	// Renvoie les données du joueur grâce à son ID
	public Player getPlayerById(int id){
		for(int i=0; i<players.size();i++){
			if(players.get(i).getId() == id){
				return players.get(i);
			}
		}
		return null;
	}
	
	// Renvoie le numéro du joueur dans le serveur de jeu grâce à son ID
	public int getPlayerNumById(int id){
		for(int i=0; i<players.size();i++){
			if(players.get(i).getId() == id){
				return i;
			}
		}
		return -1;
	}
	
	// Attaque le pêché de cette salle
	public boolean hitSin() throws RemoteException{
		this.sin.setHp(this.sin.getHp() - 1);
		this.chatClient.send("Le pêché " + this.getServerName().substring(1, serverName.length()) + " : -1 point de vie.");
		int x = this.sin.getHp();
		if(x>0) {
			this.chatClient.send("Il lui reste " + x +" points de vie.");
			return true;
		} else {
			this.chatClient.send("Il a été vaincu.");
			return false;
		}
		
	}
	
	// Attaque le joueur qui combat
	public boolean hitPlayer(int playerNum) throws RemoteException{
		Player p = players.get(playerNum);
		int x = p.getHealthPoints() - 1;
		players.set(playerNum, new Player(p.getRoom(), p.getId(), x, p.getName(), p.getSins(), p.getMaxHp()));
		this.chatClient.send(p.getName() + " : -1 point de vie.");
		if(x+1>0) {
			this.chatClient.send(p.getName() + " a " + p.getHealthPoints() + " points de vie.");
			return true;
		} else {
			this.chatClient.send("Le joueur " + p.getName() +" est mort.");
			return false;
		}
		
	}
	
	// Gère les combats
	public int attack(int playerNum) throws RemoteException{
		double x = Math.random();
		// Détermine si c'est le joueur ou le monstre qui va perdre de la vie
		if(x<0.5) {
			if(!(hitPlayer(playerNum))) {
				return 1;
			}
		}
		else {
			if(!(hitSin())){
				// Si le pêché est vaincu
				if(getServerNum()==10) {
					return 2;
				}else {
					for(int i=0; i<players.size(); i++) {
						Player p = players.get(i);
						int[] j = p.getSins();
						int y = p.getMaxHp();
						y = y + 1;
						j[this.getServerNum()-1] = 1;
						p = new Player(p.getRoom(), p.getId(), y, p.getName(), j,y);
						players.set(i, p);
						if(IntStream.of(p.getSins()).sum() == 7) {
							return 2;
						}					
					}
				}
				sin.setHp(5);
			}
		}
		return -1;
		
	}
	
	
}