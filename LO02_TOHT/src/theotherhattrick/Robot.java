package theotherhattrick;

import java.util.Scanner;

public class Robot extends Player {

	public Robot(int id) {
		super("Robot" + id, id);
	}
	
	public Robot(int id, Date birthDate) {
		super("Robot" + id, id, birthDate);
	}
	
	public int speak(String text, int choiceNumb, Player[] players, char typeOfQuestion) {
		int choice = (int) Math.floor(Math.random()*choiceNumb);
		if(typeOfQuestion == 'v') {
			choice --;
		}
		return choice;
	}
}
