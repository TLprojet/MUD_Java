package database;

import java.sql.*;
import java.util.ArrayList;
import server.Player;

public class Sql {
    static Connection conn;

    // Connexion au serveur de BDD
    public void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/MUD?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
            String user = "root";
            String passwd = "1234";

            conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion à la base de données effectuée.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    // Récupération et remise sous forme de syntaxe Java des données des joueurs
    public static ArrayList<Player> getPlayersDB() {
        try {
            Statement state = conn.createStatement();

            String query = "SELECT * FROM Player";
            ResultSet result = state.executeQuery(query);
            
            // Reconstitution des données en syntaxe Java
            ArrayList<Player> players = new ArrayList<Player>();
            while(result.next()){
            	Player p = new Player((int)(result.getObject(2)),(int)(result.getObject(1)) - 1,(int)(result.getObject(3)),result.getObject(4).toString(),result.getObject(5).toString().chars().map(c -> c-'0').toArray(),(int)(result.getObject(6)));
                players.add(p);
            }
            return players;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
}
    
    // Insertion des joueurs dans la BDD
    public void insertPlayersDB(String datas) {
        try {
        	Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        	
        	// Insert values in the table
            String query = "INSERT INTO Player VALUES " + datas;
            state.executeUpdate(query);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    // Supression des données de la BDD
    public void clearPlayersDB() {
        try {
        	Statement state = conn.createStatement();
        	
        	// Delete all the rows in the table Player
        	state.executeUpdate("TRUNCATE Player");
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {

	}

}
