package theotherhattrick;

public class GameVarSwissKnife extends Game{
	public GameVarSwissKnife() {
		super();
	}
	
	protected void realizeTrick(Player p) {
		// G�re l'enchainement des actions qui se r�alisent quand on r�alise un trick
		boolean trickSuccessful = board.comparePropsToTrick(p.getId());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			board.showAllProps(p.getId()); // On montre ses cartes
			p.printProps();
			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			board.hideAllProps(p.getId()); // On cache ses cartes
			
			// DONNER LE TRICK AU JOUEUR
			board.giveTrick(p.getId()); // On lui donne le trick
			
			System.out.println("Vous pouvez échanger l'une de vos cartes avec l'une des cartes du milieu");

			super.exchangeMiddle(p);
			p.printProps();

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			board.revealProp(p.getId());
		}
	}
	
}
