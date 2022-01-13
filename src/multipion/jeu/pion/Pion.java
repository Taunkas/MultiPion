package multipion.jeu.pion;

import multipion.jeu.Plateau;
import multipion.utils.Coordonnee;

import java.util.ArrayList;

/**
 * Defini le comportement d'un pion
 */
public class Pion extends Piece {
	
	/**  
	 * Récupère la taille de la grille
	 */
	public int tailleplateau=multipion.MenuGraphisme.jeu.Grille.TailleGrille;
	
	
	
    /**
     * Verifie si le pion a deja joue
     */
    private boolean premierCoup = true;
    
    /**
     * Constructeur de Pion
     * @param x La position en abscisse
     * @param y La position en ordonnee
     * @param couleur La couleur de la piece
     */
    public Pion(int x, int y, String couleur, Plateau plateau){
    	super(x, y, "PION", couleur, plateau);
    }
    
    /**
     * Verifie que le coup est autorise pour la piece
     * @param x d'arrive
     * @param y d'arrive
     * @return Si le coup est autorisÃ© pour cette piece
     */
    public boolean coupPossible(int x, int y){
    	if(x < 0 || x > tailleplateau-1 || y < 0 || y > tailleplateau-1) return false;
  
    	if(this.plateau.getCase(x, y) != null){
    		if(this.couleur.equals("BLANC")){
    			
    			if(x == this.x+1 && y == this.y +1  && this.plateau.getCase(x,y).couleur.equals("NOIR")){
        			return true;
        		}
    			if(x == this.x-1 && y == this.y +1  && this.plateau.getCase(x,y).couleur.equals("NOIR")){
        			return true;
        		}
    		}
    		else{
    			if(x == this.x-1 && y == this.y -1  && this.plateau.getCase(x,y).couleur.equals("BLANC")){
        			return true;
        		}
    			if(x == this.x+1 && y == this.y-1  && this.plateau.getCase(x,y).couleur.equals("BLANC")){
        			return true;
        		}
    		}

    	}
    	if(this.plateau.getCase(x, y) == null){
	    	if(this.couleur.equals("BLANC")){
	    		//On détail les coup autorisé)
	    		if(x==this.x && y ==this.y+1){
	    			return true;
	    		}
	    	}
	    	else{
	    		if(x==this.x && y==this.y-1){
	    			return true;
	    		}
	    	}
	    	return false;
	    }
    	return false;
    }
    
    /**
     * Verifie si il n'y a pas d'obstacle au deplacement
     * @param x
     * @param y
     * @return vrai ou faux
     */
    public boolean mouvementPossible(int x, int y){
        return true;
    }
    /**
     * Recupere les coordonnees de toutes les cases ou peut aller le pion
     * @return ArrayList<Coordonnee> de tous les coups possibles
     */
    public ArrayList<Coordonnee> casesPossibles(){
    	ArrayList<Coordonnee> coords = new ArrayList<Coordonnee>();	
    			if(this.couleur.equals("BLANC")){
    				if(((x+1) >= 0 && (y+1) >= 0 && (x+1) < tailleplateau && (y+1) < tailleplateau) && (this.plateau.getCase(x+1,y+1) != null) && this.plateau.getCase(x+1,y+1).couleur.equals("NOIR")){
    					coords.add(new Coordonnee(x+1,y+1));
    				}
    				if(((x-1) >= 0 && (y+1) >= 0 && (x-1) < tailleplateau && (y+1) < tailleplateau) && (this.plateau.getCase(x-1, y+1) != null) && this.plateau.getCase(x-1,y+1).couleur.equals("NOIR")){
    					coords.add(new Coordonnee(x-1,y+1));
    				}
    				if(y <= 5 && premierCoup && plateau.getCase(x, y+1) == null && plateau.getCase(x, y+2) == null){
    					coords.add(new Coordonnee(x,y+1));

    				}
    				if(y < tailleplateau-1 && plateau.getCase(x, y+1) == null){
    					coords.add(new Coordonnee(x,y+1));
    				}
    			}
    			else{
    				if(((x+1) >= 0 && (y-1) >= 0 && (x+1) < tailleplateau && (y-1) < tailleplateau) && (this.plateau.getCase(x+1,y-1) != null) && this.plateau.getCase(x+1,y-1).couleur.equals("BLANC")){
    					coords.add(new Coordonnee(x+1,y-1));
    				}
    				if(((x-1) >= 0 && (y-1) >= 0 && (x-1) < tailleplateau && (y-1) < tailleplateau) && (this.plateau.getCase(x-1,y-1) != null) && this.plateau.getCase(x-1, y-1).couleur.equals("BLANC")){
    					coords.add(new Coordonnee(x-1,y-1));
    				}
    				if(y >= 2 && premierCoup && plateau.getCase(x, y-1) == null && plateau.getCase(x, y-2) == null){
    					coords.add(new Coordonnee(x,y-1));

    				}
    				if(y > 0 && plateau.getCase(x, y-1) == null){
    					coords.add(new Coordonnee(x,y-1));
    				}
    			}
    	return coords;
    }
    /**
     * Detecte si le pion peut allez au bout du plateau
     * @return
     */
    public boolean BoutPlateau(){
    	if(this.couleur.equals("BLANC") && this.y == tailleplateau-1){
    		return true;
    	}
    	if(this.couleur.equals("NOIR") && this.y == 0){
    		return true;
    	}
    	else return false;
    }
    
    
    /**
     * Si le Pion n'as pas encore bouge
     * @return le boolean
     */
    public boolean isPremierCoup(){
    	return this.premierCoup;
    }
    
    /**
     * Setter du premierCoup
     */
    public void setPremierCoup(boolean b){
    	this.premierCoup = b;
    }
}
    
