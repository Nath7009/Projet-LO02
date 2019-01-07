package theotherhattrick;

import java.io.Serializable;

public class GameVarSwissKnife extends Game implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3306308634642419092L;

	public GameVarSwissKnife() {
		super();
	}
	
	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un trick
		boolean trickSuccessful = depiledTrick.compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p); // On montre ses cartes
			p.printProps();
			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p); // On cache ses cartes
			
			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p); // On lui donne le trick
			
			System.out.println("Vous pouvez échanger l'une de vos cartes avec l'une des cartes du milieu");

			super.exchangeMiddle(p);
			p.printProps();

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			this.revealProp(p);
		}
	}
	
}
