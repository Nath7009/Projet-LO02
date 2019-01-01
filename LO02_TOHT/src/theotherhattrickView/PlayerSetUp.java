package theotherhattrickView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class PlayerSetUp extends JPanel{
	
//	private JPanel playerPane = new JPanel();
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton human, AI;
	private JLabel nameL, natureOfPlayer;
	private JTextField name;
	
	private JPanel natureOfPlayerPane = new JPanel();
	private JPanel namePane = new JPanel();
	private JPanel datePane = new JPanel();
	
	public PlayerSetUp(int i) {
		this.initComponent(i);
	}
	
	private void initComponent(int i) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Joueur n°" + (i+1)));
		
		this.nameL = new JLabel("Nom du joueur :");
		this.natureOfPlayer = new JLabel("Type de joueur : ");
		this.name = new JTextField();
		this.human = new JRadioButton("Humain");
		this.human.addActionListener(new HumanButtonListener());
		this.AI = new JRadioButton("IA");
		this.AI.addActionListener(new IAButtonListener());
		
		
		this.human.setSelected(true);			
		this.bg.add(this.human);
		this.bg.add(this.AI);
		this.natureOfPlayerPane.setLayout(new BoxLayout(this.natureOfPlayerPane, BoxLayout.X_AXIS));
		this.natureOfPlayerPane.add(natureOfPlayer);
		this.natureOfPlayerPane.add(this.human);
		this.natureOfPlayerPane.add(this.AI);
		
		this.namePane.setLayout(new BoxLayout(this.namePane, BoxLayout.X_AXIS));
		this.namePane.add(nameL);
		this.namePane.add(name);
		
		
		this.add(this.natureOfPlayerPane);
		this.add(this.namePane);
	}
	
	class IAButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if(((JRadioButton)arg0.getSource()).isSelected()) {
				name.setBackground(Color.gray);
				name.setEnabled(false);
			}			
		}	
	}	
	
	class HumanButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if(((JRadioButton)arg0.getSource()).isSelected()) {
				name.setBackground(Color.white);
				name.setEnabled(true);
			}			
		}	
	}
}