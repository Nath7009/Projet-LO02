package theotherhattrick;

	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Stack;
	import java.util.Collections;

	public class Board {

		private Stack<Trick> tricks;
		private Stack<Trick> depiledTricks;

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

		public void createCards(int rule) {
			for(int i = 0; i < Prop.values().length - (rule == 1 ? 0 : 1); i++) {
				allProps.push(Prop.values()[i]);
				if(i == 0) {
					allProps.push(Prop.values()[i]);
					allProps.push(Prop.values()[i]);
				}
			}
			Collections.shuffle(allProps);
			
			Stack<Trick> temp = new Stack<Trick>();
			for(int i = 0; i < Trick.values().length - 1; i++) {
				temp.push(Trick.values()[i]);
			}
			Collections.shuffle(temp);
			temp.push(Trick.values()[Trick.values().length - 1]);
			for(int i = 0; i < Trick.values().length; i++) {
				tricks.push(temp.pop());
			}
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
			
			System.out.println("Votre main, " + players[id].getName() + " : \n"); // On affiche la main du joueur et on regarde quels props sont cachÃ©s
			System.out.println(players[id]);
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
				choice = players[0].speak("\nQuel prop voulez-vous rÃ©vÃ©ler ?", 2, players, 'p');
				players[id].getHand(choice).unhide();
			}
			else if(hNum == 1) {
				System.out.println("Vous n'avez que le prop " + players[id].getHand(choice).getName() + " qui soit cachÃ©. " + players[id].getHand(choice).getName() +" est maintenant visible.");
				players[id].getHand(choice).unhide();
			}
			else {
				System.out.println("Tous vos props sont dÃ©jÃ  visibles. Aucune action n'est effectuÃ©e."); 
			}
		}

		public void showAllProps(int id) { // Montre tous les props du joueur afin de montrer qu'il peut bien rÃ©aliser le
											// tour
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

/*		void setProps(Prop[] value) {
			// Automatically generated method. Please delete this comment before entering
			// specific code.
			this.allProps.get(i). = value;
		}*/
		
		void print() { //Affiche toutes les cartes des joueurs en fonction de leur visibilitÃ©
			
		}

}
