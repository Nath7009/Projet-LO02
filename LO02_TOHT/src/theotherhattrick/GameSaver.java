package theotherhattrick;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class GameSaver implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void save(Game game) {
		// Stocke l'objet dans un fichier
		File file = new File("save.ser");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(game);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
