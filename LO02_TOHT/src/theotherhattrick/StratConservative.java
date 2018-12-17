package theotherhattrick;

import java.util.ArrayList;
import java.util.Iterator;

public class StratConservative implements Decision {

	private ArrayList<Prop> hand;
	private int id;

	// Dans cette stratégie, le joueur va essaier de réaliser un trick uniquement si
	// il a au moins une des deux cartes requises
	// Si il n'a aucune des cartes, il ne va pas jouer

	public StratConservative(ArrayList<Prop> hand, int id) {
		this.hand = hand;
		this.id = id;
	}

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

	public int chooseOtherProp(Player[] players) {

		int bestOtherProp = 0;
		int indBestOtherProp = 0;
		int ind = 0;

		for (int i = 0; i < players.length; i++) {
			if (players[i].id != this.id) { // On ne vérifie pas les cartes de soi même
				for (Iterator<Prop> it = players[i].getHand().iterator(); it.hasNext();) {
					int val = it.next().getType();
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
				e.printStackTrace();
			}
		}

		return indBestOtherProp;
	}

	public boolean performTrick() {
		return Game.getTopTrick().compareToProps(hand);
	}

	public int revealProp() {
		// Duplication de la méthode chooseOwnProp
		return this.chooseOwnProp();
	}

	public int chooseMiddle() {
		ArrayList<Prop> middle = Game.getMiddleProp();
		int indBest = 0;
		int ind = 0;
		int toChange = this.chooseOwnProp(); // Le prop qui a le moins de valeur dans la main
		
		//On récupère le meilleur prop du milieu
		for (Iterator<Prop> it = middle.iterator(); it.hasNext();) {
			if (it.next().getType() > middle.get(indBest).getType()) {
				indBest = ind;
			}
			ind++;
		}
		
		//Si le meilleur prop du milieu est moins bon que le pire prop, on n'échange pas
		
		if (middle.get(indBest).getType() < this.hand.get(toChange).getType()) {
			return 2;
		}

		return toChange;
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		return 0;
	}

}
