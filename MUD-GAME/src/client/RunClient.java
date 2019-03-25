package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
  private static ChatServerIF chatServer;
  private static ChatClient chatClient;
    
  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
    
    //Connexion au serveur global
    String globalServerURL = "rmi://localhost:1099/global";
    server = (GlobalServerIF) Naming.lookup(globalServerURL);
    
    // Entrée du pseudonyme par le client
    System.out.println("Bienvenue !");
    System.out.println("Quel est votre pseudo ?");
    try {
      System.out.print(">> "); 
      playerName = in.readLine();
    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
    
    // Test pour savoir il faut créer un nouveau compte ou si le joueur a déjà joué
    accNum = server.findByName(playerName);
    if(accNum != -1) {
    	int room = server.logIn(accNum);
    	if(room != -1) {
    		System.out.println(playerName + " s'est reconnecté.");
        	gameServerURL = "rmi://localhost:1099/1" + room; 
    	} else {
    		System.out.println("Ce joueur est déjà connecté.");
    		System.out.println("Merci de relancer le client et de choisir un autre pseudonyme.");
    	}
    	
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
	chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	chatClient = new ChatClient(playerName, chatServer);
	Thread t = new Thread(chatClient);
	t.start();
	
	System.out.println(server.displayGrid(gameServer.getServerNum()));
	System.out.println("Liste des commandes : ");
	System.out.println("\t- \"<msg> pour envoyer un message");
	System.out.println("\t- quit pour quitter le jeu");
	System.out.println("\t- Z,Q,S,D pour se déplacer");
	
	// Attente des commandes joueur
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
				// Déconnexion
				server.logOut(accNum, gameServer.getServerNum());
				chatServer.delClientFromChat(chatClient);
				chatServer.broadcastMessage("Le joueur "+ playerName +" s'est déconnecté.\n");
				System.exit(0);
				break;
				
			case "Z":				
			case "z":
				// Déplacement vers le haut
				move = 1;
				break;
				
			case "Q": 				
			case "q":
				// Déplacement vers la gauche
				move = 2;
				break;
				
			case "S":
			case "s":
				// Déplacement vers le bas
				move = 3;
				break;
				
			case "D":
			case "d":
				// Déplacement vers la droite
				move = 4;
				break;
			
			case "R":
			case "r":
				if(gameServer.getPlayerById(accNum).getHealthPoints() == 0) {
					server.die(accNum, gameServer.getServerNum());
					gameServerURL = "rmi://localhost:1099/18";
					gameServer = (GameServerIF) Naming.lookup(gameServerURL);
					System.out.println(server.displayGrid(gameServer.getServerNum()));
				}
				break;
				
			case "B":
			case "b":
				if(IntStream.of(gameServer.getPlayerById(accNum).getSins()).sum() == 7) {
					move = 10;
				} else {
					System.out.println("Vous devez vaincre tous les pêchés pour rejoindre la salle du boss.");
				}
				break;
			}
			
			if(move !=0){
				// Si le joueur souhaite se déplacer, on appelle la méthode move du serveur global
				res = server.move(gameServer.getServerNum(), move, gameServer.getPlayerById(accNum), accNum);
				if (res!=-1){					
					//changement de serveur de jeu
					gameServerURL = "rmi://localhost:1099/1" + res;
					gameServer = (GameServerIF) Naming.lookup(gameServerURL);
					
					// changement serveur de chat
					chatServerURL = "rmi://localhost:1099/" + res;
					chatServer.delClientFromChat(chatClient);
					chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
					chatClient = new ChatClient(playerName, chatServer);
					t = new Thread(chatClient);
					
					// Affiche la liste des joueurs présents dans la salle
					ArrayList<Player> players = gameServer.getPlayers();
					if(gameServer.getSin()!=null) {
						System.out.println("Pêché " + gameServer.getSin().getSinName() + " : " + gameServer.getSin().getHp() + " PV");
					}
					System.out.println("Liste des joueurs de la salle " + gameServer.getServerNum() + " :");
					for(int i=0; i<players.size();i++) {
						System.out.println("\t" + players.get(i).getName());
					}
					
					//affichage de la grille
					if(res!=10) {
						System.out.println(server.displayGrid(gameServer.getServerNum()));
					}
					if(res == 9) {
						System.out.println("Vous avez gagné 2 points de vie en entrant dans la salle 9");
					}
				}
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");	
			}
		}
	}

  }
  
}