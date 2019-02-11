package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GlobalServerIF extends Remote {
	
	public ArrayList<Player> getPlayers() throws RemoteException;
	public Player addPlayer(String name) throws RemoteException;
	public void delPlayer(String name, int room) throws RemoteException;
	public void logOut(int playerNum) throws RemoteException;
	public Player logIn(int playerNum) throws RemoteException;
	public int findByName(String name) throws RemoteException;
	public int move(int playerNum, int dir) throws RemoteException;
	public String displayGrid(int currentRoom) throws RemoteException;
	
}
