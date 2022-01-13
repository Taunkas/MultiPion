package multipion.saveDonnees;

import java.util.ArrayList;

public class Historique{

	/**
	 * La liste qui stocke les coups
	 */
	private ArrayList<CoupSave> list;
	
	/**
	 * Constructeur d'un historique vide
	 */
	public Historique(){
		list = new ArrayList<CoupSave>();
	}
	
	/**
	 * Ajoute un coup a la list
	 * @param c Coup a ajouter
	 */
	public void addCoupPGN(CoupSave c){
		list.add(c);
	}
	
	/**
	 * Getter de la list des coups
	 * @return la reference la list
	 */
	public ArrayList<CoupSave> getList(){
		return list;
	}
	
	/**
	 * Retourne le dernier coup joue
	 * @return le dernier coup
	 */
	public CoupSave getDernierCoup(){
		return list.get(list.size()-1);
	}
	
	/**
	 * Supprime le dernier coup joueur
	 */
	public void supprimeDernierCoup(){
		list.remove(list.size()-1);
	}
	
	/**
	 * Retourne la list sans le dernier coup
	 */
	public ArrayList<CoupSave> listSansDernierCoup(){
		this.list.remove(this.list.size()-1);
		return list;
	}
	
	/**
	 * Test si la list est vide
	 * @return true si vide
	 */
	public boolean isEmpty(){
		return list.isEmpty();
	}
}
