package theotherhattrick;

import java.util.Scanner;

public class Game {

	private static Game game;
	private Board board;
	private Player[] players;
	private int tour; // Le joueur qui doit jouer
	private Scanner keyboard;

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
			players[i] = new Human(nom, i);
		}
		for (int i = nbHumains; i < 3; i++) {
			players[i] = new Robot(i);
		}

		board = new Board(players);
		board.depile();

		// Gestion du tour de jeu : faire jouer chaque joueur tour aprà¨s tour jusqu'à  ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		while (board.getTrickPileLength() > 0 && this.tour < 100) {
			this.playTurn();
		}

		keyboard.close();
	}

	private void playTurn() {
		Player p = players[tour % 3];
		boolean playerIn;
		int playerChoice = 0;

		if (p instanceof Human || p instanceof Robot) {
			System.out.println("C'est le tour de " + p.getName());
			System.out.println("Votre jeu est :");
			p.printProps();
			
			board.printTopTrick(); //On affiche le trick sur le dessus de la pile pour que le joueur puisse faire son choix
			playerIn = p.speak("Retourner un trick ?", 2, players, 'b', keyboard) == 1 ? true : false; // Conversion
																		// d'int en booleen
			// RETOURNER UN TRICK ?
			if (playerIn) {
				board.depile();
			}

			// CHOISIR SA CARTE A ECHANGER
			int ind1 = -1;
			int ind2 = -1;
			int p2 = -1;
			playerChoice = p.speak("Choisir la carte à  échanger", 2, players, 'p', keyboard);

			if (playerChoice >= 0) {
				ind1 = playerChoice;
			} else {
				System.out.println("Une erreur est survenue dans la réception des cartes à échanger");
			}

			// CHOISIR LA CARTE ADVERSE A ECHANGER
			playerChoice = p.speak("Choisir la carte adverse à  échanger", 4, players, 'n', keyboard);

			if (playerChoice >= 0) {
				p2 = (playerChoice / 2 + 1 + p.getId()) % 3; // Normalement cette ligne fonctionne, mais certains bugs
																// peuvent venir de là 
				ind2 = playerChoice % 2;
			}

			// ECHANGE DES PROPS
			board.exchangeProps(p.getId(), ind1, p2, ind2);
			p.printProps();

			// PERFORMER LE TRICK
			playerIn = p.speak("Performer le trick ?", 2, players, 'b', keyboard) == 1 ? true : false; // Conversion
																										// d'int en
																										// booleen
			boolean trickSuccessful = false;

			if (playerIn) { // Si le joueur souhaite performer le trick
				trickSuccessful = board.comparePropsToTrick(p.getId());
				if (trickSuccessful) { // Si le joueur a réussi le trick
					board.showAllProps(p.getId()); // On montre ses cartes

					// TODO Ajouter un délai afin que le joueur montre ses cartes pendant plus
					// longtemps

					board.giveTrick(p.getId()); // On lui donne le trick
					board.hideAllProps(p.getId()); // On cache ses cartes
				} else { // Si le joueur rate le trick
					board.revealProp(p.getId(), this.keyboard);
				}
			} else { // Le joueur ne souhaite pas faire le trick
				board.revealProp(p.getId(), this.keyboard);
			}

			// ECHANGE DES CARTES EN CAS DE SUCCES DU TRICK
			if (trickSuccessful) {
				System.out.println("Etant donné que vous avez réalisé le tour, vous pouvez échanger vos cartes avec la carte du milieu");
				System.out.println("La carte du milieu est :");
				board.getMiddleProp().print();
				playerChoice = p.speak("Avec quel prop voulez vous échanger le prop du milieu ?", 3, players, 'a', keyboard);
				if(playerChoice == 0) { //On échange le prop de gauche avec celui du milieu
					board.exchangeProps(p.getId(), 0, -1, 0);
				}
				else if(playerChoice == 1) { //On échange le prop de droite avec celui du milieu
					board.exchangeProps(p.getId(), 1, -1, 0);
				}
				board.hideAllProps(p.getId()); //Au cas où le trick du milieu était visible
			}
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
