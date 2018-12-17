package theotherhattrick;

public class StratRisky implements Decision {
	
	//Dans cette stratégie, le joueur va toujours essaier de faire le trick qui est retourné
	//Il va essayer de prendre les cartes les plus rares aux autres joueurs
	

	public boolean revealNewTrick() {

		return false;
	}

	public int chooseOwnProp() {

		return 0;
	}

	public int chooseOtherProp(Player[] players) {
		return 0;
	}

	public boolean performTrick() {
		return false;
	}

	public int revealProp() {
		return 0;
	}

	public int chooseMiddle() {
		return 0;
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		return 0;
	}

}
