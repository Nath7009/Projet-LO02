package theotherhattrickView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import theotherhattrickControler.GameControler;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

import theotherhattrick.Game;
import theotherhattrick.Human;
import theotherhattrick.Player;
import theotherhattrick.Prop;
import theotherhattrick.Robot;
import theotherhattrick.Trick;

import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("deprecation")
public class GraphicView implements Observer {

	// private JFrame menu;
	private JFrame game;
	private Menu menu;
	private GameControler controler;
	private static JButton gameStarter = new JButton("Start !");
	private JPanel player1;
	private JPanel player2;
	private JPanel player3;

	private JLabel p1Name;
	private JLabel p2Name;
	private JLabel p3Name;

	private JButton p1prop1;
	private JButton p1prop2;
	private JButton[] p1Hand = { p1prop1, p1prop2 };
	private JButton p2prop1;
	private JButton p2prop2;
	private JButton[] p2Hand = { p2prop1, p2prop2 };
	private JButton p3prop1;
	private JButton p3prop2;
	private JButton[] p3Hand = { p3prop1, p3prop2 };

	private JLabel p1Score;
	private JLabel p2Score;
	private JLabel p3Score;

	private GroupLayout gl_middleCards;
	private GroupLayout gl_player1;
	private GroupLayout gl_player2;
	private GroupLayout gl_player3;
	@SuppressWarnings("unused")
	private GroupLayout[] gl_players = { gl_player1, gl_player2, gl_player3 };
	private GroupLayout groupLayout;
	private JPanel middleCards;
	private JButton mprop1;
	private JButton mprop2;
	private JButton trickPile;
	private JButton depiledTrick;

	private JPanel infos;
	private JLabel lblInfos;
	private JLabel lblTourActuel;
	private JButton btnSave;
	private JButton btnLoad;

	private JButton btnNon;

	/**
	 * Cree la vue graphique
	 */
	public GraphicView(GameControler gc) {
		gameStarter.addActionListener(new StartButtonListener());
		this.controler = gc;
		gc.setGraphicView(this);
		this.menu = new Menu(gameStarter);
		this.menu.setVisible(true);
		initialize();
		this.game.setVisible(false);

	}

	/**
	 * Initialise la frame, code genere avec WindowsBuilder
	 */
	private void initialize() {
		// menu = new JFrame();
		game = new JFrame();
		game.setTitle("The Other Hat Trick");
		// menu.setBounds(100, 100, 450, 300);
		game.setBounds(100, 100, 1000, 800);
		// menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infos = new JPanel();

		// Initialisation du joueur 1
		player1 = new JPanel();
		player1.setBorder(new LineBorder(Color.BLACK));
		p1Name = new JLabel("Name");
		p1Hand[0] = new JButton("prop1");
		p1Hand[1] = new JButton("prop2");
		p1Score = new JLabel("Score");

		// Initialisation du joueur 2
		player2 = new JPanel();
		player2.setBorder(new LineBorder(Color.BLACK));
		p2Name = new JLabel("Name");
		p2Hand[0] = new JButton("prop1");
		p2Hand[1] = new JButton("prop2");
		p2Score = new JLabel("Score");

		// Initialisation du joueur 3
		player3 = new JPanel();
		player3.setBorder(new LineBorder(Color.BLACK));
		p3Name = new JLabel("Name");
		p3Hand[0] = new JButton("prop1");
		p3Hand[1] = new JButton("prop2");

		gl_player2 = new GroupLayout(player2);
		gl_player2
				.setHorizontalGroup(gl_player2.createParallelGroup(Alignment.LEADING).addGroup(gl_player2.createSequentialGroup().addContainerGap().addGroup(gl_player2.createParallelGroup(Alignment.LEADING).addComponent(p2Name).addComponent(p2Score))
						.addGap(100).addComponent(p2Hand[0], GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addGap(50).addComponent(p2Hand[1], GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE).addGap(57)));
		gl_player2.setVerticalGroup(gl_player2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player2.createSequentialGroup().addGap(5).addGroup(gl_player2.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_player2.createSequentialGroup()
								.addGroup(gl_player2.createParallelGroup(Alignment.BASELINE).addComponent(p2Hand[1], GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE).addComponent(p2Hand[0], GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
								.addGap(6))
						.addGroup(gl_player2.createSequentialGroup().addComponent(p2Name).addPreferredGap(ComponentPlacement.RELATED, 177, Short.MAX_VALUE).addComponent(p2Score).addGap(20)))));
		player2.setLayout(gl_player2);

		p3Score = new JLabel("Score");
		gl_player3 = new GroupLayout(player3);
		gl_player3.setHorizontalGroup(gl_player3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player3.createSequentialGroup().addGap(25)
						.addGroup(gl_player3.createParallelGroup(Alignment.LEADING).addComponent(p3Hand[1], GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addComponent(p3Hand[0], GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addGap(25))
				.addGroup(gl_player3.createSequentialGroup().addContainerGap().addComponent(p3Name).addContainerGap(155, Short.MAX_VALUE))
				.addGroup(gl_player3.createSequentialGroup().addContainerGap().addComponent(p3Score).addContainerGap(154, Short.MAX_VALUE)));
		gl_player3.setVerticalGroup(gl_player3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player3.createSequentialGroup().addComponent(p3Name).addGap(20).addComponent(p3Hand[0], GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(p3Hand[1], GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(p3Score).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		player3.setLayout(gl_player3);

		middleCards = new JPanel();
		middleCards.setBorder(new LineBorder(Color.BLACK));

		lblTourActuel = new JLabel("Tour actuel : ");

		btnSave = new JButton("Save");

		btnLoad = new JButton("Load");

		btnNon = new JButton("Non");
		groupLayout = new GroupLayout(game.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayout.createSequentialGroup().addComponent(player1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addGap(25))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnNon, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE).addGap(42)))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(middleCards, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE).addGap(25)).addComponent(player2,
						GroupLayout.PREFERRED_SIZE, 553, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(player3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblTourActuel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(infos, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnLoad).addComponent(btnSave)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayout.createSequentialGroup().addComponent(btnSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLoad))
										.addComponent(infos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblTourActuel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(player3, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE).addComponent(player1, GroupLayout.PREFERRED_SIZE, 500,
												GroupLayout.PREFERRED_SIZE))
										.addGap(37).addComponent(btnNon, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE).addGap(93))
								.addGroup(groupLayout.createSequentialGroup().addGap(473).addComponent(player2, GroupLayout.PREFERRED_SIZE, 223, Short.MAX_VALUE).addGap(10))))
				.addGroup(groupLayout.createSequentialGroup().addGap(60).addComponent(middleCards, GroupLayout.PREFERRED_SIZE, 468, Short.MAX_VALUE).addGap(242)));

		lblInfos = new JLabel("Infos");
		infos.add(lblInfos);

		trickPile = new JButton("Pile deTricks");

		mprop1 = new JButton("prop 1");

		mprop2 = new JButton("prop2");

		depiledTrick = new JButton("Trick dépilé");
		gl_middleCards = new GroupLayout(middleCards);
		gl_middleCards.setHorizontalGroup(gl_middleCards.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_middleCards.createSequentialGroup().addGap(20)
						.addGroup(gl_middleCards.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_middleCards.createSequentialGroup().addComponent(trickPile, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
										.addComponent(depiledTrick, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addGap(42))
								.addGroup(gl_middleCards.createSequentialGroup().addComponent(mprop1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(mprop2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addContainerGap()))));

		middleCards.setLayout(gl_middleCards);

		gl_player1 = new GroupLayout(player1);
		gl_player1.setHorizontalGroup(gl_player1.createParallelGroup(Alignment.LEADING).addGroup(gl_player1.createSequentialGroup().addContainerGap().addComponent(p1Name).addContainerGap(153, Short.MAX_VALUE))
				.addGroup(gl_player1.createSequentialGroup().addContainerGap().addComponent(p1Score).addContainerGap(153, Short.MAX_VALUE))
				.addGroup(gl_player1.createSequentialGroup().addGap(25)
						.addGroup(gl_player1.createParallelGroup(Alignment.LEADING).addGroup(gl_player1.createSequentialGroup().addComponent(p1Hand[0], GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE).addContainerGap())
								.addGroup(gl_player1.createSequentialGroup().addComponent(p1Hand[1], GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE).addGap(25)))));

		gl_middleCards.setVerticalGroup(gl_middleCards.createParallelGroup(Alignment.LEADING).addGroup(gl_middleCards.createSequentialGroup().addContainerGap()
				.addGroup(gl_middleCards.createParallelGroup(Alignment.BASELINE).addComponent(trickPile, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addComponent(depiledTrick, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
				.addGap(20)
				.addGroup(
						gl_middleCards.createParallelGroup(Alignment.LEADING, false).addComponent(mprop1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addComponent(mprop2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
				.addGap(12)));

		gl_player1.setVerticalGroup(gl_player1.createParallelGroup(Alignment.LEADING).addGroup(gl_player1.createSequentialGroup().addComponent(p1Name).addGap(25).addComponent(p1Hand[0], GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(p1Hand[1], GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(p1Score).addGap(6)));
		player1.setLayout(gl_player1);
		game.getContentPane().setLayout(groupLayout);

		addAllActionListeners();
	}

	/**
	 * Recupere les donnees du jeu pour pouvoir faire des modifications
	 */
	public void update(Observable arg0, Object arg1) {

		redraw();

		if (arg0 instanceof Game) {

			switch ((String) arg1) {
			case "new turn":
				controler.playTurn();
				break;

			case "nbOfPlayers":

				break;

			case "identityOfPLayer":

				break;

			case "new prop":
				this.controler.getGame().getAllProps().peek().addObserver(this);
				break;

			case "new trick":
				this.controler.getGame().getTricks().peek().addObserver(this);
				break;

			case "depile":

				break;

			case "trick pile empty":

				break;

			case "Trick realized":

				break;

			case "last turn":

				break;
			case "Player0":
				lblTourActuel.setText("Tour de " + controler.getGame().getPlayers()[0].getName());
				break;

			case "Player1":
				lblTourActuel.setText("Tour de " + controler.getGame().getPlayers()[1].getName());
				break;

			case "Player2":
				lblTourActuel.setText("Tour de " + controler.getGame().getPlayers()[2].getName());
				break;

			}
		}

		if (arg0 instanceof Player) {
			Player p = (Player) arg0;

			if (arg0 instanceof Human) {

				switch ((String) arg1) {

				case "name":

					break;

				case "Player0":
					lblTourActuel.setText(p.getName() + " joue.");
					break;

				case "Player1":
					lblTourActuel.setText(p.getName() + " joue.");
					break;

				case "Player2":
					lblTourActuel.setText(p.getName() + " joue.");
					break;

				case "revealNewTrick":
					lblInfos.setText("Cliquez sur la pile de tricks pour retourner un nouveau trick, ou sur le trick en cours pour essayer de le réalsier");
					trickPile.setEnabled(true);
					depiledTrick.setEnabled(true);
					break;

				case "chooseOwnProp":
					lblInfos.setText("CLiquez sur un de vos props pour l'échanger");
					trickPile.setEnabled(false);
					depiledTrick.setEnabled(false);
					break;

				case "chooseOtherProp":

					break;

				case "chooseMiddleVarCarrot":

					break;

				case "performTrick":

					break;

				case "revealProp":

					break;

				case "chooseMiddle":

					break;

				case "reveal prop":

					break;

				case "other hat trick realized":

					break;

				case "possess other rabbit":

					break;

				case "possess hat":

					break;

				}
			}

			if (arg0 instanceof Robot) {

			}

		}

		if (arg0 instanceof Trick) {

			switch ((String) arg1) {

			case "print trick":

				break;

			case "value decreased":

				break;
			}
		}

		if (arg0 instanceof Prop) {

			switch ((String) arg1) {

			case "hide":
				break;

			case "unhide":
				break;

			case "print":
				break;

			case "print visible":

				break;

			case "print debug":
				break;
			}
		}
	}

	/**
	 * @return le menu cree par la vue graphique
	 */
	public Menu getMenu() {
		return this.menu;
	}

	/**
	 * Desactive les cartes du joueur
	 * @param player l'indice du joueur dont les cartes vont etre desactivees
	 */
	public void disableCardsOfPlayer(int player) {
		switch (player) {
		case -1:
			mprop1.setEnabled(false);
			mprop2.setEnabled(false);
			break;
		case 0:
			p1Hand[0].setEnabled(false);
			p1Hand[1].setEnabled(false);
			break;
		case 1:
			p2Hand[0].setEnabled(false);
			p2Hand[1].setEnabled(false);
			break;
		case 2:
			p3Hand[0].setEnabled(false);
			p3Hand[1].setEnabled(false);
			break;
		}
	}

	/**
	 * Affiche un message dans la rectangle au dessus de l'ecran
	 * @param info le message a afficher
	 */
	public void setInfo(String info) {
		this.lblInfos.setText(info);
	}

	/**
	 * Active toutes les cartes des joueurs
	 */
	public void enableAllCards() {
		mprop1.setEnabled(true);
		mprop2.setEnabled(true);
		p1Hand[0].setEnabled(true);
		p1Hand[1].setEnabled(true);
		p2Hand[0].setEnabled(true);
		p2Hand[1].setEnabled(true);
		p3Hand[0].setEnabled(true);
		p3Hand[1].setEnabled(true);

	}

	/**
	 * Desactive les cartes permettant au joueur de donner des reponses binaires
	 */
	public void disableDecisionCards() {
		trickPile.setEnabled(false);
		depiledTrick.setEnabled(false);
		btnNon.setEnabled(false);
	}
	/**
	 * Active les cartes permettant au joueur de donner des reponses binaires
	 */
	public void enableDecisionCards() {
		trickPile.setEnabled(true);
		depiledTrick.setEnabled(false);
		btnNon.setEnabled(true);
	}

	/**
	 * Desactive toutes les cartes des joueurs
	 */
	public void disableAllCards() {
		mprop1.setEnabled(false);
		mprop2.setEnabled(false);
		p1Hand[0].setEnabled(false);
		p1Hand[1].setEnabled(false);
		p2Hand[0].setEnabled(false);
		p2Hand[1].setEnabled(false);
		p3Hand[0].setEnabled(false);
		p3Hand[1].setEnabled(false);

	}
	/**
	 * Active les cartes du joueur
	 * @param player l'indice du joueur dont les cartes vont etre activees
	 */
	public void enableCardsOfPlayer(int player) {
		switch (player) {
		case -1:
			mprop1.setEnabled(true);
			mprop2.setEnabled(true);
			break;
		case 0:
			p1Hand[0].setEnabled(true);
			p1Hand[1].setEnabled(true);
			break;
		case 1:
			p2Hand[0].setEnabled(true);
			p2Hand[1].setEnabled(true);
			break;
		case 2:
			p3Hand[0].setEnabled(true);
			p3Hand[1].setEnabled(true);
			break;
		}
	}

	/**
	 * Desactive le bouton des tricks depiles
	 */
	public void disableTrickCard() {
		depiledTrick.setEnabled(false);
	}

	/**
	 * Active le bouton des tricks depiles
	 */
	public void enableTrickCard() {
		depiledTrick.setEnabled(true);
	}

	/**
	 * Desactive le bouton de la pile de tricks
	 */
	public void diableTrickPile() {
		trickPile.setEnabled(false);
	}

	/**
	 * Active le bouton de la pile de tricks
	 */
	public void enableTrickPile() {
		trickPile.setEnabled(true);
	}

	/**
	 * Ajoute les ActionListeners aux boutons
	 */
	public void addAllActionListeners() {
		trickPile.addActionListener(new DepileTrickButtonListener()); // La pile de tricks
		depiledTrick.addActionListener(new DepileTrickButtonListener());
		mprop1.addActionListener(new PropButtonListener(-1, 0)); // Les deux props du milieu
		mprop2.addActionListener(new PropButtonListener(-1, 1));
		p1Hand[0].addActionListener(new PropButtonListener(0, 0));// Les props des joueurs
		p1Hand[1].addActionListener(new PropButtonListener(0, 1));
		p2Hand[0].addActionListener(new PropButtonListener(1, 0));
		p2Hand[1].addActionListener(new PropButtonListener(1, 1));
		p3Hand[0].addActionListener(new PropButtonListener(2, 0));
		p3Hand[1].addActionListener(new PropButtonListener(2, 1));
		btnLoad.addActionListener(new SaveButtonListener("load"));
		btnSave.addActionListener(new SaveButtonListener("save"));
		btnNon.addActionListener(new NonButtonListener());
	}

	/**
	 * Permet de reafficher entierement l'interface graphique en recuperant les informations contenues dans game
	 */
	public void redraw() {
		Game gameModel = this.controler.getGame();
		Player[] players = gameModel.getPlayers();

		btnLoad.hide();
		btnSave.hide();

		boolean allGood = true; // Détermine si tous les éléments sont présents pour faire l'affichage

		if (players == null) {
			allGood = false;
		}
		if (players.length != 3) {
			allGood = false;
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				allGood = false;
			} else if (players[i].getHand().size() != 2) {
				allGood = false;
			}
		}

		if (allGood) {
			p1Name.setText(players[0].getName());
			p2Name.setText(players[1].getName());
			p3Name.setText(players[2].getName());

			p1Score.setText("Score : " + String.valueOf(players[0].getScore()));
			p2Score.setText("Score : " + String.valueOf(players[1].getScore()));
			p3Score.setText("Score : " + String.valueOf(players[2].getScore()));

			setImage(p1Hand[0], players[0].getHand(0), players[0]);
			setImage(p1Hand[1], players[0].getHand(1), players[0]);
			setImage(p2Hand[0], players[1].getHand(0), players[1]);
			setImage(p2Hand[1], players[1].getHand(1), players[1]);
			setImage(p3Hand[0], players[2].getHand(0), players[2]);
			setImage(p3Hand[1], players[2].getHand(1), players[2]);

			setImage(mprop1, Game.getMiddleProp().get(0));
			System.out.println("middle prop" + Game.getMiddleProp().get(0).toString());

			trickPile.setText("");
			trickPile.setMargin(new Insets(0, 0, 0, 0));
			trickPile.setBorder(null);
			trickPile.setIcon(new ImageIcon("./CARTES/resizedPROPS/DOS.jpg"));

			if (gameModel.getDepiledTrick() != null) {
				depiledTrick.setText("");
				depiledTrick.setBorder(null);
				depiledTrick.setIcon(new ImageIcon("./CARTES/resizedTRICKS/" + controler.getGame().getDepiledTrick().getName() + ".jpg"));

				depiledTrick.setText(gameModel.getDepiledTrick().getName());
			}
			if (Game.getMiddleProp().size() > 1) {
				setImage(mprop2, Game.getMiddleProp().get(1));
			} else {
				middleCards.remove(mprop2);
			}
		}

	}

	/**
	 * Permet d'affecter une image a un bouton
	 * @param button le bouton auquel on souhaite affecter l'image
	 * @param prop le prop qui est affecte au bouton
	 * @param p le possesseur du Prop prop
	 */
	private void setImage(JButton button, Prop prop, Player p) {
		button.setText("");
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBorder(null);
		if (!prop.getState() && !p.isViewable()) { // Si le prop n'est pas visible
			ImageIcon img = new ImageIcon("./CARTES/resizedPROPS/DOS.jpg");
			button.setIcon(img);
		} else {
			button.setIcon(new ImageIcon("./CARTES/resizedPROPS/" + prop.getName() + ".jpg"));
		}
	}

	/**
	 * Permet d'affecter une image a un bouton
	 * @param button le bouton auquel on souhaite affecter l'image
	 * @param prop le prop qui est affecte au bouton
	 */
	private void setImage(JButton button, Prop prop) {
		button.setText("");
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBorder(null);
		if (!prop.getState()) { // Si le prop n'est pas visible
			ImageIcon img = new ImageIcon("./CARTES/resizedPROPS/DOS.jpg");
			button.setIcon(img);
		} else {
			button.setIcon(new ImageIcon("./CARTES/resizedPROPS/" + prop.getName() + ".jpg"));
		}
	}

	
	/**
	 * Permet d'ecouter le menu pour savoir quand on clique sur le bouton demarrer
	 *
	 */
	class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Démarrage du jeu");
			controler.createGame(menu);
			menu.setVisible(false);

			game.setVisible(true);
			redraw();
		}
	}

	/**
	 * Permet de savoir quand on appuie sur un prop, et a qui appartient le prop
	 *
	 */
	class PropButtonListener implements ActionListener {

		int player, propId;

		public PropButtonListener(int player, int propId) {
			this.player = player;
			this.propId = propId;
		}

		public void actionPerformed(ActionEvent e) {
			controler.actionPerformed(new ActionEvent(player, propId, "Appui sur le prop"));
		}
	}

	/**
	 * Permet de savoir quand on appuie sur le bouton non
	 *
	 */
	class NonButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			controler.actionPerformed(new ActionEvent(new Boolean(false), -2, ""));
		}
	}

	/**
	 * Permet de savoir quand on appuie sur le bouton pour depiler un trick
	 *
	 */
	class DepileTrickButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.actionPerformed(new ActionEvent(new Boolean(true), -2, ""));
			// controler.setRevealNewTrick(controler.getGame().getCurrentPlayer(), true);
		}
	}
	
	/**
	 * Permet de savoir quand on appuie sur un bouton de sauvegarde, et l'action que l'on veut faire
	 *
	 */
	class SaveButtonListener implements ActionListener {
		String function;

		public SaveButtonListener(String function) {
			this.function = function;
		}

		public void actionPerformed(ActionEvent e) {
			controler.actionPerformed(new ActionEvent(this.function, -2, ""));
		}
	}
}
