package theotherhattrick;

public class GameVarSwissKnife extends Game{
	public GameVarSwissKnife() {
		super();
	}
	
	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un trick
		boolean trickSuccessful = this.depiledTricks.peek().compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p.getId()); // On montre ses cartes
			p.printProps();
			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p.getId()); // On cache ses cartes
			
			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p.getId()); // On lui donne le trick
			
			System.out.println("Vous pouvez échanger l'une de vos cartes avec l'une des cartes du milieu");

			super.exchangeMiddle(p);
			p.printProps();

		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			this.revealProp(p.getId());
		}
	}
	
}
