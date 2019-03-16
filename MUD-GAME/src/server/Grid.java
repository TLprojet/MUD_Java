package server;

import java.io.Serializable;

public class Grid implements Serializable {
		
	private static final long serialVersionUID = 1L;
			
		//Cr�ation STATIQUE d'une grille (donjon) de 9 pi�ces
		//et d'une 10�me pi�ce � part (celle du boss final)
		public static int taille = 4;
		Room grille[][];
		
		
		/**
	     * Construction d'une grille
	     */
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
					
					//Cr�ation de la pi�ce du boss du donjon (clo�son�e)
					//On la consid�re comme une 4�me ligne mais ne sera pas affich�e comme telle.
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
		
		
		/**
	     * Affichage de la grille en fonction de la position du joueur
	     * @param currentRoom
	     *          La pi�ce o� le joueur se trouve actuellement
	     * @return
	     */
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
