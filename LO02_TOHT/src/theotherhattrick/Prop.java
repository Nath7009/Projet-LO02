package theotherhattrick;


public class Prop {
	private String name;
	private int type;
	private boolean isVisible;
	
	public Prop(PropEnum prop) {
		this.name = prop.getName();
		this.type = prop.getType();
		this.isVisible = false;
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
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
	
	public void printDebug() {
		System.out.println("nom : " + this.name + " type : " + this.type + " visible : " + this.isVisible);
	}
	
	public void print() {
		System.out.println("nom : " + this.name);
	}
	
	public String toString() {
		return "[" + name + (isVisible == false? " -> caché " : " -> visible") + "]";
	}
	
	public void printIfVisible() {
		System.out.println(isVisible == true ? this.toString() : "[?????]");
	}

}
