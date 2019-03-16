package server;

import java.rmi.*;

/* L'interface doit contenir toutes les m�thodes qui seront 
susceptibles d'�tre appel�es � distance. */

/* La communication entre le client et le serveur lors de 
l'invocation de la m�thode distante peut �chouer pour 
diverses raisons telles qu'un crash du serveur, une rupture de la liaison, etc ...
Ainsi chaque m�thode appel�e � distance doit d�clarer qu'elle 
est en mesure de lever l'exception java.rmi.RemoteException. */


public interface GameServerIF extends Remote {

	/**
     * @see GameServer#getServerName()
     */
   public String getServerName() throws RemoteException;
   public int getServerNum() throws RemoteException;
   public void addPlayer(Player p) throws RemoteException;
   public void delPlayer(Player p) throws RemoteException;
   public Player getPlayerById(int id) throws RemoteException;
   public boolean attack(int playerNum) throws RemoteException;
   public int getPlayerNumById(int id) throws RemoteException;
}