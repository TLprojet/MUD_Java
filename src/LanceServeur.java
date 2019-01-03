package src;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class LanceServeur {

  public static void main(String[] args) {
 		  try {
        LocateRegistry.createRegistry(1099);
    	  InformationImpl informationImpl = new InformationImpl();
    	  Naming.rebind("MonServeur", informationImpl);
    	  System.out.println("Serveur lancé");
  	  } 
    
      catch (Exception e) {
    	 e.printStackTrace();
  	  }
  }
}