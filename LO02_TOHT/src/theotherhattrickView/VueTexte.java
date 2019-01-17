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
/**
 * 
 * @author amall
 *
 */
public class VueTexte implements Observer{
	
	private GameControler gc;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static String PROMPT = ">";

	public VueTexte(GameControler gc) {
		this.gc = gc;
		this.initGame();
	}
	
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
		this.gc.getGame().initGame();
		this.gc.getGame().start();
		
	}
	
		@Override
	public void update(Observable o, Object arg1) {

		if(o instanceof Game) {
			Game g = (Game) o;
			switch((String)arg1) {
			
				case "nbOfPlayers" : 

					System.out.println("Entrez le nombre de joueurs humains voulant participer au jeu : ");
					int nbOfHuman = Integer.parseInt(this.readString());
					this.gc.setNbOfHuman(nbOfHuman);
					break;
					
				case "identityOfPLayer" : 
					
					this.askIdentity();
					break;
					
				case "new players" :
					for(int i = 0; i < 3 ; i++) {
						g.getPlayers()[i].addObserver(this);
					}
					break;
					
				case "new prop" : 
//					System.out.println("Nouveau Prop créé : " + this.gc.getGame().getAllProps().peek());
					this.gc.getGame().getAllProps().peek().addObserver(this);
					break;
					
				case "new trick" :
//					System.out.println("Nouveau Prop créé : " + this.gc.getGame().getTricks().peek());
					this.gc.getGame().getTricks().peek().addObserver(this);
					break;
					
				case "depile" :
					System.out.println("Le nouveau Trick à réaliser est : " + ((Game) o).getTopTrick());
					break;
					
				case "trick pile empty" : 
					System.out.println("Trick Pile empty");
					break;
				
				case "Trick realized" : 
					System.out.println("Vous avez réussi le tour !");
					break;
					
				case "new turn" :
					System.out.println("================================================================================");
					System.out.println("C'est le tour de " + g.getCurrentPlayer().getName());
					break;
					
				case "last turn" : 
					System.out.println("On joue le dernier tour");
					try {
						this.gc.getGame().getDepiledTrick().print();
					} catch(NullPointerException e) {
						e.printStackTrace();
					}
					
					break;
					
				
			}
		}
		
		if(o instanceof Player) {
			
			if(o instanceof Human) {
				Human p = (Human) o;
				switch((String)arg1){
				
					
					case "name" : 
						System.out.println(p.getName());
						break;
						
					case "print hand" : 
						System.out.println("Votre main est : \n");
						break;
						
					case "player0" : 
						System.out.println("Votre jeux est : \n");
						break;
						
					case "player1" : 
						System.out.println("Votre jeux est : \n");
						break;
						
					case "player2" : 
						System.out.println("Votre jeux est : \n");
						break;
				
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
						
					case "increase score" : 
						System.out.println("Vous gagnez > < points. Vous avez désormais >" + p.getScore() + "< points." );
						break;
						
					case "reveal prop" : 
						System.out.println("Vous avez échoué le tour");
						System.out.println("Votre main, " + p.getName() + " : \n"); // On affiche la main du joueur et on regarde quels props sont cachÃ©s
						System.out.println(p.getHand());
						break;
						
					case "other hat trick realized" :
						System.out.println("Le joueur " + p.getName() + " a réussi The Other Hat Trick, il gagne 6 points");
						break;
						
					case "possess other rabbit" : 
						System.out.println("Le joueur " + p.getName() + "  possède The Other Rabbit, il perd 3 points");
						break;
						
					case "possess hat" : 
						System.out.println("Le joueur " + p.getName() + "  possède The Hat, il perd 3 points");
						break;
						
						
				}
			}
			
			if(o instanceof Robot) {
				Robot p = (Robot) o;
				switch((String) arg1) {
				
					case "name" : 
						System.out.println(p.getName());
						break;
						
					case "print hand" : 
						System.out.println("Votre main est : \n");
						break;
						
					case "player0" : 
						System.out.println("Votre jeux est : \n");
						break;
						
					case "player1" : 
						System.out.println("Votre jeux est : \n");
						break;
						
					case "player2" : 
						System.out.println("Votre jeux est : \n");
						break;
						
					case "strat risky" : 
						System.out.println("Le robot " + p.getName() + " est passé à la stratégie risquée");
						break;
						
					case "strat conservative" :
						System.out.println("Le robot " + p.getName() + " est passé à la stratégie conservative");
						break;
						
					case "basic strat" : 
						System.out.println("Le robot " + p.getName() + " est passé à la stratégie de base");
						break;
						
					case "gone crazy" : 
						System.out.println("Le robot devient fou et choisit une stratégie au hasard");
						break;
						
					case "reveal new trick" :
						System.out.println("Le robot a choisi de retourner un nouveau trick");
						break;
						
					case "don't reveal new trick" : 
						System.out.println("Le robot a refusé de retourner un nouveau trick");
						break;
						
					case "prop chosen" :
						System.out.println("Le robot a choisi de retourner son prop numéro " + p.getChoice());
						break;
						
					case "other prop chosen" :
						System.out.println("Le robot a choisi de retourner le prop numéro " + p.getChoice());
						break;
						
					case "perform trick" : 
						System.out.println("Le robot a choisi de réaliser le trick");
						break;
						
					case "don't perform trick" : 
						System.out.println("Le robot a refusé de réaliser le trick");
						break;
						
					case "reveal prop" :
						System.out.println("Le robot a choisi de retourner son prop numéro " + p.getChoice());
						break;
						
					case "choose middle" : 
						System.out.println("Le robot a choisi le prop numéro " + p.getChoice());
						break;
						
					case "choose middle var" : 
						System.out.println("Le robot a choisi le prop numéro " + p.getChoice());
						break;
				}
				
			}
			
		}
		
		if(o instanceof Trick) {
			
			Trick trick =  (Trick) o;
			switch((String) arg1) {
			
				case "print trick" : 
					System.out.println("<" + trick.getName() + "> :");
					for (int i = 0; i < trick.getIngredients().length; i++) {
						for (int j = 0; j < trick.getLength(i); j++) {
							System.out.println("[" + trick.getIngredient(i,j).getName() + "]");
						}
						System.out.println();
					}
					break;
					
				case "value decreased" : 
					System.out.println("! ! Vous avez réalisé le tour <" + trick.getName() + "> avec le couteau suisse magique ! !\n");
					break;
			}
		}
		
		if(o instanceof Prop) {
			Prop prop = (Prop) o;
			switch((String) arg1) {
			
				case "hide" : 
					break; 
					
				case "unhide" : 
					break;
					
				case "print" : 
					System.out.println(prop);
					break;
					
				case "print visible" : 
					System.out.println(prop.getState() == true ? prop.toString() : "[?????]");
					break;
					
				case "print debug" : 
					break;
			}
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
		
		System.out.println("Vous pouvez échanger une de vos cartes avec celle du milieu : \n");
		System.out.println("Quel Prop voulez-vous replacer au milieu : ");
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
