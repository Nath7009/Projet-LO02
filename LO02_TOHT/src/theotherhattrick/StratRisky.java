package theotherhattrick;

import java.io.Serializable;

public class StratRisky implements Decision, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8293653320471985815L;
	StratDefault sd;
	//Dans cette strat�gie, le joueur va toujours essaier de faire le trick qui est retourn�
	//Il va essayer de prendre les cartes les plus rares aux autres joueurs
	//Il ne retourne jamais le tas de tricks
	
	public StratRisky() {
		this.sd = new StratDefault();
	}

	public boolean revealNewTrick() {
		return false;
	}

	public int chooseOwnProp() {
		return sd.chooseOwnProp();
	}

	public int chooseOtherProp(Player[] players) {
		return sd.chooseOtherProp(players);
	}

	public boolean performTrick() {
		return true;
	}

	public int revealProp() {
		return sd.revealProp();
	}

	public int chooseMiddle() {
		return sd.chooseMiddle();
	}

	public int chooseMiddleVarCarrot(Player[] players) {
		return sd.chooseMiddleVarCarrot(players);
	}

}
