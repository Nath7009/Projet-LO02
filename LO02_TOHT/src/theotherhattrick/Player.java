package theotherhattrick;


import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.Observable;
import java.util.Stack;



@SuppressWarnings("deprecation")
/**
 * Classe abstraite définissant les foncionnalités communes aux joueurs humains et aux IA.
 * Les classes qui héritent de Player spécifieront les méthodes de l'interface Decision. 
 * La classe Joueur hérite de la classe Observable. Elle notifie les Observers avec des String décrivant explicitement le changement à l'origine de la notification.
 * @author amall
 *
 */
public abstract class Player extends Observable implements Decision, Serializable {
	
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String name;
	protected Date birthD;
	protected int score;
	protected ArrayList<Prop> hand = new ArrayList<Prop>(2);
	protected Stack<Trick> successPile = new Stack<Trick>();
	protected boolean canViewProps = false;
	
	protected int ownProp = 0, otherProp = 0, middleProp = 0, middleVarCarrot = 0, revealProp;
	protected boolean newTrick = false, performTrick = false;
	
	/**
	 * Constructeur de la classe Joueur
	 * @param name le nom du joueur.S'il vaut null, on attribut un nom aléatoire au joueur.
	 * @param birthD
	 * 
	 * @see #generateName()
	 */
	public Player(String name, Date birthD) {
		if(name == null) {
			name = generateName();
		}
		if(birthD == null) {
			birthD = new Date();
		}
		this.name = name;
		this.birthD = birthD;
	}
	
	/**
	 * la méthode setChanged est privée dans la classe Observable.
	 * Cette méthode permet donc d'appeler setChanged depuis une classe extérieur effectuant des traitements sur Player.
	 */
	public void forceChanged() {
		this.setChanged();
	}
	
	public int getOwnProp() {
		return ownProp;
	}

	public void setOwnProp(int ownProp) {
		this.ownProp = ownProp;
	}

	public int getOtherProp() {
		return otherProp;
	}

	public void setOtherProp(int otherProp) {
		this.otherProp = otherProp;
	}

	public int getMiddleProp() {
		return middleProp;
	}

	public void setMiddleProp(int middleProp) {
		this.middleProp = middleProp;
	}

	public int getMiddleVarCarrot() {
		return middleVarCarrot;
	}

	public void setMiddleVarCarrot(int middleVarCarrot) {
		this.middleVarCarrot = middleVarCarrot;
	}

	public int getRevealProp() {
		return revealProp;
	}

	public void setRevealProp(int revealProp) {
		this.revealProp = revealProp;
	}

	public boolean isNewTrick() {
		return newTrick;
	}

	public void setNewTrick(boolean newTrick) {
		this.newTrick = newTrick;
	}

	public boolean isPerformTrick() {
		return performTrick;
	}

	public void setPerformTrick(boolean performTrick) {
		this.performTrick = performTrick;
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
		if(this instanceof Robot) {
			this.name = this.name + id;
		}
	}
	
	public void SetBirthD(Date birthD) {
		this.birthD = birthD;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Date getBirthD() {
		return birthD;
	}

	public boolean isEmpty() {
		return successPile.empty();
	}
	
	/**
	 * On génère un nom composé de manière aléatoire à partir d'une liste de nom.
	 * @return
	 * 
	 */
	public String generateName() {
		String[] names = {"Pierre", "Paul", "Jacques", "Rodolphe", "Nathan", "Antoine", "Léo", "Gabriel", "Jules", "Emma", "Manon", "Jade", "Louise"};
		int name1 = (int) Math.floor(Math.random() * names.length);
		int name2 = (int) Math.floor(Math.random() * names.length);
		return names[name1] + "-" + names[name2];
	}
	
	/**
	 * On notifie les Observers que le score du joueur a augmenté.
	 * @param points
	 */
	public void increaseScore(int points) {
		this.score += points;
		
		this.setChanged();
		this.notifyObservers("increase score");
	}

	public void pushTrick(Trick trick){
		successPile.add(trick);
	}
	
	/**
	 * On notifie les Observers que l'on souhaite montrer les cartes du joueur
	 */
	public void printProps() {
		this.setChanged();
		this.notifyObservers("print hand");
		for (Iterator<Prop> it = hand.iterator();it.hasNext();) {
			it.next().print();
		}
	}
	
	/**
	 * On notifie les Observers que l'on souhaite montrer les cartes visibles du joueur uniquement
	 */
	public void printVisible() {
		this.setChanged();
		this.notifyObservers("name");
		for(Prop prop : hand) {
			prop.printIfVisible();
		}
	}
	
	public boolean isViewable() {
		return this.canViewProps;
	}
	
	public void setViewable() {
		this.canViewProps = true;
	}
	
	public void setUnviewable() {
		this.canViewProps = false;
	}
	
	/**
	 * La main du joueur devient visible
	 */
	public void showAllProps() {
		this.getHand(0).unhide();
		this.getHand(1).unhide();
	}
	
	/**
	 * La main du joueur devient cachée.
	 */
	public void hideAllProps() {
		this.getHand(0).hide();
		this.getHand(1).hide();
	}

}
