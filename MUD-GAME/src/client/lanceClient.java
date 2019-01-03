package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import server.Information;

public class lanceClient {

  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
    System.out.println("Lancement du client");
    Information i = (Information) Naming.lookup("rmi://localhost:1099/TestRMI");
    System.out.println("--- "+ i.getInformation());
    System.out.println("Fin du client");
  }
}