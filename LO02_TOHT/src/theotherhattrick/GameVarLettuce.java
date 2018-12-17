package theotherhattrick;

public class GameVarLettuce extends Game {
	public GameVarLettuce() {
		super();
	}

	protected void realizeTrick(Player p) {
		// G�re l'enchainement des actions qui se r�alisent quand on r�alise un trick
		boolean trickSuccessful = depiledTricks.peek().compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p); // On montre ses cartes

			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p); // On cache ses cartes
			

			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p); // On lui donne le trick
			
			System.out.println("Vous pouvez échanger l'une de vos cartes avec la carte du milieu");

			super.exchangeMiddle(p);
		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");
			if (depiledTricks.peek().contains(new Prop(PropEnum.LETTUCE))) {
				System.out.println("Etant donné que le tour échoué contenait une laitue, vous pouvez retourner une carte");
				this.returnProp(p);
			} else {
				this.revealProp(p);
			}
		}

	}
}
