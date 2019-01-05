package theotherhattrickControler;

import theotherhattrick.*;
import theotherhattrickView.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameControler implements ActionListener{
	protected Game game;
	
/**
 * Le contructeur du controleur instancie la version du jeux souhaitée avec la méthode statique createGame(variant) de Game.
 * Le controleur lance alors la partie.
 * @param variant : on récupère la version que l'on souhaite jouer en paramètre
 */
	public GameControler(Menu menu) {
		int variant = menu.getVariant();
		this.game = Game.createGame(variant);
		this.game.start();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
