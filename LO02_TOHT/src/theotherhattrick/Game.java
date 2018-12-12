package theotherhattrick;

import java.util.Scanner;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;

public class Game {
	protected int tour; // Le joueur qui doit jouer
	protected static Scanner keyboard = new Scanner(System.in);
	protected int var;
	
	protected Player[] players;
	protected ArrayList<Prop> middleProp = new ArrayList<Prop>();
	protected Stack<Trick> tricks = new Stack<Trick>();
	protected Stack<Trick> depiledTricks = new Stack<Trick>();
	protected Stack<Prop> allProps = new Stack<Prop>();

	public Game() {
	}

	public static Game createGame() {
		Game game = new Game();
		int variant = 0;
		// 0 pour le jeu de base
		// 1 pour la variante swissKnife
		// 2 pour la variante carrot
		// 3 pour la variante lettuce
		System.out.println("==[==========]==[==========]== THE OTHER HAT TRICK ==[==========]==[==========]==\n\n");
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
		this.createCards();
		this.distributeProps();
		this.depile();
		// Gestion du tour de jeu : faire jouer chaque joueur tour après tour
		// jusqu'à ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		while (this.tour < 100 && !this.isFinished()) {
			this.playTurn();
			this.nextTurn();
			if (this.depiledIsEmpty()) {
				this.depile();
			}
		}

		if(this.isFinished()) {
			int winner = this.getWinner();
			System.out.println("Le joueur " + players[winner].getName() + " a gagné avec un score de >" + players[winner].getScore() + "< points, BRAVO !");
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
			keyboard.nextLine(); // La prochaine saisie ne sera pas un int
		} while (nbHumains > 3);

		for (int i = 0; i < nbHumains; i++) {
			System.out.println("Entrer le nom du joueur numéro " + (i + 1));
			nom = keyboard.nextLine();
			date = this.askBirthDate();
			keyboard.nextLine(); // La prochaine saisie ne sera pas un int
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
	
	/**
	 * Cette méthode créé les cartes (Props et Tricks) en fonction de la version du jeu à laquelle on joue.
	 * Utilise la méthode shuffle(Stack<\E> stack) pour mélanger les piles de cartes. On distribue les props avec la méthode distributeProps(int rule).
	 * On verse le contenu du Stack de Trick dans un second stack dans le fond duquel on a mis "The Other Hat Trick".
	 * @param rule
	 */
	public void createCards() {
		for(int i = 0; i < PropEnum.values().length - (this.getVariant() == 1 ? 0 : 1); i++) {// Si on joue avec le couteau suisse, on veut 1 prop supplémentaire.
			allProps.push(new Prop(PropEnum.values()[i]));
			if(i == 0) { // Il y a 3 carrotes dans le jeu
				allProps.push(new Prop(PropEnum.values()[i]));
				allProps.push(new Prop(PropEnum.values()[i]));
			}
		}
		Collections.shuffle(allProps);
		
		Stack<Trick> temp = new Stack<Trick>();
		for(int i = 0; i < TrickEnum.values().length - 1; i++) {
			temp.push(new Trick(TrickEnum.values()[i]));
		}
		Collections.shuffle(temp);
		temp.push(new Trick(TrickEnum.values()[TrickEnum.values().length - 1]));
		for(int i = 0; i < TrickEnum.values().length; i++) {
			tricks.push(temp.pop());
		}
	}
	
	/**
	 * Cette méthode distribue les Props générés dans la méthode createCards.
	 * Créé un ArrayList de la taille nécessaire à la variante jouée (2 si on joue avec le couteau suisse, 1 sinon).
	 * Chaque joueur reçoit 2 cartes, le milieu reçoit 1 ou 2 prop(s).
	 * @param rule
	 */
	public void distributeProps() {
		middleProp = new ArrayList<Prop>(1);
		this.middleProp.add(allProps.pop());
		if(this.getVariant() == 1) { // Si on joue avec la variante du couteau suisse, on Veut une carte supplémentaire au milieu
			middleProp.ensureCapacity(2);
			this.middleProp.add(allProps.pop());
		}
		for(int i = 0; i < 2; i++) {
			for(Player player : players) {
				player.setHand(allProps.pop(), i);
			}
		}
	}

	protected void depileTrick(Player p) {
		boolean playerIn = p.speak("Retourner un trick ?", 2, players, 'b') == 1 ? true : false; // Conversion
		if (playerIn) {
			this.depile();
			System.out.println("Le nouveau Trick est : ");
			this.printTopTrick();
		}
	}

	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un
		// trick
		boolean trickSuccessful = depiledTricks.peek().compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a réussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p.getId()); // On montre ses cartes

			// TODO Ajouter un délai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p.getId()); // On cache ses cartes
			System.out.println("Vous pouvez échanger l'une de vos cartes avec la carte du milieu");

			exchangeMiddle(p);

			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p.getId()); // On lui donne le trick

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			this.revealProp(p.getId());
		}

	}
	/**
	 * reverse le trick du dessus de la pile de tricks cachés sur le dessus de la pile de tricks dévouverts. 
	 */
	public void depile() {
		Trick temp = this.tricks.pop();
		this.depiledTricks.push(temp);
	}
	public boolean depiledIsEmpty() {
		return depiledTricks.empty();
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
		playerIn = p.speak("Voulez-vous réaliser le trick ?", 2, players, 'b') == 1 ? true : false; // Conversion
																							// d'int en
																							// booleen
		if (playerIn) { // Si le joueur souhaite performer le trick
			realizeTrick(p);
		} else { // Le joueur ne souhaite pas faire le trick
			this.revealProp(p.getId());
		}
	}

	public static int askRules() {
		int choice = 0;

		System.out.println("Voici les différentes règles du jeu : \n");

		System.out.println("***** Le Couteau Suisse *****\nAjout d'une nouvelle carte capable d'être utilisée pour réaliser n'importe quel trick");
		System.out.println("Attention : l'utilisation du couteau suisse vous fera gagner >1< points de moins quand vous remportez un trick");

		System.out.println("\n***** Les Carottes Magiques *****\nPermet d'échanger un prop avec un autre joueur quand un tour est réussi en utilisant une carotte");

		System.out.println("\n***** La Laitue Magique *****\nRater un tour où figure une laitue offre la possibilité de retourner un de vos props");
		System.out.println("Il a donc le choix de cacher une de ses cartes si elle était face visible");

		do {
			System.out.println("\nVeuillez choisir la règle que vous voulez utiliser :");
			System.out.println("Entrez 0 pour jouer sans variantes, 1 pour jouer avec le Couteau Suisse, 2 pour jouer avec la Carotte, 3 pour jouer avec la Laitue");
			choice = keyboard.nextInt();
		} while (choice < 0 || choice > 3);

		return choice;
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
	 * 	Donne le trick du dessus de depiledTricks au joueur d'id id
	 */
	public void giveTrick(int id) {
		if(players[id].getHand().contains(new Prop(PropEnum.SWISS_ARMY_KNIFE))) {
			System.out.println("! ! Vous avez réalisé le tour <" + depiledTricks.peek().getName() +"> avec le couteau suisse magique ! !\n");
			depiledTricks.peek().decreaseValue();
		}
		players[id].increaseScore(depiledTricks.peek().getPoints());
		players[id].pushTrick(depiledTricks.pop());
	}
	

	protected void exchangeMiddle(Player p) { // échange une carte avec celle du milieu
		int propToChange;
		propToChange = p.speak("Quel prop voulez-vous replacer au milieu ?", 3, this.players, 'p');
		if(propToChange != 2) { // dans speak, on renvoie 2 si on veut garder nos 2 props.
			this.exchangeProps(p.getId(), propToChange, -1, 0);
		}
	}
	
	/*	
	 *	Echange la position de 2 props 
	 * 	la version oÃ¹ l'on utilise createCopy() provoque plusieurs erreurs de type java.lang.CloneNotSupportedException
	 * 	La version oÃ¹ l'on utilise directement les accesseurs est fonctionnelle.
	 */
	public void exchangeProps(int p1, int ind1, int p2, int ind2) { //Echange le prop d'indice ind1 du joueur d'id p1, avec le prop d'indice ind2 du joueur p2 
		Prop tmp1 = players[p1].getHand(ind1);
		players[p1].getHand().remove(ind1);
//		int i = (variant ==  1 ? 0 : players[p1].speak("yeee", 2, players, 'p') ); // à utiliser si on n'a pas décidé quel prop au milieu on veut récupérer.
		
		if(p2 == -1) {  // Si on veut Ã©changer le prop du joueur dont c'est le tour avec celui du milieu 
			players[p1].setHand(this.middleProp.get(ind2) ,ind1);
			middleProp.remove(tmp1);
			middleProp.set(ind2, tmp1);
		}
		else if (p2 == (p1+1)%3 || p2 == (p1+2)%3) { // Si on veut Ã©changer le prop du joueur dont c'est le tour avec celui d'un autre joueur
			players[p1].setHand(players[p2].getHand(ind2), ind1);
			players[p2].getHand().remove(ind2);
			players[p2].setHand(tmp1, ind2);
		}
		else {
			System.out.println(p2);
			System.out.println("ERROR : undefined p2 value");
		}
	}
	
	public void returnProp(int id) {
		System.out.println("Main de " + players[id].getName() + " => ");
		System.out.println(players[id].getHand());
		int ind = players[id].speak("Quel prop voulez vous retourner ? \n", players[0].getHand().size(), players, 'p');
		if(players[id].getHand(ind).getState()) {
			players[id].getHand(ind).hide();
		}
		else {
			players[id].getHand(ind).unhide();
		}
		System.out.println("Nouvelle main => " );
		System.out.println(players[id].getHand());
	}

	protected void exchangePlayers(Player p) {
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p');
		otherProp = p.speak("Avec quelle carte de vos adversaires souhaitez vous l'échanger ?", 4, players, 'n');
		int p2 = (otherProp / 2 + 1 + p.getId()) % 3;
		this.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
	}

	protected boolean isFinished() {
		return this.getTrickPileLength() <= 0;
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
				
			}catch(Exception e) {
				keyboard.nextLine(); 
				return askBirthDate();
			};

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
	
	public void revealProp(int id) {
		int choice = 0, i, hNum = 0;
		
		System.out.println("Votre main, " + players[id].getName() + " : \n"); // On affiche la main du joueur et on regarde quels props sont cachÃ©s
		System.out.println(players[id].getHand());
		for(i = 0; i < 2; i++) {
//			players[id].getHand(i).printDebug();
			if(players[id].getHand(i).getState() == false) {
				hNum ++;
				if(i == 0) {
					choice = 0;
				}
				else if (i == 1) {
					choice = 1;
				}
				else {
					System.out.println("ERROR : choice value undefined.");
					choice = 2;
				}
			}
		}
		if(hNum == 2) {
			choice = players[id].speak("\nQuel prop voulez-vous révéler ?", 2, players, 'p');
			players[id].getHand(choice).unhide();
		}
		else if(hNum == 1) {
			System.out.println("Vous n'avez que le prop " + players[id].getHand(choice).getName() + " qui soit caché. " + players[id].getHand(choice).getName() +" est maintenant visible.");
			players[id].getHand(choice).unhide();
		}
		else {
			System.out.println("Tous vos props sont déjà  visibles. Aucune action n'est effectuée."); 
		}
	}
	
	public void printOthersHand(int id) {
		System.out.println("Voici les cartes visibles sur le terrain :");
		for(Player p : players) {
			if(p.getId() != id) {
				System.out.println(p.getName());
				p.printVisible();
			}
		}
	}
	
	public void printTopTrick() {
		this.depiledTricks.peek().print();
	}
	
	public void showAllProps(int id) { // Montre tous les props du joueur afin de montrer qu'il peut bien réaliser le tour
		players[id].getHand(0).unhide();
		players[id].getHand(1).unhide();
	}

	public void hideAllProps(int id) { // Retourne face cachée tous les props du joueur en cas de tour réussi
		players[id].getHand(0).hide();
		players[id].getHand(1).hide();
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