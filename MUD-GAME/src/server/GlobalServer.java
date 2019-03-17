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
		LocateRegistry.createRegistry(1099);
		GlobalServer globalServ = new GlobalServer(new ArrayList<GameServerIF>(), new ArrayList<ChatServerIF>(), new ArrayList<Account>());
		Naming.rebind("global", globalServ);
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
			globalServ.addGameServer("1" + num, new GameServer("1" + num, new ArrayList<Player>(), new Sin("Jacques")));
		}
		System.out.println();
		// System.out.println("Entrer la lettre p pour lister les joueurs");
		
		while(true){
			mes = in.readLine();
			if(mes.equals("save")){
				Sql db = new Sql();
				db.connectDB();
				db.clearPlayersDB();
				for(int i = 0; i<accounts.size(); i++) {
					Player p = accounts.get(i).getPlayer();
					db.insertPlayersDB("("+ p.getRoom() + "," + p.getId() + "," + p.getHealthPoints() + ",\'" + p.getName() + "\',\'" + arrayToString(p.getSins()) + "\'," + p.getMaxHp()+ ")");
				}
			}
		}
		
		
	}
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		GlobalServer.accounts = accounts;
	}
	
	public static String arrayToString(int[] arr) {
		String str = "";
		for(int i = 0; i < arr.length; i++) {
			str = str + Integer.toString(arr[i]);
		}
		return str;
	}

	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Account> accounts) throws RemoteException{
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
		this.accounts = accounts;
	}
	
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	System.out.println("Serveur de jeu de la pièce n°" + nom.substring(1) + " lancé");
	}
	
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		System.out.println("Serveur de chat de la pièce n°" + nom + " lancé");
	}
	
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
		p = new Player(8, accNum, p.getMaxHp(), p.getName(), p.getSins(), p.getMaxHp());
		accounts.set(accNum, new Account(p, 1, acc.getName()));
		gameServers.get(room).delPlayer(p);
		gameServers.get(8).addPlayer(p);
		System.out.println("Le joueur " + acc.getName() + " est mort.");
	}
	
	public Account addAccount(String name) throws RemoteException{
		Account toAdd = new Account(new Player(8, accounts.size(), 10, name, new int[9], 10), 1, name);
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
	
	public static ArrayList<GameServerIF> getGameServers() {
		return gameServers;
	}
	
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	public static void printPlayers() throws RemoteException{
//		for(int i=0; i<players.size(); i++){
//			Player player = players.get(i);
//			System.out.println("- " + player.getPlayerName() + " : " + player.getRoom());
//		}
	}
	
//	public int getPlayerRoom(int playerNum){
//		return players.get(playerNum).getRoom();
//	}
	
	//in : playerNum int, char dir (direction : ZQSD)
	//out: 1 if everything worked fine
	//     -1 can't go there (wall)
	public int move(int pos, int dir, Player p) throws RemoteException{
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
		if(newRoomNum != -1){
			gameServers.get(pos).delPlayer(p);
			gameServers.get(newRoomNum).addPlayer(p);
		}
		return newRoomNum;
	}
	
	public String displayGrid(int currentRoom) {
		return(dj.displayGrid(currentRoom));
	}
	
}
