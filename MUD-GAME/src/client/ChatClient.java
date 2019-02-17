package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.ChatServerIF;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF, Runnable {

	private static final long serialVersionUID = 5557769733752232252L;
	
	 /**
     * Nom du chatClient
     */
	private String name;
	
	 /**
     * L'interface du ChatServer
     */
	private ChatServerIF chatServer;
	
	 /**
     * Booléen décrivant l'état du thread
     */
	private boolean running = true;

	/**
     * Constructeur d'un chatClient (client)
     * @param name
     *          Nom du chat
     * @param chatServer
     * 			L'interface du chatServer
     * @throws RemoteException
     */
	protected ChatClient(String name, ChatServerIF chatServer) throws RemoteException {
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

	
	/**
     * Réception d'un message
     * @param message
     *          Message à réceptionner
     * @throws RemoteException
     */
	public void retrieveMessage(String message) throws RemoteException {
		System.out.println(message);
	}
	
	/**
     * Envoi d'un message
     * @param message
     *          Message à envoyyer
     * @throws RemoteException
     */
	public void send(String message) throws RemoteException {		
		chatServer.broadcastMessage(name + ": " + message);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
