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
import java.util.ArrayList;
import java.rmi.RemoteException;

public class GameServer extends UnicastRemoteObject implements GameServerIF {

 	
	private static final long serialVersionUID = 1L;
	private String serverName;
	private ArrayList<Player> players;
	private Grid dj = new Grid();
	
	protected GameServer(String serverName, ArrayList<Player> players) throws RemoteException {
    	super();
    	this.serverName = serverName;
		this.players = players;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String displayGrid(int currentRoom) throws RemoteException {
		return(dj.displayGrid(currentRoom));
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public Player addPlayer(String name){
		Player toAdd = new Player(8, name, 1);
		players.add(toAdd);
		System.out.println("Le joueur " + toAdd.getPlayerName() + " a cr�� un compte.");
		return toAdd;
	}
	
	public void delPlayer(String name, int room) {
		players.remove(players.indexOf(new Player(room, name, 1)));
		System.out.println("Le joueur " + name + " a �t� supprim� de la base.");
	}
	
	public void logOut(int playerNum) {
		Player curPlayer = players.get(playerNum);
		players.set(playerNum, new Player(curPlayer.getRoom(), curPlayer.getPlayerName(), 0));
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est d�connect�.");
	}
	
	public Player logIn(int playerNum) {
		Player curPlayer = players.get(playerNum);
		curPlayer.setStatus(1);
		players.set(playerNum, curPlayer);
		System.out.println("Le joueur " + curPlayer.getPlayerName() + " s'est connect�.");
		return curPlayer;
	}
	
	public int findByName(String name) {
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).getPlayerName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	//in : playerNum int, char dir (direction : ZQSD)
	//out: 1 if everything worked fine
	//     -1 can't go there (wall)
	public int move(int playerNum, char dir) throws RemoteException {
		Player curPlayer = players.get(playerNum);
		int newRoomNum;
		
		int pos = curPlayer.getRoom();
		int x = ((pos-1)/3);
		int y = ((pos-1)%3);
		if (dir == 'Q' || dir =='q') { //players wants to go to the room in the left
			if (dj.grille[x][y].getOuest() instanceof Door) {
				newRoomNum = 3*x+y;
				curPlayer.setRoom(newRoomNum);
			}
			else return -1;
		}
		
		if (dir == 'Z' || dir =='z') { //players wants to go to the room in the left
			if (dj.grille[x][y].getNord() instanceof Door) {
				newRoomNum = 3*(x-1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
			else return -1;
		}
		
		if (dir == 'S' || dir =='s') { //players wants to go to the room in the left
			if (dj.grille[x][y].getEst() instanceof Door) {
				newRoomNum = 3*x+y+2;
				curPlayer.setRoom(newRoomNum);
			}
			else return -1;
		}
		
		if (dir == 'D' || dir =='d') { //players wants to go to the room in the left
			if (dj.grille[x][y].getSud() instanceof Door) {
				newRoomNum = 3*(x+1)+y+1;
				curPlayer.setRoom(newRoomNum);
			}
			else return -1;
		}
		
		return 1;
	}
	
	
	
}