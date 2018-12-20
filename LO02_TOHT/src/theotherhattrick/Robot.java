package theotherhattrick;

public class Robot extends Player implements Decision {

	private Decision strategy;
	private double tiltLevel = 0; // La chance qu'a le robot de faire un choix de stratégie au hasard

	// On implémente décision pour étre sur que les méthodes que l'on apelle sont
	// présentes dans l'interface

	public Robot(int id, Date birthDate) {
		super("Robot" + id, id, birthDate);
		// strategy = new StratConservative(this.hand, this.id);
		strategy = new StratDefault();
	}

	public void adaptStrategy() {
		// décide la stratégie à adopter.
		// Si c'est le meilleur joueur, il joue de manière risquée.
		// Si c'est le pire joueur, il jour de manière conservative.
		// Sinon, il joue la stratégie de base
		if (this.id == Game.getBestPlayer()) {
			this.strategy = new StratRisky();
			System.out.println("Le robot " + this.name + " est passé à la stratégie risquée");
		} else if (this.id == Game.getWorstPlayer()) {
			this.strategy = new StratConservative(hand, id);
			this.tiltLevel += 0.2;
			System.out.println("Le robot " + this.name + " est passé à la stratégie conservative");
		} else {
			this.strategy = new StratDefault();
			this.tiltLevel += 0.1;
			System.out.println("Le robot " + this.name + " est passé à la stratégie de base");
		}

		if (Math.random() < this.tiltLevel) {
			System.out.println("Le robot devient fou et choisit une stratégie au hasard");
			double madness = Math.random();
			if(madness < 0.5) {
				this.strategy = new StratRisky();
			}
			else {
				this.strategy = new StratConservative(hand, id);
			}
			this.tiltLevel = 0;
		}

	}

	public boolean revealNewTrick() {
		this.adaptStrategy();

		boolean choice = strategy.revealNewTrick();
		if (choice) {
			System.out.println("Le robot a choisi de retourner un nouveau trick");
		} else {
			System.out.println("Le robot a refusé de retourner un nouveau trick");
		}
		return choice;
	}

	public int chooseOwnProp() {
		int choice = strategy.chooseOwnProp();
		System.out.println("Le robot a choisi de retourner son prop numéro " + choice);
		return choice;
	}

	public int chooseOtherProp(Player[] players) {
		int choice = strategy.chooseOtherProp(players);
		System.out.println("Le robot a choisi de retourner le prop numéro " + choice);
		return choice;
	}

	public boolean performTrick() {
		boolean choice = strategy.performTrick();
		if (choice) {
			System.out.println("Le robot a choisi de réaliser le trick");
		} else {
			System.out.println("Le robot a refusé de réaliser le trick");
		}
		return choice;
	}

	public int revealProp() {
		int choice = strategy.revealProp();
		System.out.println("Le robot a choisi de retourner son prop numéro " + choice);
		return choice;
	}

	public int chooseMiddle() {
		int choice = strategy.chooseMiddle();
		System.out.println("Le robot a choisi le prop numéro " + choice);
		return choice;
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		int choice = strategy.chooseMiddleVarCarrot(players);
		System.out.println("Le robot a choisi le prop numéro " + choice);
		return choice;
	}
}