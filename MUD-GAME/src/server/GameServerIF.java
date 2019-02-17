package server;

import java.rmi.*;

/* L'interface doit contenir toutes les méthodes qui seront 
susceptibles d'être appelées à distance. */

/* La communication entre le client et le serveur lors de 
l'invocation de la méthode distante peut échouer pour 
diverses raisons telles qu'un crash du serveur, une rupture de la liaison, etc ...
Ainsi chaque méthode appelée à distance doit déclarer qu'elle 
est en mesure de lever l'exception java.rmi.RemoteException. */


public interface GameServerIF extends Remote {

	/**
     * @see GameServer#getServerName()
     */
   public String getServerName() throws RemoteException;

}