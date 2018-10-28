package theotherhattrick;

public class Trick {
	private Prop[][] ingredients;

	private int points;

	private String name;

	public Trick(String name, Prop[][] ingredients, int points) {
		this.name = name;
		this.ingredients = ingredients;
		this.points = points;
	}

	public void print() {
		System.out.println(this.name + " composé de :");
		for (int i = 0; i < ingredients.length; i++) {
			for (int j = 0; j < ingredients[i].length; j++) {
				ingredients[i][j].print();
			}
			System.out.println();
		}
	}

	public Trick clone() {
		try {
			return (Trick) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
