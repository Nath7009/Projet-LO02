package theotherhattrick;

import java.io.Serializable;


@SuppressWarnings("deprecation")
public class Robot extends Player implements Decision, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125673734326190507L;
	private Decision strategy;
	private double tiltLevel = 0; // La chance qu'a le robot de faire un choix de stratégie au hasard
	private int choice;
	private boolean choiceBool;
	// On implémente décision pour étre sur que les méthodes que l'on apelle sont
	// présentes dans l'interface

	public Robot(Date birthDate) {
		super("Robot", birthDate);
		// strategy = new StratConservative(this.hand, this.id);
		strategy = new StratDefault();
	}
	
	
	public int getChoice() {
		return this.choice;
	}
	
	public boolean getChoiceBool() {
		return this.choiceBool;
	}

	
	public void adaptStrategy() {
		// décide la stratégie à adopter.
		// Si c'est le meilleur joueur, il joue de manière risquée.
		// Si c'est le pire joueur, il jour de manière conservative.
		// Sinon, il joue la stratégie de base
		if (this.id == Game.getBestPlayer()) {
			this.strategy = new StratRisky();
			this.setChanged();
			this.notifyObservers("strat risky");
		} else if (this.id == Game.getWorstPlayer()) {
			this.strategy = new StratConservative(hand, id);
			this.tiltLevel += 0.2;
			this.setChanged();
			this.notifyObservers("strat conservative");
		} else {
			this.strategy = new StratDefault();
			this.tiltLevel += 0.1;
			this.setChanged();
			this.notifyObservers("basic strat");
			
		}

		if (Math.random() < this.tiltLevel) {
			this.setChanged();
			this.notifyObservers("gone crazy");
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

		choiceBool = strategy.revealNewTrick();
		this.setChanged();
		if (choiceBool) {
			this.notifyObservers("reveal new trick");
			
		} else {
			this.notifyObservers("don't reaveal new trick");
			
		}
		return choiceBool;
	}

	public int chooseOwnProp() {
		choice = strategy.chooseOwnProp();
		this.setChanged();
		this.notifyObservers("prop chosen");
		return choice;
	}

	public int chooseOtherProp(Player[] players) {
		choice = strategy.chooseOtherProp(players);
		this.setChanged();
		this.notifyObservers("other prop chosen");
		return choice;
	}

	public boolean performTrick() {
		choiceBool = strategy.performTrick();
		this.setChanged();
		if (choiceBool) {
			this.notifyObservers("perform trick");
			
		} else {
			this.notifyObservers("don't perform trick");
			
		}
		return choiceBool;
	}

	public int revealProp() {
		choice = strategy.revealProp();
		this.setChanged();
		this.notifyObservers("reveal prop");
		return choice;
	}

	public int chooseMiddle() {
		choice = strategy.chooseMiddle();
		this.setChanged();
		this.notifyObservers("choose middle");
		
		return choice;
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		choice = strategy.chooseMiddleVarCarrot(players);
		this.setChanged();
		this.notifyObservers("choose middle var");
		
		return choice;
	}
}