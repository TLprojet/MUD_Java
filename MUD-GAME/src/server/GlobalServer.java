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
	private static ArrayList<GameServerIF> gameServers;
	private static ArrayList<ChatServerIF> chatServers;
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static String mes;
	private static ArrayList<Player> players;
	private Grid dj = new Grid();
	
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
	
	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Player> players) throws RemoteException{
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
		this.players = players;
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
	
	public static ArrayList<GameServerIF> getGameServers() {
		return gameServers;
	}
	
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	public static void printPlayers() throws RemoteException{
		for(int i=0; i<players.size(); i++){
			Player player = players.get(i);
			System.out.println("- " + player.getPlayerName() + " : " + player.getRoom());
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
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
	
	//in : playerNum int, char dir (direction : ZQSD)
	//out: 1 if everything worked fine
	//     -1 can't go there (wall)
	public int move(int playerNum, int dir){
		Player curPlayer = players.get(playerNum);
		int newRoomNum = -1;
		int pos = curPlayer.getRoom();
		int x = ((pos-1)/3);
		int y = ((pos-1)%3);
		
		if (dir == 2) { //players wants to go to the room in the left
			if (dj.grille[x][y].getOuest() instanceof Door) {
				newRoomNum = 3*x+y;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 1) { //players wants to go to the room in the left
			if (dj.grille[x][y].getNord() instanceof Door) {
				newRoomNum = 3*(x-1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 4) { //players wants to go to the room in the left
			if (dj.grille[x][y].getEst() instanceof Door) {
				newRoomNum = 3*x+y+2;
				curPlayer.setRoom(newRoomNum);
			}
		}
		
		if (dir == 3) { //players wants to go to the room in the left
			if (dj.grille[x][y].getSud() instanceof Door) {
				newRoomNum = 3*(x+1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
		}
		players.set(playerNum, curPlayer);
		return newRoomNum;
	}
	
	public String displayGrid(int currentRoom) {
		return(dj.displayGrid(currentRoom));
	}
	
}
