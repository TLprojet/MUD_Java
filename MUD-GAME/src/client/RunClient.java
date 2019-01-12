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
    	System.out.println(playerName + " s'est reconnect�.");
    } else {
    	player = server.addPlayer(playerName);
    	playerNum = (server.getPlayers().size()-1);
    	System.out.println(playerName + " a cr�� un compte.");
    }
    
    System.out.println("Vous �tes dans la pi�ce n�" + player.getRoom());

	String chatServerURL = "rmi://localhost:1099/" + player.getRoom();
	ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	ChatClient chatClient = new ChatClient(playerName, chatServer);
	new Thread(chatClient).start();
	
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
			switch(mes) {
			case "quit":
				server.logOut(playerNum);
				chatServer.delClientFromChat(chatClient);
				break;
			case "Z":
				res=server.move(playerNum, 'Z');
				if (res==1) System.out.println(server.displayGrid(player.getRoom()));
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");						
			case "z":
				res=server.move(playerNum, 'z');
				if (res==1) System.out.println(server.displayGrid(player.getRoom()));
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");	
				break;
			case "Q": 
				res=server.move(playerNum, 'Q');
				if (res==1) System.out.println(server.displayGrid(player.getRoom()));
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");					
			case "q":
				break;
			case "S":
			case "s":
				break;
			case "D":
			case "d":
				break;
				
			}
		}
	}

  }
  
}