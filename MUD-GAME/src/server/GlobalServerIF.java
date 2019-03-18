package server;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GlobalServerIF extends Remote {
	public void setAccounts(ArrayList<Account> accounts) throws RemoteException;
	public ArrayList<Account> getAccounts() throws RemoteException;
	public int move(int pos, int dir, Player p, int accNum) throws RemoteException;
	public String displayGrid(int currentRoom) throws RemoteException;
	public void addGameServer(String nom, GameServer serv) throws RemoteException, MalformedURLException;
	public void addChatServer(String nom, ChatServer serv) throws RemoteException, MalformedURLException;
	public int findByName(String name) throws RemoteException;
	public void logOut(int accNum, int room) throws RemoteException;
	public int logIn(int accNum) throws RemoteException;
	public Account addNewAccount(String name) throws RemoteException;
	public void delAccount(int num) throws RemoteException;
	public void die(int accNum, int room) throws RemoteException;
	public boolean canFight(int accNum, int room) throws RemoteException;
}
