package theotherhattrickView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Observer.Observer;
import theotherhattrick.Game;
import theotherhattrickControler.GameControler;
import javax.swing.JMenu;

public class GraphicView extends JFrame implements Observer{

	private JFrame frame;
	private GameControler controler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Game game = new Game();
				GameControler gc = new GameControler(game);
				try {
					GraphicView window = new GraphicView(gc);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicView(GameControler gc) {
		initialize();
		this.controler = gc;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		NewGameButtonListener sbl = new NewGameButtonListener();
		
		
		
		
		frame = new JFrame();
		frame.setTitle("The Other Hat Trick");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pane = new JPanel();
		pane.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(pane, BorderLayout.NORTH);
		
		JLabel title = new JLabel("New label");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setText(Game.NAME);
		pane.add(title);
		
		JPanel launchPane = new JPanel();
		JButton gameLauncher = new JButton("New Game");
		gameLauncher.addActionListener(sbl);
		
		launchPane.setPreferredSize(new Dimension(50, 225));
		launchPane.add(gameLauncher);
		frame.getContentPane().add(launchPane, BorderLayout.SOUTH);
		
		
	}

	@Override
	public void update(String str) {
		// TODO Auto-generated method stub
		
	}
	
	class NewGameButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Menu menu = new Menu();
			
		}
		
	}
	
	

}
