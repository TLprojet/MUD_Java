package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import server.ChatServerIF;
import server.GlobalServer;
import server.Player;
import server.GameServerIF;

public class RunClient {

	
  private static String playerName = "";
  private static String mes = "";
  private static GameServerIF server;
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private static int playerNum;
  private static Player player;
  private static boolean running = true;
    
  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
    System.out.println("Lancement du jeu");
    
    //Connexion au serveur de jeu
    server = (GameServerIF) Naming.lookup("rmi://localhost:1099/InitialServ");
    
    System.out.println("Bienvenue !");
    System.out.println("Quel est votre pseudo ?");
    try {
      System.out.print(">> ");
      playerName = in.readLine();
    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
    playerNum = server.findByName(playerName);
    if(playerNum != -1) {
    	player = server.logIn(playerNum);
    	System.out.println(playerName + " s'est reconnecté.");
    } else {
    	player = server.addPlayer(playerName);
    	playerNum = (server.getPlayers().size()-1);
    	System.out.println(playerName + " a créé un compte.");
    }
    
    System.out.println("Vous êtes dans la pièce n°" + player.getRoom());

	String chatServerURL = "rmi://localhost:1099/" + player.getRoom();
	ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	ChatClient chatClient = new ChatClient(playerName, chatServer);
	Thread t = new Thread(chatClient);
	t.start();
	
	System.out.println(server.displayGrid(player.getRoom()));
	System.out.println("\nListe des commandes : ");
	System.out.println("\t- \"<msg> pour envoyer un message");
	System.out.println("\t- quit pour quitter le jeu\n");

	
	while(running) {
		try {
			mes = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int res;

		if(mes.startsWith("\"")){
			chatClient.send(mes.substring(1,mes.length()));
		}
		else {
			int move = 0;
			switch(mes) {
			case "quit":
				server.logOut(playerNum);
				chatServer.delClientFromChat(chatClient);
				chatServer.broadcastMessage("Le joueur "+playerName+" s'est déconnecté.\n");
				break;
			case "Z":				
			case "z":
				move = 1;
				break;
			case "Q": 				
			case "q":
				move = 2;
				break;
			case "S":
			case "s":
				move = 3;
				break;
			case "D":
			case "d":
				move = 4;
				break;
			}
			if(move !=0){
				res = server.move(playerNum, move);
				chatServer.broadcastMessage("Le joueur "+playerName+" a quitté la pièce.\n");
				if (res!=-1){
					//changement de pièce
					player.setRoom(res);
					
					// changement serveur de chat
					chatServerURL = "rmi://localhost:1099/" + res;
					chatServer.delClientFromChat(chatClient);
					chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
					chatClient = new ChatClient(playerName, chatServer);
					t = new Thread(chatClient);
					
					//affichage de la grille
					System.out.println(server.displayGrid(player.getRoom()));
				}
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");	
			}
		}
	}

  }
  
}