package multipion.graphisme.jeu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import multipion.MultiPion;
import multipion.graphisme.Menu;
import multipion.jeu.Jeu;
import multipion.sauvegarde.Partie;

/**
 * Fenetre du formulaire de creation de sauvegarde de partie terminee au format PGN
 */
public class MessageFin extends JFrame implements ActionListener{
	
	/**
	 * content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	

	/**
	 * conteneur des meta donnee de la partie
	 */
	private JPanel conteneurMetaDonnee;
	
	/**
	 * conteneur des boutons de validation
	 */
	private JPanel conteneurBoutons;
	
	/**
	 * Reference de la fenetre de jeu
	 */
	private Fenetre fenetre;
	
	/**
	 * Bouton creation sauvegarde
	 */
	private JButton rejouer;
	
	/**
	 * Bouton quitter
	 */
	private JButton quitter;
	
	
	/**
	 * label message victoire
	 */
	private JLabel labelvictoire;
	
	/**
	 * Label message condition
	 */
	private JLabel labelcondition;
	
	/**
	 * Label message rejouer
	 */
	private JLabel labelrejouer;
	
	
	/**
	 * Resultat de la partie
	 */
	private String resultat;
	
	/**
	 * Condition de Fin de la partie
	 */
	private String Conditionfin;
	
	/**
	 * Constructeur
	 * @param jeu reference du jeu
	 * @param fenetre reference de la fenetre de jeu
	 */
	public MessageFin(Fenetre fenetre, String resultat, String Conditionfin){
		super("Partie Fini");
		this.fenetre = fenetre;
		this.resultat = resultat;
		this.Conditionfin = Conditionfin;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(fenetre);
		this.setSize(400, 300);
		this.setResizable(false);
		initFenetre();
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);

	}
	
	/**
	 * Instancie et positionne les elements de la fenetre
	 */
	private void initFenetre(){
		//conteneurs
		conteneurGeneral = new JPanel();
		conteneurMetaDonnee = new JPanel();
		conteneurMetaDonnee.setPreferredSize(new Dimension(200, 200));
		conteneurBoutons = new JPanel();
		conteneurBoutons.setPreferredSize(new Dimension(450, 50));
		
		//Les labels
		labelvictoire = new JLabel("Bravo au joueur " +resultat+ ", vous avez gagnez !", JLabel.RIGHT);
		
		labelcondition = new JLabel("Vous avez remportez la partie " +Conditionfin, JLabel.RIGHT);
		
		labelrejouer = new JLabel("Voulez-vous rejouez ? " , JLabel.RIGHT);
		
	
	
		
		//boutons
		rejouer = new JButton("Rejouer");
		rejouer.addActionListener(this);
		quitter = new JButton("Quitter");
		quitter.addActionListener(this);
		
		

		GridBagConstraints gbc = new GridBagConstraints();
	
		
		//Positionnement metadonnee
		conteneurMetaDonnee.setLayout(new GridBagLayout());
		conteneurMetaDonnee.setBorder(BorderFactory.createTitledBorder("Résultats :"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(5,5,5,5);
		conteneurMetaDonnee.add(labelvictoire, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		conteneurMetaDonnee.add(labelcondition, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		conteneurMetaDonnee.add(labelrejouer, gbc);
		
		//Positionnement des boutons
		conteneurBoutons.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		conteneurBoutons.add(quitter, gbc);
		gbc.gridx = 1;
		conteneurBoutons.add(rejouer, gbc);
		
		//Postionnement general
		conteneurGeneral.setLayout(new BoxLayout(conteneurGeneral, BoxLayout.PAGE_AXIS));
		conteneurGeneral.add(conteneurMetaDonnee);
		conteneurGeneral.add(conteneurBoutons);
		
		this.setContentPane(conteneurGeneral);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == quitter){
			this.setVisible(false);

				fenetre.setVisible(false);
				fenetre.dispose();
				new Menu();
			
			this.dispose();
		} else if(source == rejouer){
			this.setVisible(false);

				fenetre.getJeu().reset();
				this.dispose();
		

		}
	}

}
