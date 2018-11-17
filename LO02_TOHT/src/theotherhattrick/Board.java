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
	private Prop middleProp;

	private Player[] players;

	public Board(Player[] players, boolean[] rules) {
		this.players = players;
		this.createCards(rules);
		this.distributeCards(rules);
	}

	public Prop[] getCardsOfPlayer(int id) {
		return null;
	}

	public int getTrickPileLength() {
		return tricks.size();
	}

	public boolean depiledIsEmpty() {
		return depiledTricks.empty();
	}
	

	public ArrayList<Prop> getMiddleProp() {
		return this.middleProp;
	}

	public Prop getMiddleProp(int i){
		return this.middleProp.get(i);
	}
	
	public boolean contains(String element) {
		return false; //TEMPORAIRE
		//Retourne vrai si le trick visible contient un �l�ment de type element
	}

	public void setMiddleProp(ArrayList<Prop> middleProp){
		this.middleProp = middleProp;
	}

	public void setMiddleProp(Prop prop, int i){
		this.middleProp.set(i, prop);
	}

	public void distributeProps(boolean[] rules) {
		middleProp = new ArrayList<Prop>(1);
		this.middleProp.add(allProps.pop());
		if(rules[0]) { // Si on joue avec la variante du couteau suisse, on Veut une carte suppl�mentaire au milieu
			middleProp.ensureCapacity(2);
			this.middleProp.add(allProps.pop());
		}
		for(int i = 0; i < 2; i++) {
			for(Player player : players) {
				player.setHand(allProps.pop(), i);
			}
		}
	}

	public void createCards(boolean[] rules) {
		// Cr�ation des props
		if(rules[0]) {
			allProps = new Stack<Prop>(8);
		}
		else {
			allProps = new Stack<Prop>(7);
		}
		Prop carrot = new Prop("Carrot", 1);
		Prop lettuce = new Prop("Lettuce", 2);
		Prop hat = new Prop("Hat", 3);
		Prop rabbit = new Prop("The Rabbit", 4);
		Prop oRabbit = new Prop("The Other Rabbit", 5);

		for (int i = 0; i < 3; i++) {
			allProps.add(carrot);
		}
		allProps.add(lettuce);
		allProps.add(hat);
		allProps.add(rabbit);
		allProps.add(oRabbit);
		if(rules[0]) {
			Prop swissKnife = new Prop("Magical Swiss-Army-Knife", 6);
			allProps.add(swissKnife);
		}
		Collections.shuffle(allProps);

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
		depiledTricks = new Stack<Trick>();

		for (int i = tTricks.size() - 1; i >= 0; i--) { // On parcourt la liste à l'envers pour mettre The Other Hat
														// Trick en dernier
			tricks.push(tTricks.get(i));
		}

		// printTricks();
	}

	public Prop[] getCardsOfPlayer(int id, int numProp) {
		return null;
	}

	public void printTricks() {
		Trick[] arr = new Trick[tricks.size()];
		tricks.toArray(arr);

		for (int i = 0; i < arr.length; i++) {
			arr[i].print();
		}
	}
	
	public void printTopTrick() {
		Trick topTrick = this.depiledTricks.pop();
		System.out.println("Le trick sur le dessus de la pile est :");
		topTrick.print();
		this.depiledTricks.push(topTrick);
	}

	/*	
	 *	Echange la position de 2 props 
	 * 	la version où l'on utilise createCopy() provoque plusieurs erreurs de type java.lang.CloneNotSupportedException
	 * 	La version où l'on utilise directement les accesseurs est fonctionnelle.
	 */
	public void exchangeProps(int p1, int ind1, int p2, int ind2) { //Echange le prop d'indice ind1 du joueur d'id p1, avec le prop d'indice ind2 du joueur p2 
		Prop tmp1 = players[p1].getHand(ind1);
		
		if(p2 == -1) { // Si on veut échanger le prop du joueur dont c'est le tour avec celui d'un autre joueur
			players[p1].setHand(this.getMiddleProp() ,ind1);
			setMiddleProp(tmp1);
		}
		else if (p2 == (p1+1)%3 || p2 == (p1+2)%3) { // Si on veut échanger le prop du joueur dont c'est le tour avec celui du milieu 
			players[p1].setHand(players[p2].getHand(ind2), ind1);
			players[p2].setHand(tmp1, ind2);
		}
		else {
			System.out.println("ERROR : undefined p2 value");
		}
	}

	/* 
	 * 	Donne le trick du dessus de depiledTricks au joueur d'id id
	 */
	public void giveTrick(int id) {

		players[id].pushTrick(depiledTricks.pop());
	}

	/* 
	 * 	Compare les prop de la main d'un joueur avec les props figurant sur le sort du dessus du Stack depiledTricks
	 */
	public boolean comparePropsToTrick(int id){
		int iT1= 0, iT2, ind = 0;
		boolean match1 = false, match2 = false;
		Prop tProp; // Les props figurant sur la carte Trick
		ArrayList<Prop> comp = players[id].getHand(); // Les props dans la main du joueur
		for(Prop prop : comp) {
//			prop.print();
			if(prop.getType() == 6) {
				ind = comp.indexOf(prop);
				match2 = true;
			}
		}
		// On rentrera toujours dans cette boucle,
		while(match1 == false && iT1 < 2) {
//											System.out.println("it1 = " + iT1 + "\t" + match1 + "\t" + match2);
			iT2 = 0;
			while(match1 == false && iT2 < depiledTricks.peek().getLength(iT1)) {
//											System.out.println("it2 = " + iT2 + "\t" + match1 + "\t" + match2);
				tProp = depiledTricks.peek().getIngredient(iT1, iT2);
//				tProp.print();
				if(comp.get((ind+1)%2).getType() == tProp.getType()) {
					match1 = true;
				}
				iT2++;
//											System.out.println("it2 = " + iT2 + "\t" + match1 + "\t" + match2 + "\t ind = " + (ind+1)%2);
			}
			iT1++;
		}
		
		if(match1 == true && match2 == false) { // Si on a pas le couteau suisse et que notre premier prop est bon
			iT2 = 0;
			iT1 = iT1% 2; // on va regarder les props du trick encore libres
			System.out.println("\nit1 = "+ iT1 +"\n");
			while(match2 == false && iT2 < depiledTricks.peek().getLength(iT1)) {
				tProp = depiledTricks.peek().getIngredient(iT1, iT2);
				if(comp.get(ind).getType() == tProp.getType()) {
					match2 = true;
				}
				iT2++;
//				System.out.println("it2 = " + iT2 + "\t" + match1 + "\t" + match2 + "\t ind = " +  ind);
			}
		}
		
		if(match1 == true && match2 == true) {
			return true;
		}
		else{
			return false;
		}
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
		Trick temp = this.tricks.pop();
		this.depiledTricks.push(temp);
	}
	
	public void returnProp(int id) {
		
	}

	public void revealProp(int id) {
		int choice = 0, i, hNum = 0;
		
		System.out.println("Votre main, " + players[id].getName() + " : \n"); // On affiche la main du joueur et on regarde quels props sont cachés
		for(i = 0; i < 2; i++) {
			players[id].getHand(i).print();
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
			choice = players[0].speak("\nQuel prop voulez-vous révéler ?", 2, players, 'p');
			players[id].getHand(choice).unhide();
		}
		else if(hNum == 1) {
			System.out.println("Vous n'avez que le prop " + players[id].getHand(choice).getName() + " qui soit caché. " + players[id].getHand(choice).getName() +" est maintenant visible.");
			players[id].getHand(choice).unhide();
		}
		else {
			System.out.println("Tous vos props sont déjà visibles. Aucune action n'est effectuée."); 
		}
	}

	public void showAllProps(int id) { // Montre tous les props du joueur afin de montrer qu'il peut bien réaliser le
										// tour
		players[id].getHand(0).unhide();
		players[id].getHand(1).unhide();
		

	}

	public void hideAllProps(int id) { // Retourne face cachée tous les props du joueur en cas de tour réussi
		players[id].getHand(0).hide();
		players[id].getHand(1).hide();
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
	
	void print() { //Affiche toutes les cartes des joueurs en fonction de leur visibilité
		
	}

}
