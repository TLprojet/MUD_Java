package server;

import java.rmi.*;

/* L'interface doit contenir toutes les m�thodes qui seront 
susceptibles d'�tre appel�es � distance. */

/* La communication entre le client et le serveur lors de 
l'invocation de la m�thode distante peut �chouer pour 
diverses raisons telles qu'un crash du serveur, une rupture de la liaison, etc ...
Ainsi chaque m�thode appel�e � distance doit d�clarer qu'elle 
est en mesure de lever l'exception java.rmi.RemoteException. */


public interface Information extends Remote {

	// m�thodes susceptibles d'�tre appel�es � distante

   public String getInformation() throws RemoteException;

}