package theotherhattrick;

import java.util.Scanner;

public class Player {
	private int id;
	private String name;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void echangerCartes() {
	}

	public void realiserTrick() {
	}

	public void chooseTrick() {
	}

	public boolean returnTrick() {
		return false;
	}

	public boolean speak(String text) {
		String ans;
		Scanner keyboard = new Scanner(System.in);

		System.out.println(text);

		do {
			System.out.println("Entrer y pour répondre oui et n pour répondre non");
			ans = keyboard.nextLine();
		} while (ans != "y" || ans != "n");

		if (ans == "y") {
			return true;
		} else {
			return false;
		}
	}

	public void choosePropsToChange() {
	}


}
