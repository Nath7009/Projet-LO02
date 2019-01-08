package theotherhattrick;

import java.io.Serializable;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Prop extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5690187113774987887L;
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
	
	public int getTypeSecure() {
		if(this.isVisible) {
			return this.getType();
		}
		return 0;
	}

	public String getName() {
		return name;
	}

	public void hide() {
		this.isVisible = false;
		this.setChanged();
		this.notifyObservers("hide");
	}

	public void unhide() {
		this.isVisible = true;
		this.setChanged();
		this.notifyObservers("unhide");
	}

	public boolean getState() {
		return this.isVisible;
	}

	public int compareRarity(Prop other) {
		if (this.type > other.getTypeSecure()) {
			return 1;
		} else if (this.type < other.getTypeSecure()) {
			return -1;
		}
		return 0;
	}

	public void printDebug() {
		this.setChanged();
		this.notifyObservers("print debug");
//		System.out.println("nom : " + this.name + " type : " + this.type + " visible : " + this.isVisible);
	}

	public void print() {
		this.setChanged();
		this.notifyObservers("print");
//		System.out.println("nom : " + this.name);
	}

	public String toString() {
		return "[" + name + (isVisible == false ? " -> caché " : " -> visible") + "]";
	}

	public String toStringIfVisible() {
		return isVisible == true ? this.toString() : "[?????]";
	}

	public void printIfVisible() {
		this.setChanged();
		this.notifyObservers("print visible");
	}

	public Prop getLowest(Prop p) {
		// Si les deux props ont la même valeur, renvoie soi même
		if (this.type >= p.type) {
			return this;
		}
		return p;
	}

	public Prop getHighest(Prop p) {
		// Si les deux props ont la même valeur, renvoie soi même
		if (this.type <= p.type) {
			return this;
		}
		return p;
	}

}
