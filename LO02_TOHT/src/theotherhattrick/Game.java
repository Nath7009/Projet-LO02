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
		int nbHumains = 0;
		String nom;
		keyboard = new Scanner(System.in);

		// Instanciation de tous les joueurs humains ou robots
		players = new Player[3];
		do {
			System.out.println("Entrer le nombre de joueurs humains voulant participer au jeu");
			nbHumains = keyboard.nextInt();
			keyboard.nextLine();
		} while (nbHumains > 3);

		for (int i = 0; i < nbHumains; i++) {
			System.out.println("Entrer le nom du joueur num�ro " + (i + 1));
			nom = keyboard.nextLine();
			players[i] = new Human(nom, i);
		}
		for (int i = nbHumains; i < 3; i++) {
			players[i] = new Robot(i);
		}

		board = new Board(players, getVariant());
		board.depile();

		// Gestion du tour de jeu : faire jouer chaque joueur tour apr�s tour
		// jusqu'� ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		while (board.getTrickPileLength() > 0 && this.tour < 100) {
			this.playTurn();
		}

		keyboard.close();
	}

	protected void depileTrick(Player p) {
		boolean playerIn = p.speak("Retourner un trick ?", 2, players, 'b') == 1 ? true : false; // Conversion
		if (playerIn) {
			board.depile();
		}
	}

	protected void realizeTrick(Player p) {
		// G�re l'enchainement des actions qui se r�alisent quand on r�alise un trick
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

			/*
			 * if (this.rulesSwissKnife) { if (this.rulesCarrot) { if
			 * (this.board.contains("Carrot")) { System.out.
			 * println("l'une des cartes du milieu ou une carte de vos adversaires"); //
			 * ECHANGE AVEC TOUT LE MONDE ET LES DEUX CARTES DU MILIEU exchangeWithAll(p); }
			 * else { // si on a pas la carte carrot
			 * System.out.println("l'une des deux cartes du milieu"); // ECHANGE AVEC LES
			 * DEUX CARTES DU MILIEU exchangeMiddle2(p); }
			 * 
			 * } else { // si on a pas la règle carrot
			 * System.out.println("l'une des deux cartes du milieu"); // ECHANGE AVEC LES
			 * DEUX CARTES DU MILIEU exchangeMiddle2(p); } } else { // si on a pas la
			 * variante couteau suisse if (this.rulesCarrot) { if
			 * (this.board.contains("Carrot")) {
			 * System.out.println("la carte du milieu ou une carte de vos adversaires"); //
			 * ECHANGE AVEC TOUT LE MONDE ET LA CARTE DU MILIEU exchangePlayersMiddle(p); }
			 * else { // si on a pas la carte carrot
			 * System.out.println("la carte du milieu"); exchangeMiddle(p); // ECHANGLE
			 * MILIEU STANDART } } else { System.out.println("la carte du milieu"); //
			 * ECHANGE MILIEU STANDART exchangeMiddle(p); } }
			 */
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

		this.nextTurn();
	}

	public static int askRules() {
		int choice = 0;

		System.out.println("Veuillez choisir les r�gles avec lesquelles vous voulez jouer");

		System.out.println("Le Couteau Suisse, ajout d'une nouvelle carte capable d'�tre utilis�e pour r�aliser n'importe quel trick");
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
		if(this instanceof GameVarSwissKnife) {
			return 1;
		}
		else if(this instanceof GameVarCarrot) {
			return 2;
		}
		else if(this instanceof GameVarLettuce) {
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

	protected Player[] sortPlayers(Player[] players) {
		Player temp = new Player("", -1);
		int ind;
		for(int i = 1; i < players.length ; i++) {
			temp = players[i];
			ind  = i-1;
			while(ind >= 0 && players[ind].getBirthD().isUnder(players[ind+1].getBirthD())) {
				temp = players[i];
				players[ind + 1] = players[ind];
				ind --;
			}
			players[ind + 1] = temp;
		}
		return players;
	}
}
