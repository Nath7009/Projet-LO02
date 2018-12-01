package theotherhattrick;

	import java.util.ArrayList; 
	import java.util.Arrays;
	import java.util.Stack;
	import java.util.Collections;

	public class Board {

		private Stack<Trick> tricks = new Stack<Trick>();
		private Stack<Trick> depiledTricks = new Stack<Trick>();

		private Stack<Prop> allProps = new Stack<Prop>();
//		private Prop[][] playersProps;
		private ArrayList <Prop> middleProp;
		private int variant;
		private Player[] players;

		public Board(Player[] players, int variant) {
			this.players = players;
			this.createCards(variant);
			this.distributeProps(variant);
			this.variant = variant;
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
			//Retourne vrai si le trick visible contient un élément de type element
		}

		public void setMiddleProp(ArrayList<Prop> middleProp){
			this.middleProp = middleProp;
		}

		public void setMiddleProp(Prop prop, int i){
			this.middleProp.set(i, prop);
		}
		
		/**
		 * Cette méthode distribue les Props générés dans la méthode createCards.
		 * Créé un ArrayList de la taille nécessaire à la variante jouée (2 si on joue avec le couteau suisse, 1 sinon).
		 * Chaque joueur reçoit 2 cartes, le milieu reçoit 1 ou 2 prop(s).
		 * @param rule
		 */
		public void distributeProps(int rule) {
			middleProp = new ArrayList<Prop>(1);
			this.middleProp.add(allProps.pop());
			if(variant == 1) { // Si on joue avec la variante du couteau suisse, on Veut une carte supplémentaire au milieu
				middleProp.ensureCapacity(2);
				this.middleProp.add(allProps.pop());
			}
			for(int i = 0; i < 2; i++) {
				for(Player player : players) {
					player.setHand(allProps.pop(), i);
				}
			}
		}
		
		/**
		 * Cette méthode créé les cartes (Props et Tricks) en fonction de la version du jeu à laquelle on joue.
		 * Utilise la méthode shuffle(Stack<\E> stack) pour mélanger les piles de cartes. On distribue les props avec la méthode distributeProps(int rule).
		 * On verse le contenu du Stack de Trick dans un second stack dans le fond duquel on a mis "The Other Hat Trick".
		 * @param rule
		 */
		public void createCards(int rule) {
			for(int i = 0; i < PropEnum.values().length - (rule == 1 ? 0 : 1); i++) {// Si on joue avec le couteau suisse, on veut 1 prop supplémentaire.
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
/*
		public Prop[] getCardsOfPlayer(int id, int numProp) {
			return null;
		}*/

/*		public void printTricks() {
			Trick[] arr = new Trick[tricks.size()];
			tricks.toArray(arr);

			for (int i = 0; i < arr.length; i++) {
				arr[i].print();
			}
		}*/
		
		public void printTopTrick() {
			Trick topTrick = this.depiledTricks.pop();
//			System.out.println("Le trick sur le dessus de la pile est :");
			topTrick.print();
			this.depiledTricks.push(topTrick);
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
				System.out.println("ERROR : undefined p2 value");
			}
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

		/* 
		 * 	Compare les prop de la main d'un joueur avec les props figurant sur le sort du dessus du Stack depiledTricks
		 */
		public boolean comparePropsToTrick(int id){
			int iT1= 0, iT2, ind = 0;
			boolean match1 = false, match2 = false;
			Prop tProp; // Les props figurant sur la carte Trick
			ArrayList<Prop> comp = players[id].getHand(); // Les props dans la main du joueur
			
			if(players[id].getHand().contains(new Prop(PropEnum.SWISS_ARMY_KNIFE))) {
				ind  = players[id].getHand().indexOf(new Prop(PropEnum.SWISS_ARMY_KNIFE));
				match2 = true;
			}
			// On rentrera toujours dans cette boucle,
			while(match1 == false && iT1 < 2) {
				iT2 = 0;
				while(match1 == false && iT2 < depiledTricks.peek().getLength(iT1)) {
					tProp = new Prop(depiledTricks.peek().getIngredient(iT1, iT2));
					if(comp.get((ind+1)%2).getType() == tProp.getType()) {
						match1 = true;
					}
					iT2++;
				}
				iT1++;
			}
			
			if(match1 == true && match2 == false) { // Si on a pas le couteau suisse et que notre premier prop est bon
				iT2 = 0;
				iT1 = iT1% 2; // on va regarder les props du trick encore libres
				while(match2 == false && iT2 < depiledTricks.peek().getLength(iT1)) {
					tProp = new Prop(depiledTricks.peek().getIngredient(iT1, iT2));
					if(comp.get(ind).getType() == tProp.getType()) {
						match2 = true;
					}
					iT2++;
				}
			}
			
			if(match1 == true && match2 == true) {
				return true;
			}
			else{
				return false;
			}
		}

/*
		public Prop createCopy(Prop prop) {
			return prop.clone();
		}

		public Trick createCopy(Trick trick) {
			return trick.clone();
		}

		public void replace() {
		}*/

		public void depile() {
			Trick temp = this.tricks.pop();
			this.depiledTricks.push(temp);
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

		public void showAllProps(int id) { // Montre tous les props du joueur afin de montrer qu'il peut bien réaliser le tour
			players[id].getHand(0).unhide();
			players[id].getHand(1).unhide();
		}

		public void hideAllProps(int id) { // Retourne face cachÃ©e tous les props du joueur en cas de tour rÃ©ussi
			players[id].getHand(0).hide();
			players[id].getHand(1).hide();
		}

		Stack<Prop> getProps() {
			// Automatically generated method. Please delete this comment before entering
			// specific code.
			return this.allProps;
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
}
