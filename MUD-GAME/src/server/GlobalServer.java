package server;

import java.io.BufferedReader;
import database.Sql;
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
	private static ArrayList<GameServerIF> gameServers;
	private static ArrayList<ChatServerIF> chatServers;
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static String mes;
	private Grid dj = new Grid();
	private static ArrayList<Account> accounts;
	
	public static void main(String[] args) throws IOException, NotBoundException {
		// Initiation du serveur global
		LocateRegistry.createRegistry(1099);
		GlobalServer globalServ = new GlobalServer(new ArrayList<GameServerIF>(), new ArrayList<ChatServerIF>(), new ArrayList<Account>());
		Naming.rebind("global", globalServ);
		
		// Cr�ation des serveurs de chat et de jeu
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
			if(i==9) {
				globalServ.addGameServer("1" + num, new GameServer("1" + num, new ArrayList<Player>(), new Sin("Jacques", 15)));
			} else {
				globalServ.addGameServer("1" + num, new GameServer("1" + num, new ArrayList<Player>(), new Sin("Jacques",5)));
			}
		}
		System.out.println("Tous les serveurs ont �t� correctement lanc�s.\n");
		
		// R�cup�ration des comptes depuis la BDD
		Sql db = new Sql();
		db.connectDB();
		ArrayList<Player> pl = Sql.getPlayersDB();
		for(int i=0; i<pl.size(); i++) {
			addAccount(pl.get(i));
			// System.out.println("Le joueur suivant a �t� r�cup�r� :" + pl.get(i).getId() + pl.get(i).getName());
		}
		
		System.out.println("Liste des commandes serveur :");
		System.out.println("\t- save pour sauvegarder les donn�es dans la BDD");
		System.out.println("\t- p pour lister les joueurs");
		System.out.println("\t- clearBDD pour supprimer les donn�es de la BDD \n");
		
		// Attente des commandes serveur
		while(true){
			mes = in.readLine();
			switch(mes) {
			
			case "save":
				// Sauvegarde la liste de joueurs dans la BDD
				db.clearPlayersDB();
				for(int i = 0; i<gameServers.size();i++) {
					for(int j = 0; j<gameServers.get(i).getPlayers().size();j++) {
						Player pp = gameServers.get(i).getPlayers().get(j);
						int num = globalServ.findByName(pp.getName());
						accounts.set(num, new Account(pp, accounts.get(num).getStatus(), pp.getName()));
					}
				}
				
				for(int i = 0; i<accounts.size(); i++) {
					Player p = accounts.get(i).getPlayer();
					db.insertPlayersDB("(NULL,"+ p.getRoom() + "," + p.getHealthPoints() + ",\'" + p.getName() + "\',\'" + globalServ.arrayToString(p.getSins()) + "\'," + p.getMaxHp()+ ")");
				}
				
				System.out.println("Les donn�es ont bien �t� sauvegard�es.");
				break;
				
			case "p":
				// Liste les joueurs
				System.out.println("Liste des joueurs :");
				for(int i = 0; i<accounts.size();i++) {
					Account a = accounts.get(i);
					String str;
					if(a.getStatus() == 1) {
						str = "Connect�";
					} else {
						str = "D�connect�";
					}
					System.out.println("   " + a.getName() + ", Salle n�" + a.getPlayer().getRoom() + ", " + a.getPlayer().getHealthPoints() + " points de vie, " + str);
				}
				break;
				
			case "clearBDD":
				// Supprime les donn�es de la BDD
				db.clearPlayersDB();
				System.out.println("Les donn�es ont bien �t� supprim�es.");
				break;
				
			}
		}
		
		
	}
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		GlobalServer.accounts = accounts;
	}
	
	// Converti un tableau en String pour pouvoir l'ins�rer dans la BBD
	public String arrayToString(int[] arr) throws RemoteException {
		String str = "";
		for(int i = 0; i < arr.length; i++) {
			str = str + Integer.toString(arr[i]);
		}
		return str;
	}
	
	// Constructeur du serveur global
	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Account> accounts) throws RemoteException{
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
		this.accounts = accounts;
	}
	
	// Cr�ation d'un serveur de jeu
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	// System.out.println("Serveur de jeu de la pi�ce n�" + nom.substring(1) + " lanc�");
	}
	
	// Cr�ation d'un serveur de chat
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		// System.out.println("Serveur de chat de la pi�ce n�" + nom + " lanc�");
	}
	
	// Retourne l'ID d'un joueur � partir de son nom
	public int findByName(String name){
		for(int i=0; i<accounts.size(); i++){
			if(accounts.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	// Indique au joueur s'il peut combattre un p�ch� dans sa position actuelle
	public boolean canFight(int accNum, int room) throws RemoteException{
		int[] sins = accounts.get(accNum).getPlayer().getSins();
		return (sins[room-1] == 0 && room != 8 && room != 9);
	}
	
	// D�connexion d'un joueur
	public void logOut(int accNum, int room) throws RemoteException {
		Account acc = accounts.get(accNum);
		Player p = gameServers.get(room-1).getPlayerById(acc.getPlayer().getId());
		accounts.set(accNum, new Account(p, 0, acc.getName()));
		gameServers.get(room-1).delPlayer(gameServers.get(room-1).getPlayerNumById(accNum));
		System.out.println("Le joueur " + acc.getName() + " s'est d�connect�.");
	}
	
	// Connexion d'un joueur
	public int logIn(int accNum) throws RemoteException {
		if(accounts.get(accNum).getStatus()==1) {
			return -1;
		} else {
			Account acc = new Account(accounts.get(accNum).getPlayer(), 1, accounts.get(accNum).getName());
			accounts.set(accNum, acc);
			System.out.println("Le joueur " + acc.getName() + " s'est connect�.");
			gameServers.get(acc.getPlayer().getRoom()-1).addPlayer(acc.getPlayer());
			return acc.getPlayer().getRoom();
		}
	}
	
	// Mort d'un joueur
	public void die(int accNum, int room) throws RemoteException {
		Account acc = accounts.get(accNum);
		Player p = gameServers.get(room-1).getPlayerById(accNum);
		p = new Player(8, accNum, 10, acc.getName(), new int[9], 10);
		accounts.set(accNum, new Account(p, 1, acc.getName()));
		gameServers.get(room-1).delPlayer(gameServers.get(room-1).getPlayerNumById(accNum));
		gameServers.get(7).addPlayer(p);
		System.out.println("Le joueur " + acc.getName() + " est mort.");
	}
	
	// Cr�ation d'un nouveau compte pour un joueur n'ayant jamais jou�
	public Account addNewAccount(String name) throws RemoteException{
		Account toAdd = new Account(new Player(8, accounts.size(), 10, name, new int[9], 10), 1, name);
		accounts.add(toAdd);
		System.out.println("Le joueur " + toAdd.getName() + " a cr�� un compte.");
		gameServers.get(7).addPlayer(toAdd.getPlayer());
		return toAdd;
	}
	
	// Cr�ation d'un compte � partir d'un joueur
	public static void addAccount(Player p) {
		Account toAdd = new Account(p, 0, p.getName());
		accounts.add(toAdd);
	}
	
	// Supression d'un compte
	public void delAccount(int num) throws RemoteException {
		Account a = accounts.get(num);
		accounts.remove(a);
		gameServers.get(a.getPlayer().getRoom()-1).delPlayer(gameServers.get(a.getPlayer().getRoom()-1).getPlayerNumById(num));
		System.out.println("Le joueur a bien �t� supprim� de la base.");
	}
	
	public static ArrayList<GameServerIF> getGameServers() {
		return gameServers;
	}
	
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	// Indique � un joueur s'il peut se d�placer dans la direction
	// demand�e et retourne sa nouvelle position
	public int move(int pos, int dir, Player p, int accNum) throws RemoteException{
		int newRoomNum = -1;
		int x = ((pos-1)/3);
		int y = ((pos-1)%3);
		
		if (dir == 2) { //players wants to go to the room in the left
			if (dj.grille[x][y].getOuest() instanceof Door) {
				newRoomNum = 3*x+y;
			}
		}
		
		if (dir == 1) { //players wants to go to the room in the left
			if (dj.grille[x][y].getNord() instanceof Door) {
				newRoomNum = 3*(x-1)+y+1;
			}
		}
		
		if (dir == 4) { //players wants to go to the room in the left
			if (dj.grille[x][y].getEst() instanceof Door) {
				newRoomNum = 3*x+y+2;
			}
		}
		
		if (dir == 3) { //players wants to go to the room in the left
			if (dj.grille[x][y].getSud() instanceof Door) {
				newRoomNum = 3*(x+1)+y+1;
			}
		}
		
		if(dir == 10) {
			newRoomNum = 10;
		}
		if(newRoomNum != -1){
			gameServers.get(pos-1).delPlayer(gameServers.get(pos-1).getPlayerNumById(accNum));
			p.setRoom(newRoomNum);
		
			accounts.set(accNum, new Account(p, 1, p.getName()));
			gameServers.get(newRoomNum-1).addPlayer(p);
		}
		return newRoomNum;
	}
	
	// Affiche la grille
	public String displayGrid(int currentRoom) {
		return(dj.displayGrid(currentRoom));
	}
	
}
