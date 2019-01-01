package theotherhattrick;


import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author amall
 *
 */
public abstract class Player implements Decision {
	
	protected int id;
	protected String name;
	protected Date birthD;
	protected int score;
	protected ArrayList<Prop> hand = new ArrayList<Prop>(2);
	protected Stack<Trick> successPile = new Stack<Trick>();

	public Player(String name, int id, Date birthD) {
		this.name = name;
		this.id = id;
		this.birthD = birthD;
	}

	public String getName() {
		return this.name;
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



	public void SetBirthD(Date birthD) {
		this.birthD = birthD;
	}
	
	public Date getBirthD() {
		return birthD;
	}

	public boolean isEmpty() {
		return successPile.empty();
	}
	
	public void pushTrick(Trick trick){
		successPile.add(trick);
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
