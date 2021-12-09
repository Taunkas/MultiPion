package multipion;

import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import multipion.graphisme.Menu;

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
	 * Active le mode DEBUG
	 */
	public static boolean DEBUG = false;
	
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

    /**
     * Ajoute une ligne de log a la console
     * @param log le message a ajouter
     * @param type le type de message
     */
    public static void addLog(String log, TypeLog type){
    	/*String typelog = "[?]";
    	if(type.equals(TypeLog.INFO)){
    		typelog = "[INFO]";
    	}else if(type.equals(TypeLog.ERREUR)){
    		typelog = "[ERREUR]";
    	}else if(type.equals(TypeLog.WARNING)){
    		typelog = "[WARNING]";
    	}
    	
    	/*Date now =  new Date();
    	String formatDate = new SimpleDateFormat("HH:mm:ss").format(now);
    	System.out.println(formatDate+" "+typelog+" "+log);*/
    }
}
