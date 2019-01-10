package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import client.ChatClientIF;

public class ChatServer extends UnicastRemoteObject implements ChatServerIF {
	private static final long serialVersionUID = -189170250683093063L;
	private ArrayList<ChatClientIF> chatClients;
	private String serverName;
	
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


	public synchronized void registerChatClient(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.add(chatClient);
	}
	
	public synchronized void delClientFromChat(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.remove(chatClient);
	}

	
	public synchronized void broadcastMessage(String message) throws RemoteException {
		int i=0;
		while (i<chatClients.size()) {
			chatClients.get(i++).retrieveMessage(message);
		}
	}

}
