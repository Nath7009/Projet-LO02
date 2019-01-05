package theotherhattrickView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import theotherhattrick.Game;
import theotherhattrickControler.GameControler;

public class GraphicView2 extends JFrame implements Observer {

	private JFrame frame;
	private GameControler controler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// VueTexte vt = new VueTexte();
					GraphicView2 gv = new GraphicView2(new GameControler(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicView2 (GameControler gc) {
		this.controler = gc;
		System.out.println("TESt");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		NewGameButtonListener sbl = new NewGameButtonListener();

		System.out.println("tes");

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

		frame.setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	class NewGameButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Menu menu = new Menu();

		}
	}
}
