package multipion;

import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import multipion.MenuGraphisme.Menu;

/**
 * Main qui lance le menu du jeu
 */
public class MultiPion {
	
	/**
	 * Chemin des images du jeu
	 */
	public static final String RES_PATH = "/res/";
	
	/**
	 * Chemin des sauvegardes du jeu
	 */
	public static String SAVE_PATH = "";
	
	/**
	 * Icone des fenetres
	 */
	public static Image ICON = null;
	
	
	/**
	 * Different type de log
	 */
	public static enum TypeLog{
		INFO, ERREUR, WARNING
	};

	/**
	 * Main
	 * @param args
	 */
    public static void main(String[] args){
    	
    	//Chargement de l'icon
    	try{
			ICON = ImageIO.read(MultiPion.class.getResource(MultiPion.RES_PATH+"logo.png"));
		}catch(Exception e){
			System.out.println("Impossible de charger l'image "+MultiPion.class.getResource(MultiPion.RES_PATH+"logo.png"));
		}
    	
    	new Menu();
    }


}
