package theotherhattrick;

public class Robot extends Player implements Decision {

	private Decision strategy;

	// On implémente décision pour étre sur que les méthodes que l'on apelle sont
	// présentes dans l'interface

	public Robot(int id, Date birthDate) {
		super("Robot" + id, id, birthDate);
		strategy = new StratConservative(this.hand, this.id);
	}

	public void adaptStrategy() {
		// décide la stratégie à adopter.
	}

	public boolean revealNewTrick() {
		return strategy.revealNewTrick();
	}

	public int chooseOwnProp() {

		return strategy.chooseOwnProp();
	}

	public int chooseOtherProp(Player[] players) {
		return strategy.chooseOtherProp(players);
	}

	public boolean performTrick() {
		return strategy.performTrick();
	}

	public int revealProp() {
		return strategy.revealProp();
	}

	public int chooseMiddle() {
		return strategy.chooseMiddle();
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		return strategy.chooseMiddleVarCarrot(players);
	}
}