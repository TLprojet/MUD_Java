package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
<<<<<<< HEAD
=======
	
	/**
     * Liste des joueurs
     */
	private static ArrayList<Player> players;
	
	/**
     * Nouveau donjon (grille) crée
     */
>>>>>>> refs/remotes/origin/master
	private Grid dj = new Grid();
	private static ArrayList<Account> accounts;
	
<<<<<<< HEAD
	public static void main(String[] args) throws IOException, NotBoundException {
=======
	
	/**
     * Méthode main
     * S'occupe de RMI
     * Crée un nouveau serveur global qui gère les serveurs de jeux et de tchat
     * Possibilité de lister tous les joueurs
     */
	public static void main(String[] args) throws IOException {
>>>>>>> refs/remotes/origin/master
		LocateRegistry.createRegistry(1099);
		GlobalServer globalServ = new GlobalServer(new ArrayList<GameServerIF>(), new ArrayList<ChatServerIF>(), new ArrayList<Account>());
		Naming.rebind("global", globalServ);
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
			globalServ.addGameServer("1" + num, new GameServer("1" + num, new ArrayList<Player>(), new Sin("Jacques", i)));
		}
		System.out.println();
		// System.out.println("Entrer la lettre p pour lister les joueurs");
		
		while(true){
			mes = in.readLine();
			if(mes.equals("p")){
				printPlayers();
			}
		}
		
		
	}
	
<<<<<<< HEAD
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		GlobalServer.accounts = accounts;
	}

	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Account> accounts) throws RemoteException{
=======
	/**
     * Constructeur d'un serveur global
     * @param gameServers
     * 			Liste de tous les serveurs de jeux ( 1 par pièce )
     * @param chatServers
     * 			Liste de tous les serveurs de tchat ( 1 par pièce )
     * @param players
     * 			Liste de tous les joueurs
     */
	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Player> players) throws RemoteException{
>>>>>>> refs/remotes/origin/master
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
		this.accounts = accounts;
	}
	
	/**
     * Permet d'ajouter un serveur de jeu à la liste des serveurs de jeux
     * @param nom 
     * 			Nom du serveur de jeu
     * @param serv
     * 			Serveur de jeu à ajouter
     * @throws RemoteException,MalformedURLException
     */
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	System.out.println("Serveur de jeu de la pièce n°" + nom.substring(1) + " lancé");
	}
	
	/**
     * Permet d'ajouter un serveur de tchat à la liste des serveurs de tchat
     * @param nom 
     * 			Nom du serveur de tchat
     * @param serv
     * 			Serveur de tchat à ajouter
     * @throws RemoteException
     * @throws MalformedURLException
     */
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		System.out.println("Serveur de chat de la pièce n°" + nom + " lancé");
	}
	
<<<<<<< HEAD
	public int findByName(String name){
		for(int i=0; i<accounts.size(); i++){
			if(accounts.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	public void logOut(int accNum, int room) throws RemoteException {
		Account acc = accounts.get(accNum);
		Player p = gameServers.get(room).getPlayerById(acc.getPlayer().getId());
		accounts.set(accNum, new Account(p, 0, acc.getName()));
		gameServers.get(room).delPlayer(p);
		System.out.println("Le joueur " + acc.getName() + " s'est déconnecté.");
	}
	
	public Player logIn(int accNum) throws RemoteException {
		Account acc = accounts.get(accNum);
		acc.setStatus(1);
		accounts.set(accNum, acc);
		System.out.println("Le joueur " + acc.getName() + " s'est connecté.");
		gameServers.get(acc.getPlayer().getRoom()).addPlayer(acc.getPlayer());
		return acc.getPlayer();
	}
	
	public void die(int accNum, int room) throws RemoteException {
		Account acc = accounts.get(accNum);
		Player p = gameServers.get(room).getPlayerById(accNum);
		p = new Player(8, accNum, 8, p.getName());
		accounts.set(accNum, new Account(p, 1, acc.getName()));
		gameServers.get(room).delPlayer(p);
		gameServers.get(8).addPlayer(p);
		System.out.println("Le joueur " + acc.getName() + " est mort.");
	}
	
	public Account addAccount(String name) throws RemoteException{
		Account toAdd = new Account(new Player(8, accounts.size(), 8, name), 1, name);
		accounts.add(toAdd);
		System.out.println("Le joueur " + toAdd.getName() + " a créé un compte.");
		gameServers.get(8).addPlayer(toAdd.getPlayer());
		return toAdd;
	}
	
	public void delAccount(int num) throws RemoteException {
		Account a = accounts.get(num);
		accounts.remove(a);
		gameServers.get(a.getPlayer().getRoom()).delPlayer(a.getPlayer());
		System.out.println("Le joueur a bien été supprimé de la base.");
	}
	
=======
	/**
     * @return les serveurs de jeux
     */
>>>>>>> refs/remotes/origin/master
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
//		for(int i=0; i<players.size(); i++){
//			Player player = players.get(i);
//			System.out.println("- " + player.getPlayerName() + " : " + player.getRoom());
//		}
	}
	
<<<<<<< HEAD
//	public int getPlayerRoom(int playerNum){
//		return players.get(playerNum).getRoom();
//	}
=======
	/**
	 * @return la liste des joueurs
     */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Ajoute un joueur à la liste des joueurs
	 * @param name
	 * 			Nom du joueur
	 * @return Le joueur ajouté
     */
	public Player addPlayer(String name){
		Player toAdd = new Player(8, name, 1);
		players.add(toAdd);
		System.out.println("Le joueur " + toAdd.getPlayerName() + " a créé un compte.");
		return toAdd;
	}
	
	/**
	 * Supprime un joueur de la liste des joueurs
	 * @param name
	 * 			Nom du joueur
	 * @param room
	 * 			Numéro de la pièce où se trouve le joueur
     */
	public void delPlayer(String name, int room) {
		players.remove(players.indexOf(new Player(room, name, 1)));
		System.out.println("Le joueur " + name + " a été supprimé de la base.");
	}
	
	/**
	 * Déconnecte un joueur
	 * @param playerNum
	 * 			Numéro du joueur
     */
	public void logOut(int playerNum) {
		Player curPlayer = players.get(playerNum);
		players.set(playerNum, new Player(curPlayer.getRoom(), curPlayer.getPlayerName(), 0));
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est déconnecté.");
	}
	
	/**
	 * Connecte un joueur
	 * @param playerNum
	 * 			Numéro du joueur
	 * @return le joueur connecté
     */
	public Player logIn(int playerNum) {
		Player curPlayer = players.get(playerNum);
		curPlayer.setStatus(1);
		players.set(playerNum, curPlayer);
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est connecté.");
		return curPlayer;
	}
	
	/**
	 * Cherche un joueur grâce à son nom
	 * @param name
	 * 			Nom du joueur à chercher
	 * @return le numéro du joueur s'il a été trouvé ou -1
     */
	public int findByName(String name) {
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).getPlayerName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
>>>>>>> refs/remotes/origin/master
	
<<<<<<< HEAD
	//in : playerNum int, char dir (direction : ZQSD)
	//out: 1 if everything worked fine
	//     -1 can't go there (wall)
	public int move(int pos, int dir, Player p) throws RemoteException{
=======
	/**
	 * Déplace un joueur dans une direction donnée
	 * @param playerNum
	 * 			Numéro du joueur
	 * @param dir
	 * 			Direction où aller (1 nord | 2 ouest | 3 sud | 4 est)
	 * @return le numéro de pièce où le joueur se trouve après cette méthode
     */
	public int move(int playerNum, int dir){
		Player curPlayer = players.get(playerNum);
>>>>>>> refs/remotes/origin/master
		int newRoomNum = -1;
		int x = ((pos-1)/3);
		int y = ((pos-1)%3);
		
		if (dir == 2) {
			if (dj.grille[x][y].getOuest() instanceof Door) {
				newRoomNum = 3*x+y;
			}
		}
		
		if (dir == 1) {
			if (dj.grille[x][y].getNord() instanceof Door) {
				newRoomNum = 3*(x-1)+y+1;
			}
		}
		
		if (dir == 4) { 
			if (dj.grille[x][y].getEst() instanceof Door) {
				newRoomNum = 3*x+y+2;
			}
		}
		
		if (dir == 3) { 
			if (dj.grille[x][y].getSud() instanceof Door) {
				newRoomNum = 3*(x+1)+y+1;
			}
		}
		if(newRoomNum != -1){
			gameServers.get(pos).delPlayer(p);
			gameServers.get(newRoomNum).addPlayer(p);
		}
		return newRoomNum;
	}
	
	/**
	 * Affiche la grille en se servant de la méthode dans la classe Grille
	 * @see Grid#displayGrid(int)
     */
	public String displayGrid(int currentRoom) {
		return(dj.displayGrid(currentRoom));
	}
	
}
