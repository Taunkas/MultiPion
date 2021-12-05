package echecs.graphisme.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import echecs.Echecs;
import echecs.graphisme.Case;
import echecs.jeu.Jeu;
import echecs.jeu.JeuClient;
import echecs.reseau.client.JoueurClient;
import echecs.jeu.IA.ValeursEvaluation;
import echecs.sauvegarde.Partie;
import echecs.graphisme.Menu;

public class Fenetre extends JFrame implements ActionListener{
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Reference de la grille
	 */
	private GrilleJeu grille;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	
	/**
	 * Reference de l'affichage des pieces prises blanches
	 */
	private PiecesPrisesJeu prisesBlanches;
	
	/**
	 * Reference de l'affichage des pieces prises noires
	 */
	private PiecesPrisesJeu prisesNoires;
	
	/**
	 * Zone de text pour l'affichage d'informations sur la partie
	 */
	private JTextArea logsPartie;
	
	/**
	 * Affichage du joueur courant
	 */
	private JoueurCourant joueurCourant;
	
	/**
	 * Affichage des coordonnees abscisses du plateau
	 */
	private JPanel coordAbscisse;
	
	/**
	 * Affichage d'un effet de mise en attente pendant le tour de l'IA
	 */
	private JPanel tourIA;
	
	/**
	 * label chrono blanc
	 */
	private JLabel chronob;
	
	/**
	 * Label chrono noir
	 */
	private JLabel chronon;
	
	/**
	 * Affichage des coordonnees ordonnees du plateau
	 */
	private JPanel coordOrdonnee;
	
	/**
	 * Vrai si le jeu est en mode en ligne
	 */
	private boolean fenInternet = false;
	
	/**
	 * Vrai si le jeu est en mode blitz
	 */
	private boolean blitz;
	
	/**
	 * Champs de texte pour le chat
	 */
	private JTextField chat;
	
	/**
	 * Label du champs de text du chat
	 */
	private JLabel chat_text;
	
	/**
	 * Taille de la grille choix de 3 à 10 
	 */	
	public static int taillegrille=echecs.graphisme.Menu.taillegrille;
	
	/**
	 * Lettre de A à J  
	 */	
	public char lettre='A';

	/**
	 * Constructeur
	 * @param x position x de la fenetre
	 * @param y position y de la fenetre
	 */
	public Fenetre(int x, int y){
		super("Jeu MultiPion");
		jeu = new Jeu(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Constructeur
	 * @param x position x de la fenetre
	 * @param y position y de la fenetre
	 * @param couleur vrai si, l'ia est le joueur noir
	 * @param lvlia niveau de l'ia
	 */
	public Fenetre(int x, int y, boolean couleur, int lvlia){
		super("Jeu Multipion");
		jeu = new Jeu(this, couleur, lvlia);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Constructeur
	 * @param x position x de la fenetre
	 * @param y position y de la fenetre
	 * @param niveauBlanc niveau de l'ia blanc
	 * @param niveauNoir niveau de l'ia noir
	 * @param valeursBlanc valeurs des constantes de l'ia blanc, null si pas de niveau 3
	 * @param valeursNoir valeurs des constantes de l'ia noir, null si pas de niveau 3
	 */
	public Fenetre(int x, int y, int niveauBlanc, int niveauNoir, ValeursEvaluation valeursBlanc, ValeursEvaluation valeursNoir){
		super("Jeu d'echecs");
		jeu = new Jeu(this, niveauBlanc, niveauNoir, valeursBlanc, valeursNoir);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Constructeur
	 * @param client reference du client
	 */
	public Fenetre(JoueurClient client){
		super("Jeu MultiPion");
		fenInternet = true;
		jeu = new JeuClient(this, client);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 13);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Constructeur
	 * @param x position en x de la fenetre
	 * @param y position en y de la fenetre
	 * @param blitz vrai si mode blitz
	 */
	public Fenetre(int x, int y, boolean blitz){
		super("Jeu MultiPion");
		this.blitz = blitz;
		System.out.println(this.blitz);
		jeu = new Jeu(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Constructeur
	 * @param x position x de la fenetre
	 * @param y position y de la fenetre
	 * @param blitz vrai si mode blitz
	 * @param m temps en minute du chrono
	 * @param s temps en seconde du chrono
	 */
	public Fenetre(int x, int y, boolean blitz, int m, int s){
		super("Jeu MultiPion");
		this.blitz = blitz;
		System.out.println(this.blitz);
		jeu = new Jeu(this, m, s);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(Echecs.ICON);
	}
	
	/**
	 * Initialise les variables et positionne les elements sur la fenetre
	 */
	private void initFenetre(){
		
		//Creation de tous les conteneurs
		//Conteneur general de la fenetre
		conteneurGeneral = new JPanel();
		conteneurGeneral.setPreferredSize(this.getPreferredSize());
		
		//Affichage des chronos du mode blitz
		chronon = new JLabel("En attente");
		chronon.setFont(chronon.getFont().deriveFont(20.f));
		chronob = new JLabel("En attente");
		chronob.setFont(chronob.getFont().deriveFont(20.f));
		if(!blitz){
			chronon.setVisible(false);
			chronob.setVisible(false);
		}
		
		//Affichage du joueur courant
		joueurCourant = new JoueurCourant(this);
		joueurCourant.setPreferredSize(new Dimension(Case.CASE_LENGTH * 4, Case.CASE_LENGTH));
		
		//Grille du plateau de jeu
		grille = new GrilleJeu(this);
		grille.setPreferredSize(new Dimension(Case.CASE_LENGTH * 8, Case.CASE_LENGTH * 8));
		
		//Initialisation des conteneur de pieces prises.
		//Prises blanches
		prisesBlanches = new PiecesPrisesJeu(jeu, true);
		prisesBlanches.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		JPanel blancPriseConteneur = new JPanel();
		blancPriseConteneur.add(prisesBlanches);
		blancPriseConteneur.setBorder(BorderFactory.createTitledBorder("Prises blanches"));
		blancPriseConteneur.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		
		//Prises noires
		prisesNoires = new PiecesPrisesJeu(jeu, false);
		prisesNoires.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		JPanel noirPriseConteneur = new JPanel();
		noirPriseConteneur.add(prisesNoires);
		noirPriseConteneur.setBorder(BorderFactory.createTitledBorder("Prises noires"));
		noirPriseConteneur.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		
		//Logs de la partie
		logsPartie = new JTextArea();
		logsPartie.setEditable(false);
		logsPartie.setLineWrap(true);
		logsPartie.setMargin(new Insets(5,5,5,5));
		JScrollPane scrollZone = new JScrollPane(logsPartie);
		scrollZone.setPreferredSize(new Dimension(Case.CASE_LENGTH * 8, Case.CASE_LENGTH * 1));
		scrollZone.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		//Chat
		if(fenInternet){
			chat_text = new JLabel("Dire :");
			System.out.println("youpi");
			chat = new JTextField();
			chat.setEditable(true);
			chat.setMargin(new Insets(5,5,5,5));
			chat.setPreferredSize(new Dimension(Case.CASE_LENGTH * 8, 30));
			chat.addActionListener(this);
		}

		//Coords
		coordAbscisse = new JPanel();
		coordAbscisse.setLayout(new GridLayout(1, taillegrille));
		
		if(taillegrille==3) {
			lettre='C';
		} else if(taillegrille==4) {
			lettre='D';
		} else if(taillegrille==5) {
			lettre='E';
		} else if(taillegrille==6) {
			lettre='F';
		} else if(taillegrille==7) {
			lettre='G';
		} else if(taillegrille==8) {
			lettre='H';
		} else if(taillegrille==9) {
			lettre='I';
		} else if(taillegrille==10) {
			lettre='J';
		} 
	
		for(char i = 'A'; i <= lettre; i++){
			JLabel c = new JLabel(i+"", JLabel.CENTER);
			c.setPreferredSize(new Dimension(Case.CASE_LENGTH/taillegrille*8 , 9));
			coordAbscisse.add(c);
		}
		coordOrdonnee = new JPanel();
		coordOrdonnee.setLayout(new GridLayout(taillegrille, 1));
		for(int i = taillegrille; i >= 1; i--){
			JLabel c = new JLabel(i+"");
			c.setPreferredSize(new Dimension(9, Case.CASE_LENGTH/taillegrille*8 ));
			coordOrdonnee.add(c);
		}
		
		//Tour de l'ia
		tourIA = new JPanel(){
			public void paintComponent(Graphics g){
				try{
					Image img = ImageIO.read(getClass().getResource(Echecs.RES_PATH+"sablier.png"));
					g.drawImage(img, 0, 0, this);
				}catch (Exception e){
						e.printStackTrace();
				}
			}
		};
		try{
			Image img = ImageIO.read(getClass().getResource(Echecs.RES_PATH+"sablier.png"));
			tourIA.setSize(img.getWidth(null), img.getHeight(null));
			tourIA.setBounds(this.getWidth()/2 - tourIA.getWidth()/2, this.getHeight()/2 - tourIA.getHeight()/2, tourIA.getWidth(), tourIA.getHeight());
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		//Positionnement sur le GridBagLayout
		conteneurGeneral.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Placement de affichage joueur courant
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 10, 0, 0);
		conteneurGeneral.add(joueurCourant, gbc);
		
		//Placement prise des pieces blanches
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 10, 0, 0);
		conteneurGeneral.add(blancPriseConteneur, gbc);
		
		//Placement des ordonnees
		gbc.gridx = 1;
		conteneurGeneral.add(coordOrdonnee, gbc);
		
		//Placement de la grille
		gbc.gridx = 2;
		gbc.insets = new Insets(0,10,0,10);
		conteneurGeneral.add(grille, gbc);
		
		//Placement prise des pieces noires
		gbc.gridx = 3;
		gbc.insets = new Insets(0,10,0,10);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		conteneurGeneral.add(noirPriseConteneur, gbc);
		
		//placement des abscisses
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,10,0,10);
		conteneurGeneral.add(coordAbscisse, gbc);
		
		//Placement des logs de la partie
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridwidth = 1;
		conteneurGeneral.add(scrollZone, gbc);
		
		this.setContentPane(conteneurGeneral);
		
		JPanel gp = (JPanel)this.getGlassPane();
		gp.setLayout(null);
		gp.add(tourIA);
		gp.setVisible(false);
		
		jeu.demarrerPartieIA();
	}
	
	/**
	 * Ajoute un message au logs de la partie
	 * @param s message
	 */
	public void addLogPartie(String s){
		logsPartie.append(s+"\n");
		logsPartie.setCaretPosition(logsPartie.getDocument().getLength());
	}
	
	/**
	 * Vide les logs de la partie
	 */
	public void clearLogsPartie(){
		logsPartie.setText("");
	}
	
	/**
	 * Fenetre de dialogue de selection de la promotion du pion
	 * @return le choix
	 */
	public String showTransformations(){
		String[] pieces = {"Tour", "Cavalier", "Fou", "Reine"};
		return (String)JOptionPane.showInputDialog(null, "TEST FIN DE JEUf", "Victoire", JOptionPane.QUESTION_MESSAGE, null, pieces, pieces[0]);
	}
	
	@Override
	public void repaint(){
		if(jeu.isVsIA()){
			if(jeu.getIAThread() != null && jeu.getIAThread().isReflechi() && !Echecs.DEBUG){
				return;
			}else if(jeu.getIAThread2() != null && jeu.getIAThread2().isReflechi() && !Echecs.DEBUG){
				return;
			}
		}
		
		this.grille.updateGrille();
		this.joueurCourant.update();
		super.repaint();
	}
	
	/**
	 * Active ou desactive l'effet de mise en attente pendant le tour de l'IA
	 * @param b active ou desactive
	 */
	public void tourIA(boolean b){
		this.grille.setRecoisInput(!b);
		this.getGlassPane().setVisible(b);
	}
	/**
	 * Getter jeu
	 * @return jeu
	 */
	public Jeu getJeu(){
		return jeu;
	}
	
	/**
	 * Getter grille
	 * @return grille
	 */
	public GrilleJeu getGrille(){
		return grille;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == chat){
			if(!chat.getText().equals("")){
				((JeuClient) jeu).envoyer("/say " + chat.getText());
				chat.setText("");
			}
		}
	}
}
