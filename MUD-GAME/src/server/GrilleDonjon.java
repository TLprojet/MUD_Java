package server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class GrilleDonjon implements Serializable {
		
	private static final long serialVersionUID = 1L;
			
		//Création STATIQUE d'une grille (donjon) de 9 pièces
		//et d'une 10ème pièce à part (celle du boss final)
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
					
					//Création de la pièce du boss du donjon (cloîsonée)
					//On la considère comme une 4ème ligne mais ne sera pas affichée comme telle.
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
			String chaine="";
			for (int i=0;i<=3;i++) {
				for (int j=0; j<=2; j++) {
					if (i<3 || (i==3 && j==0)) {
						//AFFICHAGE A FAIRE
						//ceci n'est qu'un test pour tester les fonctions
						chaine+="Pièce n°"+this.grille[i][j].getIdPiece()+"\n";
						
					}
				}
			}
			return chaine;
		}
		
		

}
