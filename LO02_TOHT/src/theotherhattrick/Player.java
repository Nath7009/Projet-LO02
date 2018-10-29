package theotherhattrick;

import java.util.Scanner;

public class Player {
	private int id;
	private String name;
	private Prop[] props;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setProps(Prop[] props) {
		this.props = props;
	}

	/**	
	*	
	*
	*
	*
	**/
	public void exchangeProps() {
	}

	public void performTrick() {
	}

	public void chooseTrick() {
	}




	public int speak(String text, int choiceNumb, Player[] players, char typeOfQuestion) { 
		Scanner keyboard = new Scanner(System.in);
		String ans = null;
		int ansInt = -1;
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
			}while(ans.equals("y") && ans.equals("n") && ans.equals("r"));
			
			if(ans.equals("y")) {
				ansInt = 0;
			}
			else if(ans.equals("n")) {
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
		keyboard.close();
		
		return ansInt;
	}



	/**	ChooseProptoChange
	*	
	*
	*
	*
	**/
	public int choosePropToChange() { // Retourne l'indice dans Player.props[] de la carte que le joueur souhaite
										// échanger
		return 0; // Temporaire
	}

	public void printProps() {
		System.out.println("Le joueur " + this.name + " possède les cartes suivantes");
		for (int i = 0; i < props.length; i++) {
			props[i].print();
		}
	}

}