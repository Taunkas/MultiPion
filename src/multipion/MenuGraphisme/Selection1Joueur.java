package multipion.MenuGraphisme;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import multipion.MultiPion;
import multipion.MenuGraphisme.jeu.Fenetre;

import java.awt.*;

/**
 * Fenetre de selection du niveau de l'ia en mode joueur contre IA
 */
public class Selection1Joueur extends JFrame implements ActionListener{
	
	/** 
	 * Le JPanel principal
	 */
	private JPanel panelPrincipal;
	
	/**
	 * Button couleur blanche
	 */
	private JToggleButton toggleBlanc;
	
	/**
	 * Button couleur noire
	 */
	private JToggleButton toggleNoir;
	
	/**
	 * Button niveau 1
	 */
	private JRadioButton niveau1;
	
	/**
	 * Button niveau 2
	 */
	private JRadioButton niveau2;
	
	/**
	 * Button niveau 3
	 */
	private JRadioButton niveau3;
	
	
	/**
	 * Button valider
	 */
	private JButton valider;
	
	/**
	 * Reference du menu
	 */
	private Menu menu;
	
	/**
	 * Constructeur de l'Ecran de Selection
	 * @param x
	 * @param y
	 */
	public Selection1Joueur(int x, int y, Menu menu){
		super("Jeu MultiPion");
		this.menu = menu;
		this.setSize(450, 300);
		this.setMinimumSize(this.getSize());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setIconImage(MultiPion.ICON);
		this.setVisible(true);
	}
	
	/**
	 * Ajoute les elements a� la fenetre
	 */
	private void initFenetre(){
		//Panel principal
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(33,36,54));
		panelPrincipal.setPreferredSize(this.getPreferredSize());
		panelPrincipal.setLayout(new GridBagLayout());
		
		//Initialisation des buttons des niveaux
		niveau1 = new JRadioButton("Niveau 1", true);
		niveau1.setPreferredSize(new Dimension(100, 20));
		niveau1.setBackground(new Color(33,36,54));
		niveau1.setForeground(new Color(255,255,255));
		niveau1.addActionListener(this);
		niveau2 = new JRadioButton("Niveau 2");
		niveau2.setBackground(new Color(33,36,54));
		niveau2.setForeground(new Color(255,255,255));
		niveau2.setPreferredSize(new Dimension(100, 20));
		niveau2.addActionListener(this);
		niveau3 = new JRadioButton("Niveau 3");
		niveau3.setForeground(new Color(255,255,255));
		niveau3.setBackground(new Color(33,36,54));
		niveau3.setPreferredSize(new Dimension(100, 20));
		niveau3.addActionListener(this);
		
		//Initialisation des textes
		JLabel choixNiveaux = new JLabel("Choix du niveau :");
		choixNiveaux.setForeground(new Color(255,255,255));
		JLabel choixCouleur = new JLabel("Choix de la couleur : ");
		choixCouleur.setForeground(new Color(255,255,255));
		
		//Initialisation des buttons des couleurs
		toggleBlanc = new JToggleButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"pion_b.png")), true);
		toggleBlanc.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		toggleBlanc.setBackground(new Color(33,36,54));
		toggleBlanc.addActionListener(this);

		toggleNoir = new JToggleButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"pion_n.png")));
		toggleNoir.setPreferredSize(new Dimension(Case.CASE_LENGTH, Case.CASE_LENGTH));
		toggleNoir.setBackground(new Color(33,36,54));
		toggleNoir.addActionListener(this);
		
		//Initialisation boutton valider et mode debug
		valider = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"valider.png")));
		valider.setPreferredSize(new Dimension(150, 50));
		valider.addActionListener(this);
	
		
		//Positionnement
		GridBagConstraints gbc = new GridBagConstraints();
		
		//positionnement choix niveau
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0,0,10,0);
		panelPrincipal.add(choixNiveaux, gbc);
		
		//positionnement des niveaux
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0,0,40,0);
		panelPrincipal.add(niveau1, gbc);
		gbc.gridy = 1;
		gbc.gridx = 2;
		panelPrincipal.add(niveau2, gbc);
		gbc.gridy = 1;
		gbc.gridx = 4;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panelPrincipal.add(niveau3, gbc);
		
		//positionnement choix couleur
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0,0,10,0);
		panelPrincipal.add(choixCouleur, gbc);
		
		//positionnement des couleurs
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panelPrincipal.add(toggleBlanc, gbc);
		gbc.gridx = 4;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panelPrincipal.add(toggleNoir, gbc);
		
		
		//positionnement boutton valider
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panelPrincipal.add(valider, gbc);
		
		this.setContentPane(panelPrincipal);
	}

	// Valide les informations lors du click et lance la partie !
	@Override
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		if(source instanceof JRadioButton){
			niveau1.setSelected(false);
			niveau2.setSelected(false);
			niveau3.setSelected(false);
			JRadioButton radio = (JRadioButton)source;
			radio.setSelected(true);
		}
		
		if(source.getClass().equals(JToggleButton.class)){
			toggleBlanc.setSelected(false);
			toggleNoir.setSelected(false);
			JToggleButton toggle = (JToggleButton)source;
			toggle.setSelected(true);
		}
		
		if(source == valider){
			int niveau = 1;
			if(niveau2.isSelected()){
				niveau = 2;
			}else if(niveau3.isSelected()){
				niveau = 3;
			}
			this.setVisible(false);
			this.dispose();
			if(menu != null){
				menu.setVisible(false);
				menu.dispose();
			}
			new Fenetre(this.getX()*2, this.getY()*2, toggleBlanc.isSelected(), niveau);
		}
	}


}
