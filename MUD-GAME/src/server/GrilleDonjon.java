package server;

import java.io.Serializable;

public class GrilleDonjon implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
		public Piece grille[][];
		
		//Création d'une grille (donjon) de 9 pièces où se trouveront dans chacune un monstre ou groupe de monstres
		//à affronter pour passer à la pièce suivante.
		//On commence en haut à gauche et on finit en bas à droite
		/*  - - - >
		 *  < - - -
		 *  - - - >
		*/
		
		public GrilleDonjon () {
			for (int i=1; i<3; i++) {
				for (int j=1; j<3; j++) {
				
					Bord n = null; Bord s= null; Bord e=null; Bord o=null;
					//Création d'une grille (donjon) de 9 pièces où se trouveront dans chacune un monstre
					//à affronter pour passer à la pièce suivante.
					//Le joueur pourra ausi tuer un autre joueur à la place d'un monstre pour passer à la pièce suivante.
					
					if (i==1 && j==1) { n=new Mur(); s=new Mur(); o=new Porte(); e=new Porte(); }
					if (i==1 && j==2) { n=new Mur(); s=new Mur(); o=new Porte(); e=new Porte(); }
					if (i==1 && j==3) { n=new Mur(); s=new Porte(); o=new Mur(); e=new Mur();   }
					if (i==2 && j==1) { n=new Mur(); s=new Porte(); o=new Mur(); e=new Porte(); }
					if (i==2 && j==2) { n=new Mur(); s=new Mur(); o=new Porte(); e=new Porte(); }
					if (i==2 && j==3) { n=new Porte(); s=new Mur(); o=new Porte(); e=new Mur(); }
					if (i==3 && j==1) { n=new Porte(); s=new Mur(); o=new Mur(); e=new Porte(); }
					if (i==3 && j==2) { n=new Mur(); s=new Mur(); o=new Porte(); e=new Porte(); }
					if (i==3 && j==3) { n=new Mur(); s=new Mur(); o=new Porte(); e=new Porte(); }

					grille[i][j]= new Piece(i*j,n,s,o,e);
				}
			}
		}
		
		public void afficherLabyrinthe() {	
			
		}

}
