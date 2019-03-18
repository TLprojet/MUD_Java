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
    
    // Entr�e du pseudonyme par le client
    System.out.println("Bienvenue !");
    System.out.println("Quel est votre pseudo ?");
    try {
      System.out.print(">> "); 
      playerName = in.readLine();
    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
    
    // Test pour savoir il faut cr�er un nouveau compte ou si le joueur a d�j� jou�
    accNum = server.findByName(playerName);
    if(accNum != -1) {
    	int room = server.logIn(accNum);
    	System.out.println(playerName + " s'est reconnect�.");
    	gameServerURL = "rmi://localhost:1099/1" + room; 
    } else {
    	server.addNewAccount(playerName);
    	accNum = (server.getAccounts().size()-1);
    	System.out.println(playerName + " a cr�� un compte.");
    	gameServerURL = "rmi://localhost:1099/18";
    }
    
    //Connexion au serveur de jeu
    gameServer = (GameServerIF) Naming.lookup(gameServerURL);
    System.out.println("Vous �tes dans la pi�ce n�" + gameServer.getServerNum());

    
    // Connexion au serveur de chat
	String chatServerURL = "rmi://localhost:1099/" + gameServer.getServerNum();
	ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	ChatClient chatClient = new ChatClient(playerName, chatServer);
	Thread t = new Thread(chatClient);
	t.start();
	
	System.out.println(server.displayGrid(gameServer.getServerNum()));
	System.out.println("Liste des commandes : ");
	System.out.println("\t- \"<msg> pour envoyer un message");
	System.out.println("\t- quit pour quitter le jeu");
	System.out.println("\t- Z,Q,S,D pour se d�placer");
	System.out.println("\t- A pour attaquer");

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
				// D�connexion
				server.logOut(accNum, gameServer.getServerNum());
				chatServer.delClientFromChat(chatClient);
				chatServer.broadcastMessage("Le joueur "+ playerName +" s'est d�connect�.\n");
				break;
				
			case "Z":				
			case "z":
				// D�placement vers le haut
				move = 1;
				break;
				
			case "Q": 				
			case "q":
				// D�placement vers la gauche
				move = 2;
				break;
				
			case "S":
			case "s":
				// D�placement vers le bas
				move = 3;
				break;
				
			case "D":
			case "d":
				// D�placement vers la droite
				move = 4;
				break;
				
			case "A":
			case "a":
				// Attaque le p�ch� si le joueur y est autoris�
				if(server.canFight(accNum, gameServer.getServerNum())) {
					int x = gameServer.attack(gameServer.getPlayerNumById(accNum));
					if(x == 1){
						// Si le joueur meurt en combattant
						server.die(accNum, gameServer.getServerNum());
					} else if (x==2) {
						// Si le joueur a tu� tous les p�ch�s
						System.out.println("Vous avez vaincu tous les p�ch�s!");
						System.out.println("Vous devez maintenant combattre le boss.");
						System.out.println("C'est un combat � mort. Appuyez sur f pour le combattre.");
						move = 10;
					}
				} else {
					System.out.println("Il n'y a pas de p�ch� � combattre ici, ou vous l'avez d�j� vaincu.");
				}
				break;
				
			case"f":
				// Attaque le boss si le joueur est dans la bonne salle
				if(gameServer.getServerNum()==10) {
					int x = gameServer.attack(gameServer.getPlayerNumById(accNum));
					if(x == 1){
						server.die(accNum, gameServer.getServerNum());
					} else if (x==2) {
						// Si le joueur fini le jeu
						System.out.println("FELICITATIONS! Vous avez termin� le jeu.");
					}
				} else {
					System.out.println("Vous devez d'abord vaincre les autres p�ch�s pour combattre le boss.");
				}
			}
			
			if(move !=0){
				// Si le joueur souhaite se d�placer, on appelle la m�thode move du serveur global
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
					
					//affichage de la grille
					if(res!=10) {
						System.out.println(server.displayGrid(gameServer.getServerNum()));
					}
				}
				if (res==-1) System.out.println("Vous ne pouvez pas aller ici.\n");	
			}
		}
	}

  }
  
}