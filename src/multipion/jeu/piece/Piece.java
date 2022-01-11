package multipion.jeu.piece;

import java.util.ArrayList;

import multipion.jeu.Jeu;
import multipion.jeu.Plateau;
import multipion.sauvegarde.CoupPGN;
import multipion.utils.Coordonnee;

/**
 * Classe mere des differentes pieces
 */
public class Piece {
    /**
     * La famille de la piece
     */
    private String famille;
    
    /**
     * Reference au plateau
     */
    protected Plateau plateau;
    
    /**
     * La couleur de la piece
     */
    protected String couleur;
    
    /**
     * L'abscisse et l'ordonnee de la piece
     */
    protected int x,y;

    /**
     * Constructeur de Piece
     * @param x La position en abscisse
     * @param y La position en ordonnee
     * @param famille La famille de piece
     * @param couleur La couleur de la piece
     */
    public Piece(int x, int y, String famille, String couleur, Plateau plateau){
    	this.x = x;
    	this.y = y;
    	this.famille = famille;
    	this.couleur = couleur;
    	this.plateau = plateau;
    }

    /**
     * Retourne la famille de la piece
     * @return Famille de la piece
     */
    public String getFamille(){
        return famille;
    }
    
    /**
     * Donne la couleur de piece
     * @return La couleur de la piece
     */
    public String getCouleur(){
    	return couleur;
    }
    
    /**
     * Retourne la coordonnee X
     * @return x
     */
    public int getX(){
    	return this.x;
    }
    
    /**
     * Retourne la coordonnee Y
     * @return y
     */
    public int getY(){
    	return this.y;
    }
    
    /**
     * Setter x
     * @param x
     */
    public void setX(int x){
    	this.x = x;
    }
    
    /**
     * Setter x
     * @param y
     */
    public void setY(int y){
    	this.y = y;
    }
    
    /**
     * Setter pour x et y a la foi
     * @param x
     * @param y
     */
    public void setXY(int x, int y){
    	this.x = x;
    	this.y = y;
    }
    
    /**
     * Deplace la piece (change les coordonnees)
     * @param x Le x d'arrivee
     * @param y Le y d'arrivee
     */
    public boolean deplacer(int x,int y){
        if(coupPossible(x, y)){
            if(mouvementPossible(x,y)){
            	
            	//Coup
            	CoupPGN coup = new CoupPGN();
            	coup.departMemoire.x = this.x;
            	coup.departMemoire.y = this.y;
            	coup.arrivee.x = x;
            	coup.arrivee.y = y;
            	coup.setNomPiece(this.famille);
            	coup.referencePiece = this;
            	
            	//Prises
	            if(plateau.getCase(x, y) != null){
	            	if(plateau.getCase(x, y).getCouleur() != this.getCouleur()){
	            		this.plateau.getJeu().getPrises().add(plateau.getCase(x, y));
	            		coup.isPrise = true;
	            	}
	            }
	            
	            //on test son premier coup
	            if(this.famille.equals("PION")){
	            	Pion p = (Pion) this;
	            	p.setPremierCoup(false);
	            }
	            //Deplacement de la piece
	        	plateau.setCase(this.x, this.y, null);
	            this.x = x;
	            this.y = y;
	            plateau.setCase(this.x, this.y, this);
	            
	            //Test de fin de partie en allant au bout du plateau 
	            	Pion p = (Pion) this;
					if(p.BoutPlateau()){
						if(Jeu.test_minmax == false) {

							multipion.jeu.Jeu.fin=true;
							
							plateau.getJeu().getFenetre().Victoire(p.getCouleur(),"en allant bout du plateau.");
						}
					}
	            plateau.getJeu().getHistorique().addCoupPGN(coup);
	            return true;
            }
            else{

                return false;
            }
        }
        else{
            return false;
        }
        
    }
    
    /**
     * Recupere les coordonnees de toutes les cases ou peut aller la piece
     * @return ArrayList<Coordonnee> de tous les coups possibles
     */
    public ArrayList<Coordonnee> casesPossibles(){
    	return new ArrayList<Coordonnee>();
    }
    
    /**
     * Ne fais rien, fonction surcharger par les class heritee
     * @param x
     * @param y
     * @return
     */
    public boolean coupPossible(int x, int y){
        // JE FAIS RIEN !
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
    
    @Override
    public String toString(){
    	return famille+" "+couleur+" ("+x+","+y+")";
    }

}
