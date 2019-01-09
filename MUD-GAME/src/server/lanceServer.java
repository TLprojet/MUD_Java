package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class lanceServer {

  public static void main(String[] args) throws RemoteException, MalformedURLException {
 		  
	  LocateRegistry.createRegistry(1099);
	  ServeurImpl informationImpl = new ServeurImpl("gameServer");
	  Naming.rebind(informationImpl.getServerName(), informationImpl);
   	  System.out.println("Serveur de jeu lanc�");
   	  
  }
}