package server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Grid implements Serializable {
		
	private static final long serialVersionUID = 1L;
			
		//Création STATIQUE d'une grille (donjon) de 9 pièces
		//et d'une 10ème pièce à part (celle du boss final)
		public static int taille = 4;
		Room grille[][];
		
		public Grid () {
			grille = new Room[taille][taille];
			//pour chaque ligne de la grille
			for (int i=0; i<=3; i++) {
				//pour chaque colonne de la grille
				for (int j=0; j<=3; j++) {
					Edge n=null;
					Edge s= null;
					Edge o= null;
					Edge e = null;
					if (i==0) { n= new Wall(); s= new Door(); }
					if (i==1) { n= new Door(); s= new Door(); }
					if (i==2) { n= new Door(); s= new Wall(); }
					
					//Création de la pièce du boss du donjon (cloîsonée)
					//On la considère comme une 4ème ligne mais ne sera pas affichée comme telle.
					if (i==3) {
						if (j==0) 
							n=new Wall(); s=new Wall(); e=new Wall(); o=new Wall(); 
					}
					
					if (j==0) { e= new Door(); o=new Wall(); }
					if (j==1) { e = new Door(); o=new Door(); }
					if (j==2) { e= new Wall(); o= new Door(); } 
					grille[i][j] = new Room((3*(i))+j+1, n,s,o,e);
				}
			}				
		}
		
		public String displayGrid(int currentRoom) {
			String chaine="|---------|---------|---------|\n";
			for (int i=0;i<=2;i++) {
				chaine+="|         |         |         |\n"
					   +"|";
				for (int j=0; j<=2; j++) {
						int p = this.grille[i][j].getIdPiece();
						if(p != currentRoom){
							chaine+="    "+ p + "    |";
						}
						else{
							chaine+= "   >" + p + "<   |";
						}
				}
				chaine+="\n"
					   +"|         |         |         |\n"
					   +"|---------|---------|---------|\n";
			}
			return chaine;
		}
		
		

}
