package theotherhattrick;


import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {
	private int id;
	private String name;
	private Date birthD;
	private int score;
	private ArrayList<Prop> hand = new ArrayList<Prop>(2);
	private Stack<Trick> successPile = new Stack<Trick>();

	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public Player(String name, int id, Date birthD) {
		this.name = name;
		this.id = id;
		this.birthD = birthD;
	}

	public String getName() {
		return this.name;
	}

	public void setHand(Prop[] props) {
		for(int i=0;i<props.length;i++) {
			this.hand.add(props[i]);
		}
	}

	public void setHand(ArrayList<Prop> props) {
		this.hand = props; //AR
	}

	public void setHand(Prop p, int ind) {
		this.hand.add(ind, p);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void increaseScore(int points) {
		this.score += points;
		System.out.println("Vous gagnez >" + points + "< points. Vous avez désormais >" + this.score + "< points." );
	}

	public int getSize() {
		return hand.size();
	}
	
	public int getScore() {
		return this.score;
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

	public void SetBirthD(Date birthD) {
		this.birthD = birthD;
	}
	
	public Date getBirthD() {
		return birthD;
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
		case 2 : // On a  le choix entre deux actions
			switch(typeOfQuestion) {
			case 'b' : // On a un choix oui/non
				do {
					System.out.println("Entrer y pour oui et n pour non : ");
					ans = keyboard.nextLine();
					ans.toLowerCase();
				}while(ans.equals("y") && ans.equals("n"));
				
				if(ans.equals("n")) {
					ansInt = 0;
				}
				else if(ans.equals("y")) {
					ansInt = 1;
				}
				else {
					ansInt = -1;
				}
				break; 
				
			case 'p' : // On choisit un Prop
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
			
		case 3 : // On a un choix entre 3 actions : choix de props à remettre au milieu après un succès
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
				ansInt = -1;// s'il y a une erreur
			}
			break;
		
		case 4 : // On a le choix entre 4 actions : choisir le prop adverse avec qui échanger son prop
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
		case 5 : // On joue la règle de la carrote : on a le choix entre le prop du milieu ou un des props des joueurs
			switch(typeOfQuestion){
				case 'v' : 
					do{
						System.out.println("Entrer 0 ou 1 pour les props de gauche ou droite de " + players[(this.id+1)%3].name + ", 2 ou 3 pour le props de gauche ou droite de " + players[(this.id + 2)%3].name + " ou -1 pour le prop du milieu : " );
						ansInt = keyboard.nextInt();
					} while(ansInt >= 3 || ansInt <= -1);
					break;

				default : 
					ans = "error"; 
					break;
			}
		default : 
			ans  = "error";
			break;
		}
		
		return ansInt;
	}


	public void printProps() {
//		System.out.println("Le joueur " + this.name + " possède les cartes suivantes");
		for (Iterator<Prop> it = hand.iterator();it.hasNext();) {
			System.out.println(it.next());
		}
	}
	
	public void printVisible() {
		for(Prop p : hand) {
			p.printIfVisible();
		}
	}

}
