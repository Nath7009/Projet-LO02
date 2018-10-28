package theotherhattrick;

public class Prop {

	private int type; // Carrot = 1, Lettuce = 2, Hat = 3, Rabbit = 4, Other rabbit = 5
	private String name;
	private boolean isVisible; // false pour carte cachée et true pour carte visible

	public Prop(String name, int type) {
		this.name = name;
		this.type = type;
		this.isVisible = false;
	}

	public void hide() {
		this.isVisible = false;
	}

	public void unhide() {
		this.isVisible = true;
	}

	public boolean getState() {
		return this.isVisible;
	}

	public String getName() {
		return this.name;
	}

	public Prop clone() {
		try {
			return (Prop) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void print() {
		System.out.println("nom : " + this.name + " type : " + this.type + " caché ? :" + this.isVisible);
	}

}
