package server;

import java.rmi.*;
import java.util.ArrayList;

/* L'interface doit contenir toutes les méthodes qui seront 
susceptibles d'être appelées à distance. */

/* La communication entre le client et le serveur lors de 
l'invocation de la méthode distante peut échouer pour 
diverses raisons telles qu'un crash du serveur, une rupture de la liaison, etc ...
Ainsi chaque méthode appelée à distance doit déclarer qu'elle 
est en mesure de lever l'exception java.rmi.RemoteException. */


public interface GameServerIF extends Remote {

	// méthodes susceptibles d'être appelées à distante
   
   public String displayGrid(int currentRoom) throws RemoteException;
   public String getServerName() throws RemoteException;
   public Player addPlayer(String nom) throws RemoteException;
   public ArrayList<Player> getPlayers() throws RemoteException;
   public int findByName(String playerName) throws RemoteException;
   public Player logIn(int playerNum) throws RemoteException;
   public void logOut(int playerNum) throws RemoteException;
   public int move(int playerNum, int dir) throws RemoteException;
}