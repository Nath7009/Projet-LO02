package theotherhattrick;

import java.util.Scanner;

/**
 * 
 * @author amall
 *
 */
public class Human extends Player {

	public Human(String name, Date birthDate) {
		super(name, birthDate);
	}
	
	public boolean revealNewTrick() {
		Scanner keyboard = Game.keyboard;
		String ans = null;
		int i = 0;
		
		System.out.println("Voulez-vous retourner un nouveau Trick ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez y pour oui et n pour non : ");
			ans = keyboard.nextLine();
			ans.toLowerCase();
			i++;
		}while(ans.equals("y") && ans.equals("n"));
		
		return ans.equals("y") ? true : false;
	}
	
	public int chooseOwnProp() {
		Scanner keyboard = Game.keyboard;
		int ans = -1, i = 0;
		
		System.out.println("Lequel de vos Props choissisez-vous ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : "+ this.getHand(0).getName() +", ou 1 pour le Prop : " + this.getHand(1).getName());
			ans = keyboard.nextInt();
			i++;
		}while(ans != 0 && ans != 1);
		
		return ans;
	}
	
	public int chooseOtherProp(Player[] players) {
		Scanner keyboard = Game.keyboard;
		int ans = 0, i = 0;
		System.out.println("Avec quel Prop des autres joueurs voulez-vous faire l'échange ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println(players[(this.id+1)%3].getName() + " :\nEntrez 0 pour le Prop : " + players[(this.id+1)%3].getHand(0).toStringIfVisible() + "\nou 1 pour le Prop : " + players[(this.id+1)%3].getHand(1).toStringIfVisible());
			System.out.println(players[(this.id+2)%3].getName() + " :\nEntrez 2 pour le Prop : " + players[(this.id+2)%3].getHand(0).toStringIfVisible() + "\nou 3 pour le Prop : " + players[(this.id+2)%3].getHand(1).toStringIfVisible());
			ans = keyboard.nextInt();
			i++;
		}while(ans > 3 || ans < 0);
		return ans;
	}
	
	public int chooseMiddleVarCarrot(Player[] players) {
		Scanner keyboard = Game.keyboard;
		int ans = 0, i = 0;
		System.out.println("Avec quel Prop voulez-vous faire l'échange ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println(players[(this.id+1)%3].getName() + " :\nEntrez 0 pour le Prop : " + players[(this.id+1)%3].getHand(0).toStringIfVisible() + "\nou 1 pour le Prop : " + players[(this.id+1)%3].getHand(1).toStringIfVisible());
			System.out.println(players[(this.id+2)%3].getName() + " :\nEntrez 2 pour le Prop : " + players[(this.id+2)%3].getHand(0).toStringIfVisible() + "\nou 3 pour le Prop : " + players[(this.id+2)%3].getHand(1).toStringIfVisible());
			System.out.println("Entrez 4 pour échanger avec le Prop du milieu : ");
			ans = keyboard.nextInt();
			i++;
		}while(ans > 4 || ans < 0);
		return ans;
	}
	
	public boolean performTrick() {
		Scanner keyboard = Game.keyboard;
		String ans = null;
		int i = 0;
		
		System.out.println("Voulez-vous tenter de réalsier le Trick ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez y pour oui et n pour non : ");
			ans = keyboard.nextLine();
			ans.toLowerCase();
			i++;
		}while(ans.equals("y") && ans.equals("n"));
		
		return ans.equals("y") ? true : false;
	}
	
	public int revealProp() {
		Scanner keyboard = Game.keyboard;
		int ans = -1, i = 0;
		
		System.out.println("Lequel de vos Props souhaitez-vous retourner ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : "+ this.getHand(0).getName() +", ou 1 pour le Prop : " + this.getHand(1).getName());
			ans = keyboard.nextInt();
			i++;
		}while(ans != 0 && ans != 1);
		
		return ans;
	}
	
	public int chooseMiddle() {
		Scanner keyboard = Game.keyboard;
		int ans = -1, i = 0;
		System.out.println("Quel Prop voulez-vous replacer au milieu");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : "+ this.getHand(0).getName() +", 1 pour le Prop : " + this.getHand(1).getName() + ", ou 2 pour garder les m�mes Props : ");
			ans = keyboard.nextInt();
			i++;
		}while(ans !=0 && ans != 1 && ans != 2);
		return ans;
	}	
}
