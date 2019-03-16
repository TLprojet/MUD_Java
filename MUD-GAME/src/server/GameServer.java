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
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import client.ChatClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
=======
>>>>>>> refs/remotes/origin/master
import java.rmi.RemoteException;

public class GameServer extends UnicastRemoteObject implements GameServerIF {

 	
	private static final long serialVersionUID = 1L;
	private String serverName;
	private static ArrayList<Player> players;
	private Sin sin;
	private ChatClient chatClient;
	private ChatServerIF chatServer;
	private String chatServerURL;
	
<<<<<<< HEAD
	protected GameServer(String serverName, ArrayList<Player> players, Sin sin) throws RemoteException, MalformedURLException, NotBoundException {
=======
	/**
     * Constructeur d'un serveur de jeu
     * @param serverName
     *          Nom du serveur de jeu
     * @throws RemoteException
     */
	protected GameServer(String serverName) throws RemoteException {
>>>>>>> refs/remotes/origin/master
    	super();
    	this.serverName = serverName;
    	this.players = players;
    	this.sin = sin;
    	
    	chatServerURL = "rmi://localhost:1099/" + this.getServerName().substring(1, serverName.length());
    	chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
    	chatClient = new ChatClient("Serveur", chatServer);
    	Thread t = new Thread(chatClient);
    	t.start();
    	
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
	
	public int getServerNum(){
		return Integer.parseInt(serverName.substring(1, 2));
	}
	
	public void addPlayer(Player p) throws RemoteException{
		players.add(p);
		this.chatClient.send(this.getServerName() + "Le joueur " + p.getName() + "est rentré dans ma pièce!");
	}
	
	public void delPlayer(Player p){
		players.remove(p);
	}
	
	public Player getPlayerById(int id){
		for(int i=0; i<players.size();i++){
			if(players.get(i).getId() == id){
				return players.get(i);
			}
		}
		return null;
	}
	
	public int getPlayerNumById(int id){
		for(int i=0; i<players.size();i++){
			if(players.get(i).getId() == id){
				return i;
			}
		}
		return -1;
	}
	
	public void hitSin() throws RemoteException{
		int x = (int)(Math.random() * this.sin.getHp());
		this.sin = new Sin(this.sin.getSinName(), this.sin.getHp() - x + 1);
		this.chatClient.send("Le pêché " + this.getServerName().substring(1, serverName.length()) + " a perdu " + String.valueOf(x) + " points de vie.");
	}
	
	public int hitPlayer(int playerNum) throws RemoteException{
		Player p = players.get(playerNum);
		int x = (int)(Math.random() * p.getHealthPoints() + 1);
		players.set(playerNum, new Player(p.getRoom(), p.getId(), p.getHealthPoints() - x, p.getName()));
		this.chatClient.send("Le joueur " + p.getName() + " a perdu " + String.valueOf(x) + " points de vie.");
		return players.get(playerNum).getHealthPoints();
	}
	
	public boolean attack(int playerNum) throws RemoteException{
			hitSin();
			if(this.sin.getHp() == 0){
				this.chatClient.send("Le pêché " + this.getServerName().substring(1, serverName.length()) + " a été vaincu.");
			}
			else{
				if(hitPlayer(playerNum) == 0){
					this.chatClient.send("Le joueur " + players.get(playerNum).getName() + " est mort.");
					return false;
				} else{
					return true;
				}
			}
			return false;
	}
	
}