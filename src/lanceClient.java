package src;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import serveur.Information;

public class lanceClient {

  public static void main(String[] args) {
    System.out.println("Lancement du client");
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    try {
      Remote r = Naming.lookup("rmi://10.0.0.13/TestRMI");
      System.out.println(r);
      if (r instanceof Information) {
        String s = ((Information) r).getInformation();
        System.out.println("chaine renvoyee = " + s);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (NotBoundException e) {
      e.printStackTrace();
    }
    System.out.println("Fin du client");
  }
}