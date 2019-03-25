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
	private boolean running = true;

	public ChatClient(String name, ChatServerIF chatServer) throws RemoteException {
		this.name=name;
		this.chatServer=chatServer;
		chatServer.registerChatClient(this);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void retrieveMessage(String message) throws RemoteException {
		System.out.println(message);
	}
	
	public void send(String message) throws RemoteException {		
		chatServer.broadcastMessage(name + ": " + message);
	}

	public void senIndiv(String name, String message) throws RemoteException{
		chatServer.sendIndividualMessage(name, message);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
