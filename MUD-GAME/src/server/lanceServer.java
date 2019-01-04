package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class lanceServer {

  public static void main(String[] args) {
 		  try {
        LocateRegistry.createRegistry(1099);
    	  ServeurImpl informationImpl = new ServeurImpl();
    	  Naming.rebind("TestRMI", informationImpl);
    	  System.out.println("Serveur lancé");
  	  } 
    
      catch (Exception e) {
    	 e.printStackTrace();
  	  }
  }
}