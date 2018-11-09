package theotherhattrick;

import java.util.Scanner;

public class Game {

	private static Game game;
	private Board board;
	private Player[] players;
	private int tour; // Le joueur qui doit jouer
	private Scanner keyboard;
	private boolean rulesSwissKnife;
	private boolean rulesCarrot;
	private boolean rulesLettuce;

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
			System.out.println("Entrer le nom du joueur num�ro " + (i + 1));
			nom = keyboard.nextLine();
			players[i] = new Human(nom, i);
		}
		for (int i = nbHumains; i < 3; i++) {
			players[i] = new Robot(i);
		}

		boolean rules[] = this.askRules();

		board = new Board(players, rules);
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

	private void playTurn() {
		Player p = players[tour % 3];
		boolean playerIn;
		int playerChoice = 0;

		// if (p instanceof Human || p instanceof Robot) {
		System.out.println("C'est le tour de " + p.getName());
		System.out.println("Votre jeu est :");
		p.printProps();

		board.printTopTrick(); // On affiche le trick sur le dessus de la pile pour que le joueur puisse faire
								// son choix
		playerIn = p.speak("Retourner un trick ?", 2, players, 'b', keyboard) == 1 ? true : false; // Conversion
		// d'int en booleen
		// RETOURNER UN TRICK ?
		if (playerIn) {
			board.depile();
		}

		// DEMANDE LES CARTES A ECHANGER ET FAIT L'ECHANGE
		exchangePlayers(p);
		p.printProps();

		// PERFORMER LE TRICK
		playerIn = p.speak("Performer le trick ?", 2, players, 'b', keyboard) == 1 ? true : false; // Conversion
																									// d'int en
																									// booleen
		boolean trickSuccessful = false;

		if (playerIn) { // Si le joueur souhaite performer le trick

			trickSuccessful = board.comparePropsToTrick(p.getId());

			if (trickSuccessful) { // Si le joueur a r�ussi le trick
				System.out.println("Vous avez réussi le tour");
				board.showAllProps(p.getId()); // On montre ses cartes

				// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
				// longtemps

				board.hideAllProps(p.getId()); // On cache ses cartes
				System.out.print("Vous pouvez échanger l'une de vos cartes avec ");

				if (this.rulesSwissKnife) {
					if (this.rulesCarrot) {
						if (this.board.contains("Carrot")) {
							System.out.println("l'une des cartes du milieu ou une carte de vos adversaires");
							// ECHANGE AVEC TOUT LE MONDE ET LES DEUX CARTES DU MILIEU
							exchangeWithAll(p);
						} else { // si on a pas la carte carrot
							System.out.println("l'une des deux cartes du milieu");
							// ECHANGE AVEC LES DEUX CARTES DU MILIEU
							exchangeMiddle2(p);
						}

					} else { // si on a pas la règle carrot
						System.out.println("l'une des deux cartes du milieu");
						// ECHANGE AVEC LES DEUX CARTES DU MILIEU
						exchangeMiddle2(p);
					}
				} else { // si on a pas la variante couteau suisse
					if (this.rulesCarrot) {
						if (this.board.contains("Carrot")) {
							System.out.println("la carte du milieu ou une carte de vos adversaires");
							// ECHANGE AVEC TOUT LE MONDE ET LA CARTE DU MILIEU
							exchangePlayersMiddle(p);
						} else { // si on a pas la carte carrot
							System.out.println("la carte du milieu");
							exchangeMiddle(p);
							// ECHANGLE MILIEU STANDART
						}
					} else {
						System.out.println("la carte du milieu");
						// ECHANGE MILIEU STANDART
						exchangeMiddle(p);
					}
				}

				// DONNER LE TRICK AU JOUEUR
				board.giveTrick(p.getId()); // On lui donne le trick

				/*
				 * System.out.
				 * println("Etant donn� que vous avez r�alis� le tour, vous pouvez �changer vos cartes avec la carte du milieu"
				 * ); System.out.println("La carte du milieu est :");
				 * board.getMiddleProp().print(); playerChoice =
				 * p.speak("Avec quel prop voulez vous �changer le prop du milieu ?", 3,
				 * players, 'a', keyboard); if (playerChoice == 0) { // On �change le prop de
				 * gauche avec celui du milieu board.exchangeProps(p.getId(), 0, -1, 0); } else
				 * if (playerChoice == 1) { // On �change le prop de droite avec celui du milieu
				 * board.exchangeProps(p.getId(), 1, -1, 0); } board.hideAllProps(p.getId()); //
				 * Au cas o� le trick du milieu �tait visible
				 */

			} else { // Si le joueur rate le trick
				System.out.println("Vous avez échoué le tour");
				if (this.rulesLettuce && this.board.contains("Lettuce")) { // Si
					// retourner props, mais pas forcément cacher
					board.returnProp(p.getId(), this.keyboard);

				} else {
					board.revealProp(p.getId(), this.keyboard);
				}
			}

		} else { // Le joueur ne souhaite pas faire le trick
			board.revealProp(p.getId(), this.keyboard);
		}

		boolean hasCarrot = this.board.contains("Carrot");

		// ECHANGE DES CARTES EN CAS DE SUCCES DU TRICK
		if (trickSuccessful) {

		}

		else
			// }

			// else {
			// System.out.println("Le comportements des robots n'a pas encore �t�
			// impl�ment�");
			// }

			this.nextTurn();

	}

	/*
	 * public static boolean[] getVariants() { boolean[] variants = new boolean[3];
	 * variants[0] = this.rulesSwissKnife; variants[1] = this.rulesCarrot;
	 * variants[2] = this.rulesLettuce; return variants;
	 * 
	 * }
	 */

	public boolean[] askRules() {
		boolean[] rules = new boolean[3];

		System.out.println("Veuillez choisir les r�gles avec lesquelles vous voulez jouer");

		System.out.println("Le Couteau Suisse, ajout d'une nouvelle carte capable d'�tre utilis�e pour r�aliser n'importe quel trick");
		System.out.println("Attention, l'utilisation du couteau suisse vous fera gagner moins de points � l'ex�cution du trick");
		System.out.println("Voulez vous utiliser cette extension ?");
		rules[0] = this.getBool();
		this.rulesSwissKnife = rules[0];

		System.out.println("La Carotte, permet d'�changer un props avec un autre joueur quand un tour est r�ussi en utilisant une carotte");
		System.out.println("Voulez vous utiliser cette r�gle ?");
		rules[1] = this.getBool();
		this.rulesCarrot = rules[1];

		System.out.println("La Laitue, quand un tour contenant la laitue est rat�, donne le choix au joueur de retourner au choix l'une de ses cartes");
		System.out.println("Il a donc le choix de cacher une de ses cartes si elle �tait face visible");
		System.out.println("Voulez vous utiliser cette r�gle ?");

		rules[2] = this.getBool();
		this.rulesLettuce = rules[2];

		return rules;
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

	private void exchangeWithAll(Player p) { // échange les cartes avec soit un autre joueur, soit avec les deux cartes du
												// milieu
		int propToChange, otherProp;
		this.board.getMiddleProps(0).print();
		this.board.getMiddleProps(1).print();
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p', this.keyboard);
		otherProp = p.speak("Avec quel autre carte sur la table voulez vous échanger cette carte ?", 6, players, 'v', keyboard);
		// 0,1 pour le joueur de gauche
		// 2,3 pour le joueur de droite
		// -1,-2 pour les props du milieu
		if (otherProp >= 0) {
			int p2 = (otherProp / 2 + 1 + p.getId()) % 3; // Le joueur avec lequel on veut échanger le prop
			this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		} else {
			this.board.exchangeProps(p.getId(), propToChange, -1, otherProp + 2);
			// On échange avec un prop du milieu, donc on passe otherProp dans les nombres
			// positifs
		}
	}

	private void exchangeMiddle2(Player p) { // échange les cartes avec les deux cartes du milieu
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p', this.keyboard);
		otherProp = p.speak("Avec quelle carte du milieu voulez vous l'échanger ?", 2, players, 'p', keyboard);
		this.board.exchangeProps(p.getId(), propToChange, -1, otherProp + 2);
	}

	private void exchangeMiddle(Player p) { // échange une carte avec celle du milieu
		int propToChange;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p', this.keyboard);
		this.board.exchangeProps(p.getId(), propToChange, -1, 0);
	}

	private void exchangePlayersMiddle(Player p) { // échange une carte avec un autre joueur ou la carte du milieu
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p', this.keyboard);
		otherProp = p.speak("Avec quelle carte du milieu ou de vos adversaire voulez vous l'échanger ?", 5, players, 'v', keyboard);
		// 0,1 pour le joueur de gauche
		// 2,3 pour le joueur de droite
		// -1 pour le prop du milieu
		if (otherProp >= 0) {
			int p2 = (otherProp / 2 + 1 + p.getId()) % 3; // Le joueur avec lequel on veut échanger le prop
			this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		} else {
			this.board.exchangeProps(p.getId(), propToChange, -1, otherProp + 1);
		}
	}

	private void exchangePlayers(Player p) {
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p', this.keyboard);
		otherProp = p.speak("Avec quelle carte de vos adversaires souhaitez vous l'échanger ?", 4, players, 'n', keyboard);
		int p2 = (otherProp / 2 + 1 + p.getId()) % 3;
		this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
	}
}
