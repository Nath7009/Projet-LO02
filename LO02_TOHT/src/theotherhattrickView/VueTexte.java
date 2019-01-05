package theotherhattrickView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

//import Observer.Observer;
import theotherhattrick.Game;
import theotherhattrick.Human;
import theotherhattrick.Player;
import theotherhattrick.Prop;
import theotherhattrick.Robot;
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
		int variant;
		
		do {
			System.out.println("\nVeuillez choisir la règle que vous voulez utiliser :");
			System.out.println("Entrez 0 pour jouer sans variantes, 1 pour jouer avec le Couteau Suisse, 2 pour jouer avec la Carotte, 3 pour jouer avec la Laitue");
			variant = this.readInt();
		} while (variant < 0 || variant > 3);
		
		
		this.gc = new GameControler(variant);
		this.gc.getGame().addObserver(this);
//		System.out.println("nb d'observer (controler) : " + this.gc.getGame().countObservers());
		this.gc.setVariant(variant);
		this.gc.getGame().start();
		
	}
	
		@Override
	public void update(Observable o, Object arg1) {

			if(o instanceof Game) {
			
			switch((String)arg1) {
			
				case "nbOfPlayers" : 

					System.out.println("Entrez le nombre de joueurs humains voulant participer au jeu : ");
					int nbOfHuman = Integer.parseInt(this.readString());
					this.gc.setNbOfHuman(nbOfHuman);
					break;
					
				case "identityOfPLayer" : 
					
					this.askIdentity();
					break;
					
				case "depile" :
					System.out.println("Le nouveau Trick à réaliser est : " + ((Game) o).getTopTrick());
					break;
			}
		}
		
		if(o instanceof Player) {
			
			if(o instanceof Human) {
				Player p = (Player) o;
				switch((String)arg1){
				
					case "revealNewTrick" :
						this.askRevealNewTrick(p);
						break;
				
					case "chooseOwnProp" :
						this.askChooseOwnProp(p);
						break;
				
					case "chooseOtherProp" : 
						this.askChooseOtherProp(p);
						break;
				
					case "chooseMiddleVarCarrot" :
						this.askChooseMiddleVarCarrot(p);
						break;
				
					case "performTrick" : 
						this.askPerformTrick(p);
						break;
				
					case "revealProp" :
						this.askRevealProp(p);
						break;
				
					case "chooseMiddle" : 
						this.askChooseMiddle(p);
						break;
				}
			}
			
			if(o instanceof Robot) {
				
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
		this.gc.getGame().getNewPlayer().addObserver(this);
	}
	
	private void askRevealNewTrick(Player p) {
		int i = 0;
		String ans;
		System.out.println("Voulez-vous retourner un nouveau Trick ?");
		do {
			if(i > 0) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez y pour oui et n pour non : ");
			 ans = this.readString();
			i++;
		}while(ans.equals("y") && ans.equals("n"));
		
		this.gc.setRevealNewTrick(p, (ans.equals("y") ? true : false));
	}
	
	private void askChooseOwnProp(Player p) {
		int i = 0, ans = -1;
		
		System.out.println("Lequel de vos Props choissisez-vous ?");
		do {
			if(i > 0) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : "+ p.getHand(0).getName() +", ou 1 pour le Prop : " + p.getHand(1).getName());
			ans = this.readInt();
			i++;
		}while(ans != 0 && ans != 1);
		this.gc.setOwnProp(p, ans);
		
	}
	
	private void askChooseOtherProp(Player p) {
		int i = 0, ans = -1;
		Player[] players = this.gc.getGame().getPlayers();
		System.out.println("Avec quel Prop des autres joueurs voulez-vous faire l'échange ?");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println(players[(p.getId()+1)%3].getName() + " :\nEntrez 0 pour le Prop : " + players[(p.getId()+1)%3].getHand(0).toStringIfVisible() + "\nou 1 pour le Prop : " + players[(p.getId()+1)%3].getHand(1).toStringIfVisible());
			System.out.println(players[(p.getId()+2)%3].getName() + " :\nEntrez 2 pour le Prop : " + players[(p.getId()+2)%3].getHand(0).toStringIfVisible() + "\nou 3 pour le Prop : " + players[(p.getId()+2)%3].getHand(1).toStringIfVisible());
			ans = readInt();
			i++;
		}while(ans > 3 || ans < 0);
		this.gc.setOtherProp(p, ans);
		
	}

	private void askChooseMiddleVarCarrot(Player p) {
		int i = 0, ans = -1;
		Player[] players = this.gc.getGame().getPlayers();
		System.out.println("Avec quel Prop voulez-vous faire l'échange ?");
		do {
			if(i > 0) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println(players[(p.getId()+1)%3].getName() 
					+ " :\nEntrez 0 pour le Prop : " 
					+ players[(p.getId()+1)%3].getHand(0).toStringIfVisible() 
					+ "\nou 1 pour le Prop : " 
					+ players[(p.getId()+1)%3].getHand(1).toStringIfVisible());
			
			System.out.println(players[(p.getId()+2)%3].getName() 
					+ " :\nEntrez 2 pour le Prop : " 
					+ players[(p.getId()+2)%3].getHand(0).toStringIfVisible() 
					+ "\nou 3 pour le Prop : " 
					+ players[(p.getId()+2)%3].getHand(1).toStringIfVisible());
			
			System.out.println("Entrez 4 pour échanger avec le Prop du milieu : ");
			
			ans = this.readInt();
			i++;
		}while(ans > 4 || ans < 0);
		
		this.gc.setMiddleVarCarrot(p, ans);
	}

	private void askChooseMiddle(Player p) {
		int i = 0, ans = -1;
		
		System.out.println("Quel Prop voulez-vous replacer au milieu");
		do {
			if(i == 1) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : " 
					+ p.getHand(0).getName() 
					+ ", 1 pour le Prop : " 
					+ p.getHand(1).getName() 
					+ ", ou 2 pour garder les mêmes Props : ");
			ans = this.readInt();
			i++;
		}while(ans !=0 && ans != 1 && ans != 2);
		this.gc.setMiddleProp(p, ans);
	}
	
	private void askRevealProp(Player p) {
		int i = 0, ans = -1;
		
		System.out.println("Lequel de vos Props souhaitez-vous retourner ?");
		do {
			if(i > 0) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez 0 pour le Prop : " 
					+ p.getHand(0).getName() 
					+ ", ou 1 pour le Prop : " 
					+ p.getHand(1).getName());
			ans = this.readInt();
			i++;
		}while(ans != 0 && ans != 1);
		this.gc.setRevealProp(p, ans);
	}
	
	private void askPerformTrick(Player p) {
		
		int i = 0;
		String ans;
		
		System.out.println("Voulez-vous tenter de réalsier le Trick ?");
		do {
			if(i > 0) {
				System.out.println("\nSaisie incorrecte. Veuillez respecter les consignes : ");
			}
			System.out.println("Entrez y pour oui et n pour non : ");
			ans = this.readString();	
			ans.toLowerCase();
			i++;
		}while(ans.equals("y") && ans.equals("n"));
		
		this.gc.setPerformTrick(p, (ans.equals("y") ? true : false));
	}
	
	
	private int readInt() {
		int value = -1;
		System.out.print(VueTexte.PROMPT);
		value = Integer.parseInt(this.readString());
		return value;
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
