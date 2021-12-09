package multipion.jeu.IA;

import java.io.File;
import java.util.ArrayList;

import multipion.MultiPion;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.jeu.Plateau;
import multipion.jeu.piece.Pion;
import multipion.sauvegarde.CoupPGN;
import multipion.sauvegarde.Partie;
import multipion.utils.Coordonnee;

/**
 * IA qui joue comme les parties sauvegardees. Il prend la partie la plsu jouer exactement comme la partie en cours
 */
public class IAouverture extends Joueur implements IA{
	
	/**
	 * Liste de l'ensemble des fichiers PGN dans le dossier save
	 */
	private String[] fichiersPGN;
	
	/**
	 * Coordonnee que l'ia joue
	 */
	private Coordonnee coordonneeAJouer;
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Constructeur
	 * @param couleur couleur de l'ia
	 * @param jeu reference du jeu
	 */
	public IAouverture(String couleur, Jeu jeu){
		super(couleur);
		this.jeu = jeu;
		this.estHumain = false;
		recupererFichiersPGN();
	}
	
	/**
	 * Cherche les coordonnees depart/arrivee du coup PGN a jouer
	 * @param coup le coup PGN a jouer
	 */
	private void coordonneesCoupAJouer(CoupPGN coup){
		Joueur joueurCourant = jeu.getJoueurCourant();
		Plateau plateau = jeu.getPlateau();
		
		if(coup.isPetitRoque){
			int colonne = (joueurCourant.getCouleur().equals("BLANC"))? 0 : 7;
			coup.departMemoire.x = 4;
			coup.departMemoire.y = colonne;
			coup.arrivee.x = 6;
			coup.arrivee.y = colonne;
		}else if(coup.isGrandRoque){
			int colonne = (joueurCourant.getCouleur().equals("BLANC"))? 0 : 7;
			coup.departMemoire.x = 4;
			coup.departMemoire.y = colonne;
			coup.arrivee.x = 2;
			coup.arrivee.y = colonne;
		}else if(coup.nomPiece == ' '){
			ArrayList<Pion> pions = plateau.getPions(joueurCourant.getCouleur());
			for(Pion p: pions){
				if((plateau.getCase(coup.arrivee.x, coup.arrivee.y) == null || !plateau.getCase(coup.arrivee.x, coup.arrivee.y).getCouleur().equals(p.getCouleur())) 
						&& p.mouvementPossible(coup.arrivee.x, coup.arrivee.y) 
						&& p.coupPossible(coup.arrivee.x, coup.arrivee.y)){
					if(coup.depart.x != -1 && coup.depart.y != -1 && coup.depart.x == p.getX() && coup.depart.y == p.getY()
							|| coup.depart.x != -1 && coup.depart.x == p.getX()
							|| coup.depart.y != -1 && coup.depart.y == p.getY()
							|| coup.depart.x == -1 && coup.depart.y == -1){
						coup.departMemoire.set(p.getX(), p.getY());
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Recupere l'ensemble des nom de fichiers PGN du dossier save
	 */
	private void recupererFichiersPGN(){
		File dossier = new File(MultiPion.SAVE_PATH);
		String[] tmp = dossier.list();
		
		ArrayList<String> arrayTmp = new ArrayList<String>();
		for(int i = 0; i < tmp.length; i++){
			if(tmp[i].endsWith(".pgn")){
				arrayTmp.add(tmp[i].substring(0, tmp[i].length() -4));
			}
		}
		
		fichiersPGN = new String[arrayTmp.size()];
		for(int i = 0; i < fichiersPGN.length; i++){
			fichiersPGN[i] = arrayTmp.get(i);
		}
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return this.coordonneeAJouer;
	}
}

/**
 * Stocke un coup, avec son nombre d'occurence dans les fichiers PGN et le resultat de la partie lie
 */
class CoupEtResultat{
	
	/**
	 * Coup pgn
	 */
	public CoupPGN coup;
	
	/**
	 * Resultat de la partie du coup
	 */
	public int resultat;
	
	/**
	 * compteur du combre d'occurence du coup
	 */
	public int compteur;
	
	/**
	 * Variable qui evalue le resultat en fonction de la couleur de l'ia
	 */
	public static int signe;
	
	/**
	 * Constructeur
	 * @param coup coup PGN
	 * @param resultat resultat lie au coup PGN
	 */
	public CoupEtResultat(CoupPGN coup, String resultat){
		this.coup = coup;
		this.compteur = 1;
	}
	
	/**
	 * Compare l'objet a celui passer en parametre
	 * @param c l'objet a comparer
	 * @return vrai si c'est le meme coup
	 */
	public boolean compareTo(CoupEtResultat c){
		return this.coup.formatPGN().equals(c.coup.formatPGN()) && this.resultat >= c.resultat;
	}
	
	/**
	 * Representation en string
	 */
	public String toString(){
		return "Coup "+coup.formatPGN()+" ("+resultat+") compteur:"+compteur;
	}
	
	/**
	 * Recupere la variable qui evalue le resultat en fonction de la couleur de l'ia
	 * @param a
	 */
	public static void setSigne(int a){
		signe = a;
	}
}
