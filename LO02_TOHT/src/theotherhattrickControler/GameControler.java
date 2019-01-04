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
	
	/**
	 * 
	 * @param p
	 * @param choice
	 */
	public void setRevealNewTrick(Player p, boolean choice) {
			p.setNewTrick(choice);
	}
	
	/**
	 * 
	 * @param choice
	 */
	public void setOwnProp(Player p, int choice) {
		p.setOwnProp(choice);
	}
	
	public void setMiddleVarCarrot(Player p, int choice) {
		p.setMiddleVarCarrot(choice);
	}
	
	public void setPerformTrick(Player p, boolean choice) {
		p.setPerformTrick(choice);
	}
	
	public void setRevealProp(Player p, int choice) {
		p.setRevealProp(choice);
	}
	
	public void setMiddleProp(Player p, int choice) {
		p.setMiddleProp(choice);
	}
	
	public void setOtherProp(Player p, int choice) {
		p.setOtherProp(choice);
	}
	
	
}
