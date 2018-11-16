package theotherhattrick;

public class GameVarLettuce extends Game {
	public GameVarLettuce() {
		super();
	}

	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un trick
		boolean trickSuccessful = board.comparePropsToTrick(p.getId());

		if (trickSuccessful) { // Si le joueur a rï¿½ussi le trick
			System.out.println("Vous avez rÃ©ussi le tour");
			board.showAllProps(p.getId()); // On montre ses cartes

			// TODO Ajouter un dï¿½lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			board.hideAllProps(p.getId()); // On cache ses cartes
			

			// DONNER LE TRICK AU JOUEUR
			board.giveTrick(p.getId()); // On lui donne le trick
			
			System.out.println("Vous pouvez Ã©changer l'une de vos cartes avec la carte du milieu");

			super.exchangeMiddle(p);
		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez Ã©chouÃ© le tour");
			if (board.contains("Lettuce")) {
				System.out.println("Etant donné que le tour échoué contenait une laitue, vous pouvez retourner une carte");
				board.returnProp(p.getId());
			} else {
				board.revealProp(p.getId());
			}
		}

	}
}
