package echecs.sauvegarde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import echecs.Echecs;
import echecs.graphisme.Menu;
import echecs.jeu.Jeu;


/**
 * Contient diverses fonctions de lecture / ecriture de sauvegarde
 * <ul>
 * 	<li>Format .sv : sauvegarde des partie non terminee</li>
 * 	<li>Format .pgn : format standardise, plus de detail ici : <a href="http://fr.wikipedia.org/wiki/Portable_Game_Notation">Notation PGN</a></li>
 * </ul>
 */
public class Sauvegarde {
	
	/**
	 * Lit un fichier contenant l'historique d'une partie non terminee
	 * @param jeu la reference du jeu
	 * @param nomFichier le nom du fichier
	 * @return vrai si la lecture a reussi
	 */
	public static boolean lireSauvegardeJeuNonFini(Jeu jeu, String nomFichier){
		try{
			File file = new File(Echecs.SAVE_PATH+File.separatorChar+nomFichier+".sv");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ligne = br.readLine();
			if(ligne == null || ligne.length() == 0){
				JOptionPane.showMessageDialog(null, "Aucun historique de partie trouve", "Aucun historique de partie trouve", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			ligne = ligne.replaceAll("\\.\\p{Space}", ".");
			String[] tours = ligne.split("\\d+\\.");
			
			ArrayList<CoupPGN> coups = new ArrayList<CoupPGN>();
			
			if(tours.length > 1){
				for(int i = 0; i < tours.length; i++){
					if(tours[i].length() > 0){
						String[] coupsTour = tours[i].split(" +");
						for(int j = 0; j < coupsTour.length; j++){
							if(coupsTour[j].length() > 0){
								if(CoupPGN.notationValide(coupsTour[j])){
								coups.add(new CoupPGN(coupsTour[j]));
								}else{
									JOptionPane.showMessageDialog(null, "La notation ["+coupsTour[j]+"] n'est pas une notation PGN valide\nImpossible de charger la sauvegarde", "Notation invalide", JOptionPane.WARNING_MESSAGE);
									return false;
								}
							}
						}
					}
				}
			}
			
			ligne  = br.readLine();
			//jeu.jouerSauvegarde(coups);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Chargement de la sauvegarde avec succes", "Succes", JOptionPane.INFORMATION_MESSAGE);
		
		return true;
	}

	/**
	 * Ecrit une sauvegarde du jeu en cours dans le dossier 'save'
	 * @param jeu l'instance du jeu en cours
	 * @param nomFichier nom du fichier de la sauvegarde
	 * @return succes de creation de la sauvegarde
	 */
	public static boolean creerSauvegardeJeuNonFini(Jeu jeu, String nomFichier){
		File dossier = new File(Echecs.SAVE_PATH);
		dossier.mkdir();
		File file = new File(Echecs.SAVE_PATH+File.separatorChar+nomFichier+".sv");
		try{
			if(!file.createNewFile()){
				file.delete();
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(new FileWriter(file));

			pw.flush();
			pw.close();
		}catch(Exception e){
			System.out.println("Exception lors de la creation de la sauvegarde");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Lit un fichier PGN et renvoi les informations de la parties dans l'objet Partie
	 * @param nomFichier nom du fichier 
	 * @param numero numero dans la partie dans le fichier
	 * @return les informations de la partie
	 */
	public static Partie lireSauvegardePGN(String nomFichier, int numero){
		Partie partie = null;
		try{
			if(numero == -1) return null;
			File file = new File(Echecs.SAVE_PATH+File.separatorChar+nomFichier+".pgn");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ligne = br.readLine();
			partie = new Partie();
			
			while(ligne != null && numero > 0){
				ligne = br.readLine();
				while(!ligne.startsWith("[Event ")){
					ligne = br.readLine();
				}
				numero--;
			}
			
			ligne = br.readLine();
			while(ligne != null && !ligne.startsWith("[Event ")){
				if(ligne.startsWith("[Site ")){
					partie.site = ligne.substring(7, ligne.length()-2);
				}else if(ligne.startsWith("[Date ")){
					partie.date = ligne.substring(7, ligne.length()-2);
				}else if(ligne.startsWith("[Round ")){
					partie.round = ligne.substring(8, ligne.length()-2);
				}else if(ligne.startsWith("[White ")){
					partie.white = ligne.substring(8, ligne.length()-2);
				}else if(ligne.startsWith("[Black ")){
					partie.black = ligne.substring(8, ligne.length()-2);
				}else if(ligne.startsWith("[Result ")){
					partie.result = ligne.substring(9, ligne.length()-2);
				}else if(ligne.startsWith("1.")){
					String toursBrut = "";
					String resultat = "";
					while(ligne != null && ligne.length() != 0 && !ligne.startsWith("[Event ")){
						toursBrut += ligne+" ";
						ligne = br.readLine();
					}
					toursBrut = toursBrut.replaceAll("\\{Space}+", " ");
					toursBrut = toursBrut.replaceAll("\\.\\p{Space}", ".");
					String[] tours = toursBrut.split("\\d+\\.");
					
					Historique historique = new Historique();
					
					if(tours.length > 1){
						for(int i = 0; i < tours.length; i++){
							if(tours[i].length() > 0){
								String[] coupsTour = tours[i].split(" +");
								if(i == tours.length-1){
									for(int j = 0; j < coupsTour.length-1; j++){
										if(coupsTour[j].length() > 0){
											if(CoupPGN.notationValide(coupsTour[j])){
												historique.addCoupPGN(new CoupPGN(coupsTour[j]));
											}else{
												JOptionPane.showMessageDialog(null, "La notation ["+coupsTour[j]+"] n'est pas une notation PGN valide\nImpossible de charger la sauvegarde", "Notation invalide", JOptionPane.WARNING_MESSAGE);
												return null;
											}
										}
									}
									resultat = coupsTour[coupsTour.length-1];
								}else{
									for(int j = 0; j < coupsTour.length; j++){
										if(coupsTour[j].length() > 0){
											if(CoupPGN.notationValide(coupsTour[j])){
												historique.addCoupPGN(new CoupPGN(coupsTour[j]));
											}else{
												JOptionPane.showMessageDialog(null, "La notation ["+coupsTour[j]+"] n'est pas une notation PGN valide\nImpossible de charger la sauvegarde", "Notation invalide", JOptionPane.WARNING_MESSAGE);
												return null;
											}
										}
									}
								}
							}
						}
					}
					
					partie.coups = historique;
				}
				ligne = br.readLine();
			}
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return partie;
	}
	/**
	 * Ecrit une sauvegarde d'une partie finie dans le dossier 'save'
	 * @param p Partie du jeu termine
	 * @param nomFichier nom du fichier
	 * @return succes de creation de la sauvegarde
	 */
	public static boolean creerSauvegardePGN(Partie p, String nomFichier){
		try{
			File dossier = new File(Echecs.SAVE_PATH);
			dossier.mkdir();
			File file = new File(Echecs.SAVE_PATH+File.separatorChar+nomFichier+".pgn");
			
			if(!file.createNewFile()){
				System.out.println("Fichier deja existant");
				return false;
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			
			pw.println(p.formatPGN());
			
			pw.flush();
			pw.close();
			return true;
			
		}catch(Exception e){
			System.out.println("Erreur lors de la creation de la sauvegarde "+Echecs.SAVE_PATH+File.pathSeparator+nomFichier+".pgn");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Recupere le nombre de parties contenu dans le fichier PGN
	 * @param nomFichier le nom du fichier
	 * @return le nombre de partie
	 */
	public static int nombrePartiesFichierPGN(String nomFichier){
		try{
			File file = new File(Echecs.SAVE_PATH+File.separatorChar+nomFichier+".pgn");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ligne = br.readLine();
			int compteur = 0;
			
			while(ligne != null){
				if(ligne.startsWith("[Event ")){
					compteur++;
				}
				ligne = br.readLine();
			}
			br.close();
			return compteur;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
