package theotherhattrick;

import java.util.ArrayList;
import java.util.Collections;

public class Trick {
	String name;
	int points;
	PropEnum[][] ingredients = {{} , {}};
	
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
	}
	
	public void print() {
		System.out.println("<" + this.name + "> :");
		for (int i = 0; i < ingredients.length; i++) {
			for (int j = 0; j < ingredients[i].length; j++) {
				System.out.println("[" + ingredients[i][j].getName() + "]");
			}
			System.out.println();
		}
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