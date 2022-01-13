package multipion.MenuGraphisme;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import multipion.MultiPion;
import multipion.MenuGraphisme.jeu.Fenetre;
import multipion.jeu.IA.ValeursEvaluation;
import java.awt.*;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * Fenetre de selection des IA pour un mode de jeu IA contre IA
 */
public class ConfigIAvsIA extends JFrame implements ActionListener{
	
	/**
	 * Reference du menu
	 */
	private Menu menu;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	
	/**
	 * Boutons des niveaux d'IA blanc
	 */
	private JRadioButton niveau1Blanc, niveau2Blanc, niveau3Blanc;
	
	/**
	 * Boutons des niveaux d'IA noir
	 */
	private JRadioButton niveau1Noir, niveau2Noir, niveau3Noir;
	
	/**
	 * Label des choix de niveaux d'IA blanc
	 */
	private JLabel choixNiveauxBlanc, choixNiveauxNoir;
	
	/**
	 * Label des choix de niveaux d'IA noir
	 */
	private JCheckBox activeConfigBlanc, activeConfigNoir;
	
	/**
	 * Label des pieces blanches
	 */
	private JLabel piecesBlanches, piecesNoires;
	
	/**
	 * Label des pieces noires
	 */
	private JLabel pionBlanc;
	
	/**
	 * Label des pieces blanches
	 */
	private JLabel pionNoir;
	
	/**
	 * Champs de texte des pieces blanches
	 */
	private JTextField textPionBlanc;
	
	/**
	 * Champs de texte des pieces noires
	 */
	private JTextField textPionNoir;
	
	/**
	 * Label des differentes strategies
	 */
	private JLabel strategieBlanc, strategieNoir, defenseBlanc, defenseNoir, dangerBlanc, dangerNoir, attaqueBlanc, attaqueNoir;
	
	/**
	 * Champs de texte des differentes strategies
	 */
	private JTextField textDefenseBlanc, textAttaqueBlanc, textDangerBlanc, textDefenseNoir, textAttaqueNoir, textDangerNoir;
	
	/**
	 * Bouton valider et reset
	 */
	private JButton reset, valider;
	
	/**
	 * Constructeur
	 * @param menu reference du menu
	 * @param x coordonnee x de la fenetre
	 * @param y coordonnee y de la fenetre
	 */
	public ConfigIAvsIA(Menu menu, int x, int y){
		// Nom du panneau de configuration de IA vs IA
		super("Configuration de l'IA vs IA");
		this.menu = menu;
		// Cr�ation de Position/dimension/autre pour la frame
		this.setSize(new Dimension(800, 500));
		this.setIconImage(MultiPion.ICON);
		this.setMinimumSize(this.getSize());
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		initFenetre();
		checkConfig();
		initValeurEvaluation();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Initialise les variables et positionne les elements de la fenetre
	 */
	private void initFenetre(){
		//Conteneur general
		conteneurGeneral = new JPanel();
		conteneurGeneral.setBackground(new Color(33,36,54));
		
		//Label choix niveaux
		choixNiveauxBlanc = new JLabel("Choix du niveau de l'IA blanc");
		choixNiveauxBlanc.setForeground(new Color(255,255,255));
		choixNiveauxBlanc.setFont(new Font("Lucida Fax", Font.BOLD,20));
		choixNiveauxNoir = new JLabel("Choix du niveau de l'IA noir");
		choixNiveauxNoir.setForeground(new Color(255,255,255));
		choixNiveauxNoir.setFont(new Font("Lucida Fax", Font.BOLD,20));

		//niveaux
		niveau1Blanc = new JRadioButton("Niveau 1", true);
		niveau1Blanc.setBackground(new Color(33,36,54));
		niveau1Blanc.setForeground(new Color(255,255,255));
		niveau1Blanc.setPreferredSize(new Dimension(100, 20));
		niveau1Blanc.addActionListener(this);

		niveau2Blanc = new JRadioButton("Niveau 2");
		niveau2Blanc.setBackground(new Color(33,36,54));
		niveau2Blanc.setForeground(new Color(255,255,255));
		niveau2Blanc.setPreferredSize(new Dimension(100, 20));
		niveau2Blanc.addActionListener(this);

		niveau3Blanc = new JRadioButton("Niveau 3");
		niveau3Blanc.setPreferredSize(new Dimension(100, 20));
		niveau3Blanc.setBackground(new Color(33,36,54));
		niveau3Blanc.setForeground(new Color(255,255,255));
		niveau3Blanc.addActionListener(this);

		niveau1Noir = new JRadioButton("Niveau 1", true);
		niveau1Noir.setPreferredSize(new Dimension(100, 20));
		niveau1Noir.setBackground(new Color(33,36,54));
		niveau1Noir.setForeground(new Color(255,255,255));
		niveau1Noir.addActionListener(this);

		niveau2Noir = new JRadioButton("Niveau 2");
		niveau2Noir.setPreferredSize(new Dimension(100, 20));
		niveau2Noir.setBackground(new Color(33,36,54));
		niveau2Noir.setForeground(new Color(255,255,255));
		niveau2Noir.addActionListener(this);

		niveau3Noir = new JRadioButton("Niveau 3");
		niveau3Noir.setPreferredSize(new Dimension(100, 20));
		niveau3Noir.setBackground(new Color(33,36,54));
		niveau3Noir.setForeground(new Color(255,255,255));
		niveau3Noir.addActionListener(this);
		
		//Active config
		activeConfigBlanc = new JCheckBox("Modifier les valeurs d'evaluation", false);
		activeConfigBlanc.setBackground(new Color(33,36,54));
		activeConfigBlanc.setForeground(new Color(255,255,255));
		activeConfigBlanc.setHorizontalTextPosition(SwingConstants.LEFT);
		activeConfigBlanc.addActionListener(this);

		activeConfigNoir = new JCheckBox("Modifier les valeurs d'evaluation", false);
		activeConfigNoir.setBackground(new Color(33,36,54));
		activeConfigNoir.setForeground(new Color(255,255,255));
		activeConfigNoir.setHorizontalTextPosition(SwingConstants.LEFT);
		activeConfigNoir.addActionListener(this);
		
		//label pieces
		piecesBlanches = new JLabel("Valeur des pieces blanches");
		piecesBlanches.setForeground(new Color(255,255,255));
		piecesNoires = new JLabel("Valeur des pieces noires");
		piecesNoires.setForeground(new Color(255,255,255));
		
		//Label des pieces
		pionBlanc = new JLabel("Pion");
		pionNoir = new JLabel("Pion");
		//textfield des pieces
		Dimension textFieldDimension = new Dimension(40, 20);
		Insets textInsets = new Insets(2,5,2,5);
		textPionBlanc = new JTextField();
		textPionBlanc.setPreferredSize(textFieldDimension);
		textPionBlanc.setMargin(textInsets);
		textPionNoir = new JTextField();
		textPionNoir.setPreferredSize(textFieldDimension);
		textPionNoir.setMargin(textInsets);
		
		//label des strategies
		strategieBlanc = new JLabel("Strategie des blancs");
		strategieBlanc.setForeground(new Color(255,255,255));
		strategieNoir = new JLabel("Strategie des noirs");
		strategieNoir.setForeground(new Color(255,255,255));
		defenseBlanc = new JLabel("Defense");
		defenseBlanc.setForeground(new Color(255,255,255));
		dangerBlanc = new JLabel("Danger");
		dangerBlanc.setForeground(new Color(255,255,255));
		attaqueBlanc = new JLabel("Attaque");
		attaqueBlanc.setForeground(new Color(255,255,255));
		defenseNoir = new JLabel("Defense");
		defenseNoir.setForeground(new Color(255,255,255));
		dangerNoir = new JLabel("Danger");
		dangerNoir.setForeground(new Color(255,255,255));
		attaqueNoir = new JLabel("Attaque");
		attaqueNoir.setForeground(new Color(255,255,255));
		//textfield des strategies
		textDefenseBlanc = new JTextField();
		
		textDefenseBlanc.setPreferredSize(textFieldDimension);
		textDefenseBlanc.setMargin(textInsets);
		textDangerBlanc = new JTextField();
		textDangerBlanc.setPreferredSize(textFieldDimension);
		textDangerBlanc.setMargin(textInsets);
		textAttaqueBlanc = new JTextField();
		textAttaqueBlanc.setPreferredSize(textFieldDimension);
		textAttaqueBlanc.setMargin(textInsets);
		textDefenseNoir = new JTextField();
		textDefenseNoir.setPreferredSize(textFieldDimension);
		textDefenseNoir.setMargin(textInsets);
		textDangerNoir = new JTextField();
		textDangerNoir.setPreferredSize(textFieldDimension);
		textDangerNoir.setMargin(textInsets);
		textAttaqueNoir = new JTextField();
		textAttaqueNoir.setPreferredSize(textFieldDimension);
		textAttaqueNoir.setMargin(textInsets);
		
		//Boutons
		reset = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"Reset.png")));
		reset.setPreferredSize(new Dimension(65, 25));
		reset.addActionListener(this);
		valider = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"valider.png")));
		valider.setPreferredSize(new Dimension(150, 50));
		valider.addActionListener(this);
		
		
		//Positionnement
		conteneurGeneral.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		int margeMilieu = 80;
		
		//postionnement choix niveaux
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 6;
		gbc.weightx = 1;
		gbc.insets = new Insets(10,10,10,margeMilieu);
		conteneurGeneral.add(choixNiveauxBlanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 10;
		conteneurGeneral.add(choixNiveauxNoir, gbc);
		
		//positionnement niveaux
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0,0,0,0);
		conteneurGeneral.add(niveau1Blanc, gbc);
		gbc.gridx = 2;
		conteneurGeneral.add(niveau2Blanc, gbc);
		gbc.gridx = 4;
		gbc.insets.right = margeMilieu;
		conteneurGeneral.add(niveau3Blanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(niveau1Noir, gbc);
		gbc.gridx = 8;
		conteneurGeneral.add(niveau2Noir, gbc);
		gbc.gridx = 10;
		conteneurGeneral.add(niveau3Noir, gbc);
		
		//postitionnement active config
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 6;
		gbc.insets = new Insets(30, 0, 10, margeMilieu);
		gbc.anchor = GridBagConstraints.LINE_END;
		conteneurGeneral.add(activeConfigBlanc, gbc);
		gbc.insets.right = 0;
		gbc.gridx = 6;
		conteneurGeneral.add(activeConfigNoir, gbc);
		
		//postionnement label pieces
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(0,0,10,margeMilieu);
		gbc.anchor = GridBagConstraints.CENTER;
		conteneurGeneral.add(piecesBlanches, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(piecesNoires, gbc);
		
		//positionnement label des pieces
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,0,0,0);
		conteneurGeneral.add(pionBlanc, gbc);
		gbc.gridx = 1;
		conteneurGeneral.add(pionNoir, gbc);
		gbc.gridx = 7;
		
		//positionnement des textfield des pieces
		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 20, 0);
		conteneurGeneral.add(textPionBlanc, gbc);
		gbc.gridx = 1;
		conteneurGeneral.add(textPionNoir, gbc);
		gbc.gridx = 7;
		
		//positionnement label strategie
		gbc.gridy = 6;
		gbc.gridx = 0;
		gbc.gridwidth = 6;
		gbc.insets = new Insets(10, 0, 10, margeMilieu);
		conteneurGeneral.add(strategieBlanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(strategieNoir, gbc);
		
		
		//positionnement des lables des differentes strategies
		gbc.gridy = 7;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		conteneurGeneral.add(defenseBlanc, gbc);
		gbc.gridx = 2;
		conteneurGeneral.add(attaqueBlanc, gbc);
		gbc.gridx = 4;
		gbc.insets.right = margeMilieu;
		conteneurGeneral.add(dangerBlanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(defenseNoir, gbc);
		gbc.gridx = 8;
		conteneurGeneral.add(attaqueNoir, gbc);
		gbc.gridx = 10;
		conteneurGeneral.add(dangerNoir, gbc);
		
		//positionnement textfield des strategies
		gbc.gridy = 8;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 30, 0);
		conteneurGeneral.add(textDefenseBlanc, gbc);
		gbc.gridx = 2;
		conteneurGeneral.add(textAttaqueBlanc, gbc);
		gbc.gridx = 4;
		gbc.insets.right = margeMilieu;
		conteneurGeneral.add(textDangerBlanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(textDefenseNoir, gbc);
		gbc.gridx = 8;
		conteneurGeneral.add(textAttaqueNoir, gbc);
		gbc.gridx = 10;
		conteneurGeneral.add(textDangerNoir, gbc);
		
		//positionnement des boutons
		gbc.gridy = 9;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0,0,0,0);
		conteneurGeneral.add(reset, gbc);
		gbc.gridx = 2;
		gbc.gridwidth = 8;
		gbc.anchor = GridBagConstraints.CENTER;
		conteneurGeneral.add(valider, gbc);
		
		
		this.setContentPane(conteneurGeneral);
	}
	
	/**
	 * Active ou desactive les champs en fonction de quel niveau est selectionne
	 */
	private void checkConfig(){
		boolean etat = this.niveau3Blanc.isSelected();
		this.activeConfigBlanc.setEnabled(etat);
		if(!etat && this.activeConfigBlanc.isSelected()) this.activeConfigBlanc.setSelected(false);
		
		etat = this.niveau3Blanc.isSelected() && this.activeConfigBlanc.isSelected();
		this.piecesBlanches.setEnabled(etat);
		this.pionBlanc.setEnabled(etat);
		this.textPionBlanc.setEnabled(etat);
		this.strategieBlanc.setEnabled(etat);
		this.defenseBlanc.setEnabled(etat);
		this.attaqueBlanc.setEnabled(etat);
		this.dangerBlanc.setEnabled(etat);
		this.textDangerBlanc.setEnabled(etat);
		this.textDefenseBlanc.setEnabled(etat);
		this.textAttaqueBlanc.setEnabled(etat);
		
		etat = niveau3Noir.isSelected();
		this.activeConfigNoir.setEnabled(etat);
		if(!etat && this.activeConfigNoir.isSelected()) this.activeConfigNoir.setSelected(false);
		
		etat = this.niveau3Noir.isSelected() && this.activeConfigNoir.isSelected();
		this.piecesNoires.setEnabled(etat);
		this.pionNoir.setEnabled(etat);
		this.textPionNoir.setEnabled(etat);
		this.strategieNoir.setEnabled(etat);
		this.defenseNoir.setEnabled(etat);
		this.attaqueNoir.setEnabled(etat);
		this.dangerNoir.setEnabled(etat);
		this.textDangerNoir.setEnabled(etat);
		this.textDefenseNoir.setEnabled(etat);
		this.textAttaqueNoir.setEnabled(etat);
	}
	
	/**
	 * Met les champs de text au valeur de l'ia complet par defaut
	 */
	private void initValeurEvaluation(){
		ValeursEvaluation valeurs = new ValeursEvaluation();
		this.textPionBlanc.setText(valeurs.PION+"");
		this.textDangerBlanc.setText(valeurs.DANGER+"");
		this.textAttaqueBlanc.setText(valeurs.ATTAQUE+"");
		this.textPionNoir.setText(valeurs.PION+"");
		this.textDangerNoir.setText(valeurs.DANGER+"");
		this.textAttaqueNoir.setText(valeurs.ATTAQUE+"");
	}
	
	/**
	 * Verifie que l'utilisateur a entree des valeurs correctes dans les differents champs
	 * @return resultat du test
	 */
	private boolean checkValeur(){
		char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		String[] valeurs = {this.textPionBlanc.getText(), 
				this.textDefenseBlanc.getText(), 
				this.textDangerBlanc.getText().substring(1), 
				this.textAttaqueBlanc.getText(),
				this.textPionNoir.getText(), 
				this.textDefenseNoir.getText(), 
				this.textDangerNoir.getText().substring(1), 
				this.textAttaqueNoir.getText()};
		
		for(int i = 0; i < valeurs.length; i++){
			for(int j = 0; j < valeurs[i].length(); j++){
				boolean faux = false;
				for(char c : table){
					if( c == valeurs[i].charAt(j)) faux = true;
				}
				if(!faux) return false;
			}
		}
		return true;
	}

	//Niveau IA
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == niveau1Blanc || source == niveau2Blanc || source == niveau3Blanc){
			niveau1Blanc.setSelected(false);
			niveau2Blanc.setSelected(false);
			niveau3Blanc.setSelected(false);
			JRadioButton radio = (JRadioButton)source;
			radio.setSelected(true);
		}
		
		if(source == niveau1Noir || source == niveau2Noir || source == niveau3Noir){
			niveau1Noir.setSelected(false);
			niveau2Noir.setSelected(false);
			niveau3Noir.setSelected(false);
			JRadioButton radio = (JRadioButton)source;
			radio.setSelected(true);
		}
		
		if(source == reset){
			initValeurEvaluation();
		}
		
		// Valide les informations lors du click sur valider et lance si tout est bon
		if(source == valider){
			if(niveau3Blanc.isSelected() || niveau3Noir.isSelected()){
				if(checkValeur()){
					ValeursEvaluation valeursBlanc = new ValeursEvaluation();
					valeursBlanc.PION = Integer.parseInt(this.textPionBlanc.getText());
					valeursBlanc.DANGER = Integer.parseInt(this.textDangerBlanc.getText());
					valeursBlanc.ATTAQUE = Integer.parseInt(this.textAttaqueBlanc.getText());
					
					ValeursEvaluation valeursNoir = new ValeursEvaluation();
					valeursNoir.PION = Integer.parseInt(this.textPionNoir.getText());
					valeursNoir.DANGER = Integer.parseInt(this.textDangerNoir.getText());
					valeursNoir.ATTAQUE = Integer.parseInt(this.textAttaqueNoir.getText());
					
					int niveauBlanc = 1;
					if(niveau2Blanc.isSelected()) niveauBlanc = 2;
					else if(niveau3Blanc.isSelected()) niveauBlanc = 3;
					
					int niveauNoir = 1;
					if(niveau2Noir.isSelected()) niveauNoir = 2;
					else if(niveau3Noir.isSelected()) niveauNoir = 3;
					
					new Fenetre(this.getX(), this.getY(), niveauBlanc, niveauNoir, valeursBlanc, valeursNoir);
					this.setVisible(false);
					this.dispose();
					this.menu.setVisible(false);
					this.menu.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Valeur incorrecte", "Erreur", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				int niveauBlanc = 1;
				if(niveau2Blanc.isSelected()) niveauBlanc = 2;
				else if(niveau3Blanc.isSelected()) niveauBlanc = 3;
				
				int niveauNoir = 1;
				if(niveau2Noir.isSelected()) niveauNoir = 2;
				else if(niveau3Noir.isSelected()) niveauNoir = 3;
				
				new Fenetre(this.getX(), this.getY(), niveauBlanc, niveauNoir, null, null);
				this.setVisible(false);
				this.dispose();
				this.menu.setVisible(false);
				this.menu.dispose();
			}
		}
		
		checkConfig();
		
	}

}
