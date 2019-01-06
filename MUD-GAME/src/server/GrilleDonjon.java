package server;

import java.io.Serializable;

public class GrilleDonjon implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
		public Piece grille[][];
		
		//Cr�ation STATIQUE d'une grille (donjon) de 9 pi�ces
		//et d'une 10�me pi�ce � part (celle du boss final)
		
		public GrilleDonjon () {
			//pour chaque ligne de la grille
			for (int i=1; i<=3; i++) {
				//pour chaque colonne de la grille
				for (int j=1; j<=3; j++) {
					
					Bord n=null;
					Bord s= null;
					Bord o= null;
					Bord e = null;
					if (i==1) { n= new Mur(); s= new Porte(); }
					if (i==2) { n= new Porte(); s= new Porte(); }
					if (i==3) { n= new Porte(); s= new Mur(); }
					
					//Cr�ation de la pi�ce du boss du donjon (clo�son�e)
					//On la consid�re comme une 4�me ligne mais ne sera pas affich�e comme telle.
					if (i==4) { n=new Mur(); s=new Mur(); e=new Mur(); o=new Mur(); }
					
					if (j==1) { e= new Porte(); o=new Mur(); }
					if (j==2) { e = new Porte(); o=new Porte(); }
					if (j==3) { e= new Mur(); o= new Porte(); } 
					Piece current = new Piece(i*j, n,s,o,e);
					grille[i][j] = current;
				}
			}
				
		}
		
		public String afficherLabyrinthe() {
			String chaineReturn="";
			for (int i =1; i<=4; i++) {
				for (int j=1; j<=3; j++) {
					chaineReturn+="Pi�ce n�" + grille[i][j].getIdPiece() + "\n";
				}
			}
			return chaineReturn;
		}

}
