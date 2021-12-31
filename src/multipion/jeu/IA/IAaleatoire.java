package multipion.jeu.IA;

import java.util.ArrayList;


import multipion.MultiPion;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.jeu.piece.Piece;
import multipion.utils.Coordonnee;

/**
 * IA qui joue de facon aleatoire
 */
public class IAaleatoire extends Joueur implements IA{
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Coordonnee d'arrivee de la piece a jouer
	 */
	private Coordonnee coordonneeAJouer;

	/**
	 * Constructeur de l'IA
	 * @param c la couleur de l'IA
	 * @param jeu reference du jeu
	 */
	public IAaleatoire(String couleur, Jeu jeu) {
		super(couleur);
		this.jeu = jeu;
		this.estHumain = false;
	}

	@Override
	public void jouer(){
		this.coordonneeAJouer = null;
		
		//Recupere toutes les pieces de la couleurs de l'ia
		ArrayList<Piece> pieces = (this.getCouleur().equals("BLANC"))? jeu.getPlateau().getPiecesBlanches() : jeu.getPlateau().getPiecesNoires();
		
		boolean echec = true;
		int x = -1;
		int y = -1;
		Piece pieceABouger = null;
		while(echec){

			int index;
			pieceABouger = null;
			do{

				index = (int) (Math.random()*pieces.size());
				pieceABouger = pieces.get(index);
				
				
			}while(pieceABouger.casesPossibles().isEmpty());

			int j = (int) (Math.random()*pieceABouger.casesPossibles().size());
			x = pieceABouger.casesPossibles().get(j).x;
			y = pieceABouger.casesPossibles().get(j).y;
			
			
			Coordonnee coordPieceABouger = new Coordonnee(pieceABouger.getX(), pieceABouger.getY());
			if(pieceABouger.deplacer(x, y)){
				
					echec = false;
				
			}else{
				MultiPion.addLog("Erreur dans la recherche de coup pour l'ia aleatoire", MultiPion.TypeLog.ERREUR);
			}
			jeu.getPlateau().annulerDernierCoup(false);
			jeu.setPieceSelectionee(coordPieceABouger.x, coordPieceABouger.y);
			
			if(pieceABouger.casesPossibles().isEmpty()==true){
				break;
			}
			
		}
		this.coordonneeAJouer = new Coordonnee(x,y);
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return coordonneeAJouer;
	}

}
