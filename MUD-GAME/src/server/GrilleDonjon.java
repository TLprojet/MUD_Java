package server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class GrilleDonjon implements Serializable {
		
	private static final long serialVersionUID = 1L;
			
		//Cr�ation STATIQUE d'une grille (donjon) de 9 pi�ces
		//et d'une 10�me pi�ce � part (celle du boss final)
		public static int taille = 4;
		Piece grille[][];
		
		public GrilleDonjon () {
			grille = new Piece[taille][taille];
			//pour chaque ligne de la grille
			for (int i=0; i<=3; i++) {
				//pour chaque colonne de la grille
				for (int j=0; j<=3; j++) {
					Bord n=null;
					Bord s= null;
					Bord o= null;
					Bord e = null;
					if (i==0) { n= new Mur(); s= new Porte(); }
					if (i==1) { n= new Porte(); s= new Porte(); }
					if (i==2) { n= new Porte(); s= new Mur(); }
					
					//Cr�ation de la pi�ce du boss du donjon (clo�son�e)
					//On la consid�re comme une 4�me ligne mais ne sera pas affich�e comme telle.
					if (i==3) {
						if (j==0) 
							n=new Mur(); s=new Mur(); e=new Mur(); o=new Mur(); 
					}
					
					if (j==0) { e= new Porte(); o=new Mur(); }
					if (j==1) { e = new Porte(); o=new Porte(); }
					if (j==2) { e= new Mur(); o= new Porte(); } 
					grille[i][j] = new Piece((3*(i))+j+1, n,s,o,e);
				}
			}				
		}
		
		public String afficherGrille() {
			String chaine="|---------|---------|---------|\n";
			for (int i=0;i<=2;i++) {
				chaine+="|         |         |         |\n"
					   +"|";
				for (int j=0; j<=2; j++) {
						//AFFICHAGE A FAIRE
						//ceci n'est qu'un test pour tester les fonctions
						//chaine+="Pi�ce n�"+this.grille[i][j].getIdPiece()+"\n";
						chaine+="    "+ this.grille[i][j].getIdPiece() + "    |";
				}
				chaine+="\n"
					   +"|         |         |         |\n"
					   +"|---------|---------|---------|\n";
			}
			return chaine;
		}
		
		

}
