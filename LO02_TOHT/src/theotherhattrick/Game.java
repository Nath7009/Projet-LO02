package theotherhattrick;

import java.util.Scanner;

public class Game {

	protected Board board;
	protected Player[] players;
	protected int tour; // Le joueur qui doit jouer
	protected static Scanner keyboard = new Scanner(System.in);

	public Game() {
	}

	public static Game createGame() {
		Game game = new Game();
		int variant = 0;
		// 0 pour le jeu de base
		// 1 pour la variante swissKnife
		// 2 pour la variante carrot
		// 3 pour la variante lettuce
		variant = askRules();

		// On utilise le polymorphisme pour g�rer les variantes
		switch (variant) {
		case 0:
			return new Game();
		case 1:
			return new GameVarSwissKnife();
		case 2:
			return new GameVarCarrot();
		case 3:
			return new GameVarLettuce();
		}

		return game;
	}

	/*
	 * public static Game newGame() { if (game == null) { game = new Game(); return
	 * game; } return game; }
	 */

	public void start() {

		keyboard = new Scanner(System.in);

		// Instanciation de tous les joueurs humains ou robots
		this.createPlayers();
		
		board = new Board(players, getVariant());
		board.depile();

		// Gestion du tour de jeu : faire jouer chaque joueur tour apr�s tour
		// jusqu'� ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		while (this.tour < 100 && !this.isFinished()) {
			this.playTurn();
			this.nextTurn();
			if (board.depiledIsEmpty()) {
				board.depile();
			}
		}

		if (this.isFinished()) {
			int winner = this.getWinner();
			System.out.println("Le joueur " + players[winner].getName() + " a gagné avec un score de " + players[winner].getScore() + "points, BRAVO !");
		}

		keyboard.close();
	}

	private void createPlayers() {
		int nbHumains = 0;
		String nom;
		Date date;
		
		players = new Player[3];
		do {
			System.out.println("Entrer le nombre de joueurs humains voulant participer au jeu");
			nbHumains = keyboard.nextInt();
			keyboard.nextLine();
		} while (nbHumains > 3);

		for (int i = 0; i < nbHumains; i++) {
			System.out.println("Entrer le nom du joueur num�ro " + (i + 1));
			nom = keyboard.nextLine();
			date = this.askBirthDate();
			players[i] = new Human(nom, i, date);
		}

		for (int i = nbHumains; i < 3; i++) {
			date = new Date();
			players[i] = new Robot(i, date);
		}

		this.sortPlayers();

		for (int i = 0; i < players.length; i++) {
			players[i].setId(i);
		}
	}

	protected void depileTrick(Player p) {
		boolean playerIn = p.speak("Retourner un trick ?", 2, players, 'b') == 1 ? true : false; // Conversion
		if (playerIn) {
			board.depile();
		}
	}

	protected void realizeTrick(Player p) {
		// G�re l'enchainement des actions qui se r�alisent quand on r�alise un
		// trick
		boolean trickSuccessful = board.comparePropsToTrick(p.getId());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			board.showAllProps(p.getId()); // On montre ses cartes

			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			board.hideAllProps(p.getId()); // On cache ses cartes
			System.out.println("Vous pouvez échanger l'une de vos cartes avec la carte du milieu");

			exchangeMiddle(p);

			// DONNER LE TRICK AU JOUEUR
			board.giveTrick(p.getId()); // On lui donne le trick

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			board.revealProp(p.getId());
		}

	}

	private void playTurn() {
		Player p = players[tour % 3];
		boolean playerIn;
		// if (p instanceof Human || p instanceof Robot) {
		System.out.println("C'est le tour de " + p.getName());
		System.out.println("Votre jeu est :");
		p.printProps();

		board.printTopTrick(); // On affiche le trick sur le dessus de la pile pour que le joueur puisse faire
								// son choix
		// Demande � l'utilisateur si il souhaite d�piler un trick
		depileTrick(p);

		// DEMANDE LES CARTES A ECHANGER ET FAIT L'ECHANGE
		exchangePlayers(p);
		p.printProps();

		// PERFORMER LE TRICK
		playerIn = p.speak("Performer le trick ?", 2, players, 'b') == 1 ? true : false; // Conversion
																							// d'int en
																							// booleen
		if (playerIn) { // Si le joueur souhaite performer le trick
			realizeTrick(p);
		} else { // Le joueur ne souhaite pas faire le trick
			board.revealProp(p.getId());
		}
	}

	public static int askRules() {
		int choice = 0;

		System.out.println("Veuillez choisir les règles avec lesquelles vous voulez jouer");

		System.out.println("Le Couteau Suisse, ajout d'une nouvelle carte capable d'être utilisé pour réaliser n'importe quel trick");
		System.out.println("Attention, l'utilisation du couteau suisse vous fera gagner moins de points � l'ex�cution du trick");

		System.out.println("La Carotte, permet d'�changer un props avec un autre joueur quand un tour est r�ussi en utilisant une carotte");

		System.out.println("La Laitue, quand un tour contenant la laitue est rat�, donne le choix au joueur de retourner au choix l'une de ses cartes");
		System.out.println("Il a donc le choix de cacher une de ses cartes si elle �tait face visible");

		do {
			System.out.println("Veullez choisir la r�gle que vous voulez utiliser");
			System.out.println("Entrer 0 pour jouer sans variantes, 1 pour jouer avec le Couteau Suisse, 2 pour jouer avec la Carotte, 3 pour jouer avec la Laitue");
			choice = keyboard.nextInt();
		} while (choice < 0 || choice > 3);

		return choice;
	}

	public boolean getBool() { // R�cup�re un bool�en du joueur qui cr�e la
								// partie
		String answer;
		do {
			System.out.println("Entrer y pour oui et n pour non : ");
			answer = keyboard.nextLine();
			answer.toLowerCase();
		} while (answer.equals("y") && answer.equals("n"));
		return answer.equals("y") ? true : false;
	}

	private void nextTurn() {
		this.tour++;
	}

	public int getVariant() {
		if (this instanceof GameVarSwissKnife) {
			return 1;
		} else if (this instanceof GameVarCarrot) {
			return 2;
		} else if (this instanceof GameVarLettuce) {
			return 3;
		}
		return 0;
	}

	protected void exchangeMiddle(Player p) { // échange une carte avec celle du milieu
		int propToChange;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p');
		this.board.exchangeProps(p.getId(), propToChange, -1, 0);
	}

	protected void exchangePlayers(Player p) {
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous �changer ?", 2, this.players, 'p');
		otherProp = p.speak("Avec quelle carte de vos adversaires souhaitez vous l'�changer ?", 4, players, 'n');
		int p2 = (otherProp / 2 + 1 + p.getId()) % 3;
		this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
	}

	protected boolean isFinished() {
		return this.board.getTrickPileLength() <= 0;
	}

	public Date askBirthDate() {
		int day = 0, month = 0, year = 0;
		do {
			System.out.println("Entrer votre jour de naissance");
			day = keyboard.nextInt();
			System.out.println("Entrer votre mois de naissance");
			month = keyboard.nextInt();
			System.out.println("Entrer votre ann�e de naissance");
			year = keyboard.nextInt();

		} while (day < 0 || month < 0 || year < 1910 || day > 31 || month > 12 || year > 2010);

		return new Date(year, month, day);
	}

	protected int getWinner() {
		int bestPlayer = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].getScore() > players[bestPlayer].getScore()) {
				bestPlayer = i;
			}
		}
		return bestPlayer;
	}

	protected void sortPlayers() {
		Player[] newPlayers = new Player[players.length];
		int indMax = 0;
		int bestInd = 0;
		while (indMax < newPlayers.length) {
			for (int i = 0; i < newPlayers.length; i++) {
				if (players[i] != null && players[bestInd] != null && players[bestInd].getBirthD().isUnder(players[i].getBirthD())) {
					bestInd = i;
				}
			}
			newPlayers[indMax] = players[bestInd];
			players[bestInd] = null;
			indMax++;
			bestInd = 0;
			while (bestInd < players.length && players[bestInd] == null) {
				bestInd++;
			}
		}
		players = newPlayers;
	}
}
