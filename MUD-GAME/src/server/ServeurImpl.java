package server;

/* Cette classe correspond � l'objet distant. Elle doit 
donc impl�menter l'interface d�finie et contenir le code n�cessaire. */

/* Comme indiqu� dans l'interface, toutes les m�thodes distantes, 
mais aussi le constructeur de la classe, doivent indiquer qu'elles 
peuvent lever l'exception RemoteException. Ainsi, m�me si le constructeur 
ne contient pas de code il doit �tre red�fini pour inhiber la g�n�ration du 
constructeur par d�faut qui ne l�ve pas cette exception. */ 


/* Cette classe doit obligatoirement h�riter de la classe UnicastRemoteObject 
qui contient les diff�rents traitements �l�mentaires
pour un objet distant dont l'appel par le stub du client est unique. */
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServeurImpl extends UnicastRemoteObject implements ServeurInterface {

 	
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