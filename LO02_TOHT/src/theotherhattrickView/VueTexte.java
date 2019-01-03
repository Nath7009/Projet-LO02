package theotherhattrickView;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

//import Observer.Observer;
import theotherhattrick.Game;
import theotherhattrick.Player;
import theotherhattrick.Prop;
import theotherhattrick.Trick;
import theotherhattrickControler.GameControler;
 
@SuppressWarnings("deprecation")
public class VueTexte implements Observer{
	
	private GameControler gc;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static String PROMPT = ">";

	public VueTexte() {
		
		this.initGame();
	}
	
	public void initGame() {
//		On choisit la variante
		System.out.println("=|=[=====]=|=[=====]=|=" + Game.NAME + "=|=[=====]=|=[=====]=|=\n");
		System.out.println("Voici les différentes règles du jeu : \n" 
				+ Game.RULE_SWISS_ARMY_KNIFE 
				+ Game.RULE_CARROT
				+ Game.RULE_LETTUCE);
		System.out.println("Entrez 0 pour jouer sans variantes, "
				+ "1 pour jouer avec le Couteau Suisse, "
				+ "2 pour jouer avec la Carotte, "
				+ "3 pour jouer avec la Laitue.");
		int variant = Integer.parseInt(this.readString());
		this.gc = new GameControler(variant);
		this.gc.getGame().addObserver(this);
//		System.out.println("nb d'observer (controler) : " + this.gc.getGame().countObservers());
		this.gc.setVariant(variant);
		this.gc.getGame().start();
		
	}
	
		@Override
	public void update(Observable o, Object arg1) {
//		System.out.println("update appelé");
		if(o instanceof Game) {
//			System.out.println("Game reconnu");
			switch((String)arg1) {
			
				case "nbOfPlayers" : 
//					System.out.println("nbOfPlayers reconnu");
					System.out.println("Entrez le nombre de joueurs humains voulant participer au jeu : ");
					int nbOfHuman = Integer.parseInt(this.readString());
					this.gc.setNbOfHuman(nbOfHuman);
					break;
					
				case "identityOfPLayer" : 
//					System.out.println("identityOfPlayer reconnu");
					this.askIdentity();
					break;
			}
		}
		
		if(o instanceof Player) {
		
			switch((String)arg1){
			
				case "revealNewTrick" :
					break;
			
				case "chooseOwnProp" : 
					break;
			
				case "chooseOtherProp" : 
					break;
			
				case "chooseMiddleVarCarrot" : 
					break;
			
				case "performTrick" : 
					break;
			
				case "revealProp" :
					break;
			
				case "chooseMiddle" : 
					break;
			}
		}
		
		if(o instanceof Trick) {
			
		}
		
		if(o instanceof Prop) {
			
		}
	}
	
	private void askIdentity() {
		System.out.println("Entrez le nom du joueur : ");
		String name = this.readString();
//		this.gc.setName();
		
		int day = 0, month = 0, year = 0;
		do {
			System.out.println("Entrer votre jour de naissance");
			day = Integer.parseInt(this.readString());
			System.out.println();
		} while(day < 0 || day > 31);
		do {
			System.out.println("Entrer votre mois de naissance");
			month = Integer.parseInt(this.readString());
		} while(month < 0 || month > 12);
		do {
			System.out.println("Entrer votre année de naissance");
			year = Integer.parseInt(this.readString());
		} while(year > 2014);
		 
		this.gc.setNewPlayer(name, day, month, year);
	}
	
	private boolean readBool() {
		boolean value = false;
		try {
		    System.out.print(VueTexte.PROMPT);
			value = ((this.reader.readLine() == "y\n" ) ? true : false);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	private String readString() {
		String value = "";
		try {
		    System.out.print(VueTexte.PROMPT);
			value = this.reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	
}
