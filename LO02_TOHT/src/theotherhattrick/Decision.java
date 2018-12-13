package theotherhattrick;

public interface Decision {
	
	public boolean revealNewTrick();
	public int chooseOwnProp();
	public int chooseOtherProp(Player[] players);
	public boolean performTrick();
	public int revealProp();
	public int chooseMiddle();
	public int chooseMiddleVarCarrot(Player[] players);
}
