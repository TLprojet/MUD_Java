package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import server.ChatServerIF;
import server.GlobalServer;
import server.GlobalServerIF;
import server.Player;
import server.GameServerIF;

public class RunClient {

	
  private static String playerName = "";
  private static String mes = "";
  private static GlobalServerIF server;
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private static int accNum;
  private static GameServerIF gameServer;
  private static String gameServerURL;
    
  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
    
    //Connexion au serveur global
    String globalServerURL = "rmi://localhost:1099/global";
    server = (GlobalServerIF) Naming.lookup(globalServerURL);
     
    System.out.println("Bienvenue !");
    System.out.println("Quel est votre pseudo ?");
    try {
      System.out.print(">> "); 
      playerName = in.readLine();
    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
    accNum = server.findByName(playerName);
    if(accNum != -1) {
    	int room = server.logIn(accNum);
    	System.out.println(playerName + " s'est reconnecté.");
    	gameServerURL = "rmi://localhost:1099/1" + room; //+ server.getAccounts().get(accNum).getPlayer().getRoom();
    } else {
    	server.addNewAccount(playerName);
    	accNum = (server.getAccounts().size()-1);
    	System.out.println(playerName + " a créé un compte.");
    	gameServerURL = "rmi://localhost:1099/18";
    }
    
    //Connexion au serveur de jeu
    gameServer = (GameServerIF) Naming.lookup(gameServerURL);
    System.out.println("Vous êtes dans la pièce n°" + gameServer.getServerNum());

    
    // Connexion au serveur de chat
	String chatServerURL = "rmi://localhost:1099/" + gameServer.getServerNum();
	ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	ChatClient chatClient = new ChatClient(playerName, chatServer);
	Thread t = new Thread(chatClient);
	t.start();
	
	System.out.println(server.displayGrid(gameServer.getServerNum()));
	System.out.println("\nListe des commandes : ");
	System.out.println("\t- \"<msg> pour envoyer un message");
	System.out.println("\t- quit pour quitter le jeu\n");

	
	while(true) {
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
				server.logOut(accNum, gameServer.getServerNum());
				chatServer.delClientFromChat(chatClient);
				chatServer.broadcastMessage("Le joueur "+ playerName +" s'est déconnecté.\n");
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
			case "A":
			case "a":
				int x = gameServer.attack(gameServer.getPlayerNumById(accNum));
				TimeUnit.SECONDS.sleep(1);
				if(x == 1){
					server.die(accNum, gameServer.getServerNum());
				}
			}
			if(move !=0){
				res = server.move(gameServer.getServerNum(), move, gameServer.getPlayerById(accNum), accNum);
				if (res!=-1){					
					//changement de pièce
					gameServerURL = "rmi://localhost:1099/1" + res;
					gameServer = (GameServerIF) Naming.lookup(gameServerURL);
					
					// changement serveur de chat
					chatServerURL = "rmi://localhost:1099/" + res;
					chatServer.delClientFromChat(chatClient);
					chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
					chatClient = new ChatClient(playerName, chatServer);
					t = new Thread(chatClient);
					
					//affichage de la grille
					System.out.println(server.displayGrid(gameServer.getServerNum()));
				}
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");	
			}
		}
	}

  }
  
}