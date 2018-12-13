package theotherhattrick;

public class StratDefault implements Decision{
	
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
