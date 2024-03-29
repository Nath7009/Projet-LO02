package theotherhattrick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import theotherhattrickControler.GameParameters;

/**
 * 
 * @author Antoine Mallet, Nathan Cantat
 *
 */

public class Game extends Observable implements Serializable, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6832247783280823778L;
	protected transient int tour; // Le joueur qui doit jouer
	protected static Scanner keyboard = new Scanner(System.in);

	public static String NAME = "THE OTHER HAT TRICK";
	public static String RULE_SWISS_ARMY_KNIFE = "***** Le Couteau Suisse *****\n" + "Ajout d'une nouvelle carte capable d'être utilisée pour réaliser n'importe quel trick\n"
			+ "Attention : l'utilisation du couteau suisse vous fera gagner >1< points de moins quand vous remportez un trick.\n";
	public static String RULE_LETTUCE = "***** Les Carottes Magiques *****\n" + "Permet d'échanger un prop avec un autre joueur quand un tour est réussi en utilisant une carotte.\n";
	public static String RULE_CARROT = "***** La Laitue Magique *****\n" + "Rater un tour où figure une laitue offre la possibilité de retourner un de vos props\n" + "Il a donc le choix de cacher une de ses cartes si elle était face visible.\n";

	protected int variant;
	protected int nbOfHuman;

	protected static Player[] players;
	protected Player newPlayer;

	protected static ArrayList<Prop> middleProp = new ArrayList<Prop>();
	protected Stack<Trick> tricks = new Stack<Trick>();
	protected Stack<Trick> temp = new Stack<Trick>();
	protected static Trick depiledTrick;
	protected Stack<Prop> allProps = new Stack<Prop>();
	protected Thread t;

	public Game() {
		t = new Thread(this);
	}

	public static Game createGame(int variant) {
		Game game = new Game();

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

	public static Game createGame(GameParameters param) {
		players = new Player[3];
		// On crée les joueurs puis on les trie suivant leur date de naissance
		for (int i = 0; i < param.players.length; i++) {
			players[i] = param.players[i];
			players[i].setId(i);
		}

		sortPlayers();
		return createGame(param.variant);
	}

	// Crée les éléments du jeu, utilisé uniquement pour l'interface en ligne de
	// commande
	public void initGame() {
		// Instanciation de tous les joueurs humains ou robots
		this.createPlayers();
	}

	public void run() {
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
			}

			if (this.tricks.size() == 0 || depiledTrick.name.equals("The Other Hat Trick")) {
				this.playLastTurn();
				gameisFinished = true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
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

	public void start() {
		this.createCards();
		this.distributeProps();
		this.depile();
		// Gestion du tour de jeu : faire jouer chaque joueur tour après tour
		// jusqu'à ce
		// que la pile de tricks soit vide
		// Quand la pile de tricks est vide, on cherche le joueur gagnant et on
		// l'affiche
		this.tour = 0;

		t.start();

	}

	public int getNbOfHuman() {
		return nbOfHuman;
	}

	public void setNbOfHuman(int nbOfHuman) {
		this.nbOfHuman = nbOfHuman;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	private void createPlayers() {
		Date date;
		players = new Player[3];
		this.setChanged();
		this.notifyObservers("nbOfPlayers");

		for (int i = 0; i < nbOfHuman; i++) {
			this.setChanged();
			this.notifyObservers("identityOfPLayer");
			players[i] = this.newPlayer;
		}

		for (int i = nbOfHuman; i < 3; i++) {
			date = new Date();
			this.newPlayer = new Robot(date);
			players[i] = this.newPlayer;
		}

		sortPlayers();
		this.setChanged();
		this.notifyObservers("new players");

		for (int i = 0; i < players.length; i++) {
			players[i].setId(i);
			System.out.println("Nombre d'observer du joueur : " + players[i].countObservers());
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
		for (int i = 0; i < PropEnum.values().length - (this.getVariant() == 1 ? 0 : 1); i++) {
			allProps.push(new Prop(PropEnum.values()[i]));
			if (i == 0) { // Il y a 3 carrots dans le jeu
				allProps.push(new Prop(PropEnum.values()[i]));
				this.setChanged();
				this.notifyObservers("new prop");
				allProps.push(new Prop(PropEnum.values()[i]));
				this.setChanged();
				this.notifyObservers("new prop");
			}
			this.setChanged();
			this.notifyObservers("new prop");
		}
		Collections.shuffle(allProps);

		for (int i = 0; i < TrickEnum.values().length - 1; i++) {
			temp.push(new Trick(TrickEnum.values()[i]));
		}
		Collections.shuffle(temp);
		temp.push(new Trick(TrickEnum.values()[TrickEnum.values().length - 1]));

		for (int i = 0; i < TrickEnum.values().length; i++) {
			tricks.push(temp.pop());
			this.setChanged();
			this.notifyObservers("new trick");
		}

	}

	public Stack<Trick> getTricks() {
		return tricks;
	}

	public void setTricks(Stack<Trick> tricks) {
		this.tricks = tricks;
	}

	public Stack<Prop> getAllProps() {
		return allProps;
	}

	public void setAllProps(Stack<Prop> allProps) {
		this.allProps = allProps;
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
		if (this.getVariant() == 1) { 
			middleProp.ensureCapacity(2);
			middleProp.add(allProps.pop());
		}
		for (int i = 0; i < 2; i++) {
			for (Player player : players) {
				player.setHand(allProps.pop(), i);
			}
		}
	}

	/**
	 * Appelle la méthode pour demander au joueur s'il veut retourner un trick.
	 * @param p le joueur à qui l'on veut poser la question
	 */
	protected void depileTrick(Player p) {
		boolean playerIn = p.revealNewTrick();
		if (playerIn) {
			this.depile();
		}
	}

	protected void playLastTurn() {

		setChanged();
		this.notifyObservers("last turn");

		boolean finished = false;

		for (int i = 0; i < players.length; i++) {
			ArrayList<Prop> hand = players[i].getHand();
			if (!depiledIsEmpty() && depiledTrick.compareToProps(hand)) {
				players[i].forceChanged();
				players[i].notifyObservers("other hat trick realized");
				giveTrick(players[i]);
				finished = false;
			} else if (hand.contains(new Prop(PropEnum.THE_OTHER_RABBIT)) && !finished) {
				players[i].forceChanged();
				players[i].notifyObservers("possess other rabbit");
				players[i].score -= 3;
			} else if (hand.contains(new Prop(PropEnum.HAT)) && !finished) {
				players[i].forceChanged();
				players[i].notifyObservers("possess hat");
				players[i].score -= 3;
			}
		}
	}

	/**
	 * On regarde si le joueur p a réussi le tour. 
	 * S'il a réussi, on lui demande quel prop il veut replacer au milieu.
	 * S'il a raté, on lui demande lequel de ses props il veut retourner.
	 * @param p
	 */
	protected void realizeTrick(Player p) {
		boolean trickSuccessful = depiledTrick.compareToProps(p.getHand());

		if (trickSuccessful) {
			this.setChanged();
			this.notifyObservers("");
			p.showAllProps();
			p.hideAllProps();
			exchangeMiddle(p);
			this.giveTrick(p);
			this.setChanged();
			this.notifyObservers("trick given");
		}
		else {
			this.setChanged();
			this.notifyObservers("trick failed");
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
			this.setChanged();
			this.notifyObservers("depile");
		} catch (Exception e) {
			this.setChanged();
			this.notifyObservers("trick pile empty");
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

	private void playTurn() {
		setChanged();
		notifyObservers("Player" + tour % 3);
		Player p = players[tour % 3];
		if (p instanceof Human) {
			setChanged();
			notifyObservers("new turn");
			return;
		}
		boolean playerIn;
		this.printTopTrick(); // On affiche le trick sur le dessus de la pile pour que le joueur puisse faire
								// son choix
		// Demande à l'utilisateur si il souhaite dépiler un trick
		depileTrick(p);

		// DEMANDE LES CARTES A ECHANGER ET FAIT L'ECHANGE

		exchangePlayers(p);

		p.printProps();

		// PERFORMER LE TRICK
		playerIn = p.performTrick();
		if (playerIn) { // Si le joueur souhaite performer le trick
			realizeTrick(p);
		} else { // Le joueur ne souhaite pas faire le trick
			this.revealProp(p);
		}
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

	public Player[] getPlayers() {
		return players;
	}

	public Player getCurrentPlayer() {
		return players[tour % 3];
	}

	public int getCurrentPlayerIndex() {
		return tour % 3;
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

	public Trick getDepiledTrick() {
		return depiledTrick;
	}

	private void nextTurn() {
		this.tour++;
		if (!depiledIsEmpty()) {
			depiledTrick.age();
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

	/**
	 * Donne le trick du dessus de depiledTricks au joueur d'id id
	 * @param p
	 */
	public void giveTrick(Player p) {

		if (p.getHand().contains(new Prop(PropEnum.SWISS_ARMY_KNIFE))) {
			depiledTrick.decreaseValue();
		}

		p.increaseScore(depiledTrick.getPoints());
		p.pushTrick(depiledTrick);
		depiledTrick = null;
	}

	/**
	 * Appelle la méthode pour demander au joueur quelle carte il veut reposer au milieu.
	 * @param p
	 */
	protected void exchangeMiddle(Player p) { 
		int propToChange;
		propToChange = p.chooseMiddle();
		if (propToChange != 2) {
			this.exchangeProps(p.getId(), propToChange, -1, 0);
		}
	}
	
	public void showMiddleProps() {
		for(Iterator<Prop> it = middleProp.iterator();it.hasNext(); ) {
			it.next().unhide();
		}
		System.out.println(middleProp.get(0).toString());
	}
	
	public void hideMiddleProps() {
		for(Iterator<Prop> it = middleProp.iterator();it.hasNext(); ) {
			it.next().hide();
		}
	}

	/**
	 * Réalise un échange de props. En fonction de la valeur de p2, l'échange se fait soit entre deux joueurs, soit entre un joueur et la carte du milieu.
	 * @param p1 indice du joueur qui veut échanger un de ses Props
	 * @param ind1 indice de la carte du joueur p1
	 * @param p2 si p2 vaut -1, l'échange se fait avec la carte du milieu, si elle vaut 0 ou 1, elle se fait avec un autre joueur. 
	 * @param ind2
	 */
	public void exchangeProps(int p1, int ind1, int p2, int ind2) { // Echange le prop d'indice ind1 du joueur d'id p1, avec le prop d'indice ind2
																	// du joueur p2
		Prop tmp1 = players[p1].getHand(ind1);
		players[p1].getHand().remove(ind1);

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
		}
	}
	
	/**
	 * Change l'état de visibilité d'une carte.
	 * @param p
	 */
	public void returnProp(Player p) {
		System.out.println("Main de " + p.getName() + " => ");
		System.out.println(p.getHand());
		int ind = p.revealProp();
		if (p.getHand(ind).getState()) {
			p.getHand(ind).hide();
		} else {
			p.getHand(ind).unhide();
		}
		p.printProps();
	}

	/**
	 * Appelle les méthodes pour demander au joueur p quels sont les props qu'il veut échanger.
	 * Puis appelle la méthode qui réalise l'échange de Props.
	 * @param p
	 */
	protected void exchangePlayers(Player p) {
		int propToChange, otherProp;
		propToChange = p.chooseOwnProp();
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

	protected int getWinner() {
		int bestPlayer = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].getScore() > players[bestPlayer].getScore()) {
				bestPlayer = i;
			}
		}
		return bestPlayer;
	}
	/**
	 * Si le joueur a raté un tour de magie, il doit retourner une de ses cartes.
	 * On compte d'abord le nombre de cartes qui sont déjà visibles.
	 * S'il a le choix (si les deux sont cachées), on lui demande laquelle il veut retourner.
	 * Sinon, l'action est automatique
	 * @param p
	 */
	public void revealProp(Player p) {
		int choice = 0, i, hNum = 0;

		p.forceChanged();
		p.notifyObservers("reveal prop");

		for (i = 0; i < 2; i++) {
			if (p.getHand(i).getState() == false) {
				hNum++;
				if (i == 0) {
					choice = 0;
				} else if (i == 1) {
					choice = 1;
				} else {
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
		setChanged();
		notifyObservers("Un prop a été retourné");
	}

	/**
	 * Affiche les cartes des autres joueurs en fonction de leur visibilité
	 * @param id le joueur d'indice id qui veut voir les cartes des autres joueurs.
	 */
	public void printOthersHand(int id) {
		for (Player p : players) {
			if (p.getId() != id) {
				p.printVisible();
			}
		}
	}

	public void printTopTrick() {
		depiledTrick.print();
	}

	/**
	 * Si le joueur réussi le tour de magie, il révèle toutes ses cartes.
	 * @param p
	 */
	public void showAllProps(Player p) {
		p.getHand(0).unhide();
		p.getHand(1).unhide();
	}

	/**
	 * Si le joueur réussi le tour de magie, il révèle puis retourne toutes ses cartes.
	 * @param p
	 */
	public void hideAllProps(Player p) { 
		p.getHand(0).hide();
		p.getHand(1).hide();
	}

	protected static void sortPlayers() {
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