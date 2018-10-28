package theotherhattrick;

import java.util.Scanner;

public class Player {
	private int id;
	private String name;
	private Prop[] props;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setProps(Prop[] props) {
		this.props = props;
	}

	public void echangerCartes() {
	}

	public void realiserTrick() {
	}

	public void chooseTrick() {
	}

	public boolean speak(String text) {
		String ans;
		Scanner keyboard = new Scanner(System.in);

		System.out.println(text);

		do {
			System.out.println("Entrer y pour répondre oui et n pour répondre non");
			ans = keyboard.nextLine();
			ans.toLowerCase();
		} while (ans != "y" || ans != "n");

		keyboard.close();

		if (ans == "y") {
			return true;
		} else {
			return false;
		}

	}

	public int choosePropsToChange() { // Retourne l'indice dans Player.tricks[] de la carte que le joueur souhaite
										// retourner
		return 0; // Temporaire
	}

	public void printProps() {
		System.out.println("Le joueur " + this.name + " possède les cartes suivantes");
		for (int i = 0; i < props.length; i++) {
			props[i].print();
		}
	}

}
