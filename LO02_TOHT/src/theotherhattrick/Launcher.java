package theotherhattrick;

import java.io.Serializable;

import theotherhattrickControler.GameControler;
import theotherhattrickView.GraphicView;

/**
 * La classe qui permet de lancer le jeu
 *
 */
public class Launcher implements Serializable {

	private static final long serialVersionUID = -26706685655911217L;

	/**
	 * La méthode main qui lance le programme
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GameControler gc = new GameControler();
		GraphicView gv = new GraphicView(gc);
	}

}