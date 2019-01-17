package theotherhattrick;

import java.io.Serializable;

/**
 * Cette classe permet de modéliser les date de naissances des joueurs.
 * On peut générer des dates de façon aléatoire ou à partir de valeurs données.
 * On peut comparer deux objets Date pour savoir si l'objet Date courant est plus petit ou pas que l'objet Datepassé en paramètre
 * @author amall
 *
 */
public class Date implements Serializable{
	
	private static final long serialVersionUID = 829283417861837956L;
		private int year;
		private int month;
		private int day;
		
		/**
		 * Constructeur qui créé une date à partir des paramètres d'entrée.
		 * @param year
		 * @param month
		 * @param day
		 */
		public Date(int year, int month, int day) {
			this.day = day; 
			this.month = month;
			this.year = year;
		}
		/**
		 * Constructeur qui créé une date aléatoirement.
		 * @param year
		 * @param month
		 * @param day
		 */
		public Date() {
			this.day =  (int)Math.floor(Math.random()*31)+1;
			this.month =  (int)Math.floor(Math.random()*31)+1;
			this.year =  (int)Math.floor(Math.random()*100) + 1910;

		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}
		
		/**
		 * @param date On va comparer l'objet courant à date.
		 * @return true si l'objet courant est plus petit que date, faux sinon.
		 */
		public boolean isUnder(Date date) {
			boolean isUnder = true;
			if(this.year > date.getYear()) {
				isUnder = false;
			}
			else if(this.year == date.getYear()) {
				if(this.month > date.getMonth()) {
					isUnder = false;
				}
				else if(this.month == date.getMonth()) {
					if(this.day > date.getDay()) {
						isUnder = false;
					}
				}
			}
			return isUnder;
		}
		
		

	}