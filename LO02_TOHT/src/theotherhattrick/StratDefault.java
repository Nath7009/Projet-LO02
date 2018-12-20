package theotherhattrick;

public class StratDefault implements Decision {

	// Dans cette strat�gie, le joueur va choisir au hasard

	public boolean revealNewTrick() {
		// On multiplie 2 Math.random pour que les parties soient plus longues
		return Math.random() * Math.random() * Math.random() >= 0.5 ? true : false;
	}

	public int chooseOwnProp() {
		return Math.random() >= 0.5 ? 0 : 1;
	}

	public int chooseOtherProp(Player[] players) {
		int choice = (int) Math.floor(Math.random() * 4);
		return choice;
	}

	public boolean performTrick() {
		return Math.random() >= 0.5 ? true : false;
	}

	public int revealProp() {
		return Math.random() >= 0.5 ? 0 : 1;
	}

	public int chooseMiddle() {
		int choice = (int) Math.floor(Math.random() * 3);
		return choice;
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		int choice = (int) Math.floor(Math.random() * 5);
		return choice;
	}

}
