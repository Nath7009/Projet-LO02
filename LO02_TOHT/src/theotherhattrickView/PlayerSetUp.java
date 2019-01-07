package theotherhattrickView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import theotherhattrick.Date;

public class PlayerSetUp extends JPanel {

//	private JPanel playerPane = new JPanel();
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton human, AI;
	private JLabel nameL, natureOfPlayer, birthD;
	private JTextField name;

	private JPanel natureOfPlayerPane = new JPanel();
	private JPanel namePane = new JPanel();
	private JPanel datePane = new JPanel();

	private JComboBox comboBoxDay;
	private JComboBox comboBoxMonth;
	private JComboBox comboBoxYear;

	public PlayerSetUp(int i) {
		this.initComponent(i);
	}

	private void initComponent(int i) {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Joueur n°" + (i + 1)));

		this.nameL = new JLabel("Nom du joueur : ");
		this.natureOfPlayer = new JLabel("Type de joueur : ");
		this.birthD = new JLabel("Date de naissance du joueur : ");
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

		this.datePane.add(birthD);

		comboBoxDay = new JComboBox();
		comboBoxDay.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		datePane.add(comboBoxDay, BorderLayout.WEST);

		comboBoxMonth = new JComboBox();
		comboBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		comboBoxMonth.setMaximumRowCount(12);
		datePane.add(comboBoxMonth, BorderLayout.CENTER);

		comboBoxYear = new JComboBox();
		comboBoxYear.setModel(new DefaultComboBoxModel(
				new String[] { "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936",
						"1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964",
						"1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992",
						"1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018" }));
		comboBoxYear.setSelectedIndex(108);
		datePane.add(comboBoxYear, BorderLayout.EAST);

		this.add(this.natureOfPlayerPane);
		this.add(this.namePane);
		this.add(this.datePane);
	}

	public Date getBirthD() {
		if (!isAI()) {
			// On ajoute 1 puisque les index commencent à 0
			int day = comboBoxDay.getSelectedIndex() + 1;
			int month = comboBoxMonth.getSelectedIndex() + 1;
			int year = comboBoxYear.getSelectedIndex() + 1;
			Date birthD = new Date(day, month, year);
			return birthD;
		}
		return null;
	}

	public String getName() {
		if (!isAI() && name.getText().length() > 0) { // On a affaire à un humain
			return name.getText();
		}
		// Si c'est un robot, ou qu'aucun nom n'a été renseigné, on génère un nom au
		// hasard
		return null;
	}
	
	public boolean isAI() {
		return !name.isEnabled();
	}

	class IAButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (((JRadioButton) arg0.getSource()).isSelected()) {
				name.setBackground(Color.gray);
				name.setEnabled(false);
				datePane.getComponent(1).setEnabled(false);
				datePane.getComponent(2).setEnabled(false);
				datePane.getComponent(3).setEnabled(false);
			}
		}
	}

	class HumanButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (((JRadioButton) arg0.getSource()).isSelected()) {
				name.setBackground(Color.white);
				name.setEnabled(true);
				datePane.getComponent(1).setEnabled(true);
				datePane.getComponent(2).setEnabled(true);
				datePane.getComponent(3).setEnabled(true);
			}
		}
	}
}