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

/**
 *  Un objet de type Menu représente la fenêtre s'affichant en premier lors du lancement de la partie. 
 *  On y rentre les paramètres de la partie.
 *  On utilise un objet de type JComboBox pour choisir la variante, et un boutton, quand appuyé, instancie un objet JOptionPane qui indique les règles du jeux.
 *  
 * @author amall
 *
 * @see PlayerSetUp
 * @see GraphicView
 */
public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame frame;

	private JPanel variantPanel = new JPanel();
	private JLabel variantLabel;
	private JComboBox<String> variantcb = new JComboBox<String>();
	private JButton rules = new JButton("rules");
	private JButton start;

	private PlayerSetUp[] psu = new PlayerSetUp[3];

	/**
	 * Constructeur du Menu. Il lance l'initialisation.
	 * 
	 * @see #initialize()
	 */
	public Menu(JButton startButton) {
		this.start = startButton;
		initialize();
	}

	/**
	 * Initialise le contenu de la fenetre.
	 * Le panneau de la variant et les panneaux des joueurs sont agencés verticalement.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.white);
		frame.setBounds(100, 100, 450, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("THE OTHER HAT TRICK - Nouvelle partie");
		frame.setBackground(Color.WHITE);

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

		for (int i = 0; i < 3; i++) {
			psu[i] = new PlayerSetUp(i);
			frame.getContentPane().add(psu[i]);
		}

		start.setPreferredSize(new Dimension(225, 25));
		start.setAlignmentX(CENTER_ALIGNMENT);

		frame.getContentPane().add(start);
	}

	/**
	 * Permet de controler l'affichage du Menu par un autre objet.
	 */
	public void setVisible(boolean bool) {
		this.frame.setVisible(bool);
	}

	public int getVariant() {
		return this.variantcb.getSelectedIndex();
	}

	/**
	 * @param id
	 * @return la date de naissance du naissance du joueur représenté par l'objet PlayerSetup d'indice id.
	 */
	public Date getBirthDate(int id) {
		return psu[id].getBirthD();
	}
	
	/**
	 * @param id
	 * @return le nom du joueur représenté par l'objet PlayerSetup d'indice id.
	 */
	public String getName(int id) {
		return psu[id].getName();
	}
	
	/**
	 * @param id
	 * @return la nature du joueur représenté par l'objet PlayerSetup d'indice id : joueur humain ou IA
	 */
	public boolean isAI(int id) {
		return psu[id].isAI();
	}
	
	
	class VariantListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			
		}

	}

	/**
	 * Classe interne qui écoute le bouton des règles.
	 * @author amall
	 *
	 */
	class RuleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, Game.RULE_SWISS_ARMY_KNIFE + Game.RULE_CARROT + Game.RULE_LETTUCE, "Règles du jeux", JOptionPane.INFORMATION_MESSAGE);

		}

	}
}