 package theotherhattrick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author Antoine Mallet, Nathan Cantat
 *
 */
public class Game extends Observable implements Serializable {
	
	protected transient int tour; // Le joueur qui doit jouer
	protected static Scanner keyboard = new Scanner(System.in);
	
	public static String NAME = "THE OTHER HAT TRICK";
	public static String RULE_SWISS_ARMY_KNIFE = 
			"***** Le Couteau Suisse *****\n"
			+ "Ajout d'une nouvelle carte capable d'être utilisée pour réaliser n'importe quel trick\n"
			+ "Attention : l'utilisation du couteau suisse vous fera gagner >1< points de moins quand vous remportez un trick.\n";
	public static String RULE_LETTUCE = 
			"***** Les Carottes Magiques *****\n"
			+ "Permet d'échanger un prop avec un autre joueur quand un tour est réussi en utilisant une carotte.\n";
	public static String RULE_CARROT = 
			"***** La Laitue Magique *****\n"
			+ "Rater un tour où figure une laitue offre la possibilité de retourner un de vos props\n"
			+ "Il a donc le choix de cacher une de ses cartes si elle était face visible.\n";
			
	protected int variant;
	protected int nbOfHuman;

	public int getNbOfHuman() {
		return nbOfHuman;
	}

	public void setNbOfHuman(int nbOfHuman) {
		this.nbOfHuman = nbOfHuman;
	}

	public void setVariant(int variant) {
		this.variant = variant;
//		this.createGame(variant);
	}

	protected static Player[] players;
	protected Player newPlayer;
	

	protected static ArrayList<Prop> middleProp = new ArrayList<Prop>();
	protected Stack<Trick> tricks = new Stack<Trick>();
// 	protected static Stack<Trick> depiledTricks = new Stack<Trick>();
	protected static Trick depiledTrick;
	protected Stack<Prop> allProps = new Stack<Prop>();

	public Game() {
	}

	public static Game createGame(int variant) {
		Game game = new Game();
//		Game.keyboard = new Scanner(System.in);
		// 0 pour le jeu de base
		// 1 pour la variante swissKnife
		// 2 pour la variante carrot
		// 3 pour la variante lettuce
//		variant = askRules();

		// On utilise le polymorphisme pour gérer les variantes
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

	public void start() {

		// Instanciation de tous les joueurs humains ou robots
		this.createPlayers();
		this.createCards();
		this.distributeProps();
		this.depile();
		// Gestion du tour de jeu : faire jouer chaque joueur tour après tour
		// jusqu'à ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;
		boolean gameisFinished = false;
		while (this.tour < 10000 && !gameisFinished) {
			gameisFinished = this.isFinished();
			this.playTurn();
			System.out.println("La taille de la pile de tricks est " + this.tricks.size());

			this.nextTurn();
			if (this.depiledIsEmpty() || depiledTrick.isTooOld()) {
				System.out.println("Plus de tricks à réaliser, on en tire un nouveau ");
				this.depile();
				if (depiledIsEmpty()) { // Le joueur a réussi TheOtherHatTrick du premier coup
					gameisFinished = true;
					System.out.println("Le joueur a réussi The Other Hat Trick, la partie est terminée");
				} else {
					System.out.println("Le nouveau trick est ");
					depiledTrick.print();
				}
				// System.out.println("La taille de la pile de tricks est maintenant " +
				// this.tricks.size());
			}

			if (this.tricks.size() == 0 || depiledTrick.name.equals("The Other Hat Trick")) {
				this.playLastTurn();
				gameisFinished = true;
			}
		}

		if (gameisFinished) {
			int winner = this.getWinner();
			System.out.println("Félicitations aux joueurs qui ont participé :");
			for (int i = 0; i < players.length; i++) {
				System.out.println("Le joueur " + players[i].getName() + " avec un score de " + players[i].getScore() + " points");
			}
			System.out.println("Le joueur " + players[winner].getName() + " a gagné avec un score de >" + players[winner].getScore() + "< points, BRAVO !");
		}

		keyboard.close();
	}

	private void createPlayers() {
		String nom;
		Date date;
		players = new Player[3];
		this.setChanged();
		this.notifyObservers("nbOfPlayers");
		
		for (int i = 0; i < nbOfHuman; i++) {
			this.setChanged();
			this.notifyObservers("identityOfPLayer");
			this.players[i] = this.newPlayer;
		}

		for (int i = nbOfHuman; i < 3; i++) {
			date = new Date();
			players[i] = new Robot(date);
		}

		this.sortPlayers();

		for (int i = 0; i < players.length; i++) {
			players[i].setId(i);
		}
	}

	/**
	 * Cette méthode créé les cartes (Props et Tricks) en fonction de la version du
	 * jeu à laquelle on joue. Utilise la méthode shuffle(Stack<\E> stack) pour
	 * mélanger les piles de cartes. On distribue les props avec la méthode
	 * distributeProps(int rule). On verse le contenu du Stack de Trick dans un
	 * second stack dans le fond duquel on a mis "The Other Hat Trick".
	 * 
	 * @param rule
	 */
	public void createCards() {
		for (int i = 0; i < PropEnum.values().length - (this.getVariant() == 1 ? 0 : 1); i++) {// Si on joue avec le couteau suisse, on veut 1 prop supplémentaire.
			allProps.push(new Prop(PropEnum.values()[i]));
			if (i == 0) { // Il y a 3 carrots dans le jeu
				allProps.push(new Prop(PropEnum.values()[i]));
				allProps.push(new Prop(PropEnum.values()[i]));
			}
		}
		Collections.shuffle(allProps);

		Stack<Trick> temp = new Stack<Trick>();
		for (int i = 0; i < TrickEnum.values().length - 1; i++) {
			temp.push(new Trick(TrickEnum.values()[i]));
		}
		Collections.shuffle(temp);
		temp.push(new Trick(TrickEnum.values()[TrickEnum.values().length - 1]));
		for (int i = 0; i < TrickEnum.values().length; i++) {
			tricks.push(temp.pop());
		}
	}

	/**
	 * Cette méthode distribue les Props générés dans la méthode createCards. Créé
	 * un ArrayList de la taille nécessaire à la variante jouée (2 si on joue avec
	 * le couteau suisse, 1 sinon). Chaque joueur reçoit 2 cartes, le milieu reçoit
	 * 1 ou 2 prop(s).
	 * 
	 * @param rule
	 */
	public void distributeProps() {
		middleProp = new ArrayList<Prop>(1);
		middleProp.add(allProps.pop());
		if (this.getVariant() == 1) { // Si on joue avec la variante du couteau suisse, on Veut une carte
										// supplémentaire au milieu
			middleProp.ensureCapacity(2);
			middleProp.add(allProps.pop());
		}
		for (int i = 0; i < 2; i++) {
			for (Player player : players) {
				player.setHand(allProps.pop(), i);
			}
		}
	}

	protected void depileTrick(Player p) {
		boolean playerIn = p.revealNewTrick(); // Conversion
		if (playerIn) {
			this.depile();
			System.out.println("Le nouveau Trick est : ");
			this.printTopTrick();
		}
	}

	protected void playLastTurn() {
		System.out.println("On joue le dernier tour");
		depiledTrick.print();
		boolean finished = false;
		for (int i = 0; i < players.length; i++) {
			ArrayList<Prop> hand = players[i].getHand();
			System.out.println(hand.size());
			if (!depiledIsEmpty() && depiledTrick.compareToProps(hand)) {
				System.out.println("Le joueur " + players[i].getName() + " a réussi The Other Hat Trick, il gagne 6 points");
				giveTrick(players[i]);
				finished = false;
			} else if (hand.contains(new Prop(PropEnum.THE_OTHER_RABBIT)) && !finished) {
				System.out.println("Le joueur " + players[i].getName() + "  possède The Other Rabbit, il perd 3 points");
				players[i].score -= 3;
			} else if (hand.contains(new Prop(PropEnum.HAT)) && !finished) {
				System.out.println("Le joueur " + players[i].getName() + "  possède The Hat, il perd 3 points");
				players[i].score -= 3;
			}
		}
	}

	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un
		// trick
		boolean trickSuccessful = depiledTrick.compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a réussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p); // On montre ses cartes

			// TODO Ajouter un délai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p); // On cache ses cartes
			System.out.println("Vous pouvez échanger l'une de vos cartes avec la carte du milieu");

			exchangeMiddle(p);

			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p); // On lui donne le trick

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			this.revealProp(p);
		}

	}

	/**
	 * reverse le trick du dessus de la pile de tricks cachés sur le dessus de la
	 * pile de tricks dévouverts.
	 */
	public void depile() {
		try {
			Trick temp = this.tricks.pop();
			depiledTrick = temp;
		} catch (Exception e) {
			System.out.println("Impossible de retirer un trick, puisque la pile est vide");
		}
	}

	public boolean depiledIsEmpty() {
		return depiledTrick == null ? true : false;
	}

	public static Trick getTopTrick() {
		return depiledTrick;
	}

	public static ArrayList<Prop> getMiddleProp() {
		return middleProp;
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
	
	public void setNewPlayer(Player newPlayer) {
		this.newPlayer = newPlayer;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}
	
	private void playTurn() {
		System.out.println("\n]=====||=====||=====||=====[TOUR N°" + tour + "]=====||=====||=====||=====[\n");
		Player p = players[tour % 3];
		boolean playerIn;
		// if (p instanceof Human || p instanceof Robot) {
		System.out.println(p.getName() + " joue");
		System.out.println("\nVotre jeu est :");
		p.printProps();
		this.printOthersHand(p.getId());
		System.out.println("\nLe trick à réaliser est : ");
		this.printTopTrick(); // On affiche le trick sur le dessus de la pile pour que le joueur puisse faire
								// son choix
		// Demande à l'utilisateur si il souhaite dépiler un trick
		depileTrick(p);

		// DEMANDE LES CARTES A ECHANGER ET FAIT L'ECHANGE

		exchangePlayers(p);
		System.out.println("\nVotre nouvelle main :");
		p.printProps();

		// PERFORMER LE TRICK
		playerIn = p.performTrick();
		if (playerIn) { // Si le joueur souhaite performer le trick
			realizeTrick(p);
		} else { // Le joueur ne souhaite pas faire le trick
			this.revealProp(p);
		}
	}

	public static int askRules() {
		int choice = 0;

		System.out.println("Voici les différentes règles du jeu : \n");
		
		System.out.println(Game.RULE_SWISS_ARMY_KNIFE);
//		System.out.println("***** Le Couteau Suisse *****\nAjout d'une nouvelle carte capable d'être utilisée pour réaliser n'importe quel trick");
//		System.out.println("Attention : l'utilisation du couteau suisse vous fera gagner >1< points de moins quand vous remportez un trick");
		
		System.out.println(Game.RULE_CARROT);
//		System.out.println("\n***** Les Carottes Magiques *****\nPermet d'échanger un prop avec un autre joueur quand un tour est réussi en utilisant une carotte");
		
		System.out.println(Game.RULE_LETTUCE);
//		System.out.println("\n***** La Laitue Magique *****\nRater un tour où figure une laitue offre la possibilité de retourner un de vos props");
//		System.out.println("Il a donc le choix de cacher une de ses cartes si elle était face visible");

		do {
			System.out.println("\nVeuillez choisir la règle que vous voulez utiliser :");
			System.out.println("Entrez 0 pour jouer sans variantes, 1 pour jouer avec le Couteau Suisse, 2 pour jouer avec la Carotte, 3 pour jouer avec la Laitue");
			choice = keyboard.nextInt();
		} while (choice < 0 || choice > 3);

		return choice;
	}

	public static int getBestPlayer() {
		boolean exAequo = false;
		int bestPlayer = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[bestPlayer].score < players[i].score) {
				bestPlayer = i;
			} else if (bestPlayer != i && players[bestPlayer].score == players[i].score) {
				exAequo = true;
			}
		}
		if (exAequo) {
			return -1;
		}
		return bestPlayer;
	}

	public static int getWorstPlayer() {
		boolean exAequo = false;
		int worstPlayer = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[worstPlayer].score > players[i].score) {
				worstPlayer = i;
			} else if (worstPlayer != i && players[worstPlayer].score == players[i].score) {
				exAequo = true;
			}
		}
		if (exAequo) {
			return -1;
		}
		return worstPlayer;
	}

	public boolean getBool() { // Récupère un booléen du joueur qui créé la
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
		if (!depiledIsEmpty()) {
			depiledTrick.age();
			/*
			 * System.out.println("Le trick " +depiledTricks.peek().name + " a un age de " +
			 * depiledTricks.peek().lifeLength); if (depiledTricks.peek().isTooOld()) {
			 * System.out.
			 * println("Personne n'a réussi à réaliser le trick, on en retourne un nouveau"
			 * ); this.depile(); System.out.println("Le nouveau trick est " +
			 * depiledTricks.peek().name); }
			 */
		}
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

	public int getTrickPileLength() {
		return tricks.size();
	}

	/*
	 * Donne le trick du dessus de depiledTricks au joueur d'id id
	 */
	public void giveTrick(Player p) {
		if (p.getHand().contains(new Prop(PropEnum.SWISS_ARMY_KNIFE))) {
			System.out.println("! ! Vous avez réalisé le tour <" + depiledTrick.getName() + "> avec le couteau suisse magique ! !\n");
			depiledTrick.decreaseValue();
		}
		p.increaseScore(depiledTrick.getPoints());
		p.pushTrick(depiledTrick);
		depiledTrick = null;
	}

	protected void exchangeMiddle(Player p) { // échange une carte avec celle du milieu
		int propToChange;
		propToChange = p.chooseMiddle();
		if (propToChange != 2) { // dans speak, on renvoie 2 si on veut garder nos 2 props.
			this.exchangeProps(p.getId(), propToChange, -1, 0);
		}
	}

	/*
	 * Echange la position de 2 props la version où l'on utilise createCopy()
	 * provoque plusieurs erreurs de type java.lang.CloneNotSupportedException La
	 * version où l'on utilise directement les accesseurs est fonctionnelle.
	 */
	public void exchangeProps(int p1, int ind1, int p2, int ind2) { // Echange le prop d'indice ind1 du joueur d'id p1, avec le prop d'indice ind2
																	// du joueur p2
		Prop tmp1 = players[p1].getHand(ind1);
		players[p1].getHand().remove(ind1);
//		int i = (variant ==  1 ? 0 : players[p1].speak("yeee", 2, players, 'p') ); // à utiliser si on n'a pas décidé quel prop au milieu on veut récupérer.

		if (p2 == -1) { // Si on veut échanger le prop du joueur dont c'est le tour avec celui du milieu
			players[p1].setHand(middleProp.get(ind2), ind1);
			middleProp.remove(tmp1);
			middleProp.set(ind2, tmp1);
		} else if (p2 == (p1 + 1) % 3 || p2 == (p1 + 2) % 3) { // Si on veut Ã©changer le prop du joueur dont c'est le tour avec celui d'un
																// autre joueur
			players[p1].setHand(players[p2].getHand(ind2), ind1);
			players[p2].getHand().remove(ind2);
			players[p2].setHand(tmp1, ind2);
		} else {
			System.out.println(p2);
			System.out.println("ERROR : undefined p2 value");
		}
	}

	public void returnProp(Player p) {
		System.out.println("Main de " + p.getName() + " => ");
		System.out.println(p.getHand());
		int ind = p.revealProp();
		if (p.getHand(ind).getState()) {
			p.getHand(ind).hide();
		} else {
			p.getHand(ind).unhide();
		}
		System.out.println("Nouvelle main => ");
		System.out.println(p.getHand());
	}

	protected void exchangePlayers(Player p) {
		int propToChange, otherProp;
		propToChange = p.chooseOwnProp();
		System.out.println("\n OwnProp has been asked");
		otherProp = p.chooseOtherProp(players);
		int p2 = (otherProp / 2 + 1 + p.getId()) % 3;
		this.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
	}

	protected boolean isFinished() {
		if (this.getTrickPileLength() <= 0 && depiledTrick.isTooOld()) {
			return true;
		}
		return false;
	}

	public Date askBirthDate() {
		int day = 0, month = 0, year = 0;
		do {
			try {
				System.out.println("Entrer votre jour de naissance");
				day = keyboard.nextInt();
				System.out.println("Entrer votre mois de naissance");
				month = keyboard.nextInt();
				System.out.println("Entrer votre année de naissance");
				year = keyboard.nextInt();

			} catch (Exception e) {
				keyboard.nextLine();
				return askBirthDate();
			}
			;

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

	public void revealProp(Player p) {
		int choice = 0, i, hNum = 0;

		System.out.println("Votre main, " + p.getName() + " : \n"); // On affiche la main du joueur et on regarde quels props sont cachÃ©s
		System.out.println(p.getHand());
		for (i = 0; i < 2; i++) {
//			players[id].getHand(i).printDebug();
			if (p.getHand(i).getState() == false) {
				hNum++;
				if (i == 0) {
					choice = 0;
				} else if (i == 1) {
					choice = 1;
				} else {
					System.out.println("ERROR : choice value undefined.");
					choice = 2;
				}
			}
		}
		if (hNum == 2) {
			choice = p.revealProp();
			p.getHand(choice).unhide();
		} else if (hNum == 1) {
			System.out.println("Vous n'avez que le prop " + p.getHand(choice).getName() + " qui soit caché. " + p.getHand(choice).getName() + " est maintenant visible.");
			p.getHand(choice).unhide();
		} else {
			System.out.println("Tous vos props sont déjà  visibles. Aucune action n'est effectuée.");
		}
	}

	public void printOthersHand(int id) {
		System.out.println("Voici les cartes visibles sur le terrain :");
		for (Player p : players) {
			if (p.getId() != id) {
				System.out.println(p.getName());
				p.printVisible();
			}
		}
	}

	public void printTopTrick() {
		depiledTrick.print();
	}

	public void showAllProps(Player p) { // Montre tous les props du joueur afin de montrer qu'il peut bien réaliser le
											// tour
		p.getHand(0).unhide();
		p.getHand(1).unhide();
	}

	public void hideAllProps(Player p) { // Retourne face cachée tous les props du joueur en cas de tour réussi
		p.getHand(0).hide();
		p.getHand(1).hide();
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