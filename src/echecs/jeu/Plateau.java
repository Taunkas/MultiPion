package echecs.jeu;

import java.util.ArrayList;

import echecs.jeu.piece.Cavalier;
import echecs.jeu.piece.Fou;
import echecs.jeu.piece.Piece;
import echecs.jeu.piece.Pion;
import echecs.jeu.piece.Reine;
import echecs.jeu.piece.Roi;
import echecs.jeu.piece.Tour;
import echecs.sauvegarde.CoupPGN;
import echecs.graphisme.jeu.GrilleJeu;
/**
 * Classe qui va creer le plateau du jeu, elle est compose d'un tableau de Pieces 
 */
public class Plateau {
	
	/**	
	 *  Un tableau de pieces.
	 */
	protected Piece[][] plateau;
	
	/**	
	 *  récupère la taille de la grille
	 */
	public int tailleplateau=echecs.graphisme.jeu.GrilleJeu.TailleGrille;
	
	/**
	 * reference du jeu
	 */
	protected Jeu jeu;
	
	/**
	 * Constructeur
	 * @param jeu reference du jeu
	 */
	public Plateau(Jeu jeu){
		this();
		this.jeu = jeu;
	}
	
	/**
	 * Constructeur
	 */
	public Plateau(){
		plateau = new Piece[tailleplateau][tailleplateau];
		//this.setRois();
	}
	
	/**
	 * Mise en place des pieces sur le plateau pour une partie d'echecs
	 */
	public void miseEnPlacePlateau(){
		
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				plateau[i][j] = null;
			}
		}
		
		
		for(char i = 0; i<=tailleplateau-1; i++) {
			setCase(i, 0, new Pion(i, 0,"BLANC", this));
			setCase(i, tailleplateau-1, new Pion(i, tailleplateau-1,"NOIR", this));
			
		}
			
	}
	
	/**
	 * Retourne a la case d'abscisse x et d'ordonnee y.
	 * @param x represente l'abscisse.
	 * @param y represente l'ordonnee.
	 * @return soit la Piece contenu dans la case x,y; soit une erreur dut aux coordonnees.
	 */
	public Piece getCase(int x, int y){
		if (x < 0 || y > tailleplateau-1){
			System.out.println("Erreur dans la coordonnee sur l'axe des abscisse : ("+x+","+y+")");
			return null;
		}
		if (y>tailleplateau-1 || y<0){
			System.out.println("Erreur dans la coordonnee sur l'axe des ordonnees : ("+x+","+y+")");
			return null;
		}
		return plateau[x][y];
	}
	
	/**
	 * Insert une Piece dans la case d'abscisse x et d'ordonnee y.
	 * @param x represente l'abscisse.
	 * @param y represente l'ordonnee.
	 * @param a est la Piece a mettre dans la case d'abscisse x et d'ordonnee y.
	 */
	public void setCase(int x, int y, Piece a){
		if (x < 0 || x > tailleplateau-1){
			System.out.println("Erreur dans la coordonnee sur l'axe des abscisse : ("+x+","+y+")"+" : "+a.toString());
		}
		if (y>tailleplateau-1 || y<0){
			System.out.println("Erreur dans la coordonnee sur l'axe des ordonnees : ("+x+","+y+") : "+a.toString());
		}
		this.plateau[x][y]=a;
		
	}

    /**
     * Regarde si une piece est presente dans une case
     * @param x Abscisse de la case
     * @param y Ordonnee de la case
     * @return Vrai si elle est vide
     */
    public boolean estVide(int x, int y){
        if(plateau[x][y] == null){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Getter jeu
     * @return reference du jeu
     */
    public Jeu getJeu(){
    	return jeu;
    }
    
    /**
     * Recupere une liste de toutes les pieces
     * @return une liste
     */
    public ArrayList<Piece> getPiece(){
    	ArrayList<Piece> p = new ArrayList<Piece>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.getCase(i, j) != null){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    /**
     * Recupere une list de l'ensemble des pieces blanches
     * @return la liste
     */
    public ArrayList<Piece> getPiecesBlanches(){
    	ArrayList<Piece> p = new ArrayList<Piece>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.plateau[i][j] != null && this.plateau[i][j].getCouleur().endsWith("BLANC")){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    /**
     * Recupere une list de l'ensemble des pieces noirs
     * @return la liste
     */
    public ArrayList<Piece> getPiecesNoires(){
    	ArrayList<Piece> p = new ArrayList<Piece>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.plateau[i][j] != null && this.plateau[i][j].getCouleur().endsWith("NOIR")){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    /**
     * Annule le dernier coup joue
     * @param changerDeJoueur apres avoir annuler le dernier coup, vrai pour switcher de joueur
     */
    public void annulerDernierCoup(boolean changerDeJoueur){
    	if(jeu.getHistorique().isEmpty()) return;
    	
    	CoupPGN coup = jeu.getHistorique().getDernierCoup();
    	Piece pieceDuCoup = plateau[coup.arrivee.x][coup.arrivee.y];
    	if(coup.nomPiece == ' ' && ((coup.departMemoire.y == 1 && pieceDuCoup.getCouleur().equals("BLANC")) || (coup.departMemoire.y == 6 && pieceDuCoup.getCouleur().equals("NOIR")))){
    		Pion pionDuCoup = (Pion)pieceDuCoup;
    		pionDuCoup.setPremierCoup(true);
    	}else if(coup.isPetitRoque){
    		int rangee = -1;
    		if(jeu.getJoueurCourant().getCouleur().equals("BLANC") && jeu.getHistorique().getList().size()%2 == 1){
    			rangee = 0;
    		}else if(jeu.getJoueurCourant().getCouleur().equals("NOIR") && jeu.getHistorique().getList().size()%2 == 0){
    			rangee = tailleplateau-1;
    		}else{
    			rangee = (jeu.getJoueurCourant().getCouleur().equals("BLANC"))? tailleplateau-1 : 0;
    		}
    		plateau[6][rangee] = null;
    		plateau[5][rangee] = null;

    		if(changerDeJoueur){
        		jeu.switchJoueur();
        	}
        	jeu.getHistorique().supprimeDernierCoup();
        	jeu.getFenetre().repaint();
        	return;
    	}else if(coup.isGrandRoque){
    		int rangee = -1;
    		if(jeu.getJoueurCourant().getCouleur().equals("BLANC") && jeu.getHistorique().getList().size()%2 == 1){
    			rangee = 0;
    		}else if(jeu.getJoueurCourant().getCouleur().equals("NOIR") && jeu.getHistorique().getList().size()%2 == 0){
    			rangee = tailleplateau-1;
    		}else{
    			rangee = (jeu.getJoueurCourant().getCouleur().equals("BLANC"))? tailleplateau-1 : 0;
    		}
    		plateau[2][rangee] = null;
    		plateau[3][rangee] = null;
    		if(changerDeJoueur){
        		jeu.switchJoueur();
        	}
        	jeu.getHistorique().supprimeDernierCoup();
        	jeu.getFenetre().repaint();
        	return;
    	}else if(coup.isTransformation){
    		Pion p = new Pion(coup.arrivee.x, coup.arrivee.y, pieceDuCoup.getCouleur(), this);
    		p.setPremierCoup(false);
    		pieceDuCoup = p;
    	}else if(coup.nomPiece == CoupPGN.ROI && coup.departMemoire.x == 4 && (coup.departMemoire.y == 0 && pieceDuCoup.getCouleur().equals("BLANC") || coup.departMemoire.y == tailleplateau-1 && pieceDuCoup.getCouleur().equals("NOIR"))){
    		boolean premierCoup = true;
    		for(int i = jeu.getHistorique().getList().size()-3; i >= 0; i-=2){
    			if(jeu.getHistorique().getList().get(i).nomPiece == CoupPGN.ROI){
    				premierCoup = false;
    			}
    		}
    		if(premierCoup){
    			Roi roiDuCoup = (Roi)pieceDuCoup;
    			roiDuCoup.setPremierCoup(true);
    		}
    	}else if(coup.nomPiece == CoupPGN.TOUR && 
    			(coup.departMemoire.x == 0 && (coup.departMemoire.y == 0 && pieceDuCoup.getCouleur().equals("BLANC") || coup.departMemoire.y == tailleplateau-1 && pieceDuCoup.getCouleur().equals("NOIR")))){
    		Tour tourDuCoup = (Tour) pieceDuCoup;
    		boolean premierCoup = true;
    		for(int i = jeu.getHistorique().getList().size()-3; i >= 0; i-=2){
    			if(jeu.getHistorique().getList().get(i).nomPiece == CoupPGN.TOUR){
    				Tour tourATest = (Tour) jeu.getHistorique().getList().get(i).referencePiece;
    				if(tourATest.isStartGauche()){
    					premierCoup = false;
    				}
    			}
    		}
    		
    		if(premierCoup){
    			tourDuCoup.setPremierCoup(true);
    		}
    	}else if(coup.nomPiece == CoupPGN.TOUR &&
    			(coup.departMemoire.x == tailleplateau-1 && (coup.departMemoire.y == 0 && pieceDuCoup.getCouleur().equals("BLANC") || coup.departMemoire.y == tailleplateau-1 && pieceDuCoup.getCouleur().equals("NOIR")))){
    		Tour tourDuCoup = (Tour) pieceDuCoup;
    		boolean premierCoup = true;
    		for(int i = jeu.getHistorique().getList().size()-3; i >= 0; i-=2){
    			if(jeu.getHistorique().getList().get(i).nomPiece == CoupPGN.TOUR){
    				Tour tourATest = (Tour) jeu.getHistorique().getList().get(i).referencePiece;
    				if(!tourATest.isStartGauche()){
    					premierCoup = false;
    				}
    			}
    		}
    		
    		if(premierCoup){
    			tourDuCoup.setPremierCoup(true);
    		}
    	}
    	
    	plateau[coup.departMemoire.x][coup.departMemoire.y] = pieceDuCoup;
    	if(coup.isPrise){
    		plateau[coup.arrivee.x][coup.arrivee.y] = jeu.getPrises().get(jeu.getPrises().size()-1);
    		jeu.getPrises().remove(jeu.getPrises().size()-1);
    	}else{
    		plateau[coup.arrivee.x][coup.arrivee.y] = null;
    	}
    	pieceDuCoup.setX(coup.departMemoire.x);
    	pieceDuCoup.setY(coup.departMemoire.y);
    	
    	if(changerDeJoueur){
    		jeu.switchJoueur();
    	}
    	jeu.getHistorique().supprimeDernierCoup();
    	if(jeu.getFenetre() != null){
    		jeu.getFenetre().repaint();
    	}
    }
    
    /**
     * Getter des pions de la couleur choisi
     * @param couleur couleur des pions
     * @return
     */
    public ArrayList<Pion> getPions(String couleur){
    	ArrayList<Piece> pieces = (couleur.equals("BLANC"))? this.getPiecesBlanches() : this.getPiecesNoires();
    	
    	ArrayList<Pion> pions = new ArrayList<Pion>();
    	
    	for(int i = 0; i < pieces.size(); i++){
    		if(pieces.get(i).getClass().equals(Pion.class)){
    			Pion p = (Pion)pieces.get(i);
    			pions.add(p);
    		}
    	}
    	
    	return pions;
    }
    
    
    /**
     * Affichage en String du plateau
     */
    public void affiche(){
    	System.out.println("    A B C D E F G H");
		for(int i = plateau.length-1; i >= 0; i--){
			System.out.print((i+1)+" | ");
			for(int j=0; j<plateau[i].length; j++){
				if(plateau[j][i]!= null){
					if(plateau[j][i].getCouleur().equals("NOIR")){
						if(plateau[j][i].getFamille().equals("REINE")){
							System.out.print("d ");
						}else{
							System.out.print(plateau[j][i].getFamille().toLowerCase().charAt(0)+" ");
						}
					}else{
						if(plateau[j][i].getFamille().equals("REINE")){
							System.out.print("D ");
						}else{
							System.out.print(plateau[j][i].getFamille().charAt(0)+" ");
						}
					}
				}
				else{
					System.out.print(". ");
				}
			}
			System.out.println();
		}
    	System.out.println("Tour du joueur : "+jeu.getJoueurCourant().getCouleur());
    }
}	
