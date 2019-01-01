package theotherhattrickControler;
import theotherhattrick.*;
import java.util.ArrayList;
public class GameControler {
	protected Game game;
	
	public GameControler(Game game) {
		this.game = game;
	}
	
	public void launchGame() {
		this.game.createGame();
	}
	
	public void setNbOfPlayer() {
		
	}
	
	
	
	
}
