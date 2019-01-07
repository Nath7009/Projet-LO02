package theotherhattrickControler;

import theotherhattrick.Human;
import theotherhattrick.Player;
import theotherhattrick.Robot;
import theotherhattrickView.Menu;

//Classe utilisée pour définir les paramètres d'une partie, comme les noms de joueurs, ou les ages des joueurs
//Permet de créer une partie en faisant new Game(GameParameter)
public class GameParameters {

	public int variant;
	public Player[] players;

	public GameParameters(Menu menu) {
		players = new Player[3];
		variant = menu.getVariant();
		
		for (int i = 0; i < players.length; i++) {
			System.out.println(i);
			if (menu.isAI(i)) {
				players[i] = new Robot(menu.getBirthDate(i));
			} else {
				players[i] = new Human(menu.getName(i), menu.getBirthDate(i));
			}
		}
		
	}

}
