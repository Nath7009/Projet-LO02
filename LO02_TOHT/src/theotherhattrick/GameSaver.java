package theotherhattrick;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Permet de sauvegarder le jeu
 *
 */
public abstract class GameSaver implements Serializable{

	private static final long serialVersionUID = 1L;

	/** Sauvegarde le jeu dans un fichier
	 * @param game l'instance du jeu que l'on veut sauvegarder
	 */
	public static void save(Game game) {
		// Stocke l'objet dans un fichier
		File file = new File("save.ser");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(game);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de charger un jeu à partir du fichier
	 * @return l'instance du jeu qui a été sauvegardée
	 */
	public static Game load() {
		try {
			File file = new File("save.ser");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			// désérialization de l'objet
			Game game = (Game) ois.readObject();
			ois.close();
			return game;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}
}
