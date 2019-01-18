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

/**
 * Permet de controler le jeu sous la forme d'un automate à etats
 *
 */
public class GameControler implements ActionListener {

	/**
	 * Le jeu qui va tourner sous le controle du controleur
	 */
	protected Game game;

	/**
	 * La vue graphique
	 */
	private GraphicView gv;

	/**
	 * La vue texte
	 */
	@SuppressWarnings("unused")
	private VueTexte vt;

	/**
	 * Permet de savoir si on peut continuer le jeu
	 */
	private Object canContinue = null;

	/**
	 * La phase de l'automate a etats
	 */
	private int phase = 1;

	/**
	 * Le contructeur du controleur instancie la version du jeux souhaitée avec la
	 * méthode statique createGame(variant) de Game. Le controleur lance alors la
	 * partie.
	 * 
	 * @param variant : on récupère la version que l'on souhaite jouer en paramètre
	 */
	public GameControler() {
	}

	public GameControler(int variant) {
		this.game = Game.createGame(variant);
	}

	/**
	 * Permet de creer le jeu a l'aide du menu
	 * 
	 * @param menu recupere les informations remplies dans le menu
	 */
	public void createGame(Menu menu) {
		// Cette méthode est appelée quand on valide le menu
		// c'est l'interface graphique qui décide quand le jeu peut commencer
		GameParameters gp = new GameParameters(menu);
		game = Game.createGame(gp);
		game.addObserver(this.gv);
		game.start();
	}

	/**
	 * Joue un tour de jeu
	 * 
	 */
	public void playTurn() {
		// Differentes valeurs pour la phase de jeu
		// 1 : Retourner le trick ?
		// 2 : Selection de la carte que le joueur veut echanger
		// 3 : Selection de la carte de l'autre joueur
		// 4 : Performer le trick ?
		// 5 : Echange au milieu si le trick est reussi
		// 6 : Choisit le trick a retourner en cas d'echec
		phase = 1;
		int currPlayer = game.getCurrentPlayerIndex();
		game.getPlayers()[currPlayer].setViewable();
		gv.disableAllCards();
		gv.disableTrickCard();
		gv.enableDecisionCards();
		gv.redraw();
		canContinue = null;
		gv.setInfo("Voulez vous retourner le trick ?");
		while (canContinue == null) { // On attend une reponse d'actionPerformed a chaque fois que l'on veut une
										// entree utilisateur
			try {
				Thread.sleep(10);
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
				Thread.sleep(10);
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
				Thread.sleep(10);
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
				Thread.sleep(10);
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
					Thread.sleep(1000);
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
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Game.getMiddleProp().get(0).setState(middleCardState);
				// Si le joueur ne veut échanger sa carte avec un prop du milieu
				if ((int) ((ActionEvent) canContinue).getSource() != -1) {
					game.exchangeProps(currPlayer, ((ActionEvent) canContinue).getID(), -1, 0);
				}

			} else { // Le joueur échoue le trick

				diableAllPlayersButOne(currPlayer);
				gv.disableDecisionCards();
				canContinue = null;
				while (canContinue == null) {
					try {
						Thread.sleep(10);
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
					Thread.sleep(10);
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

	/**
	 * Desactive les boutons de tous les joueurs sauf du joueur d'indice players
	 * 
	 * @param player le joueur dont on ne desactive pas les cartes
	 */
	public void diableAllPlayersButOne(int player) {
		for (int i = -1; i < 3; i++) {
			if (i != player) {
				gv.disableCardsOfPlayer(i);
			}
		}
	}

	/**
	 * Permet d'affecter la vue graphique au controleur
	 * 
	 * @param gv
	 */
	public void setGraphicView(GraphicView gv) {
		this.gv = gv;
	}

	/**
	 * Demarre le jeu
	 */
	public void start() {
		this.game.start();
	}

	/**
	 * @return l'instance du jeu que l'on est en train de jouer
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Modifie la variante du jeu
	 * 
	 * @param variant la variante que l'on veut affecter
	 */
	public void setVariant(int variant) {
		this.game.setVariant(variant);
	}

	/**
	 * Fixe le nombre d'humains qui jouent au jeu
	 * 
	 * @param nbOfHuman le nombre d'humains qui jouent
	 */
	public void setNbOfHuman(int nbOfHuman) {
		this.game.setNbOfHuman(nbOfHuman);
	}

	/**
	 * Cree un nouveau joueur
	 * 
	 * @param name  le nom du joueur
	 * @param day   le jour de naissance du joueur
	 * @param month le mois de naissance du joueur
	 * @param year  l'annee de naissance du joueur
	 */
	public void setNewPlayer(String name, int day, int month, int year) {
		Player newPlayer = new Human(name, new Date(day, month, year));
		this.game.setNewPlayer(newPlayer);
	}

	/**
	 * Recupere les entrees des vues et les filtre pour etre sur que les entrees
	 * utilisateurs sont valables
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Boolean) {
			if (phase == 1 || phase == 4) {
				canContinue = e.getSource();
			}
		} else if (e.getID() >= -1) {
			canContinue = e;
		} else if (e.getSource() instanceof String) {
			if (e.getSource().equals("save")) {
				this.saveGame();
			} else if (e.getSource().equals("load")) {
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
	 * @param p
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

	/**
	 * Charge le jeu à partir du fichier de sauvegarde
	 */
	public void loadGame() {
		game = GameSaver.load();
		System.out.println("Game chargée");
	}

	/**
	 * Sauvagarde le jeu dans un fichier de configuration
	 */
	public void saveGame() {
		GameSaver.save(game);
		System.out.println("Game sauvegardée");
	}
}
