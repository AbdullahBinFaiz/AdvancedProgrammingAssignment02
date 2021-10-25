import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.BufferedReader;

public class Flight {
	private int flightID;
	private String departureAirportID;
	private String arrivalAirportID;
	private DateAndTime planeDepartureDateAndTime;
	private DateAndTime planeArrivalDateAndTime;
	private Airplane airplane;
	private boolean isDirectFlight;
	private int price;
	private int noOfStops;
	private String[] airportStopID;
	
	Flight() {
		flightID = 0;
		departureAirportID = null;
		arrivalAirportID = null;
		airplane = null;
		planeDepartureDateAndTime = new DateAndTime();
		planeDepartureDateAndTime.setDate(null);
		planeDepartureDateAndTime.setTime(null);
		planeArrivalDateAndTime = new DateAndTime();
		planeArrivalDateAndTime.setDate(null);
		planeArrivalDateAndTime.setTime(null);
		price = 0;
		noOfStops = 0;
		airportStopID = null;
		isDirectFlight = false;
	}
	
	Flight(int stopsCount) {
		flightID = 0;
		departureAirportID = null;
		arrivalAirportID = null;
		planeDepartureDateAndTime = new DateAndTime();
		planeDepartureDateAndTime.setDate(null);
		planeDepartureDateAndTime.setTime(null);
		planeArrivalDateAndTime = new DateAndTime();
		planeArrivalDateAndTime.setDate(null);
		planeArrivalDateAndTime.setTime(null);
		price = 0;
		airplane = null;
		if (stopsCount == 0)
			isDirectFlight = false;
		else
			isDirectFlight = true;
		noOfStops = stopsCount;
		if (stopsCount > 0)
			airportStopID = new String[stopsCount];
		else
			airportStopID = null;
	}
	
	// Setters
	public void setflightID(int fID) {
		flightID = fID;
	}
	public void setDepartureAirportID(String DAID) {
		departureAirportID = DAID;
	}
	public void setArrivalAirportID(String AAID) {
		arrivalAirportID = AAID;
	}
	public void setDepartureDateAndTime(String d, String t) {
		planeDepartureDateAndTime.setDate(d);
		planeDepartureDateAndTime.setTime(t);
	}
	public void setArrivalDateAndTime(String d, String t) {
		planeArrivalDateAndTime.setDate(d);
		planeArrivalDateAndTime.setTime(t);
	}
	public void setPrice(int p) {
		price = p;
	}
	
	// Getters
	public int getFlightID() {
		return flightID;
	}
	public String getDepartureAirportID() {
		return departureAirportID;
	}
	public String getArrivalAirportID() {
		return arrivalAirportID;
	}
	public String getDepartureDateAndTime() {
		String date = planeDepartureDateAndTime.getDate();
		String time = planeDepartureDateAndTime.getTime();
		String finalString = date + " " + time;
		return finalString;
	}
	public String getDepartureDate() {
		return planeDepartureDateAndTime.getDate();
	}
	public String getDepartureTime() {
		return planeDepartureDateAndTime.getTime();
	}
	public String getArrivalDate() {
		return planeArrivalDateAndTime.getDate();
	}
	public String getArrivalTime() {
		return planeArrivalDateAndTime.getTime();
	}
	public String getArrivalDateAndTime() {
		String date = planeArrivalDateAndTime.getDate();
		String time = planeArrivalDateAndTime.getTime();
		String finalString = date + " " + time;
		return finalString;
	}
	public int getPrice() {
		return price;
	}
	public int getNoOfStops() {
		return noOfStops;
	}
	public boolean getFlightDirectStatus() {
		return isDirectFlight;
	}
	public Airplane getSeats() {
		return airplane;
	}
	
	// Specified Methods
	public String[] getListOfStops() {
		if (noOfStops > 0) {
			return airportStopID;
		}
		return null;
	}
	public void initializeFlightRoute(int stopsCount, String[] stopsID) {
		if (stopsCount > 0) {
			if (airportStopID == null ) {
				noOfStops = stopsCount;
				airportStopID = new String[stopsCount];
				for (int i = 0; i < noOfStops; i++) {
					airportStopID[i] = stopsID[i];
				}
				isDirectFlight = false;
			}
		} else {
			isDirectFlight = true;
			noOfStops = 0;
			airportStopID = null;
		}
	}
	public void initializeAirplane(String name) throws IOException {
		if (airplane == null) {
			FileReader fIn = new FileReader("files\\airplanes.txt");
			BufferedReader bIn = new BufferedReader(fIn);
			String line = null;
			while ( (line = bIn.readLine()) != null ) {
				StringTokenizer tokenTab = new StringTokenizer(line, "\t");
				String[] temp = new String[3];
				int i = 0;
				while (tokenTab.hasMoreTokens()) {
					temp[i] = tokenTab.nextToken();
					i++;
				}
				if (name.equals(temp[0])) {
					int tempType = Integer.parseInt(temp[2]);
					int tempSeatCount = Integer.parseInt(temp[1]);
					airplane = new Airplane(name, tempType, tempSeatCount);
					break;
				}
			}
			fIn.close();
		}
	}
	public String getAirplaneName() {
		return airplane.getAirplaneName();
	}
	public void getFlightDetails() {
		System.out.println("Flight ID: " + flightID);
		System.out.println("Departure Airport: " + departureAirportID);
		System.out.println("Arrival Airport: " + arrivalAirportID);
		System.out.println("Plane departure date/time: " + planeDepartureDateAndTime.getDateAndTime());
		System.out.println("Plane arrival date/time: " + planeArrivalDateAndTime.getDateAndTime());
		System.out.println("Plane name: " + airplane.getAirplaneName());
		System.out.println("Plane class: " + airplane.getAirplaneClass());
		System.out.println("Price: " + price);
		System.out.print("Direct flight: " );
		if (isDirectFlight == true) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		if (noOfStops > 0) {
			System.out.println("List of stops:");
			System.out.println("--------");
			for (int i = 0; i < noOfStops; i++) {
				System.out.println("Stop " + (i+1) + ": " + airportStopID[i]);
			}
			System.out.println("--------");
		}
		System.out.println();
	}
	public void displayAvailableSeats() {
		airplane.displayAvailableSeats();
	}
	public void bookSeat(int seatNo) {
		airplane.bookSeat(seatNo);
	}
	public void cancelSeat(int seatNo) {
		airplane.cancelSeat(seatNo);
	}
	public void purchaseSeat(int seatNo) {
		airplane.purchaseSeat(seatNo);
	}
	public void setAirplane(String airplaneName, AirplaneList obj) {
		Airplane[] array = obj.getListOfAirplanes();
		int noOfAirplanes = obj.getNoOfAirplanes();
		for (int i = 0; i < noOfAirplanes; i++) {
			if (array[i].getAirplaneName().equals(airplaneName)) {
				airplane = new Airplane(array[i].getAirplaneName(), array[i].getAirplaneClassNum(), array[i].getNoOfSeats());
				break;
			}
		}
	}
}
