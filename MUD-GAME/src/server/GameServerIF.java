package server;

import java.rmi.*;
import java.util.ArrayList;

/* L'interface doit contenir toutes les m�thodes qui seront 
susceptibles d'�tre appel�es � distance. */

/* La communication entre le client et le serveur lors de 
l'invocation de la m�thode distante peut �chouer pour 
diverses raisons telles qu'un crash du serveur, une rupture de la liaison, etc ...
Ainsi chaque m�thode appel�e � distance doit d�clarer qu'elle 
est en mesure de lever l'exception java.rmi.RemoteException. */


public interface GameServerIF extends Remote {

	// m�thodes susceptibles d'�tre appel�es � distante
   
   public String displayGrid(int currentRoom) throws RemoteException;
   public String getServerName() throws RemoteException;
   public Player addPlayer(String nom) throws RemoteException;
   public ArrayList<Player> getPlayers() throws RemoteException;
   public int findByName(String playerName) throws RemoteException;
   public Player logIn(int playerNum) throws RemoteException;
   public void logOut(int playerNum) throws RemoteException;
   public int move(int playerNum, char dir) throws RemoteException;
}