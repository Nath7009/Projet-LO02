package theotherhattrickControler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import theotherhattrick.Date;
import theotherhattrick.Game;
import theotherhattrick.GameSaver;
import theotherhattrick.Human;
import theotherhattrick.Player;
import theotherhattrickView.GraphicView;
import theotherhattrickView.VueTexte;
import theotherhattrickView.Menu;

@SuppressWarnings("deprecation")
public class GameControler implements ActionListener, Runnable {

	protected Game game;
	private GraphicView gv;
	private VueTexte vt;
	private Object canContinue = null;
	private int phase = 1;
	private Thread t;

	/**
	 * Le contructeur du controleur instancie la version du jeux souhaitée avec la
	 * méthode statique createGame(variant) de Game. Le controleur lance alors la
	 * partie.
	 * 
	 * @param variant : on récupère la version que l'on souhaite jouer en paramètre
	 */
	public GameControler() {
		t = new Thread(this);
//		gv = new GraphicView(this);
//		vt = new VueTexte(this);

		// int variant = gv.getMenu().getVariant();
		// this.game = Game.createGame(variant);
		// Création du jeu à l'aide des données du menu

		// this.game.initGame();
		// this.game.start();
	}

	public GameControler(int variant) {
		this.game = Game.createGame(variant);

	}

	public void createGame(Menu menu) {
		// Cette méthode est appelée quand on valide le menu
		// c'est l'interface graphique qui décide quand le jeux peut commencer
		GameParameters gp = new GameParameters(menu);
		game = Game.createGame(gp);
		game.addObserver(this.gv);
		/*
		 * vt = new VueTexte(this); game.addObserver(vt);
		 * game.getPlayers()[0].addObserver(vt); game.getPlayers()[1].addObserver(vt);
		 * game.getPlayers()[2].addObserver(vt);
		 */

		game.start();
	}

	public void playTurn() {
		phase = 1;
		int currPlayer = game.getCurrentPlayerIndex();
		game.getPlayers()[currPlayer].setViewable();
		gv.disableAllCards();
		gv.disableTrickCard();
		gv.enableDecisionCards();
		gv.redraw();
		canContinue = null;
		gv.setInfo("Voulez vous retourner le trick ?");
		while (canContinue == null) {
			try {
				t.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ((Boolean) canContinue == true) {
			System.out.println("Depile");
			game.depile();
		}
		canContinue = null;

		phase++;
		gv.enableAllCards();
		gv.setInfo("Choisissez celle de vos cartes que vous voulez échanger");
		diableAllPlayersButOne(currPlayer);
		gv.disableDecisionCards();
		while (canContinue == null) {
			try {
				t.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int carte1 = ((ActionEvent) canContinue).getID();
		canContinue = null;
		phase++;
		gv.setInfo("Choisissez la carte d'un adversaire");
		gv.enableAllCards();
		gv.disableCardsOfPlayer(-1);
		gv.disableCardsOfPlayer(currPlayer);

		while (canContinue == null) {
			try {
				t.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		game.exchangeProps(currPlayer, carte1, (int) ((ActionEvent) canContinue).getSource(), ((ActionEvent) canContinue).getID());
		gv.redraw();
		
		phase++;
		canContinue = null;
		gv.disableAllCards();
		gv.enableDecisionCards();
		gv.diableTrickPile();
		gv.enableTrickCard();
		gv.setInfo("Voulez vous performer le trick ?");

		while (canContinue == null) {
			try {
				t.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ((Boolean) canContinue == true) { // Le joueur souhaite réaliser le trick
			if ((game.getDepiledTrick().compareToProps(game.getCurrentPlayer().getHand()))) {
				gv.setInfo("Vous avez réussi le trick");
				game.giveTrick(game.getCurrentPlayer());
				try {
					t.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Faire l'échange au milieu
				phase++;
				gv.setInfo("Choisissez la carte que vous voulez mettre au milieu");
				gv.disableAllCards();
				gv.disableDecisionCards();
				gv.enableCardsOfPlayer(currPlayer);
				gv.enableCardsOfPlayer(-1);
				boolean middleCardState = Game.getMiddleProp().get(0).getState();
				game.showMiddleProps();
				System.out.println("Show");
				gv.redraw();
				System.out.println("redraw");
				gv.redraw();
				canContinue = null;
				while (canContinue == null) {
					try {
						t.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Game.getMiddleProp().get(0).setState(middleCardState);
				//Si le joueur ne veut échanger sa carte avec un prop du milieu
				if((int)((ActionEvent) canContinue).getSource() != -1) {
					game.exchangeProps(currPlayer, ((ActionEvent) canContinue).getID(), -1, 0);
				}
				
			} else { // Le joueur échoue le trick

				diableAllPlayersButOne(currPlayer);
				gv.disableDecisionCards();
				canContinue = null;
				while (canContinue == null) {
					try {
						t.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				game.getPlayers()[currPlayer].getHand().get(((ActionEvent) canContinue).getID()).unhide();
			}
		} else {
			gv.setInfo("Vous avez choisi de ne pas réaliser le trick, vous devez retourner l'une de vos cartes");
			gv.enableAllCards();
			diableAllPlayersButOne(currPlayer);
			gv.disableDecisionCards();
			canContinue = null;
			while (canContinue == null) {
				try {
					t.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int answer = ((ActionEvent) canContinue).getID();
			game.getPlayers()[currPlayer].getHand().get(answer).unhide();
		}

		game.getPlayers()[currPlayer].setUnviewable();

	}

	public void diableAllPlayersButOne(int player) {
		for (int i = -1; i < 3; i++) {
			if (i != player) {
				gv.disableCardsOfPlayer(i);
			}
		}
	}

	public void setGraphicView(GraphicView gv) {
		this.gv = gv;
	}

	public void start() {
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
		if (e.getSource() instanceof Boolean) {
			if (phase == 1 || phase == 4) {
				System.out.println(e.getSource());
				canContinue = e.getSource();
			}
		} else if (e.getID() >= -1) {
			canContinue = e;
		} else if(e.getSource() instanceof String) {
			if(e.getSource().equals("save")) {
				this.saveGame();
			}
			else if(e.getSource().equals("load")) {
				this.loadGame();
			}
		}
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

	public void loadGame() {
		game = GameSaver.load();
		System.out.println("Game chargée");
	}

	public void saveGame() {
		GameSaver.save(game);
		System.out.println("Game sauvegardée");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
