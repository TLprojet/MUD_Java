package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientIF extends Remote {
	public void retrieveMessage(String message) throws RemoteException;
	public String getName() throws RemoteException;
	public void senIndiv(String name, String message) throws RemoteException;
}
