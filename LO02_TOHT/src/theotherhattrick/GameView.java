package theotherhattrick;

public class GameView implements Observer{

	private Game game;
	
	public GameView(Game game) {
		this.game = game;
	}
}
