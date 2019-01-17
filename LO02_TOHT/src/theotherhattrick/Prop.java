package theotherhattrick;

import java.io.Serializable;
import java.util.Observable;

@SuppressWarnings("deprecation")
/**
 * Prop est un type d'objets observables représentant les cartes accessoires.
 * @author amall
 * @see PropEnum
 */
public class Prop extends Observable implements Serializable{
	private static final long serialVersionUID = -5690187113774987887L;
	private String name;
	private int type;
	private boolean isVisible;

	/**
	 * On génère les Props à partir des valeurs de l'énumération PropEnum
	 * 
	 * @param prop
	 * @see PropEnum
	 */
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
	}

	public void unhide() {
		this.isVisible = true;
	}

	public boolean getState() {
		return this.isVisible;
	}
	
	public void setState(boolean state) {
		this.isVisible = state;
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
		System.out.println("nom : " + this.name + " type : " + this.type + " visible : " + this.isVisible);
	}

	public void print() {
		System.out.println("nom : " + this.name);
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
