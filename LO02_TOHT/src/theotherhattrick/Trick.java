package theotherhattrick;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.Observable;

@SuppressWarnings("deprecation")
/**
 * Classe représentant les cartes tour de magie qui sont à réaliser au cours de la partie.
 * Les objets Tricks sont générés à partir de l'énumération TrickEnum. Les Tricks sont des objets Observable
 * 
 * @see TrickEnum
 */
public class Trick extends Observable implements Serializable {

	private static final long serialVersionUID = 8559185521054700937L;
	String name;
	int points;
	PropEnum[][] ingredients = {{} , {}};
	int lifeLength = 0;
	
	/**
	 * Le constructeur de la classe Trick utilise les valeurs du TrickEnum correspondant pour son initialisation
	 * @param trick
	 */
	public Trick(TrickEnum trick){
		this.name = trick.getName();
		this.points = trick.getPoints();
		this.ingredients = trick.getIngredients();
	}
	
	public PropEnum[][] getIngredients() {
		return ingredients;
	}
	public PropEnum getIngredient(int indRL, int ind) {
		return ingredients[indRL][ind];
	}

	public int getLength(int ind) {
		return ingredients[ind].length;
	}
	
	/**
	 * La variante du couteau suisse implique parfois une perte de valeur des Props
	 * 
	 * @see GameVarSwissKnife
	 */
	public void decreaseValue() {
		this.points--;
		this.setChanged();
		this.notifyObservers("value decreased");
	}
	
	public void print() {
		System.out.println("Trick : <" + name + ">");
		for(int i = 0; i < ingredients.length ; i++) {
			for(int j = 0; j < ingredients[i].length ; j++) {
				ingredients[i][j].print();
			}
			System.out.println("\n");
		}
	}
	
	public void age() {
		this.lifeLength++;
	}
	
	public boolean isTooOld() {
		return this.lifeLength > 5;
	}
	
	
	public String toString() {
		return name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPoints() {
		return points;
	}
	
	/**
	 * Test si le prop passé en paramètre est un ingrédient du Trick.
	 * @param prop
	 * @return 
	 */
	public boolean contains(Prop prop) {
		boolean match = false;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < this.getLength(i); j++) {
				match = prop == new Prop(this.getIngredient(i, j)) ? true : false;
			}
		}
		return match;
	}
	
	/**
	 * Compte le nombre de Prop en commun entre la main d'un joueur et les ingrédients du Trick.
	 * @param hand
	 * @return
	 */
	public int getMatchingTricks(ArrayList<Prop> hand) {
		int matching = 0;

		if(compareToProps(hand)) {
			return 2;
		}
		
		for(Iterator<Prop> it = hand.iterator();it.hasNext();) {
			if(this.contains(it.next())) {
				matching++;
			}
		}
		if(matching>=2) {
			matching = 1;
		}
		return matching;
	}
	
	/**
	 * Détermine si un joueur peut réaliser le Trick. Compare les cartes de la main l'une après l'autre aux ingrédients du Trick. 
	 * Dès qu'un Prop de la main ne figure pas parmi les ingrédients, on retourne faux.
	 * @param hand la main d'un joueur
	 * @return renvoie true si la main permet de réaliser le Trick, false sinon. 
	 */
	public boolean compareToProps(ArrayList<Prop> hand) {
		int ind1 = 0, ind2 = 0, ind = (hand.contains(new Prop(PropEnum.SWISS_ARMY_KNIFE)) ? hand.indexOf(new Prop(PropEnum.SWISS_ARMY_KNIFE)) : 0);
		boolean card1 = false, card2 = (hand.contains(new Prop(PropEnum.SWISS_ARMY_KNIFE)) ? true : false);
		Prop tProp;
		
		while(card1 == false && ind1 < 2) {
			ind2 = 0;
			while(card1 == false && ind2 < this.getLength(ind1)) {
				tProp = new Prop(this.getIngredient(ind1, ind2));
				if(hand.get((ind+1)%2).getType() == tProp.getType()) {
					card1 = true;
				}
				ind2++;
			}
			ind1++;
		}
		
		if(card1 == true && card2 == false) { // Si on a pas le couteau suisse et que notre premier prop est bon
			ind2 = 0;
			ind1 = ind1% 2; // on va regarder les props du trick encore libres
			while(card2 == false && ind2 < this.getLength(ind1)) {
				tProp = new Prop(this.getIngredient(ind1, ind2));
				if(hand.get(ind).getType() == tProp.getType()) {
					card2 = true;
				}
				ind2++;
			}
		}
		
		if(card1 == true && card2 == true) {
			return true;
		}
		else{
			return false;
		}
	}
}