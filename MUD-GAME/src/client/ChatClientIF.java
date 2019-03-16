package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientIF extends Remote {
	
	 /**
     * @see ChatClient#retrieveMessage(String)
     */
	public void retrieveMessage(String message) throws RemoteException;
	
	 /**
     * @see ChatClient#getName()
     */
	public String getName() throws RemoteException;
}
