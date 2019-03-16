package server;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GlobalServerIF extends Remote {
<<<<<<< HEAD
	public void setAccounts(ArrayList<Account> accounts) throws RemoteException;
	public ArrayList<Account> getAccounts() throws RemoteException;
	public int move(int pos, int dir, Player p) throws RemoteException;
=======
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
>>>>>>> refs/remotes/origin/master
	public String displayGrid(int currentRoom) throws RemoteException;
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException;
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException;
	public int findByName(String name) throws RemoteException;
	public void logOut(int accNum, int room) throws RemoteException;
	public Player logIn(int accNum) throws RemoteException;
	public Account addAccount(String name) throws RemoteException;
	public void delAccount(int num) throws RemoteException;
	public void die(int accNum, int room) throws RemoteException;
	
}
