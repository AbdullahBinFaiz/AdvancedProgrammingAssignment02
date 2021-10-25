import java.io.File;
import java.io.IOException;

public class FlightReservator {
	private FlightSchedule calendar;
	private AirplaneList listOfAirplanes;
	private AirportList listOfAirports;
	private CustomerList listOfCustomers;
	private TicketList listOfTickets;
	private Admin admin;
	private boolean inUse; // for use when someone logs in (either customer or admin)
	private boolean isAdminUsingSystem; // another fail-safe boolean; FALSE denotes a customer is using the system, while TRUE denotes that an admin is using the system
	
	FlightReservator() {
		calendar = new FlightSchedule();
		listOfAirplanes = new AirplaneList();
		listOfAirports = new AirportList();
		listOfCustomers = new CustomerList();
		listOfTickets = new TicketList();
		admin = new Admin();
		inUse = false;
		isAdminUsingSystem = false;
	}
	
	// Set and get the two states ("inUse" here means that whether someone is logged in or not)
	public void setInUseState(boolean state) {
		inUse = state;
	}
	public void setAdminState(boolean state) {
		isAdminUsingSystem = state;
	}
	public boolean getInUseState() {
		return inUse;
	}
	public boolean getAdminLoginState() {
		return isAdminUsingSystem;
	}
	
	public void initialize() {
		try {
			calendar.initializeFlights();
			calendar.initalizeSeatAvailability();
			listOfAirports.initializeAirports();
			listOfAirplanes.initializeAirplanes();
			listOfCustomers.initializeCustomers();
			listOfTickets.initializeTickets(calendar);
			deleteFiles();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	// For clarification: when the program starts, it deletes the files so no necessary reading is done in-between the program running (and therefore an idea of altering files). When the program logs out, it saves all of the information again.
	public void deleteFiles() throws IOException {
		String dir = System.getProperty("user.dir");
		dir += "\\files";
		System.out.println(dir);
		File airplanes = new File(dir + "\\airplanes.txt");
		File airports = new File(dir + "\\airports.txt");
		File bookings = new File(dir + "\\bookings.txt");
		File customers = new File(dir + "\\customers.txt");
		File flights = new File(dir + "\\flights.txt");
		File purchases = new File(dir + "\\purchases.txt");
		File tickets = new File(dir + "\\tickets.txt");
		airplanes.delete();
		airports.delete();
		bookings.delete();
		customers.delete();
		flights.delete();
		purchases.delete();
		tickets.delete();
	}
	public void save() {
		try {
			calendar.saveFlights();
			listOfAirplanes.saveAirplanes();
			listOfAirports.saveAirports();
			listOfCustomers.saveCustomers();
			listOfCustomers.saveBookings();
			listOfCustomers.savePurchases();
			listOfTickets.saveTickets();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void display() {
		calendar.getFlightsSchedule();
		listOfAirports.displayAirports();
		listOfAirplanes.displayAirplanes();
		listOfCustomers.displayAllCustomers(listOfTickets);
	}
	public boolean CustomerLogin(String username, String password) {
		if (listOfCustomers.verifyCustomerLogin(username, password)) {
			inUse = true;
			isAdminUsingSystem = false;
			return true;
		}
		return false;
	}
	public boolean AdminLogin(String username, String password) {
		if (username.equals(admin.getUsername())) {
			if (password.equals(admin.getPassword())) {
				inUse = true;
				isAdminUsingSystem = true;
				return true;
			}
		}
		return false;
	}
	public void displayCustomerProfile(String username) {
		listOfCustomers.displaySpecifiedUser(username, listOfTickets);
	}
	public int getTicketID(String username) {
		int tID = listOfCustomers.getSpecifiedUserTicket(username);
		return tID;
	}

	public void changeUserName(String username, String name) {
		listOfCustomers.setSpecifiedUserName(username, name);
	}
	public void changeUserAge(String username, int age) {
		listOfCustomers.setSpecifiedUserAge(username, age);
	}
	public void changeUserGender(String username, short gender) {
		listOfCustomers.setSpecifiedUserGender(username, gender);
	}
	public void changeUserMobileNo(String username, String mobileNo) {
		if (mobileNo.charAt(0) == '+') {
			int dashesCount = 0;
			for (int i = 0; i < mobileNo.length(); i++) {
				if (mobileNo.charAt(i) == '-') {
					dashesCount++;
				} else if (!(mobileNo.charAt(i) >= '0' && mobileNo.charAt(i) <= '9')) {
					throw new MobilePhoneVerificationException("Mobile does not contain only numbers.");
				}
				if (dashesCount > 2) {
					throw new MobilePhoneVerificationException("Mobile format not followed.");
				}
			}
			listOfCustomers.setSpecifiedUserMobilePhone(username, mobileNo);
		}
		throw new MobilePhoneVerificationException("Mobile format not followed.");
	}
	public void searchFunction(String date, boolean directRouteFlag, boolean lowestFareFlag, boolean noOfAvailableSeatsFlag) {
		if (date.charAt(2) == '/' && date.charAt(5) == '/') {
			for (int i = 0; i < date.length() || i < 8; i++) {
				if (!(date.charAt(i) >= '0' || date.charAt(i) <= '9') && !(date.charAt(i) == '/')) {
					throw new SearchFlightException("Date does not contain only numbers.");
				}
			}
		} else {
			throw new SearchFlightException("Date format not valid.");
		}
		System.out.println();
		Flight[] array = calendar.getFlights();
		int noOfFlights = calendar.getNoOfFlights();
		if (lowestFareFlag && !directRouteFlag) {
			Flight minFareFlight = array[0];
			int minFare = array[0].getPrice();
			for (int i = 0; i < noOfFlights; i++) {
				if (array[i].getDepartureDate().equals(date)) {
					if (minFare > array[i].getPrice()) {
						minFare = array[i].getPrice();
						minFareFlight = array[i];
					}
				}
			}
			minFareFlight.getFlightDetails();
			if (noOfAvailableSeatsFlag) {
				minFareFlight.displayAvailableSeats();
			}
		} else if (directRouteFlag && !lowestFareFlag) {
			for (int i = 0; i < noOfFlights; i++) {
				if (array[i].getDepartureDate().equals(date)) {
					if (array[i].getFlightDirectStatus()) {
						array[i].getFlightDetails();
						if (noOfAvailableSeatsFlag) {
							array[i].displayAvailableSeats();
						}
					}
				}
			}
		} else if (directRouteFlag && lowestFareFlag) {
			Flight minFareFlight = array[0];
			int minFare = array[0].getPrice();
			for (int i = 0; i < noOfFlights; i++) {
				if (array[i].getDepartureDate().equals(date)) {
					if (minFare > array[i].getPrice()) {
						if (array[i].getFlightDirectStatus()) {
							minFare = array[i].getPrice();
							minFareFlight = array[i];
						}
					}
				}
			}
			minFareFlight.getFlightDetails();
			if (noOfAvailableSeatsFlag) {
				minFareFlight.displayAvailableSeats();
			}
		} else if (!directRouteFlag && !lowestFareFlag) {
			for (int i = 0; i < noOfFlights; i++) {
				if (array[i].getDepartureDate().equals(date)) {
					array[i].getFlightDetails();
					if (noOfAvailableSeatsFlag) {
						System.out.println("Seats available:");
						array[i].displayAvailableSeats();
					}
				}
			}
		}
	}
	public boolean isFlightAvailable(int flightID) {
		return calendar.isFlightAvailable(flightID);
	}
	public void displaySeats(int flightID) {
		Flight[] array = calendar.getFlights();
		int noOfFlights = calendar.getNoOfFlights();
		for (int i = 0; i < noOfFlights; i++) {
			if (array[i].getFlightID() == flightID) {
				array[i].displayAvailableSeats();
				return;
			}
		}
	}
	public boolean checkSeatAvailability(int flightID, int seatNo) {
		Flight[] array = calendar.getFlights();
		int noOfFlights = calendar.getNoOfFlights();
		for (int i = 0; i < noOfFlights; i++) {
			if (array[i].getFlightID() == flightID) {
				return (array[i].getSeats()).checkSpecifiedSeat(seatNo);
			}
		}
		return false;
	}
	public void bookSeat(int flightID, int seatNo, String username) {
		calendar.bookSeat(flightID, seatNo);
		listOfCustomers.setBook(username, flightID, seatNo);
	}
	public boolean verifyCreditCard(String crdNo) {
		if (crdNo.charAt(4) == '-' && crdNo.charAt(9) == '-' && crdNo.charAt(14) == '-') {
			for (int i = 0; i < crdNo.length(); i++) {
				if (!(crdNo.charAt(i) >= '0' && crdNo.charAt(i) <= '9') && !(crdNo.charAt(i) == '-')) {
					throw new CardVerificationException("No number found at desired index");
				}
			}
			return true;
		}
		throw new CardVerificationException("Card format not identified");
	}
	public void purchaseSeat(int flightID, int seatNo) {
		calendar.purchaseSeat(flightID, seatNo);
	}
	public void printTicket(int ticketID) {
		listOfTickets.getSpecifiedTicketDetails(ticketID, listOfAirports);
	}
	public boolean getBookingStatus(String username) {
		return listOfCustomers.getBookingStatus(username);
	}
	public int getSeat(String username) {
		return listOfCustomers.getSeatNo(username);
	}
	public int getFlight(String username) {
		return listOfCustomers.getFlightID(username);
	}
	public void cancelBooking(String username, int flightID, int seatNo) {
		listOfCustomers.cancelBooking(username);
		calendar.cancelBooking(flightID, seatNo);
	}
	public void cancelPurchase(String username, int flightID, int seatNo, int ticketID) {
		listOfCustomers.cancelPurchase(username);
		calendar.cancelPurchase(flightID, seatNo);
		listOfTickets.deleteTicket(ticketID);
	}
	
	// Verification methods
	public void verifyMobileNumber(String mobileNo) {
		if (mobileNo.charAt(0) == '+') {
			int dashesCount = 0;
			for (int i = 1; i < mobileNo.length(); i++) {
				if (mobileNo.charAt(i) == '-') {
					dashesCount++;
				} else if ((mobileNo.charAt(i) != '0') && (mobileNo.charAt(i) != '1') && (mobileNo.charAt(i) != '2') && (mobileNo.charAt(i) != '3') && (mobileNo.charAt(i) != '4') && (mobileNo.charAt(i) != '5') && (mobileNo.charAt(i) != '6') && (mobileNo.charAt(i) != '7') && (mobileNo.charAt(i) != '8') && (mobileNo.charAt(i) != '9')) {
					throw new MobilePhoneVerificationException("Mobile does not contain only numbers.");
				}
				if (dashesCount > 2) {
					throw new MobilePhoneVerificationException("Mobile format not followed.");
				}
			}
			return;
		}
		throw new MobilePhoneVerificationException("Mobile format not followed.");
	}
	public void verifyPassportID(String passportID) {
		if (passportID.charAt(0) >= 'A' && passportID.charAt(0) <= 'Z') {
			for (int i = 1; i < passportID.length(); i++) {
				if (!(passportID.charAt(i) >= '0' && passportID.charAt(i) <= '9')) {
					throw new InvalidPassportIDException("Passport does not contain after intial letter.");
				}
			}
			return;
		}
		throw new InvalidPassportIDException("Passport's initial index does not have a capital letter.");
	}
	public void verifyDate(String date) {
		if (date.charAt(2) == '/' && date.charAt(5) == '/') {
			for (int i = 0; i < date.length(); i++) {
				if (!(date.charAt(i) >= '0' && date.charAt(i) <= '9') && date.charAt(i) != '/') {
					throw new InvalidDateException("Date contains random characters.");
				}
			}
			return;
		}
		throw new InvalidDateException("Date format is not valid.");
	}
	public void verifyTime(String time) {
		if (time.charAt(2) == ':') {
			for (int i = 0; i < time.length(); i++) {
				if (!(time.charAt(i) >= '0' && time.charAt(i) <= '9') && time.charAt(i) != ':') {
					throw new InvalidTimeException("Time contains random characters.");
				}
			}
			return;
		}
		throw new InvalidTimeException("Time format is not valid.");
	}
	public void verifyAirportCode(String code) {
		for (int i = 0; i < 2; i++) {
			if (!(code.charAt(i) >= 'A' && code.charAt(i) <= 'Z')) {
				throw new InvalidAirportCodeException("Airport code contains random characters.");
			}
		}
		for (int i = 2; i < 5; i++) {
			if (!(code.charAt(i) >= '0' && code.charAt(i) <= '9')) {
				throw new InvalidAirportCodeException("Airport code contains random characters.");
			}
		}
		if (code.length() != 5) {
			throw new InvalidAirportCodeException("Format is not valid.");
		}
	}
	
	public boolean checkAirplane(String airplaneName) {
		return listOfAirplanes.checkAirplane(airplaneName);
	}
	public boolean checkFlightID(int fID) {
		return calendar.isFlightAvailable(fID);
	}

	public void createNewCustomer(String name, short gender, int age, String mobileNo, String passportID, String newUser, String newPass) {
		try {
			verifyMobileNumber(mobileNo);
		}
		catch (MobilePhoneVerificationException e) {
			return;
		}
		try {
			verifyPassportID(passportID);
		}
		catch (InvalidPassportIDException e) {
			return;
		}
		listOfCustomers.addNewCustomer(name, gender, age, mobileNo, passportID, newUser, newPass);
	}
	public void createNewAirplane(String name, int aClass, int seatCount) {
		listOfAirplanes.addNewAirplane(name, aClass, seatCount);
	}
	public void createNewFlight(String srcCode, String destCode, String srcDate, String srcTime, String destDate, String destTime, String airplaneName, int price, int noOfStops, String[] stops) {
		try {
			verifyAirportCode(srcCode);
			verifyAirportCode(destCode);
		}
		catch (InvalidAirportCodeException e) {
			return;
		}
		try {
			verifyDate(srcDate);
			verifyDate(destDate);
		}
		catch (InvalidDateException e) {
			return;
		}
		try {
			verifyTime(srcTime);
			verifyTime(destTime);
		}
		catch (InvalidTimeException e) {
			return;
		}
		if (checkAirplane(airplaneName)) {
			if (noOfStops > 0) {
				for (int i = 0; i < noOfStops; i++) {
					try {
						verifyAirportCode(stops[i]);
					}
					catch (InvalidAirportCodeException e) {
						return;
					}
				}
			}
			// If all verifications pass above, then continue on, but not before generating a Flight ID
			boolean isUnique = false;
			int flightID = 0;
			do {
				flightID = (int)(Math.random() * (999999-100000+1)+100000);
				isUnique = !(checkFlightID(flightID));
			} while (isUnique == false);
			calendar.addNewFlight(flightID, srcCode, destCode, srcDate, srcTime, destDate, destTime, airplaneName, price, noOfStops, stops, listOfAirplanes);
		}
		else {
			System.out.println("ERROR: Could not schedule new flight.");
		}
	}
}
