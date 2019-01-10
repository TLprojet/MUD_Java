package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class GlobalServer {
	
	private static ArrayList<GameServerIF> gameServers;
	private static ArrayList<ChatServerIF> chatServers;
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		LocateRegistry.createRegistry(1099);
		GlobalServer globalServ = new GlobalServer(new ArrayList<GameServerIF>(), new ArrayList<ChatServerIF>());
		globalServ.addGameServer(new GameServer("InitialServ", new ArrayList<Player>()));
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
		}

	}
	
	public GlobalServer(ArrayList<GameServerIF> gameServers, ArrayList<ChatServerIF> chatServers){
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
	}
	
	public void addGameServer(GameServer serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	System.out.println("Serveur de jeu lancé");
	}
	
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		System.out.println("Serveur de chat de la pièce n°" + nom + " lancé");
	}
	
	public static ArrayList<GameServerIF> getGameServers() {
		return gameServers;
	}
	
	public static void setGameServers(ArrayList<GameServerIF> gameServers) {
		GlobalServer.gameServers = gameServers;
	}
	
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	public void setChatServers(ArrayList<ChatServerIF> chatServers) {
		this.chatServers = chatServers;
	}
	
}
