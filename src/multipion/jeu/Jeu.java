package multipion.jeu;

import multipion.graphisme.jeu.Fenetre;
import multipion.graphisme.jeu.GrilleJeu;
import multipion.jeu.IA.IAThread;
import multipion.jeu.IA.ValeursEvaluation;
import multipion.jeu.piece.Piece;
import multipion.jeu.piece.Pion;
import multipion.sauvegarde.CoupPGN;
import multipion.sauvegarde.Historique;
import multipion.sauvegarde.Partie;
import multipion.utils.Coordonnee;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Class qui gere les differentes instances d'une partie d'echecs
 */
public class Jeu{
	
	/**
	 * fin de jeux true si la partie est fini false sinon. Initalisé à false
	 */
	public static boolean fin = false;
	
	/**
	 * Plateau courant
	 */
	protected Plateau plateau;
	
	/**
	 * Liste des pieces prises
	 */
	protected ArrayList<Piece> prises;
	
	/**
	 * Variable vrai si le jeu est en mode joueur vs ia ou ia vs ia
	 */
	private boolean vsIA;
	
	/**
	
	/**
	 * Instance du Joueur blanc
	 */
	protected Joueur joueurBlanc;
	
	/**
	 * Instance du Joueur noir
	 */
	protected Joueur joueurNoir;
	
	/**
	 * Instance du Joueur courant pendant la partie
	 */
	protected Joueur joueurCourant;
	
	/**
	 * Thread pour l'ia
	 */
	private IAThread iaThread;
	
	/**
	 * Deuxieme thread d'ia si IAvsIA
	 */
	private IAThread iaThread2;
	
	/**
	 * Instance de la piece selectionnee par le joueur
	 */
	protected Piece pieceSelectionee;
	
	/**
	 * Liste de l'historique des coups
	 */
	protected Historique historique;
	
	/**
	 * Instance de la fenetre de jeu
	 */
	protected Fenetre fenetre;
	
	/**
	 * Constructeur
	 */
	public Jeu() {
		super();
		plateau = null;
		prises = null;
		vsIA = false;
		joueurBlanc = null;
		joueurNoir = null;
		joueurCourant = null;
		iaThread = null;
		iaThread2 = null;
		pieceSelectionee = null;
		historique = null;
		fenetre = null;

	}
	
	/**
	 * Constructeur
	 * @param fenetre reference de la fenetre
	 */
	public Jeu(Fenetre fenetre){
		super();
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Piece>();
		joueurBlanc = new Joueur("BLANC");
		joueurNoir = new Joueur("NOIR");
		joueurCourant = joueurBlanc;
		historique = new Historique();
		this.fenetre = fenetre;
		vsIA = false;
		plateau.miseEnPlacePlateau();
		fin=false;
	}
	
	

	/**
	 * Constructeur pour une partie contre l'ordinateur
	 * @param fenetre reference de la fenetre
	 * @param humainEstBlanc couleur du joueur humain
	 * @param lvlia niveau de l'ia
	 */
	public Jeu(Fenetre fenetre, boolean humainEstBlanc, int lvlia){
		super();
		if(humainEstBlanc){
			joueurBlanc = new Joueur("BLANC");
			joueurNoir = new Joueur("NOIR");
			joueurNoir.setHumain(false);
			iaThread = new IAThread(lvlia, "NOIR", this, new ValeursEvaluation());
		}else{
			joueurBlanc = new Joueur("BLANC");
			joueurBlanc.setHumain(false);
			joueurNoir = new Joueur("NOIR");
			iaThread = new IAThread(lvlia, "BLANC", this, new ValeursEvaluation());
		}
		iaThread.pause(true);
		iaThread.start();
		joueurCourant = joueurBlanc;
		historique = new Historique();
		this.fenetre = fenetre;
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Piece>();
		vsIA = true;
		plateau.miseEnPlacePlateau();
		fin=false;
	}
	
	/**
	 * Constructeur d'une partie ordinateur contre ordinateur
	 * @param fenetre reference de la fenetre
	 * @param niveauBlanc niveau du joueur blanc
	 * @param niveauNoir niveau du joueur noir
	 * @param valeursBlanc valeurs des constantes du joueur blanc si niveau 3, sinon null
	 * @param valeursNoir valeurs des constantes du joueur noir si niveau 3, sinon null
	 */
	public Jeu(Fenetre fenetre, int niveauBlanc, int niveauNoir, ValeursEvaluation valeursBlanc, ValeursEvaluation valeursNoir){
		super();
		joueurBlanc = new Joueur("BLANC");
		joueurBlanc.setHumain(false);
		joueurNoir = new Joueur("NOIR");
		joueurNoir.setHumain(false);
		
		iaThread = new IAThread(niveauBlanc, "BLANC", this, valeursBlanc);
		iaThread2 = new IAThread(niveauNoir, "NOIR", this, valeursNoir);
		
		iaThread.pause(true);
		iaThread2.pause(true);
		iaThread.start();
		iaThread2.start();
		
		joueurCourant = joueurBlanc;
		//historique = new Historique();
		this.fenetre = fenetre;
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Piece>();
		this.vsIA = true;
		plateau.miseEnPlacePlateau();
		fin=false;
	}

	/**
	 * Demarre une partie si le joueur blanc est un ordinateur
	 */
	public void demarrerPartieIA(){
		if(!joueurCourant.estHumain){
			iaThread.pause(false);
			fenetre.tourIA(true);
		}
	}
	
	/**
	 * Joue le coup de l'ia apres que celui ci est reflechi a son coup a jouer
	 * @param coord coordonnee d'arrivee du coup a jouer
	 */
	public void jouerIA(Coordonnee coord){
		fenetre.tourIA(false);
		if(coord != null){
			this.deplacerPiece(coord.x, coord.y);
		}else{
			fenetre.repaint();
		}
	}
	
	/**
	 * Regarde si le joueur est bloqué ou non pour égalité
	 * @param p piece deplacer
	 */
	
	public void testBloque(Piece p) {
		int tailleplateau=multipion.graphisme.jeu.GrilleJeu.TailleGrille;
		int tmp =0;
		String coul = (p.getCouleur()=="BLANC") ? "NOIR" : "BLANC";
			
			ArrayList<Pion> pions = plateau.getPions(coul);
			System.out.print(coul);
			for(Pion a : pions){
				for(int u = 0; u<tailleplateau; u++) {
					for(int v = 0; v<tailleplateau; v++) {
					if(a.coupPossible(u, v)==true){
						tmp++;
					}else {	
						}	
					}
				}
		}
			if(tmp==0) {
				fin=true;
				plateau.getJeu().getFenetre().Victoire(p.getCouleur(),"en bloquant votre adversaire.");
			}
		}

		
		
	
	/**
	 * Designe comme piece selectionnee
	 * @param x coordonnee en abscisse de la futur piece selectionnee
	 * @param y coordonnee en ordonnee de la futur piece selectionnee
	 */
	public void setPieceSelectionee(int x, int y){
		pieceSelectionee = plateau.getCase(x, y);
		if(joueurCourant.estHumain){
			fenetre.getGrille().ajouterDeplacementPossible(pieceSelectionee.casesPossibles());
		}
		
		if(fenetre != null){
			fenetre.repaint();
		}
	}
	
	/**
	 * Renvoi le test boolean de si la piece selectionnee est a null
	 * @return le resultat du test
	 */
	public boolean aucunePieceSelectionee(){
		return pieceSelectionee == null;
	}
	
	/**
	 * Deplace la piece selectionnee sur la coordonnee (x,y)
	 * @param x la coordonnee en abscisse 
	 * @param y la coordonnee en ordonee
	 */
	public void deplacerPiece(int x, int y){
		boolean deplacementSucces = false;
		
		int valeurPrerequis = recherchePrerequis(pieceSelectionee, x, y);
		
		//Deplace la piece selectionne
		if(pieceSelectionee.deplacer(x, y)){
			CoupPGN coup = historique.getDernierCoup();
			coup.setPrerequis(valeurPrerequis);
			switchJoueur();
			deplacementSucces = true;
		}
		
		pieceSelectionee = null;
		fenetre.getGrille().updateGrille();
		fenetre.getGrille().resetEtatCases();
		if(deplacementSucces){
			CoupPGN coup = historique.getDernierCoup();
			fenetre.getGrille().setCaseDernierCoup(coup.departMemoire.x, coup.departMemoire.y);
			fenetre.getGrille().setCaseDernierCoup(coup.arrivee.x, coup.arrivee.y);
		}
		fenetre.repaint();
		
		if(!joueurBlanc.estHumain() && !joueurNoir.estHumain() && !iaThread.isReflechi() && !iaThread2.isReflechi()){
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Si c'est le tour de l'ia
		if(!joueurCourant.estHumain()){
			if(joueurCourant.getCouleur().equals(iaThread.getCouleur())){
				fenetre.tourIA(true);
				iaThread.pause(false);
			}else{
				fenetre.tourIA(true);
				iaThread2.pause(false);
			}
		}
	}
	
	/**
	 * Recherche les prerequis pour un coups
	 * @param p piece sur laquelle il faut rechercher les prerequis
	 * @param x deplacement de la piece en x
	 * @param y deplacement de la piece en y
	 * @return un code de la valeur de prerequis
	 */
	public int recherchePrerequis(Piece p, int x, int y){
		int valeur = 1;

		if(p.getClass().equals(Pion.class)){
			ArrayList<Pion> pions = plateau.getPions(p.getCouleur());
			for(Pion a : pions){
				if(a != p){
					if(a.coupPossible(x, y) && a.mouvementPossible(x, y)){
						if(a.getX() == p.getX()){
							valeur += 10;
						}
						if(a.getY() == p.getY()){
							valeur  += 100;
						}else{
							if(valeur %100 < 90) valeur += 10;
						}
					}
				}
			}
		}
		
		return valeur;
	}
	
	/**
	 * Remet une partie a zero
	 */
	public void reset(){
		if(vsIA){
			this.fermerThreadIA();
			if(!joueurBlanc.estHumain() && !joueurNoir.estHumain()){
				iaThread = new IAThread(iaThread.getNiveau(), "BLANC", this, iaThread.getValeurs());
				iaThread2 = new IAThread(iaThread2.getNiveau(), "NOIR", this, iaThread2.getValeurs());
				iaThread.pause(true);
				iaThread2.pause(true);
				iaThread.start();
				iaThread2.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurBlanc.setHumain(false);
				joueurNoir.setHumain(false);
			}else if(!joueurBlanc.estHumain()){
				iaThread = new IAThread(iaThread.getNiveau(), "BLANC", this, iaThread.getValeurs());
				iaThread.pause(true);
				iaThread.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurBlanc.setHumain(false);
			}else{
				iaThread = new IAThread(iaThread.getNiveau(), "NOIR", this, iaThread.getValeurs());
				iaThread.pause(true);
				iaThread.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurNoir.setHumain(false);
			}
		}else{
			joueurBlanc = new Joueur("BLANC");
			joueurNoir = new Joueur("NOIR");
		}
		
		joueurCourant = joueurBlanc;
        plateau = new Plateau(this);
        prises = new ArrayList<Piece>();
		historique = new Historique();
		pieceSelectionee = null;
		
		plateau.miseEnPlacePlateau();
		
		fenetre.clearLogsPartie();
		fenetre.repaint();
		
		this.demarrerPartieIA();
	}
	
	/**
	 * Change le joueur courant (Si c'etait le joueur blanc, joueurCourant devient le joueur noir) egalement verifie si bloquer
	 */
	public void switchJoueur(){
		testBloque(pieceSelectionee);
		joueurCourant = (joueurCourant == joueurBlanc)? joueurNoir : joueurBlanc;
		
	}
	
	/**
	 * Getter pour historique
	 * @return la reference de l'historique de Coup
	 */
	public Historique getHistorique(){
		return historique;
	}
	
	/**
	 * Affiche les prises dans la console
	 */
	public void afficherPrises(){
		System.out.print("Piece Prises : [");
		for(int i=0; i<prises.size(); i++){
			System.out.print(prises.get(i).getFamille() + ""+ prises.get(i).getCouleur()+", ");
		}
		System.out.println("]");
	}
	
	/**
	 * Getter pour le joueurCourant
	 * @return la reference du joueurCourant
	 */
	public Joueur getJoueurCourant(){
		return joueurCourant;
	}
	
	/**
	 * Getter pour le joueur blanc
	 * @return la reference du joueurBlanc
	 */
	public Joueur getJoueurBlanc(){
		return joueurBlanc;
	}
	
	/**
	 * Getter pour le joueur noir
	 * @return la reference du joueurNoir
	 */
	public Joueur getJoueurNoir(){
		return joueurNoir;
	}
	
	/**
	 * Setter pour joueurCourant
	 * @param j joueurCourant
	 */
	public void setJoueurCourant(Joueur j){
		this.joueurCourant = j;
	}
	
	/**
	 * Getter pour la fenetre lie au jeu
	 * @return fenetre
	 */
	public Fenetre getFenetre(){
		return this.fenetre;
	}
	
	/**
	 * Getter du plateau
	 * @return Plateau
	 */
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	/**
	 * Getter des pieces prises
	 * @return ArrayList<Piece>
	 */
	public ArrayList<Piece> getPrises(){
		return this.prises;
	}
	
	/**
	 * Test si le jeu est en mode vs IA
	 * @return true si contre IA
	 */
	public boolean isVsIA(){
		return this.vsIA;
	}
	
	/**
	 * Getter du thread traitant les ia
	 * @return IAThread
	 */
	public IAThread getIAThread(){
		return this.iaThread;
	}
	
	/**
	 * Getter du thread 2 pour la deuxieme ia si partie de type ia vs ia
	 * @return
	 */
	public IAThread getIAThread2(){
		return this.iaThread2;
	}
	
	
	/**
	 * Stop les thread des IA
	 */
	public void fermerThreadIA(){
		if(iaThread != null && iaThread.isAlive()){
			if(iaThread.isReflechi()){
				iaThread.stop();
			}
			iaThread.setVivant(false);
		}
		
		if(iaThread2 != null && iaThread2.isAlive()){
			if(iaThread2.isReflechi()){
				iaThread2.stop();
			}
			iaThread2.setVivant(false);
		}
	}
}
