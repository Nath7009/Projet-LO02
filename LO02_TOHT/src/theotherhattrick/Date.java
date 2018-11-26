package theotherhattrick;

	public class Date {
		private int year;
		private int month;
		private int day;
		
		public Date(int year, int month, int day) {
			this.day = day; 
			this.month = month;
			this.year = year;
		}
		
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
		
		public boolean isUnder(Date date) {
			boolean isUnder = true; // true si la date courante est plus petite que le ou égale au paramètre.
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
					else {
						System.out.println("Les 2 joueurs sont nés le même jour, on ne change pas l'ordre\n");
					}
				}
			}
			return isUnder;
		}
		
		

	}