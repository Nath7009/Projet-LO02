package theotherhattrick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Strategie qui permet au robot de ne pas prendre de risques
 *
 */
public class StratConservative implements Decision, Serializable {

	private static final long serialVersionUID = 5665304101790267834L;

	/**
	 * Les cartes du joueur
	 */
	private ArrayList<Prop> hand;

	/**
	 * L'identifiant du joueur
	 */
	private int id;

	/**
	 * Constructeur
	 * 
	 * @param hand la main du joueur
	 * @param id   l'id du joueur
	 */
	public StratConservative(ArrayList<Prop> hand, int id) {
		this.hand = hand;
		this.id = id;
	}

	/**
	 * Détermine si le robot va retourner un nouveau Trick
	 * 
	 * @return le choix du robot
	 */
	public boolean revealNewTrick() {
		int nbMatch = 0;
		// le nombre de cartes que le joueur a en commun avec le trick dépilé

		Trick topTrick = Game.getTopTrick();

		if (topTrick != null) {
			nbMatch = topTrick.getMatchingTricks(hand);

			if (nbMatch < 1) {
				// Si on a aucun prop correspondant, on retourne le trick
				return true;
			}
		}
		return false;
	}

	/**
	 * Détermine quel prop le joueur veut echanger
	 * 
	 * @return le choix du robot
	 */
	public int chooseOwnProp() {
		// Choisit quel prop on retourne
		// Retourne le prop le moins rare
		int choice = hand.get(0).compareRarity(hand.get(1));
		// On compare la rareté des cartes pour retourner la moins rare
		switch (choice) {
		case -1: // Le prop 0 est moins rare, donc on le retourne
			return 0;
		case 1: // Le prop 1 est moins rare, donc on le retourne
			return 1;
		}
		return Math.random() >= 0.5 ? 0 : 1; // Les props ont la même rareté, on en retourne 1 au hasard
	}

	/**
	 * Détermine avec quel prop le joueur veut echanger le sien
	 * 
	 * @param players la liste des joueurs
	 * @return le choix du robot
	 */
	public int chooseOtherProp(Player[] players) {

		int bestOtherProp = 0;
		int indBestOtherProp = 0;
		int ind = 0;

		for (int i = 0; i < players.length; i++) {
			if (players[i].id != this.id) { // On ne vérifie pas ses propres cartes
				for (Iterator<Prop> it = players[i].getHand().iterator(); it.hasNext();) {
					int val = it.next().getTypeSecure();
					if (val > bestOtherProp) {
						bestOtherProp = val;
						indBestOtherProp = ind;
					}
					ind++;
				}
			}
		}
		if (indBestOtherProp > 3 || indBestOtherProp < 0) {
			try {
				throw new Exception("Erreur dans la sélection du prop le plus rare résultat = " + indBestOtherProp);
			} catch (java.lang.Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return indBestOtherProp;
	}

	/**
	 * Détermine si le joueur veut performer le trick
	 * 
	 * @return le choix du robot
	 */
	public boolean performTrick() {
		return Game.getTopTrick().compareToProps(hand);
	}

	/**
	 * Détermine quel prop le joueur veut retourner
	 * 
	 * @return le choix du robot
	 */
	public int revealProp() {
		// Duplication de la méthode chooseOwnProp
		return this.chooseOwnProp();
	}

	/**
	 * Détermine quel prop le joueur veut echanger, et lequel il veut garder
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddle() {
		ArrayList<Prop> middle = Game.getMiddleProp();
		int indBest = 0;
		int ind = 0;
		int toChange = this.chooseOwnProp(); // Le prop qui a le moins de valeur dans la main

		// On récupère le meilleur prop du milieu
		for (Iterator<Prop> it = middle.iterator(); it.hasNext();) {
			if (it.next().getTypeSecure() > middle.get(indBest).getTypeSecure()) {
				indBest = ind;
			}
			ind++;
		}

		// Si le meilleur prop du milieu est moins bon que le pire prop, on n'échange
		// pas

		if (middle.get(indBest).getTypeSecure() < this.hand.get(toChange).getTypeSecure()) {
			return 2;
		}

		return toChange;
	}

	/**
	 * Détermine quel prop le joueur veut echanger quand il joue avec la variante Carrot
	 * 
	 * @return le choix du robot
	 */
	public int chooseMiddleVarCarrot(Player[] players) {
		return 0;
	}

}
