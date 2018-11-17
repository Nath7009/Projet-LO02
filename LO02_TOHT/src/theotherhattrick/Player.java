package theotherhattrick;

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
public class Player {
	private int id;
	private String name;
	private ArrayList<Prop> hand = new ArrayList<Prop>(2);
	private Stack<Trick> successPile = new Stack<Trick>();

	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setHand(Prop[] props) {
		this.hand = props;
	}

	public void setHand(ArrayList<Prop> props) {
		this.hand = props; //AR
	}

	public void setHand(Prop p, int ind) {
		this.hand.add(ind, prop);
	}
	
	public int getSize() {
		return hand.size();
	}

	public Prop getHand(int ind) {
		return hand.get(ind); 
	}

	public ArrayList<Prop> getHand(){
		return hand; 
	}

	public int getId() {
		return this.id;
	}

	public boolean isEmpty() {
		return successPile.empty();
	}

	public boolean has(String card) {
		for(int i=0;i<hand.size();i++) {
			if(hand.get(i).getName().equals(card)) {
				return true;
			}
		}
		return false;
	}
	
	public void pushTrick(Trick trick){
		successPile.add(trick);
	}
	
	//choiceNumb = nombre de réponses possibles
	// typeOfQuestion = b pour oui/non, 
	public int speak(String text, int choiceNumb, Player[] players, char typeOfQuestion) { 
		String ans = null;
		int ansInt = -1;
		Scanner keyboard = Game.keyboard;
		System.out.println(text);
		
		switch(choiceNumb) {
		case 2 : 
			switch(typeOfQuestion) {
			case 'b' : 
				do {
					System.out.println("Entrer y pour oui et n pour non : ");
					ans = keyboard.nextLine();
					ans.toLowerCase();
				}while(ans.equals("y") && ans.equals("n"));
				
				if(ans.equals("y")) {
					ansInt = 0;
				}
				else if(ans.equals("n")) {
					ansInt = 1;
				}
				else {
					ansInt = -1;
				}
				break; 
				
			case 'p' : 
				do {
					System.out.println("Entrer g pour la prop de gauche et d pour la prop de droite : ");
					ans = keyboard.nextLine();
					ans.toLowerCase();
					} while (ans.equals("g") && ans.equals("d"));
				if(ans.equals("g")) {
					ansInt = 0;
				}
				else if(ans.equals("d")) {
					ansInt = 1;
				}
				else {
					ansInt = -1;
				}
				break;
			
			default :
				ans = "error";
				break;
			}
			break;
			
		case 3 : 
			do {
				System.out.println("Entrer g, pour le prop de gauche, d pour le prop de droite, r pour garder les mêmes props : ");
				ans = keyboard.nextLine();
				ans.toLowerCase();
			}while(ans.equals("g") && ans.equals("d") && ans.equals("r"));
			
			if(ans.equals("g")) {
				ansInt = 0;
			}
			else if(ans.equals("d")) {
				ansInt = 1;
			}
			else if(ans.equals("r")) {
				ansInt = 2;
			}
			else {
				ansInt = -1;
			}
			break;
		
		case 4 : 
			switch(typeOfQuestion) {
			case 'n' : // n stands for neutral
				do {
					System.out.println("Entrer d1 ou g1 pour les props droite ou gauche de " + players[(this.id+1)%3].name + ", ou d2 ou g2 pour les props droite ou gauche de " + players[(this.id+2)%3].name + " : ");
					ans = keyboard.nextLine();
					ans.toLowerCase();
				}while(ans.equals("g1") && ans.equals("g2") && ans.equals("d1") && ans.equals("d2"));
				
				if(ans.equals("g1")) {
					ansInt = 0;
				}
				else if(ans.equals("d1")) {
					ansInt = 1;
				}
				else if(ans.equals("g2")) {
					ansInt = 2;
				}
				else if(ans.equals("d2")) {
					ansInt = 3;
				}
				else {
					ansInt = -1;
				}
				break;
			
			case 'v' : // v stands for variant. Pas encore développé.
				ans = "error";
				break;
			
			default : 
				ans = "error";
			}
			break;
		
		default : 
			ans  = "error";
			break;
		}
		
		return ansInt;
	}


	public void printProps() {
		System.out.println("Le joueur " + this.name + " possède les cartes suivantes");
		for (int i = 0; i < hand.size(); i++) {
			hand[i].print();
		}
	}

}
