package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GlobalServer extends UnicastRemoteObject implements GlobalServerIF {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Liste des serveurs de jeux
     */
	private static ArrayList<GameServerIF> gameServers;
	
	/**
     * Liste des serveurs de tchat
     */
	private static ArrayList<ChatServerIF> chatServers;
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static String mes;
	
	/**
     * Liste des joueurs
     */
	private static ArrayList<Player> players;
	
	/**
     * Nouveau donjon (grille) cr�e
     */
	private Grid dj = new Grid();
	
	
	/**
     * M�thode main
     * S'occupe de RMI
     * Cr�e un nouveau serveur global qui g�re les serveurs de jeux et de tchat
     * Possibilit� de lister tous les joueurs
     */
	public static void main(String[] args) throws IOException {
		LocateRegistry.createRegistry(1099);
		GlobalServer globalServ = new GlobalServer(new ArrayList<GameServerIF>(), new ArrayList<ChatServerIF>(), new ArrayList<Player>());
		Naming.rebind("global", globalServ);
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
			globalServ.addGameServer("1" + num, new GameServer("1" + num));
		}
		System.out.println();
		System.out.println("Entrer la lettre p pour lister les joueurs");
		
		while(true){
			mes = in.readLine();
			if(mes.equals("p")){
				printPlayers();
			}
		}
		
		
	}
	
	/**
     * Constructeur d'un serveur global
     * @param gameServers
     * 			Liste de tous les serveurs de jeux ( 1 par pi�ce )
     * @param chatServers
     * 			Liste de tous les serveurs de tchat ( 1 par pi�ce )
     * @param players
     * 			Liste de tous les joueurs
     */
	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Player> players) throws RemoteException{
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
		this.players = players;
	}
	
	/**
     * Permet d'ajouter un serveur de jeu � la liste des serveurs de jeux
     * @param nom 
     * 			Nom du serveur de jeu
     * @param serv
     * 			Serveur de jeu � ajouter
     * @throws RemoteException,MalformedURLException
     */
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	System.out.println("Serveur de jeu de la pi�ce n�" + nom.substring(1) + " lanc�");
	}
	
	/**
     * Permet d'ajouter un serveur de tchat � la liste des serveurs de tchat
     * @param nom 
     * 			Nom du serveur de tchat
     * @param serv
     * 			Serveur de tchat � ajouter
     * @throws RemoteException
     * @throws MalformedURLException
     */
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		System.out.println("Serveur de chat de la pi�ce n�" + nom + " lanc�");
	}
	
	/**
     * @return les serveurs de jeux
     */
	public static ArrayList<GameServerIF> getGameServers() {
		return gameServers;
	}
	
	/**
     * @return les serveurs de tchat
     */
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	/**
     * Permet d'afficher les joueurs
     * @throws RemoteException
     */
	public static void printPlayers() throws RemoteException{
		for(int i=0; i<players.size(); i++){
			Player player = players.get(i);
			System.out.println("- " + player.getPlayerName() + " : " + player.getRoom());
		}
	}
	
	/**
	 * @return la liste des joueurs
     */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Ajoute un joueur � la liste des joueurs
	 * @param name
	 * 			Nom du joueur
	 * @return Le joueur ajout�
     */
	public Player addPlayer(String name){
		Player toAdd = new Player(8, name, 1);
		players.add(toAdd);
		System.out.println("Le joueur " + toAdd.getPlayerName() + " a cr�� un compte.");
		return toAdd;
	}
	
	/**
	 * Supprime un joueur de la liste des joueurs
	 * @param name
	 * 			Nom du joueur
	 * @param room
	 * 			Num�ro de la pi�ce o� se trouve le joueur
     */
	public void delPlayer(String name, int room) {
		players.remove(players.indexOf(new Player(room, name, 1)));
		System.out.println("Le joueur " + name + " a �t� supprim� de la base.");
	}
	
	/**
	 * D�connecte un joueur
	 * @param playerNum
	 * 			Num�ro du joueur
     */
	public void logOut(int playerNum) {
		Player curPlayer = players.get(playerNum);
		players.set(playerNum, new Player(curPlayer.getRoom(), curPlayer.getPlayerName(), 0));
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est d�connect�.");
	}
	
	/**
	 * Connecte un joueur
	 * @param playerNum
	 * 			Num�ro du joueur
	 * @return le joueur connect�
     */
	public Player logIn(int playerNum) {
		Player curPlayer = players.get(playerNum);
		curPlayer.setStatus(1);
		players.set(playerNum, curPlayer);
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est connect�.");
		return curPlayer;
	}
	
	/**
	 * Cherche un joueur gr�ce � son nom
	 * @param name
	 * 			Nom du joueur � chercher
	 * @return le num�ro du joueur s'il a �t� trouv� ou -1
     */
	public int findByName(String name) {
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).getPlayerName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * D�place un joueur dans une direction donn�e
	 * @param playerNum
	 * 			Num�ro du joueur
	 * @param dir
	 * 			Direction o� aller (1 nord | 2 ouest | 3 sud | 4 est)
	 * @return le num�ro de pi�ce o� le joueur se trouve apr�s cette m�thode
     */
	public int move(int playerNum, int dir){
		Player curPlayer = players.get(playerNum);
		int newRoomNum = -1;
		int pos = curPlayer.getRoom();
		int x = ((pos-1)/3);
		int y = ((pos-1)%3);
		
		if (dir == 2) {
			if (dj.grille[x][y].getOuest() instanceof Door) {
				newRoomNum = 3*x+y;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 1) {
			if (dj.grille[x][y].getNord() instanceof Door) {
				newRoomNum = 3*(x-1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 4) { 
			if (dj.grille[x][y].getEst() instanceof Door) {
				newRoomNum = 3*x+y+2;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 3) { 
			if (dj.grille[x][y].getSud() instanceof Door) {
				newRoomNum = 3*(x+1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
		}
		players.set(playerNum, curPlayer);
		return newRoomNum;
	}
	
	/**
	 * Affiche la grille en se servant de la m�thode dans la classe Grille
	 * @see Grid#displayGrid(int)
     */
	public String displayGrid(int currentRoom) {
		return(dj.displayGrid(currentRoom));
	}
	
}
