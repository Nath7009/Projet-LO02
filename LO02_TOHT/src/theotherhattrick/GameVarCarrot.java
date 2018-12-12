package theotherhattrick;

public class GameVarCarrot extends Game {
	public GameVarCarrot() {
		super();
	}

	protected void realizeTrick(Player p) {
		// Gère l'enchainement des actions qui se réalisent quand on réalise un trick
		boolean trickSuccessful = this.depiledTricks.peek().compareToProps(p.getHand());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			this.showAllProps(p.getId()); // On montre ses cartes

			// TODO Ajouter un délai afin que le joueur montre ses cartes pendant plus
			// longtemps

			this.hideAllProps(p.getId()); // On cache ses cartes
			
			
			
			System.out.print("Vous pouvez échanger l'une de vos cartes avec");

			if (this.depiledTricks.peek().contains(new Prop(PropEnum.CARROT))) {
				System.out.println(" la carte du milieu ou une carte de vos adversaires : ");
				// ECHANGE AVEC TOUT LE MONDE ET LA CARTE DU MILIEU
				exchangePlayers(p);
			} else { // si on a pas la carte carrot
				System.out.println(" la carte du milieu :");
				super.exchangeMiddle(p);
				// ECHANGLE MILIEU STANDART
			}
			// DONNER LE TRICK AU JOUEUR
			this.giveTrick(p.getId()); // On lui donne le trick
		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			this.revealProp(p.getId());
		}
	}

	protected void exchangePlayers(Player p) { // échange une carte avec un autre joueur ou la carte du milieu
		int propToChange, otherProp;
		propToChange = p.speak("Lequel de vos props voulez vous échanger ?", 2, this.players, 'p');
		otherProp = p.speak("Avec quelle carte du milieu ou de vos adversaire voulez vous l'échanger ?", 5, players, 'v');
		// 0,1 pour le joueur de gauche
		// 2,3 pour le joueur de droite
		// -1 pour le prop du milieu
		if (otherProp >= 0 && otherProp < 2) { // échange avec le joueur d'après
			int p2 = (p.getId() + 1)%3; // Le joueur avec lequel on veut échanger le prop
			System.out.println(p2 + "\t" + p.getId());
			this.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		}else if(otherProp >= 2 && otherProp <= 3) { //échange avec le joueur d'avant
			int p2 = (p.getId() + 2) % 3; // Le joueur avec lequel on veut échanger le prop
			System.out.println(p2 + "\t" + p.getId());
			this.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		}
		else {
			System.out.println(otherProp);
			this.exchangeProps(p.getId(), propToChange, -1, otherProp + 1);
		}
	}
}
