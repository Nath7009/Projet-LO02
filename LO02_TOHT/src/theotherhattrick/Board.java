package theotherhattrick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Collections;

public class Board {

	private Stack<Trick> tricks;
	private Stack<Trick> depiledTricks;

	private Prop[] allProps;
	private Prop[][] playersProps;

	private Player[] players;

	public Board(Player[] players) {
		this.players = players;
		this.createCards();
		this.distributeCards();
	}

<<<<<<< HEAD
=======
	public Prop[] getCardsOfPlayer(int id) {
		return null;
	}
	
	public int getTrickPileLength() {
		return tricks.size();
	}

	public void distributeCards() {
		this.playersProps = new Prop[players.length][];

		for (int i = 0; i < 3; i++) {
			this.playersProps[i] = new Prop[2];
		}

		Collections.shuffle(Arrays.asList(allProps));

		int ind = 0;
		for (int i = 0; i < playersProps.length; i++) {
			for (int j = 0; j < 2; j++) {
				playersProps[i][j] = allProps[ind];
				ind++;
			}
		}

		for (int i = 0; i < playersProps.length; i++) {
			players[i].setProps(playersProps[i]);
		}
	}
>>>>>>> 4c87daf8e5153920c4a1a5b09b9f1397c468037d

	public void createCards() {

		// Création des props
		allProps = new Prop[7];

		Prop carrot = new Prop("Carrot", 1);
		Prop lettuce = new Prop("Lettuce", 2);
		Prop hat = new Prop("Hat", 3);
		Prop rabbit = new Prop("The Rabbit", 4);
		Prop oRabbit = new Prop("The Other Rabbit", 5);

		for (int i = 0; i < 3; i++) {
			allProps[i] = carrot;
		}

		allProps[3] = lettuce;
		allProps[4] = hat;
		allProps[5] = rabbit;
		allProps[6] = oRabbit;

		// Création des tricks
		ArrayList<Trick> tTricks = new ArrayList<Trick>(10);
		// Liste permettant de stocker les tricks, on utilise une liste parcequ'elle
		// peut être mélangée
		// Ensuite, on place l'arraylist dans une pile

		tTricks.add(new Trick("The Hungry Rabbit", new Prop[][] { { rabbit, oRabbit }, { carrot, lettuce } }, 1));
		tTricks.add(new Trick("The Bunch of Carrots", new Prop[][] { { carrot }, { carrot } }, 2));
		tTricks.add(new Trick("The Vegetable Patch", new Prop[][] { { carrot }, { lettuce } }, 3));
		tTricks.add(
				new Trick("The Rabbit That Didn't Like Carrots", new Prop[][] { { rabbit, oRabbit }, { lettuce } }, 4));
		tTricks.add(new Trick("The Pair of Rabbits", new Prop[][] { { rabbit }, { oRabbit } }, 5));
		tTricks.add(new Trick("The Vegetable Hat Trick", new Prop[][] { { hat }, { carrot, lettuce } }, 2));
		tTricks.add(new Trick("The Carrot Hat Trick", new Prop[][] { { hat }, { carrot } }, 3));
		tTricks.add(new Trick("The Slightly Easier Hat Trick", new Prop[][] { { hat }, { rabbit, oRabbit } }, 4));
		tTricks.add(new Trick("The Hat Trick", new Prop[][] { { hat }, { rabbit } }, 5));

		Collections.shuffle(tTricks); // Mélange des cartes
		tTricks.add(new Trick("The Other Hat Trick", new Prop[][] { { hat }, { oRabbit } }, 6));
		// Ajout de cette carte à la fin pour qu'on soit sure qu'elle se trouve en fin
		// de tas

		tricks = new Stack<Trick>();

		for (int i = tTricks.size() - 1; i >= 0; i--) { // On parcourt la liste à l'envers pour mettre The Other Hat
														// Trick en dernier
			tricks.push(tTricks.get(i));
		}

		// printTricks();
	}


	
	public Prop[] getCardsOfPlayer(int id, int numProp) {
		return null;
	}

	public void distributeCards() {
		this.playersProps = new Prop[players.length][];

		for (int i = 0; i < 3; i++) {
			this.playersProps[i] = new Prop[2];
		}

		Collections.shuffle(Arrays.asList(allProps));

		int ind = 0;
		for (int i = 0; i < playersProps.length; i++) {
			for (int j = 0; j < 2; j++) {
				playersProps[i][j] = allProps[ind];
				ind++;
			}
		}

		for (int i = 0; i < playersProps.length; i++) {
			players[i].setProps(playersProps[i]);
		}
	}


	public void printTricks() {
		Trick[] arr = new Trick[tricks.size()];
		tricks.toArray(arr);

		for (int i = 0; i < arr.length; i++) {
			arr[i].print();
		}
	}
	
	public void exchangeProps(int p1, int ind1, int p2, int ind2) { //Echange le prop d'indice ind1 du joueur d'id p1, avec le prop d'indice ind2 du joueur p2 
	}
	
	public void giveTrick(int id) { //Donne le trick sur le dessus de depiledTricks au joueur d'id id
	}

	public boolean comparePropsToTrick() {
		return false; //TEMPORAIRE
	}

	public Prop createCopy(Prop prop) {
		return prop.clone();
	}

	public Trick createCopy(Trick trick) {
		return trick.clone();
	}

	public void replace() {
	}

	public void depile() {
	}

	public void revealProp(int id) { //Si le joueur n'a aucun prop révélé, on lui demande son avis, sinon, on retourne sont other prop.
	}
	
	public void showAllProps(int id) { //Montre tous les props du joueur afin de montrer qu'il peut bien réaliser le tour
		
	}
	
	public void hideAllProps(int id) { //Retourne face cachée tous les props du joueur en cas de tour réussi
	}

	Prop[] getProps() {
		// Automatically generated method. Please delete this comment before entering
		// specific code.
		return this.allProps;
	}

	void setProps(Prop[] value) {
		// Automatically generated method. Please delete this comment before entering
		// specific code.
		this.allProps = value;
	}

}
