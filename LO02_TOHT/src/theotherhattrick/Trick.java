package theotherhattrick;

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
}