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
		
		// Initialisation des paramètres
		boolean tourcoupIA = true;
		int x = -1;
		int y = -1;
		Piece pieceABouger = null;
		
		// Fait jouer l'IA uniquement si la fin == false
		if(multipion.jeu.Jeu.fin==false) {
			
		while(tourcoupIA){

			int index;
			pieceABouger = null;
			do{
				// genere un index aléatoire correspondant à une piece 
				index = (int) (Math.random()*pieces.size());
				pieceABouger = pieces.get(index);
				
				// Si la pièce ne peut pas bouger le code ce rexecute 
			}while(pieceABouger.casesPossibles().isEmpty());

			// genere un nombre aléatoire correspondant au différents déplacement de la pièce sélectionner
			int j = (int) (Math.random()*pieceABouger.casesPossibles().size());
			
			// affecte à x et y les position choisit aléatoirement
			x = pieceABouger.casesPossibles().get(j).x;
			y = pieceABouger.casesPossibles().get(j).y;
			
			// Créer la nouvelle coordonnée 
			Coordonnee coordPieceABouger = new Coordonnee(pieceABouger.getX(), pieceABouger.getY());
			
			// Si l'on déplace la pièce à la nouvelle position
			if(pieceABouger.deplacer(x, y)){
				
				// le tour du coup de l'IA se termine et ferme la boucle while
				tourcoupIA = false;
				
			}else{
				MultiPion.addLog("Erreur dans la recherche de coup pour l'ia aleatoire", MultiPion.TypeLog.ERREUR);
			}
			
			// Garde le coup alors choisit et set les nouvelles coordonnées
			jeu.getPlateau().annulerDernierCoup(false);
			jeu.setPieceSelectionee(coordPieceABouger.x, coordPieceABouger.y);
			
			}
		// Affecte les nouvelles coordonnées à la pièce à bouger
		this.coordonneeAJouer = new Coordonnee(x,y);
		}
		
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return coordonneeAJouer;
	}

}
