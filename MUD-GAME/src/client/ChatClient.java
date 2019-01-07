package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.ChatServerIF;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF, Runnable {

	private static final long serialVersionUID = 5557769733752232252L;
	private String name;
	private ChatServerIF chatServer;

	protected ChatClient(String name, ChatServerIF chatServer) throws RemoteException {
		this.name=name;
		this.chatServer=chatServer;
		chatServer.registerChatClient(this);
	}


	public void retrieveMessage(String message) throws RemoteException {
		System.out.println(message);
	}
	
	public void run() {
		String message="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Vous pouvez maintenant discuter avec les membres de la pi�ce <numPi�ce>");
	    while (true) {
	      try {
			message = in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      try {
	    	  chatServer.broadcastMessage(name + ": " + message);
	      } catch (RemoteException e) {
	    	  e.printStackTrace();
	      }
	    }
		
	}
}
