package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GlobalServerIF extends Remote {
	/**
     * @see GlobalServer#getPlayers()
     */
	public ArrayList<Player> getPlayers() throws RemoteException;
	
	/**
     * @see GlobalServer#addPlayer(String)
     */
	public Player addPlayer(String name) throws RemoteException;
	
	/**
     * @see GlobalServer#delPlayer(String, int)
     */
	public void delPlayer(String name, int room) throws RemoteException;
	
	/**
     * @see GlobalServer#logOut(int)
     */
	public void logOut(int playerNum) throws RemoteException;
	
	/**
     * @see GlobalServer#logIn(int)
     */
	public Player logIn(int playerNum) throws RemoteException;
	
	/**
     * @see GlobalServer#findByName(String)
     */
	public int findByName(String name) throws RemoteException;
	
	/**
     * @see GlobalServer#move(int, int)
     */
	public int move(int playerNum, int dir) throws RemoteException;
	
	/**
     * @see GlobalServer#displayGrid(int)
     */
	public String displayGrid(int currentRoom) throws RemoteException;
	
}
