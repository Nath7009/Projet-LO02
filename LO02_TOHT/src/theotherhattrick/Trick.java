package theotherhattrick;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.Observable;


public class Trick extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8559185521054700937L;
	String name;
	int points;
	PropEnum[][] ingredients = {{} , {}};
	int lifeLength = 0;
	
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
	
	public void decreaseValue() {
		this.points--;
		this.setChanged();
		this.notifyObservers("value decreased");
	}
	
	public void age() {
		this.lifeLength++;
	}
	
	public boolean isTooOld() {
		return this.lifeLength > 5;
	}
	
	public void print() {
		this.setChanged();
		this.notifyObservers("print trick");
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
	
	public boolean contains(Prop prop) {
		boolean match = false;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < this.getLength(i); j++) {
				match = prop == new Prop(this.getIngredient(i, j)) ? true : false;
			}
		}
		return match;
	}
	
	public int getMatchingTricks(ArrayList<Prop> hand) {
		//Retourne le nombre de tricks en commun avec le trick courant
		int matching = 0;

		if(compareToProps(hand)) {
			return 2;
		}
		
		for(Iterator<Prop> it = hand.iterator();it.hasNext();) {
			if(this.contains(it.next())) {
				matching++;
			}
		}
		//Si on a trouvé plus de 2 cartes qui fonctionnent et que compareToProps n'a pas renvoyé vrai,
		//Il est impossible que l'on pait plus de 2 matchs
		if(matching>=2) {
			matching = 1;
		}
		return matching;
	}
	
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