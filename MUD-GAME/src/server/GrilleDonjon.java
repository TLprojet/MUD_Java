package server;

import java.io.Serializable;

public class GrilleDonjon implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
		private Piece grille[][];
		
		//Cr�ation d'une grille (donjon) de 9 pi�ces o� se trouveront dans chacune un monstre ou groupe de monstres
		//� affronter pour passer � la pi�ce suivante.
		//On commence en haut � gauche et on finit en bas � droite
		/*  - - - >
		 *  < - - -
		 *  - - - >
		*/
		
		public GrilleDonjon () {
			for (int i=1; i<3; i++) {
				for (int j=1; j<3; j++) {
				
					Bord n = null; Bord s= null; Bord e=null; Bord o=null;
					//Cr�ation d'une grille (donjon) de 9 pi�ces o� se trouveront dans chacune un monstre
					//� affronter pour passer � la pi�ce suivante.
					//Le joueur pourra ausi tuer un autre joueur � la place d'un monstre pour passer � la pi�ce suivante.
					
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
		
		public String afficherLabyrinthe() {	
			
			return(""
					+ " _______ _______ _______ \r\n" + 
					"|       |       |       |\r\n" + 
					"|       |       |       |\r\n" + 
					"|_______|_______|_______| \r\n" + 
					"|       |       |       |\r\n" + 
					"|       |       |       |\r\n" + 
					"|_______|_______|_______| \r\n" + 
					"|       |       |       |\r\n" + 
					"|       |       |       |\r\n" + 
					"|_______|_______|_______|");
		}

}
