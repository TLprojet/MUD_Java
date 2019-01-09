package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.ChatServerIF;

public class LanceChatClient {
	
	private static String nomJoueur;
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String chatServerURL = "rmi://localhost:1099/8";
		ChatServerIF chatServer = (ChatServerIF) Naming.lookup(chatServerURL);
		
		System.out.println("Quel est votre pseudo ?");
	    try {
	      System.out.print(">> ");
	      nomJoueur = in.readLine();
	    } catch (IOException e) {
	      System.err.println("I/O error.");
	      System.err.println(e.getMessage());
	    }
		new Thread(new ChatClient(nomJoueur,chatServer)).start();
	}

}
