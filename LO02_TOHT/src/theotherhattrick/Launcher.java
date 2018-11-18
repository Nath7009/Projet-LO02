package theotherhattrick;

public class Launcher {

	public static void main(String[] args) {
		Game game = Game.createGame();
		//game.start();
		System.out.println(game.getVariant());
	}

}
