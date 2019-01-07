package server;

/* Cette classe correspond à l'objet distant. Elle doit 
donc implémenter l'interface définie et contenir le code nécessaire. */

/* Comme indiqué dans l'interface, toutes les méthodes distantes, 
mais aussi le constructeur de la classe, doivent indiquer qu'elles 
peuvent lever l'exception RemoteException. Ainsi, même si le constructeur 
ne contient pas de code il doit être redéfini pour inhiber la génération du 
constructeur par défaut qui ne lève pas cette exception. */ 


/* Cette classe doit obligatoirement hériter de la classe UnicastRemoteObject 
qui contient les différents traitements élémentaires
pour un objet distant dont l'appel par le stub du client est unique. */
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServeurImpl extends UnicastRemoteObject implements ServeurInterface {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServeurImpl() throws RemoteException {
    	super();
	}

	@Override
	public String afficherGrille() throws RemoteException {
		GrilleDonjon dj= new GrilleDonjon();
		return(dj.afficherGrille());
	}

	
}