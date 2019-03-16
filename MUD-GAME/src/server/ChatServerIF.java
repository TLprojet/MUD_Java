package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import client.ChatClientIF;

public interface ChatServerIF extends Remote{
	
	 /**
     * @see ChatServer#registerChatClient(ChatClientIF)
     */
	void registerChatClient(ChatClientIF chatClient) throws RemoteException;
	
	 /**
     * @see ChatServer#delClientFromChat(ChatClientIF)
     */
	void delClientFromChat(ChatClientIF chatClient) throws RemoteException;
	
	 /**
     * @see ChatServer#broadcastMessage(String)
     */
	void broadcastMessage(String message) throws RemoteException;
	
	 /**
     * @see ChatServer#getServerName()
     */
	String getServerName() throws RemoteException;
}
