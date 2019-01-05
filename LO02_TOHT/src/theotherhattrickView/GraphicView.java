package theotherhattrickView;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import theotherhattrickControler.GameControler;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import theotherhattrick.Game;
import theotherhattrick.Player;

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
	private JButton p2prop1;
	private JButton p2prop2;
	private JButton p3prop1;
	private JButton p3prop2;

	private JLabel p1Score;
	private JLabel p2Score;
	private JLabel p3Score;

	private GroupLayout gl_middleCards;
	private GroupLayout gl_player1;
	private GroupLayout gl_player2;
	private GroupLayout gl_player3;
	private GroupLayout groupLayout;
	private JPanel middleCards;
	private JButton mprop1;
	private JButton mprop2;
	private JButton trickPile;
	private JLabel depiledTrick;

	private JPanel infos;
	private JLabel lblInfos;
	private JLabel lblTourActuel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Menu menu = new Menu(gameStarter);
//					GameControler gc = new GameControler(menu);
//					GraphicView window = new GraphicView(gc, menu);
//					menu.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public GraphicView(GameControler gc) {
		gameStarter.addActionListener(new StartButtonListener());
		this.controler = gc;
		this.menu = new Menu(gameStarter);
		this.menu.setVisible(true);
		initialize();
		this.game.setVisible(false);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// menu = new JFrame();
		game = new JFrame();
		game.setTitle("The Other Hat Trick");
		// menu.setBounds(100, 100, 450, 300);
		game.setBounds(100, 100, 600, 350);
		// menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		player1 = new JPanel();

		p1Name = new JLabel("Name");

		p1prop1 = new JButton("prop1");

		p1prop2 = new JButton("prop2");

		p1Score = new JLabel("Score");

		infos = new JPanel();

		player2 = new JPanel();

		p2Name = new JLabel("Name");

		p2prop1 = new JButton("prop1");

		p2prop2 = new JButton("prop2");

		p2Score = new JLabel("Score");
		gl_player2 = new GroupLayout(player2);
		gl_player2.setHorizontalGroup(gl_player2.createParallelGroup(Alignment.LEADING).addGroup(gl_player2.createSequentialGroup().addContainerGap().addComponent(p2Name).addGap(18).addComponent(p2prop1).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(p2prop2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(p2Score).addContainerGap(29, Short.MAX_VALUE)));
		gl_player2.setVerticalGroup(gl_player2.createParallelGroup(Alignment.LEADING).addGap(0, 133, Short.MAX_VALUE)
				.addGroup(gl_player2.createSequentialGroup().addGap(5).addGroup(gl_player2.createParallelGroup(Alignment.BASELINE).addComponent(p2Name).addComponent(p2prop1).addComponent(p2prop2).addComponent(p2Score)).addGap(114)));
		player2.setLayout(gl_player2);

		player3 = new JPanel();

		p3Name = new JLabel("Name");

		p3prop1 = new JButton("prop1");

		p3prop2 = new JButton("prop2");

		p3Score = new JLabel("Score");
		gl_player3 = new GroupLayout(player3);
		gl_player3
				.setHorizontalGroup(
						gl_player3.createParallelGroup(Alignment.LEADING).addGap(0, 91, Short.MAX_VALUE)
								.addGroup(gl_player3
										.createSequentialGroup().addGroup(gl_player3.createParallelGroup(Alignment.LEADING).addComponent(p3prop1).addComponent(p3prop2)
												.addGroup(gl_player3.createSequentialGroup().addContainerGap().addComponent(p3Name)).addGroup(gl_player3.createSequentialGroup().addContainerGap().addComponent(p3Score)))
										.addContainerGap(169, Short.MAX_VALUE)));
		gl_player3.setVerticalGroup(gl_player3.createParallelGroup(Alignment.LEADING).addGap(0, 133, Short.MAX_VALUE).addGroup(gl_player3.createSequentialGroup().addGap(5).addComponent(p3Name).addGap(18).addComponent(p3prop1)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(p3prop2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(p3Score).addGap(19)));
		player3.setLayout(gl_player3);

		middleCards = new JPanel();
		
		lblTourActuel = new JLabel("Tour actuel : ");
		groupLayout = new GroupLayout(game.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblTourActuel, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(infos, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
					.addGap(121))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(player1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(player2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(middleCards, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(player3, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(infos, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTourActuel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(player1, GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
							.addGap(77))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(player3, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(middleCards, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(49)
							.addComponent(player2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))))
		);

		lblInfos = new JLabel("Infos");
		infos.add(lblInfos);

		trickPile = new JButton("Pile deTricks");

		mprop1 = new JButton("prop 1");

		mprop2 = new JButton("prop2");

		depiledTrick = new JLabel("Trick dépilé");
		gl_middleCards = new GroupLayout(middleCards);
		gl_middleCards.setHorizontalGroup(
				gl_middleCards.createParallelGroup(Alignment.LEADING).addGroup(gl_middleCards.createSequentialGroup().addContainerGap().addGroup(gl_middleCards.createParallelGroup(Alignment.LEADING).addComponent(trickPile).addComponent(mprop1))
						.addPreferredGap(ComponentPlacement.RELATED, 104, Short.MAX_VALUE).addGroup(gl_middleCards.createParallelGroup(Alignment.LEADING).addComponent(mprop2).addComponent(depiledTrick)).addContainerGap()));
		gl_middleCards.setVerticalGroup(gl_middleCards.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_middleCards.createSequentialGroup().addContainerGap().addGroup(gl_middleCards.createParallelGroup(Alignment.BASELINE).addComponent(trickPile).addComponent(depiledTrick))
						.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE).addGroup(gl_middleCards.createParallelGroup(Alignment.BASELINE).addComponent(mprop1).addComponent(mprop2)).addGap(32)));
		middleCards.setLayout(gl_middleCards);
		gl_player1 = new GroupLayout(player1);
		gl_player1
				.setHorizontalGroup(
						gl_player1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_player1
										.createSequentialGroup().addGroup(gl_player1.createParallelGroup(Alignment.LEADING).addComponent(p1prop1).addComponent(p1prop2)
												.addGroup(gl_player1.createSequentialGroup().addContainerGap().addComponent(p1Name)).addGroup(gl_player1.createSequentialGroup().addContainerGap().addComponent(p1Score)))
										.addContainerGap(169, Short.MAX_VALUE)));
		gl_player1.setVerticalGroup(gl_player1.createParallelGroup(Alignment.LEADING).addGroup(gl_player1.createSequentialGroup().addGap(5).addComponent(p1Name).addGap(18).addComponent(p1prop1).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(p1prop2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(p1Score).addGap(19)));
		player1.setLayout(gl_player1);
		game.getContentPane().setLayout(groupLayout);

		addAllActionListeners();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		redraw();
		if (arg1 instanceof String) {
			switch ((String) arg1) {
			case "player0":
				lblTourActuel.setText("Tour actuel : " + controler.getGame().getPlayers()[0].getName());
				break;
			case "player1":
				lblTourActuel.setText("Tour actuel : " + controler.getGame().getPlayers()[1].getName());
				break;
			case "player2":
				lblTourActuel.setText("Tour actuel : " + controler.getGame().getPlayers()[2].getName());
				break;
			}
		}
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void addAllActionListeners() {
		trickPile.addActionListener(new DepilePropButton()); //La pile de tricks
		mprop1.addActionListener(new PropButtonListener(-1, 0)); //Les deux props du milieu
		mprop2.addActionListener(new PropButtonListener(-1, 1));
		p1prop1.addActionListener(new PropButtonListener(0, 0));//Les props des joueurs
		p1prop2.addActionListener(new PropButtonListener(0, 1));
		p2prop1.addActionListener(new PropButtonListener(1, 0));
		p2prop2.addActionListener(new PropButtonListener(1, 1));
		p3prop1.addActionListener(new PropButtonListener(2, 0));
		p3prop2.addActionListener(new PropButtonListener(2, 1));
	}

	public void redraw() {
		Game gameModel = this.controler.getGame();
		Player[] players = gameModel.getPlayers();

		boolean allGood = true; //Détermine si tous les éléments sont présents pour faire l'affichage

		if (players == null) {
			allGood = false;
		}
		if (players.length != 3) {
			allGood = false;
		}
		
		if(gameModel.getDepiledTrick() == null) {
			allGood = false;
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				allGood = false;
			}
		}

		if (allGood) {
			System.out.println(players);
			p1Name.setText(players[0].getName());
			p2Name.setText(players[1].getName());
			p3Name.setText(players[2].getName());

			p1Score.setText(new Integer(players[0].getScore()).toString());
			p2Score.setText(new Integer(players[1].getScore()).toString());
			p3Score.setText(new Integer(players[2].getScore()).toString());

			p1prop1.setText(players[0].getHand(0).getName());
			p1prop2.setText(players[0].getHand(1).getName());
			p2prop1.setText(players[1].getHand(0).getName());
			p2prop2.setText(players[1].getHand(1).getName());
			p3prop1.setText(players[2].getHand(0).getName());
			p3prop2.setText(players[2].getHand(1).getName());

			mprop1.setText(Game.getMiddleProp().get(0).getName());

			depiledTrick.setText(gameModel.getDepiledTrick().getName());

			if (Game.getMiddleProp().size() > 1) {
				mprop2.setText(Game.getMiddleProp().get(1).getName());
			} else {
				middleCards.remove(mprop2);
				// mprop2.setEnabled(false);
			}
		}

	}

	class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Démarrage du jeu");

			menu.setVisible(false);

			game.setVisible(true);
			redraw();
		}
	}

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

	class DepilePropButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.setRevealNewTrick(controler.getGame().getCurrentPlayer(), true);
		}

	}
}
