package theotherhattrick;

import java.util.Scanner;

public class Game {

	private static Game game;
	private Board board;
	private Player[] players;
	private int tour; // Le joueur qui doit jouer

	private Game() {
	}

	public static Game newGame() {
		if (game == null) {
			game = new Game();
			return game;
		}
		return game;
	}

	public void start() {
		Scanner keyboard;
		int nbHumains = 0;
		String nom;

		// Instanciation de tous les joueurs humains ou robots
		players = new Player[3];
		keyboard = new Scanner(System.in);
		do {
			System.out.println("Entrer le nombre de joueurs humains voulant participer au jeu");
			nbHumains = keyboard.nextInt();
			keyboard.nextLine();
		} while (nbHumains > 3);

		for (int i = 0; i < nbHumains; i++) {
			System.out.println("Entrer le nom du joueur numéro " + (i + 1));
			nom = keyboard.nextLine();
			players[i] = new Player(nom);
		}
		for (int i = nbHumains - 1; i < 3; i++) {
			players[i] = new Robot();
		}

		board = new Board(players);

		// Gestion du tour de jeu : faire jouer chaque joueur tour après tour jusqu'à ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		keyboard.close();
	}

	private void JouerTour() {
		Player p = players[tour];
		boolean playerIn;

		if (p instanceof Human) {
			System.out.println("C'est le tour de " + p.getName());
			playerIn = p.speak("Retourner un trick ?");

			if (playerIn) {
				board.depile();
			}

			// Changer les props
			playerIn = p.speak("Performer le trick ?");
			// Performer le trick ?

		}

		else {
			System.out.println("Le comportements des robots n'a pas encore été implémenté");
		}

		this.nextTurn();

	}

	private void nextTurn() {
		this.tour++;
	}

}
