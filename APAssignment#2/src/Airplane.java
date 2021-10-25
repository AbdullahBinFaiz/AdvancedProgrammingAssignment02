public class Airplane {
	class Seats {
		int seatID;
		boolean isBooked;
		boolean isPurchased;
		
		Seats() {
			seatID = 0;
			isBooked = false;
			isPurchased = false;
		}
		
		// Setters
		void setSeatID(int id) {
			seatID = id;
		}
		void setBookStatus(boolean status) {
			isBooked = status;
		}
		void setPurchaseStatus(boolean status) {
			isPurchased = status;
		}
		
		// Getters
		boolean getPurchaseStatus() {
			return isPurchased;
		}
		boolean getBookedStatus() {
			return isBooked;
		}
		int getSeatID() {
			return seatID;
		}
	}
	
	private String airplaneName;
	private int airplaneClass; //  0 for "Business", 1 for "Economy", 2 for "First"
	private Seats[] seats;
	private int noOfSeats;
	
	Airplane(String name, int type, int seatCount) {
		airplaneName = name;
		airplaneClass = type;
		noOfSeats = seatCount;
		seats = new Seats[noOfSeats];
		for (int i = 0; i < noOfSeats; i++) {
			seats[i] = new Seats();
			seats[i].setSeatID(i+1);
		}
	}
	Airplane(String name, int type) {
		airplaneName = name;
		airplaneClass = type;
		if (type == 0)
			noOfSeats = 200;
		else if (type == 2)
			noOfSeats = 100;
		else
			noOfSeats = 300;
		seats = new Seats[noOfSeats];
		for (int i = 0; i < noOfSeats; i++) {
			seats[i] = new Seats();
			seats[i].setSeatID(i+1);
		}
	}
	
	// Setters
	public void setAirplaneName(String name) {
		airplaneName = name;
	}
	public void setAirplaneClass(int type) {
		airplaneClass = type;
	}
	
	// Getters
	public String getAirplaneName() {
		return airplaneName;
	}
	public String getAirplaneClass() {
		if (airplaneClass == 0) {
			return "Business class";
		} else if (airplaneClass == 2) {
			return "First class";
		}
		return "Economy class";
	}
	public int getAirplaneClassNum() {
		return airplaneClass;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public int getNoOfAvailableSeats() {
		int count = 0;
		for (int i = 0; i < noOfSeats; i++) {
			if (seats[i].getBookedStatus() == false && seats[i].getPurchaseStatus() == false) {
				count++;
			}
		}
		return count;
	}
	
	// Specified Methods
	public boolean checkAvailabilityOfSeats() {
		for (int i = 0; i < noOfSeats; i++) {
			if (seats[i].getPurchaseStatus() == false || seats[i].getBookedStatus() == false) {
				return true;
			}
		}
		return false;
	}
	public boolean checkSpecifiedSeat(int seatID) {
		if (seats[seatID-1].getPurchaseStatus() == false || seats[seatID-1].getBookedStatus() == false) {
			return true;
		}
		return false;
	}
	public void displayAvailableSeats() {
		for (int i = 0; i < noOfSeats; i++) {
			if (seats[i].getBookedStatus() == false && seats[i].getPurchaseStatus() == false) {
				System.out.print(seats[i].getSeatID() + ", ");
			}
		}
		System.out.println();
	}
	public void bookSeat(int seatNo) {
		try {
			seats[seatNo-1].setBookStatus(true);
			seats[seatNo-1].setPurchaseStatus(false); // fail-safe measure
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	public void cancelSeat(int seatNo) {
		try {
			seats[seatNo-1].setBookStatus(false);
			seats[seatNo-1].setPurchaseStatus(false); // fail-safe measure
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	public void purchaseSeat(int seatNo) {
		try {
			seats[seatNo-1].setBookStatus(true);
			seats[seatNo-1].setPurchaseStatus(false); // fail-safe measure
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
