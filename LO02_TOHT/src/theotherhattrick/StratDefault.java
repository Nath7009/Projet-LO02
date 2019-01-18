package theotherhattrick;

import java.io.Serializable;

/**
 * Strategie qui prend des decisions au hasard
 *
 */
public class StratDefault implements Decision, Serializable {

	private static final long serialVersionUID = 3367408643741394896L;

	/**
	 * Détermine si le robot va retourner un nouveau Trick
	 * 
	 * @return le choix du robot
	 */
	public boolean revealNewTrick() {
		// On multiplie 2 Math.random pour que les parties soient plus longues
		return Math.random() * Math.random() * Math.random() >= 0.5 ? true : false;
	}

	/**
	 * Détermine quel prop le joueur veut echanger
	 * 
	 * @return le choix du robot
	 */
	public int chooseOwnProp() {
		return Math.random() >= 0.5 ? 0 : 1;
	}

	/**
	 * Détermine avec quel prop le joueur veut echanger le sien
	 * 
	 * @param players la liste des joueurs
	 * @return le choix du robot
	 */
	public int chooseOtherProp(Player[] players) {
		int choice = (int) Math.floor(Math.random() * 4);
		return choice;
	}

	/**
	 * Détermine si le joueur veut performer le trick
	 * 
	 * @return le choix du robot
	 */
	public boolean performTrick() {
		return Math.random() >= 0.5 ? true : false;
	}

	/**
	 * Détermine quel prop le joueur veut retourner
	 * 
	 * @return le choix du robot
	 */
	public int revealProp() {
		return Math.random() >= 0.5 ? 0 : 1;
	}

	/**
	 * Détermine quel prop le joueur veut echanger, et lequel il veut garder
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddle() {
		int choice = (int) Math.floor(Math.random() * 3);
		return choice;
	}

	/**
	 * Détermine quel prop le joueur veut echanger quand il joue avec la variante Carrot
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddleVarCarrot(Player[] players) {
		int choice = (int) Math.floor(Math.random() * 5);
		return choice;
	}

}
