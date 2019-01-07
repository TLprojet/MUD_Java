package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import server.ChatServerIF;
import server.ServeurInterface;

public class lanceClient {

	
  private static String nomJoueur = "";
  private static ServeurInterface serveur;
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
    System.out.println("Lancement du jeu");
    
    //Connexion au serveur de jeu
    serveur = (ServeurInterface) Naming.lookup("rmi://localhost:1099/TestRMI");
    
    System.out.println("Bienvenue !");
    System.out.println("Quel est votre pseudo?");
    try {
      System.out.print(">> ");
      nomJoueur = in.readLine();
    } catch (IOException e) {
      System.err.println("I/O error.");
      System.err.println(e.getMessage());
    }
    
    System.out.println("Ravi de vous rencontrer \"" + nomJoueur + "\", c'est parti !");

   
     /* 
	String chatServerURL = "rmi://localhost:1099/RMIChatServer";
	ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
	new Thread(new ChatClient(nomJoueur,chatServer)).start();
	*/ 
	
    
	System.out.println(serveur.afficherGrille());

  }
}