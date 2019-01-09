package theotherhattrick;

import java.io.Serializable;

import theotherhattrickControler.GameControler;
import theotherhattrickView.GraphicView;
import theotherhattrickView.VueTexte;

public class Launcher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -26706685655911217L;

	public static void main(String[] args) {

		GameControler gc = new GameControler();
		//VueTexte vt = new VueTexte(gc);
		GraphicView gv = new GraphicView(gc);
		//gc.start();
//		Game game = Game.createGame();
//		game.start();
	}

}