package theotherhattrick;

import java.io.Serializable;

/**
 * Strategie qui fait prendre le plus de risques au joueur
 *
 */
public class StratRisky implements Decision, Serializable {

	private static final long serialVersionUID = 8293653320471985815L;
	
	/**
	 * Le robot s'inspire de cette strategie pour faire de coups
	 */
	StratDefault sd;
	//Dans cette strat�gie, le joueur va toujours essaier de faire le trick qui est retourn�
	//Il va essayer de prendre les cartes les plus rares aux autres joueurs
	//Il ne retourne jamais le tas de tricks
	
	
	/**
	 * Constructeur
	 */
	public StratRisky() {
		this.sd = new StratDefault();
	}

	/**
	 * Détermine si le robot va retourner un nouveau Trick
	 * 
	 * @return le choix du robot
	 */
	public boolean revealNewTrick() {
		return false;
	}

	/**
	 * Détermine quel prop le joueur veut echanger
	 * 
	 * @return le choix du robot
	 */
	public int chooseOwnProp() {
		return sd.chooseOwnProp();
	}

	/**
	 * Détermine avec quel prop le joueur veut echanger le sien
	 * 
	 * @param players la liste des joueurs
	 * @return le choix du robot
	 */
	public int chooseOtherProp(Player[] players) {
		return sd.chooseOtherProp(players);
	}

	/**
	 * Détermine si le joueur veut performer le trick
	 * 
	 * @return le choix du robot
	 */
	public boolean performTrick() {
		return true;
	}

	/**
	 * Détermine quel prop le joueur veut retourner
	 * 
	 * @return le choix du robot
	 */
	public int revealProp() {
		return sd.revealProp();
	}

	/**
	 * Détermine quel prop le joueur veut echanger, et lequel il veut garder
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddle() {
		return sd.chooseMiddle();
	}

	/**
	 * Détermine quel prop le joueur veut echanger quand il joue avec la variante Carrot
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddleVarCarrot(Player[] players) {
		return sd.chooseMiddleVarCarrot(players);
	}

}
