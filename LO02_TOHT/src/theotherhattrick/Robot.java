package theotherhattrick;

import java.io.Serializable;

/**
 * Classe qui gère les joueurs dotes d'intelligence artificielle
 *
 */
public class Robot extends Player implements Decision, Serializable {

	private static final long serialVersionUID = 8125673734326190507L;
	/**
	 * Pattern Strategy, permet de connaitre les choix du robot
	 */
	private Decision strategy;

	/**
	 * La chance qu'a le robot de choisir un choix de stratégie au hasard
	 */
	private double tiltLevel = 0;

	/**
	 * Stocke le choix que les stratégies donnent au robot
	 */
	private int choice;

	/**
	 * Stocke le choix que les stratégies donnent au robot
	 */
	private boolean choiceBool;

	/**
	 * Constructeur
	 * 
	 * @param birthDate la date de naissance du robot
	 */
	public Robot(Date birthDate) {
		super("Robot", birthDate);
		strategy = new StratDefault();
	}

	/**
	 * @return le choix que le robot a fait
	 */
	public int getChoice() {
		return this.choice;
	}

	/**
	 * @return le choix que le robot a fait
	 */
	public boolean getChoiceBool() {
		return this.choiceBool;
	}

	/**
	 * Décide la stratégie à adopter. Si c'est le meilleur joueur, il joue de
	 * manière risquée. Si c'est le pire joueur, il jouera de manière conservative
	 * Sinon, il joue la stratégie de base
	 */
	public void adaptStrategy() {

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
			if (madness < 0.5) {
				this.strategy = new StratRisky();
			} else {
				this.strategy = new StratConservative(hand, id);
			}
			this.tiltLevel = 0;
		}

	}

	/**
	 * Détermine si le robot va retourner un nouveau Trick Adapte aussi la stratégie
	 * 
	 * @return le choix du robot
	 */
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

	/**
	 * Détermine quel prop le joueur veut echanger
	 * 
	 * @return le choix du robot
	 */
	public int chooseOwnProp() {
		choice = strategy.chooseOwnProp();
		this.setChanged();
		this.notifyObservers("prop chosen");
		return choice;
	}
	
	/**
	 * Détermine avec quel prop le joueur veut echanger le sien
	 * 
	 * @param players la liste des joueurs
	 * @return le choix du robot
	 */
	public int chooseOtherProp(Player[] players) {
		choice = strategy.chooseOtherProp(players);
		this.setChanged();
		this.notifyObservers("other prop chosen");
		return choice;
	}

	/**
	 * Détermine si le joueur veut performer le trick
	 * 
	 * @return le choix du robot
	 */
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

	/**
	 * Détermine quel prop le joueur veut retourner
	 * 
	 * @return le choix du robot
	 */
	public int revealProp() {
		choice = strategy.revealProp();
		this.setChanged();
		this.notifyObservers("reveal prop");
		return choice;
	}

	/**
	 * Détermine quel prop le joueur veut echanger, et lequel il veut garder
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddle() {
		choice = strategy.chooseMiddle();
		this.setChanged();
		this.notifyObservers("choose middle");

		return choice;
	}

	/**
	 * Détermine quel prop le joueur veut echanger quand il joue avec la variante Carrot
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddleVarCarrot(Player[] players) {
		choice = strategy.chooseMiddleVarCarrot(players);
		this.setChanged();
		this.notifyObservers("choose middle var");

		return choice;
	}
}