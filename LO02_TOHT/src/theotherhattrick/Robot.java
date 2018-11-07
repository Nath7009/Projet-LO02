package theotherhattrick;

import java.util.Scanner;

public class Robot extends Player {

	public Robot(int id) {
		super("Robot", id);
	}
	
	public int speak(String text, int choiceNumb, Player[] players, char typeOfQuestion, Scanner keyboard) { 
		int choice = (int) Math.floor(Math.random()*choiceNumb);
		return choice;
	}
}
