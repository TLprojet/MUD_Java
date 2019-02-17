package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import client.ChatClientIF;

public class ChatServer extends UnicastRemoteObject implements ChatServerIF {
	private static final long serialVersionUID = -189170250683093063L;
	
	/**
     * Liste des chats Clients
     */
	private ArrayList<ChatClientIF> chatClients;
	
	/**
     * Nom du serveur de chat
     */
	private String serverName;
	
	/**
     * Constructeur d'un chatServer (serveur)
     * @param nomServeur
     *          Nom du serveur de chat
     * @throws RemoteException
     */
	protected ChatServer(String nomServeur) throws RemoteException {
		chatClients = new ArrayList<ChatClientIF>();
		this.serverName = nomServeur;
	}


	public String getServerName() {
		return serverName;
	}


	public void setServerName(String nomServeur) {
		this.serverName = nomServeur;
	}


	/**
     * Ajoute un client de chat dans la liste puis notifie les joueurs concernés
     * @param chatClient
     *          Interface du chatClient
     * @throws RemoteException
     */
	public synchronized void registerChatClient(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.add(chatClient);
		broadcastMessage("Le joueur " + chatClient.getName() + " est rentré dans la pièce " + this.getServerName() + ".");
	}
	
	/**
     * Supprime un client de chat dans la liste puis notifie les joueurs concernés
     * @param chatClient
     *          Interface du chatClient
     * @throws RemoteException
     */
	public synchronized void delClientFromChat(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.remove(chatClient);
		broadcastMessage("Le joueur "+ chatClient.getName() +" a quitté la pièce.\n");
	}

	/**
     * Affiche un message dans le tchat
     * @param message
     *          Message à afficher
     * @throws RemoteException
     */
	public synchronized void broadcastMessage(String message) throws RemoteException {
		int i=0;
		while (i<chatClients.size()) {
			chatClients.get(i++).retrieveMessage(message);
		}
	}

}
