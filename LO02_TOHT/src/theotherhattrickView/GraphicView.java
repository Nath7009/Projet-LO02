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
	private JButton btnSave;
	private JButton btnLoad;

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
		game.setBounds(100, 100, 1000, 800);
		// menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		player1 = new JPanel();
		player1.setBorder(new LineBorder(Color.BLACK));

		p1Name = new JLabel("Name");

		p1prop1 = new JButton("prop1");

		p1prop2 = new JButton("prop2");

		p1Score = new JLabel("Score");

		infos = new JPanel();

		player2 = new JPanel();
		player2.setBorder(new LineBorder(new Color(0, 0, 0)));

		p2Name = new JLabel("Name");

		p2prop1 = new JButton("prop1");

		p2prop2 = new JButton("prop2");

		p2Score = new JLabel("Score");
		gl_player2 = new GroupLayout(player2);
		gl_player2.setHorizontalGroup(
			gl_player2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_player2.createParallelGroup(Alignment.LEADING)
						.addComponent(p2Name)
						.addComponent(p2Score))
					.addGap(100)
					.addComponent(p2prop1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGap(50)
					.addComponent(p2prop2, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(57))
		);
		gl_player2.setVerticalGroup(
			gl_player2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player2.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_player2.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_player2.createSequentialGroup()
							.addGroup(gl_player2.createParallelGroup(Alignment.BASELINE)
								.addComponent(p2prop2, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
								.addComponent(p2prop1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
							.addGap(6))
						.addGroup(gl_player2.createSequentialGroup()
							.addComponent(p2Name)
							.addPreferredGap(ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
							.addComponent(p2Score)
							.addGap(20))))
		);
		player2.setLayout(gl_player2);

		player3 = new JPanel();
		player3.setBorder(new LineBorder(new Color(0, 0, 0)));

		p3Name = new JLabel("Name");

		p3prop1 = new JButton("prop1");

		p3prop2 = new JButton("prop2");

		p3Score = new JLabel("Score");
		gl_player3 = new GroupLayout(player3);
		gl_player3.setHorizontalGroup(
			gl_player3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player3.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_player3.createParallelGroup(Alignment.LEADING)
						.addComponent(p3prop2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(p3prop1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
					.addGap(25))
				.addGroup(gl_player3.createSequentialGroup()
					.addContainerGap()
					.addComponent(p3Name)
					.addContainerGap(155, Short.MAX_VALUE))
				.addGroup(gl_player3.createSequentialGroup()
					.addContainerGap()
					.addComponent(p3Score)
					.addContainerGap(154, Short.MAX_VALUE))
		);
		gl_player3.setVerticalGroup(
			gl_player3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player3.createSequentialGroup()
					.addComponent(p3Name)
					.addGap(20)
					.addComponent(p3prop1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p3prop2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(p3Score)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		player3.setLayout(gl_player3);

		middleCards = new JPanel();
		middleCards.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblTourActuel = new JLabel("Tour actuel : ");

		btnSave = new JButton("Save");

		btnLoad = new JButton("Load");
		groupLayout = new GroupLayout(game.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(player1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addGap(25)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(middleCards, GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE).addComponent(player2, GroupLayout.PREFERRED_SIZE, 550, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(player3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblTourActuel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(infos, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnLoad).addComponent(btnSave)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayout.createSequentialGroup().addComponent(btnSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLoad))
										.addComponent(infos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblTourActuel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup().addGap(20)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(player3, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE).addComponent(player1, GroupLayout.PREFERRED_SIZE, 500,
												GroupLayout.PREFERRED_SIZE))
										.addGap(206))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(player2, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE).addGap(24))))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addGap(60).addComponent(middleCards, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		lblInfos = new JLabel("Infos");
		infos.add(lblInfos);

		trickPile = new JButton("Pile deTricks");

		mprop1 = new JButton("prop 1");

		mprop2 = new JButton("prop2");

		depiledTrick = new JLabel("Trick dépilé");
		gl_middleCards = new GroupLayout(middleCards);
		gl_middleCards.setHorizontalGroup(
			gl_middleCards.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_middleCards.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_middleCards.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_middleCards.createSequentialGroup()
							.addComponent(trickPile, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
							.addComponent(depiledTrick, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addGap(42))
						.addGroup(gl_middleCards.createSequentialGroup()
							.addComponent(mprop1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(mprop2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_middleCards.setVerticalGroup(
			gl_middleCards.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_middleCards.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_middleCards.createParallelGroup(Alignment.BASELINE)
						.addComponent(trickPile, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
						.addComponent(depiledTrick, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
					.addGap(20)
					.addGroup(gl_middleCards.createParallelGroup(Alignment.LEADING, false)
						.addComponent(mprop1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
						.addComponent(mprop2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
					.addGap(12))
		);
		middleCards.setLayout(gl_middleCards);
		gl_player1 = new GroupLayout(player1);
		gl_player1.setHorizontalGroup(
			gl_player1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player1.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_player1.createParallelGroup(Alignment.LEADING)
						.addComponent(p1prop2, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
						.addComponent(p1prop1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
					.addGap(25))
				.addGroup(gl_player1.createSequentialGroup()
					.addContainerGap()
					.addComponent(p1Name)
					.addContainerGap(155, Short.MAX_VALUE))
				.addGroup(gl_player1.createSequentialGroup()
					.addContainerGap()
					.addComponent(p1Score)
					.addContainerGap(152, Short.MAX_VALUE))
		);
		gl_player1.setVerticalGroup(
			gl_player1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_player1.createSequentialGroup()
					.addComponent(p1Name)
					.addGap(20)
					.addComponent(p1prop1, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p1prop2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(p1Score)
					.addGap(6))
		);
		player1.setLayout(gl_player1);
		game.getContentPane().setLayout(groupLayout);

		addAllActionListeners();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		redraw();
		
		if(arg0 instanceof Game) {
			
			switch((String)arg1) {
			
				case "nbOfPlayers" : 
					
					break;
					
				case "identityOfPLayer" : 
					
					break;
					
				case "new prop" : 
					
					break;
					
				case "new trick" :
					
					break;
					
				case "depile" :
					
					break;
					
				case "trick pile empty" : 
					
					break;
				
				case "Trick realized" : 
					
					break;
					
				case "last turn" : 
					
					break;
					
				
			}
		}
		
		if(arg0 instanceof Player) {
			
			if(arg0 instanceof Human) {
				
				switch((String)arg1){
				
					
					case "name" : 
						
						break;
						
						
					case "Player0" : 
						
						break;
						
					case "Player1" : 
						
						break;
						
					case "Player2" : 
						
						break;
				
					case "revealNewTrick" :
						
						break;
				
					case "chooseOwnProp" :
						
						break;
				
					case "chooseOtherProp" : 
						
						break;
				
					case "chooseMiddleVarCarrot" :
						
						break;
				
					case "performTrick" : 
						
						break;
				
					case "revealProp" :
						
						break;
				
					case "chooseMiddle" : 
						
						break;
						
						
					case "reveal prop" : 
						
						break;
						
					case "other hat trick realized" :
						
						break;
						
					case "possess other rabbit" : 
						
						break;
						
					case "possess hat" : 
						
						break;
						
						
				}
			}
			
			if(arg0 instanceof Robot) {
				
			}
			
		}
		
		if(arg0 instanceof Trick) {
			
			switch((String) arg1) {
			
				case "print trick" : 
					
					break;
					
				case "value decreased" : 
					
					break;
			}
		}
		
		if(arg0 instanceof Prop) {
			
			switch((String) arg1) {
			
				case "hide" : 
					break; 
					
				case "unhide" : 
					break;
					
				case "print" : 
					break;
					
				case "print visible" : 
					
					break;
					
				case "print debug" : 
					break;
			}
		}
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void addAllActionListeners() {
		trickPile.addActionListener(new DepilePropButton()); // La pile de tricks
		mprop1.addActionListener(new PropButtonListener(-1, 0)); // Les deux props du milieu
		mprop2.addActionListener(new PropButtonListener(-1, 1));
		p1prop1.addActionListener(new PropButtonListener(0, 0));// Les props des joueurs
		p1prop2.addActionListener(new PropButtonListener(0, 1));
		p2prop1.addActionListener(new PropButtonListener(1, 0));
		p2prop2.addActionListener(new PropButtonListener(1, 1));
		p3prop1.addActionListener(new PropButtonListener(2, 0));
		p3prop2.addActionListener(new PropButtonListener(2, 1));
		btnLoad.addActionListener(new SaveButtonListener("load"));
		btnSave.addActionListener(new SaveButtonListener("save"));
	}

	public void redraw() {
		Game gameModel = this.controler.getGame();
		Player[] players = gameModel.getPlayers();

		boolean allGood = true; // Détermine si tous les éléments sont présents pour faire l'affichage

		if (players == null) {
			allGood = false;
		}
		if (players.length != 3) {
			allGood = false;
		}

		if (gameModel.getDepiledTrick() == null) {
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

			p1Score.setText("Score : " + String.valueOf(players[0].getScore()));
			p2Score.setText("Score : " + String.valueOf(players[1].getScore()));
			p3Score.setText("Score : " + String.valueOf(players[2].getScore()));

			/*p1prop1.setText(players[0].getHand(0).getName());
			p1prop2.setText(players[0].getHand(1).getName());
			p2prop1.setText(players[1].getHand(0).getName());
			p2prop2.setText(players[1].getHand(1).getName());
			p3prop1.setText(players[2].getHand(0).getName());
			p3prop2.setText(players[2].getHand(1).getName());*/

			setImage(p1prop1, players[0].getHand(0));
			setImage(p1prop2, players[0].getHand(1));
			setImage(p2prop1, players[1].getHand(0));
			setImage(p2prop2, players[1].getHand(1));
			setImage(p3prop1, players[2].getHand(0));
			setImage(p3prop2, players[2].getHand(1));
			
			setImage(mprop1, Game.getMiddleProp().get(0));
			
			trickPile.setText("");
			trickPile.setMargin(new Insets(0, 0, 0, 0));
			trickPile.setBorder(null);
			trickPile.setIcon(new ImageIcon("./CARTES/resizedPROPS/DOS.jpg"));
			
			depiledTrick.setText("");
			depiledTrick.setBorder(null);
			depiledTrick.setIcon(new ImageIcon("./CARTES/resizedTRICKS/" + controler.getGame().getDepiledTrick().getName() +".jpg"));
			
			//mprop1.setText(Game.getMiddleProp().get(0).getName());

			depiledTrick.setText(gameModel.getDepiledTrick().getName());

			if (Game.getMiddleProp().size() > 1) {
				setImage(mprop2, Game.getMiddleProp().get(1));
				//mprop2.setText(Game.getMiddleProp().get(1).getName());
			} else {
				middleCards.remove(mprop2);
				// mprop2.setEnabled(false);
			}
		}

	}

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

	class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Démarrage du jeu");
			controler.createGame();
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
			controler.actionPerformed(new ActionEvent("trickPile", 0, "Appui sur la pile de tricks"));
			// controler.setRevealNewTrick(controler.getGame().getCurrentPlayer(), true);
		}

	}

	class SaveButtonListener implements ActionListener {
		String function;

		public SaveButtonListener(String function) {
			this.function = function;
		}

		public void actionPerformed(ActionEvent e) {
			controler.actionPerformed(new ActionEvent(this.function, 0, ""));
		}
	}
}
