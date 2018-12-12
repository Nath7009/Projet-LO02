package theotherhattrick;

public enum TrickEnum {
	
	THE_HUNGRY_RABBIT("The Hungry Rabbit", new PropEnum[][] {{PropEnum.RABBIT, PropEnum.THE_OTHER_RABBIT} , {PropEnum.CARROT, PropEnum.LETTUCE}}, 1),
	THE_BUNCH_OF_CARROTS("The Bunch of Carrots", new PropEnum[][] {{PropEnum.CARROT} , {PropEnum.CARROT}}, 2), 
	THE_VEGETABLE_PATCH("The vegetable Patch", new PropEnum[][] {{PropEnum.CARROT} , {PropEnum.LETTUCE}}, 3),
	THE_RABBIT_THAT_DIDNT_LIKE_CARROTS("The Rabbit That Didn't Like Carrots", new PropEnum[][] {{PropEnum.RABBIT, PropEnum.THE_OTHER_RABBIT} , {PropEnum.LETTUCE}}, 4),
	THE_PAIR_OF_RABBITS("The Pair of Rabbits", new PropEnum[][] {{PropEnum.RABBIT} , {PropEnum.THE_OTHER_RABBIT}}, 5),
	THE_VEGETABLE_HAT_TRICK("The Vegetable Hat Trick", new PropEnum[][] {{PropEnum.HAT} , {PropEnum.CARROT, PropEnum.LETTUCE}}, 2),
	THE_CARROT_HAT_TRICK("The Carrot Hat Trick", new PropEnum[][] {{PropEnum.HAT} , {PropEnum.CARROT}}, 3),
	THE_SLIGHTLY_EASIER_HAT_TRICK("The Slightly Easier Hat Trick", new PropEnum[][] {{PropEnum.HAT} , {PropEnum.RABBIT, PropEnum.THE_OTHER_RABBIT}}, 4),
	THE_HAT_TRICK("The Hat Trick", new PropEnum[][] {{PropEnum.HAT} , {PropEnum.RABBIT}}, 5),
	
	THE_OTHER_HAT_TRICK("The Other Hat Trick", new PropEnum[][] {{PropEnum.HAT} , {PropEnum.THE_OTHER_RABBIT}}, 6); 
	
	String name = "";
	int points = 0;
	PropEnum[][] ingredients = {{} , {}};
	
	TrickEnum(String name, PropEnum[][] ingredients, int points){
		this.name = name;
		this.points = points;
		this.ingredients = ingredients;
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
