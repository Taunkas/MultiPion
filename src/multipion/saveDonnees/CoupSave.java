package multipion.saveDonnees;

import multipion.jeu.pion.Piece;
import multipion.utils.Coordonnee;

public class CoupSave {
	
	
	/**
	 * Notation de la piece
	 */
	public char nomPiece;
	
	/**
	 * Coordonnee de depart du coup
	 */
	public Coordonnee depart;
	
	/**
	 * Coordonnee d'arrivee du coup
	 */
	public Coordonnee arrivee;
	
	/**
	 * Coordonnee memoire d'arrivee du coup
	 */
	public Coordonnee departMemoire;
	
	/**
	 * Coup est une prise
	 */
	public boolean isPrise;
	

	/**
	 * Representation du coup au format PGN
	 */
	public String representationString;
	
	/**
	 * Reference de la piece joue
	 */
	public Piece referencePiece;
	
	/**
	 * Constructeur
	 */
	public CoupSave(){
		
		this.isPrise = false;
		this.nomPiece = 'Z';
		this.depart = new Coordonnee(-1);
		this.departMemoire = new Coordonnee(-1);
		this.arrivee = new Coordonnee(-1);
		
		this.representationString = "";
		this.referencePiece = null;
	}
	
	/**
	 * Constructeur a partir d'une notation PGN
	 * @param coupString notation PGN du coup
	 */
	public CoupSave(String coupString){
		this();
		this.representationString = coupString;
		
		try{
		
			//Cas d'une attaque
			if(coupString.length() >= 1 && coupString.charAt(0) == 'x'){
				this.isPrise = true;
				coupString = coupString.substring(1);
			}
			
			//Coordonnee de deplacement
			if(isValidChar(coupString.charAt(0))){
				this.arrivee.x = convertionCharEnInt(coupString.charAt(0));
			}else{
				System.out.println("erreur coup");
			}
			
			if(isValidInt(coupString.charAt(1))){
				this.arrivee.y = Integer.parseInt(coupString.charAt(1)+"")-1;
			}else{
				System.out.println("erreur coup");
			}
			coupString = coupString.substring(2);
			

			

		}catch(Exception e){
			System.out.println("erreur coup");
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifie si la notation du coup est valide
	 * @param coup coup a verifier
	 * @return vrai si notation valide
	 */
	public static boolean notationValide(String coup){
		//Taille minimale
		if(coup.length() < 2){
			return false;
		}
		
		//Recherche de la piece
		if(coup.charAt(0) == 'O'){
			if(coup.startsWith("O-O-O")){
				return true;
			}else if(coup.startsWith("O-O")){
				return true;
			}else{
				return false;
			}
		}else if(coup.charAt(0) == 'K' || coup.charAt(0) == 'Q' || coup.charAt(0) == 'B' || coup.charAt(0) == 'N' || coup.charAt(0) == 'R'){
			coup = coup.substring(1);
		}
		
		//Recherche prerequis
		if(coup.length() >= 3 && isValidChar(coup.charAt(0)) && isValidInt(coup.charAt(1)) && (coup.charAt(2) == 'x' || isValidChar(coup.charAt(2)))){
			coup = coup.substring(2);
		}else if(coup.length() >= 2 && isValidChar(coup.charAt(0)) && (coup.charAt(1) == 'x' || isValidChar(coup.charAt(1)))){
			coup = coup.substring(1);
		}else if(coup.length() >= 2 && isValidInt(coup.charAt(0)) && (coup.charAt(1) == 'x' || isValidChar(coup.charAt(1)))){
			coup = coup.substring(1);
		}else if(coup.length() == 0){
			return false;
		}
		
		//Cas d'une attaque
		if(coup.length() >= 1 && coup.charAt(0) == 'x'){
			coup = coup.substring(1);
		}
		
		//Coordonnee de deplacement
		if(coup.length() == 0 || !isValidChar(coup.charAt(0))){
			return false;
		}
		coup = coup.substring(1);
		
		if(coup.length() == 0 || !isValidInt(coup.charAt(0))){
			return false;
		}
		coup = coup.substring(1);
		


		
		if(coup.length() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Converti le coup au format PGN
	 * @return String
	 */
	public String formatPGN(){
		String format = "";
		
		//Recherche de la piece
		if(this.nomPiece != ' '){
			
			format += this.nomPiece;
		}
		
		//Recherche de prerequis
		if(depart.x != -1 && depart.y != -1){
			format += convertionIntEnChar(depart.x) +""+ depart.y;
		}else if(depart.x != -1){
			format += convertionIntEnChar(depart.x);
		}else if(depart.y != -1){
			format += depart.y+1;
		}
		
		//Cas d'une attaque
		if(this.isPrise){
			format += 'x';
		}
		
		//Coordonnee de deplacement
		if(this.arrivee.x != -1){
			format += convertionIntEnChar(arrivee.x);
		}
		
		if(this.arrivee.y != -1){
			format += arrivee.y+1;
		}
		
	
		this.representationString = format;
		return format;
	}
	
	/**
	 * Ajoute les prerequis du coup
	 * @param valeur
	 */
	public void setPrerequis(int valeur){
		
		if(valeur / 100 > 0){
			depart.x = departMemoire.x;
		}
		valeur %= 100;
		if(valeur / 10 > 0){
			depart.y = departMemoire.y+1;
			
		}
	}
	
	/**
	 * Test si la notation d'un char est une coordonnee valide
	 * @param c
	 * @return boolean
	 */
	private static boolean isValidChar(char c){
		for(char i = 'a'; i <= 'j'; i++){
			if(c == i) return true;
		}
		return false;
	}
	
	/**
	 * Test si la notation d'un int est une coordonnee valide
	 * @param c
	 * @return boolean
	 */
	private static boolean isValidInt(char c){
		for(char i = '1'; i <= '8'; i++){
			if(c == i) return true;
		}
		return false;
	}
	
	/**
	 * Convertion d'une notation d'une coordonnee en char en notation en int
	 * @param c
	 * @return int
	 */
	private static int convertionCharEnInt(char c){
		int valeur =0;
		for(char i = 'a'; i <= 'j'; i++){
			if(c == i) return valeur;
			valeur++;
		}
		return -1;
	}
	
	/**
	 * Convertion d'une notation d'une coordonnee en int en notation en char
	 * @param a
	 * @return char
	 */
	private static char convertionIntEnChar(int a){
		int valeur = 0;
		for(char i = 'a'; i <= 'j'; i++){
			if(a == valeur) return i;
			valeur++;
		}
		return 'Z';
	}
	
	/**
	 * Selectionne la bonne notation de la piece selon la famille de la piece
	 * @param nom
	 */
	public void setNomPiece(String nom){
		
			this.nomPiece = ' ';
		
	}
	
	/**
	 * Return la representation en string du coup (notation PGN)
	 * @return String
	 */
	public String toString(){
		return this.formatPGN();
	}
	
	/**
	 * Reprensention en String pour du debug
	 * @return String
	 */
	public String variablesToString(){
		return "Coup "+representationString+"  "+nomPiece+" "+depart.toString()+" -> "+arrivee.toString()+" memoire:"+departMemoire.toString();
	}
	
}
