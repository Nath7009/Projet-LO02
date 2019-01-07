package theotherhattrick;

import java.io.Serializable;
import java.util.Scanner;

/**
 * 
 * @author amall
 *
 */
@SuppressWarnings("deprecation")
public class Human extends Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -681790841132521940L;

	public Human(String name, Date birthDate) {
		super(name, birthDate);
	}
	
	
	public boolean revealNewTrick() {
		
		this.setChanged();
		this.notifyObservers("revealNewTrick");
		
		return this.newTrick;
	}
	
	public int chooseOwnProp() {
		
		this.setChanged();
		this.notifyObservers("chooseOwnProp");

		return this.ownProp;
	}
	
	public int chooseOtherProp(Player[] players) {
		
		this.setChanged();
		this.notifyObservers("chooseOtherProp");
		
		return this.otherProp;
	}
	
	public int chooseMiddleVarCarrot(Player[] players) {
		
		this.setChanged();
		this.notifyObservers("chooseMiddleVarCarrot");
		
		return middleVarCarrot;
	}
	
	public boolean performTrick() {
		
		this.setChanged();
		this.notifyObservers("performTrick");
				
		return this.performTrick;
	}
	
	public int revealProp() {
		
		this.setChanged();
		this.notifyObservers("revealProp");
		
		return this.revealProp;
	}
	
	public int chooseMiddle() {
		
		this.setChanged();
		this.notifyObservers("chooseMiddle");
		
		return this.middleProp;
	}	
}
