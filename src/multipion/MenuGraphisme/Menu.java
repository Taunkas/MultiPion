package multipion.MenuGraphisme;


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

import multipion.MultiPion;
import multipion.MenuGraphisme.jeu.Fenetre;
/**
 * Menu du jeu d'echec
 * Acces au different mode de jeu
 */
public class Menu extends JFrame implements ActionListener, MouseListener{
	
	/**
	 * Bouton un joueur
	 */
	private JButton unJoueur;
	
	/**
	 * Bouton deux joueurs
	 */
	private JButton deuxJoueurs;
	
	/**
	 * Bouton a Propos
	 */
	private JButton aPropos;
	
	/**
	 * Bouton quitter
	 */
	private JButton quitter;
	
	/**
	 * Bouton IA contre IA
	 */
	private JButton iavsia;
	
	/**
	 * Textfield taille grille
	 */
	private JTextField dimgrille;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneur;
	
	/**
	 * Image 1
	 */
	private ImagePion image1;
	
	/**
	 * Image 2
	 */
	private ImagePion image2;
	
	/**
	 * Image 3
	 */
	private ImagePion image3;
	
	/**
	 * Image 4
	 */
	private ImagePion image4;
	
	/**
	 * Image 5
	 */
	private ImagePion image5;
	
	/**
	 * Image 6
	 */
	private ImagePion image6;
	
	/**
	 * Taille de la grille
	 */
	public static int taillegrille;
	
	
	/**
	 * Constructeur
	 */
	public Menu(){
		super("Jeu MultiPion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(Case.CASE_LENGTH * 9, Case.CASE_LENGTH * 9);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		initFenetre();
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);
	}
	
	/**
	 * Instancie et positionne les elements de la fenetre
	 */
	public void initFenetre(){
		//Initialise les boutons
		Dimension taille = new Dimension(150, 50);
		unJoueur = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"1joueur.png")));
		unJoueur.setPreferredSize(taille);
		unJoueur.addActionListener(this);
		unJoueur.addMouseListener(this);
		
		deuxJoueurs = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"2joueur.png")));
		deuxJoueurs.setPreferredSize(taille);
		deuxJoueurs.addActionListener(this);
		deuxJoueurs.addMouseListener(this);
		
		aPropos = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"info.png")));
		aPropos.setBorderPainted(false);
		aPropos.setPreferredSize(new Dimension(45, 45));
		aPropos.addActionListener(this);
		
		quitter = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"quit.png")));
		quitter.setPreferredSize(new Dimension(45, 45));
		quitter.setBorderPainted(false);
		quitter.addActionListener(this);
		
		dimgrille = new JTextField("3");
		dimgrille.setBackground(new Color(53,56,74));
		dimgrille.setFont(new Font("Arial", Font.PLAIN, 20));
		dimgrille.setHorizontalAlignment(JTextField.CENTER);
		dimgrille.setForeground(new Color(180,180,180));
		dimgrille.setPreferredSize(taille);
		
		iavsia = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"ia.png")));
		iavsia.setPreferredSize(taille);
		iavsia.addMouseListener(this);
		iavsia.addActionListener(this);
		
		//Images
		image1 = new ImagePion(true);
		image1.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		image2 = new ImagePion(true);
		image2.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		image3 = new ImagePion(true);
		image3.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		image4 = new ImagePion(false);
		image4.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		image5 = new ImagePion(false);
		image5.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		image6 = new ImagePion(false);
		image6.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		 
		//Initialise le Jpanel principal
		conteneur = new JPanel();
		conteneur.setBackground(new Color(33,36,54));
		conteneur.setLayout(new GridBagLayout());
		this.setContentPane(conteneur);
		
		//Label
		ImageIcon image = new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"Titre.png"));
		JLabel titre = new JLabel(image);
		titre.setPreferredSize(new Dimension(450, 80));
		titre.setFont(new Font("Dialog", Font.BOLD,50));
		titre.setHorizontalAlignment(JLabel.CENTER);
		
		
		//Positionnement
		GridBagConstraints gbc = new GridBagConstraints();
		
		//positionnement titre
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		conteneur.add(titre, gbc);
		
		//positionnement image 1
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		conteneur.add(image1, gbc);
		
		//positionnement image 2
		gbc.gridx = 0;
		gbc.gridy = 2;
		conteneur.add(image2, gbc);
		
		//postitionnement image 3
		gbc.gridx = 0;
		gbc.gridy = 3;
		conteneur.add(image3, gbc);
		
		//positionnement image 4
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		conteneur.add(image4, gbc);
		
		//positionnement image 5
		gbc.gridx = 3;
		gbc.gridy = 2;
		conteneur.add(image5, gbc);
		
		//postitionnement image 6
		gbc.gridx = 3;
		gbc.gridy = 3;
		conteneur.add(image6, gbc);
		
		//positionnement un joueur
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		conteneur.add(unJoueur, gbc);
		
		//postionnement deux joueurs
		gbc.gridy = 2;
		conteneur.add(deuxJoueurs, gbc);
		

		//positionnement iavsia
		gbc.gridy = 3;
		conteneur.add(iavsia, gbc);
		
		
		//positionnement choix de dimesnion de la grille
		gbc.gridy = 4;
		conteneur.add(dimgrille, gbc);
		
		//positionnement a propos
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		conteneur.add(aPropos, gbc);
		
		//positionnement quitter
		gbc.gridx = 3;
		conteneur.add(quitter, gbc);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		if(source == unJoueur){
			//Cr�e la nouvelle fen�tre de s�lection
			new Selection1Joueur(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2, this);
			
			//R�cup�re l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise � 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
	             taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie � 10 ou � 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}
		}
		if(source == deuxJoueurs){
		
			//R�cup�re l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise � 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
				taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie � 10 ou � 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}		
			
			this.setVisible(false);
			this.dispose();
			new Fenetre(this.getX() + this.getWidth(), this.getY() + this.getHeight());
			
		}
		if(source == aPropos){
			new Info();
		}
		if(source == quitter){
			setVisible(false);
			this.dispose();
			 System.exit(0);
		}
		
		
		if(source == iavsia){
			
			//R�cup�re l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise � 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
				taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie � 10 ou � 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}
			new ConfigIAvsIA(this, this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	// G�re la surbrillance des images lors du passage de la souris
	// Active
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if(source == unJoueur ){
			image1.isHover = true;
			image4.isHover = true;
		}
		
		if( source == deuxJoueurs){
			image2.isHover = true;
			image5.isHover = true;
		}
		
		if(source == iavsia){
			image3.isHover = true;
			image6.isHover = true;
		}
		
		this.repaint();
	}
	
	// Desactive
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if(source == unJoueur ){
			image1.isHover = false;
			image4.isHover = false;
		}
		
		if( source == deuxJoueurs){
			image2.isHover = false;
			image5.isHover = false;
		}
		
		if(source == iavsia){
			image3.isHover = false;
			image6.isHover = false;
		}
		this.repaint();
	}
}

/**
 * Affichage d'une pes pions dans les images
 */
class ImagePion extends JPanel{
	
	/**
	 * Affichage pion
	 */
	private String[] pieces = {"pion" };
	
	/**
	 * Couleur de la pion
	 */
	private char couleur;
	
	/**
	 * Valeur du tirage au sort
	 */
	private int index;
	
	/**
	 * Vrai : suppression de l'effet de transparence
	 */
	public boolean isHover;
	
	/**
	 * Constructeur
	 * @param isBlanc
	 */
	public ImagePion(boolean isBlanc){
		super();
		this.couleur = (isBlanc)? 'b' : 'n';
		this.isHover = false;
		Random rand = new Random();
		index = rand.nextInt(pieces.length);
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		// Gere l'affichage des pions / chargements des images
		try{
			float transparence = (isHover)? 1f : 0.5f;
			Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, transparence));
			Image imgPiece = ImageIO.read(getClass().getResource(MultiPion.RES_PATH+pieces[index]+"_"+couleur+".png"));
			g2d.drawImage(imgPiece, 0, 0, this);
		}catch(Exception e){
			System.out.println("Impossible de charger l'image "+getClass().getResource(MultiPion.RES_PATH+pieces[index]+"_"+couleur+".png"));
		}
	}
}
