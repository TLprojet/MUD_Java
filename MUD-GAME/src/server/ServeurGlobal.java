package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import client.ChatClientIF;

public class ServeurGlobal {
	
	private static ArrayList<ServeurInterface> gameServers;
	private static ArrayList<ChatServerIF> chatServers;
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		LocateRegistry.createRegistry(1099);
		ServeurGlobal globalServ = new ServeurGlobal(new ArrayList<ServeurInterface>(), new ArrayList<ChatServerIF>(), new ArrayList<Joueur>());
		globalServ.addGameServer(new ServeurImpl("InitialServ", new ArrayList<Joueur>()));
		for(int i=0;i<10;i++){
			String num = Integer.toString(i+1);
			globalServ.addChatServer(num, new ChatServer(num));
		}

	}
	
	public ServeurGlobal(ArrayList<ServeurInterface> gameServers, ArrayList<ChatServerIF> chatServers, ArrayList<Joueur> playersInGame){
		super();
		this.gameServers = gameServers;
		this.chatServers = chatServers;
	}
	
	public void addGameServer(ServeurImpl serv) throws RemoteException, MalformedURLException{
		gameServers.add(serv);
		Naming.rebind(serv.getServerName(), serv);
	   	System.out.println("Serveur de jeu lanc�");
	}
	
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException{
		chatServers.add(serv);
		Naming.rebind(nom, serv);
		System.out.println("Serveur de chat de la pi�ce n�" + nom + " lanc�");
	}
	
	public static ArrayList<ServeurInterface> getGameServers() {
		return gameServers;
	}
	
	public static void setGameServers(ArrayList<ServeurInterface> gameServers) {
		ServeurGlobal.gameServers = gameServers;
	}
	
	public ArrayList<ChatServerIF> getChatServers() {
		return chatServers;
	}
	
	public void setChatServers(ArrayList<ChatServerIF> chatServers) {
		this.chatServers = chatServers;
	}
	
}