package theotherhattrick;

import java.io.Serializable;
import java.util.Scanner;

import theotherhattrickView.VueTexte;

/**
 * Classe représentant les joueurs humains. Pour interagir avec l'utilisateur, elle implémente le patron Observer/Observable pour notifier les vues des actions à réaliser.
 * Elle spécifie les méthodes de l'interface Decision.
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
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public boolean revealNewTrick() {
		
		this.setChanged();
		this.notifyObservers("revealNewTrick");
		
		return this.newTrick;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public int chooseOwnProp() {
		
		this.setChanged();
		this.notifyObservers("chooseOwnProp");

		return this.ownProp;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public int chooseOtherProp(Player[] players) {
		
		this.setChanged();
		this.notifyObservers("chooseOtherProp");
		
		return this.otherProp;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public int chooseMiddleVarCarrot(Player[] players) {
		
		this.setChanged();
		this.notifyObservers("chooseMiddleVarCarrot");
		
		return middleVarCarrot;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public boolean performTrick() {
		
		this.setChanged();
		this.notifyObservers("performTrick");
				
		return this.performTrick;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public int revealProp() {
		
		this.setChanged();
		this.notifyObservers("revealProp");
		
		return this.revealProp;
	}
	
	/**
	 * notifie les Observers qu'une action doit être réalisée
	 * 
	 * @see VueTexte
	 */
	public int chooseMiddle() {
		
		this.setChanged();
		this.notifyObservers("chooseMiddle");
		
		return this.middleProp;
	}	
}
