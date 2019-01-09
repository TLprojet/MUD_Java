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

		try {
			System.out.println("Vous pouvez maintenant discuter avec les membres de la pièce n°" + chatServer.getNomServeur());
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
	    while (true) {
	      try {
			message = in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      try {
	    	  if(message.startsWith("\"")){
	    		  chatServer.broadcastMessage(name + ": " + message.substring(1,message.length()));
	    	  }
	    	  else if(message.equals("quit")){
	    		  chatServer.delClientFromChat(this);
	    		  break;
	    	  }
	      } catch (RemoteException e) {
	    	  e.printStackTrace();
	      } catch (IOException e) {
			e.printStackTrace();
		}
	    }
		
	}
}
