package theotherhattrickView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import theotherhattrick.Date;
import theotherhattrick.Game;

public class Menu extends JFrame {

	private JFrame frame;

	private JPanel variantPanel = new JPanel();
	private JLabel variantLabel;
	private JComboBox variantcb = new JComboBox();
	private JButton rules = new JButton("rules");
	private JButton start;

	private PlayerSetUp[] psu = new PlayerSetUp[3];

	/**
	 * Create the application.
	 */
	public Menu(JButton startButton) {
		this.start = startButton;
		initialize();
		// setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.white);
		frame.setBounds(100, 100, 450, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("THE OTHER HAT TRICK - Menu");
		frame.setBackground(Color.WHITE);

		// Initialize the first line of the window : the choice of the variant
		variantPanel.setBackground(null);
		variantPanel.setPreferredSize(new Dimension(225, 35));
		variantLabel = new JLabel("Choisissez une variante : ");
		variantLabel.setBackground(Color.WHITE);

		variantcb.addItem("Sans variante");
		variantcb.addItem("Couteau suisse");
		variantcb.addItem("Carotte magique");
		variantcb.addItem("Laitue magique");
		variantcb.addItemListener(new VariantListener());

		rules.addActionListener(new RuleButtonListener());
		variantPanel.add(variantLabel);
		variantPanel.add(variantcb);
		variantPanel.add(rules);
		frame.getContentPane().add(variantPanel);

		// Initialize the 3 lines for the set up of the players
		for (int i = 0; i < 3; i++) {
			psu[i] = new PlayerSetUp(i);
			frame.getContentPane().add(psu[i]);
		}

		start.setPreferredSize(new Dimension(225, 25));
		start.setAlignmentX(CENTER_ALIGNMENT);

		frame.getContentPane().add(start);
	}

	public void setVisible(boolean bool) {
		this.frame.setVisible(bool);
	}

	public int getVariant() {
		return this.variantcb.getSelectedIndex();
	}

	//Retourne la date de naissance du joueur ID
	public Date getBirthDate(int id) {
		return psu[id].getBirthD();
	}
	
	public String getName(int id) {
		return psu[id].getName();
	}
	
	public boolean isAI(int id) {
		return psu[id].isAI();
	}

	class VariantListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// System.out.println(variantcb.getSelectedIndex());

		}

	}

	class RuleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane rules = new JOptionPane();
			rules.showMessageDialog(null, Game.RULE_SWISS_ARMY_KNIFE + Game.RULE_CARROT + Game.RULE_LETTUCE, "Règles du jeux", JOptionPane.INFORMATION_MESSAGE);

		}

	}
}