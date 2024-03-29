package theotherhattrick;


/**
 * Enumération qui répertorie les différentes cartes du jeux.
 * Par défaut, les cartes sont cachées.
 * @author amall
 *
 */
public enum PropEnum {
	
	CARROT("Carrot", 1, false),
	LETTUCE("Lettuce", 2, false),
	HAT("Hat", 3, false),
	RABBIT("Rabbit", 4, false),
	THE_OTHER_RABBIT("The Other Rabbit", 5, false), 
	SWISS_ARMY_KNIFE("Swiss Army Knife", 6, false);
	
	private String name ="";
	private int type = 0;
	private boolean isVisible = false;
	
	PropEnum(String name, int type, boolean isVisible){
		this.name  = name; 
		this.type = type;
		this.isVisible = false;
	}
	
	public boolean getState() {
		return this.isVisible;
	}

	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public void printDebug() {
		System.out.println("nom : " + this.name + " type : " + this.type + " visible : " + this.isVisible);
	}
	
	public void print() {
		System.out.println("nom : " + this.name);
	}
	
	public String toString() {
		return "[" + name + (isVisible == false? " -> caché" : " -> visible") + "]";
	}
	
	public void printIfVisible() {
		System.out.println(isVisible == true ? this.toString() : "[?????]");
	}
}
