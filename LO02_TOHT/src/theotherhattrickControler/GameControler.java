package theotherhattrickControler;

import theotherhattrick.*;
import java.util.ArrayList;

public class GameControler {
	protected Game game;
	
/**
 * Le contructeur du controleur instancie la version du jeux souhaitée avec la méthode statique createGame(variant) de Game.
 * Le controleur lance alors la partie.
 * @param variant : on récupère la version que l'on souhaite jouer en paramètre
 */
	public GameControler(int variant) {

		this.game = Game.createGame(variant);
//		this.game.start();
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public void setVariant(int variant) {
		this.game.setVariant(variant);
	}
	
	public void setNbOfHuman(int nbOfHuman) {
		this.game.setNbOfHuman(nbOfHuman);
	}
	
	public void setNewPlayer(String name, int day, int month, int year) {
		Player newPlayer = new Human(name, new Date(day, month, year));
		this.game.setNewPlayer(newPlayer);
	}
	
	
	
	
}
