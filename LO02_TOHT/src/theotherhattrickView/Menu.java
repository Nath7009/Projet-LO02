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

import theotherhattrick.Game;

public class Menu extends JFrame{

	private JFrame frame;
	
	private JPanel variantPanel = new JPanel();
	private JLabel variantLabel;
	private JComboBox variantcb = new JComboBox();
	private JButton rules = new JButton("rules");
	private JButton start = new JButton("Start !");

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.white);
		frame.setBounds(100, 100, 450, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("THE OTHER HAT TRICK - Menu");
		frame.setBackground(Color.WHITE);
		
		//Initialize the first line of the window : the choice of the variant
		variantPanel.setBackground(null);
		variantPanel.setPreferredSize(new Dimension(225,25));
		variantLabel = new JLabel("Choisissez une variante : ");
		variantLabel.setBackground(Color.WHITE);
		
		variantcb.addItem("sans variante");
		variantcb.addItem("couteau suisse");
		variantcb.addItem("Laitue magique");
		variantcb.addItem("Carotte magique");
		variantcb.addItemListener(new VariantListener());
		
		rules.addActionListener(new RuleButtonListener());
		variantPanel.add(variantLabel);
		variantPanel.add(variantcb);
		variantPanel.add(rules);
		frame.getContentPane().add(variantPanel);
		
		//Initialize the 3 lines for the set up of the players
		for(int i = 0; i < 3 ; i++) {
			PlayerSetUp psu = new PlayerSetUp(i);
			frame.getContentPane().add(psu);
		}
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		start.setPreferredSize(new Dimension(225,25));
		start.setAlignmentX(CENTER_ALIGNMENT);
		
		frame.getContentPane().add(start);
	}
	
	
	public void setVisible(boolean bool) {
		this.frame.setVisible(bool);
	}
	
	
	class VariantListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			
			
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