package theotherhattrick;

/**
 * Interface qui définit les différentes fonctionnalités que doivent implémenter les joueurs.
 * Permet d'implémenter le patron de conception Stratégie pour le robot.
 * @author amall
 * @see Player
 * @see Human
 * @see Robot
 * @see StratConservative
 * @see StratDefault
 * @see StratRisky
 */
public interface Decision {

	public int chooseMiddleVarCarrot(Player[] players);
	public int chooseOtherProp(Player[] players);
	public boolean revealNewTrick();
	public int chooseOwnProp();
	public boolean performTrick();
	public int chooseMiddle();
	public int revealProp();
}
