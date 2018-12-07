package theotherhattrick;

public class GameVarCarrot extends Game {
	public GameVarCarrot() {
		super();
	}

	protected void realizeTrick(Player p) {
		// G�re l'enchainement des actions qui se r�alisent quand on r�alise un trick
		boolean trickSuccessful = board.comparePropsToTrick(p.getId());

		if (trickSuccessful) { // Si le joueur a r�ussi le trick
			System.out.println("Vous avez réussi le tour");
			board.showAllProps(p.getId()); // On montre ses cartes

			// TODO Ajouter un d�lai afin que le joueur montre ses cartes pendant plus
			// longtemps

			board.hideAllProps(p.getId()); // On cache ses cartes
			
			// DONNER LE TRICK AU JOUEUR
			board.giveTrick(p.getId()); // On lui donne le trick
			
			System.out.print("Vous pouvez échanger l'une de vos cartes avec");

			if (this.board.contains("Carrot")) {
				System.out.println("la carte du milieu ou une carte de vos adversaires");
				// ECHANGE AVEC TOUT LE MONDE ET LA CARTE DU MILIEU
				exchangePlayers(p);
			} else { // si on a pas la carte carrot
				System.out.println("la carte du milieu");
				super.exchangeMiddle(p);
				// ECHANGLE MILIEU STANDART
			}
		}

		else {
			// Si le joueur rate le trick
			System.out.println("Vous avez échoué le tour");

			board.revealProp(p.getId());
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
			this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		}else if(otherProp >= 2 && otherProp <= 3) { //échange avec le joueur d'avant
			int p2 = (p.getId() + 2) % 3; // Le joueur avec lequel on veut échanger le prop
			System.out.println(p2 + "\t" + p.getId());
			this.board.exchangeProps(p.getId(), propToChange, p2, otherProp % 2);
		}
		else {
			System.out.println(otherProp);
			this.board.exchangeProps(p.getId(), propToChange, -1, otherProp + 1);
		}
	}
}
