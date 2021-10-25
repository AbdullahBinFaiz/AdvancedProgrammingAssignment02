public class Ticket {
	private int ticketID;
	private int price;
	private int flightID;
	private int seatNo; // equivalent to seatID (+1);
	private String airportDestinationID;
	private String airportSourceID;
	private DateAndTime planeDepartureDateAndTime;
	private DateAndTime planeArrivalDateAndTime;
	private Airplane airplaneInfo;
	
	Ticket() {
		ticketID = 0;
		price = 0;
		flightID = 0;
		seatNo = 0;
		airportDestinationID = null;
		airportSourceID = null;
		planeDepartureDateAndTime = null;
		planeArrivalDateAndTime = null;
		airplaneInfo = null;
	}
	
	Ticket(int tID, int p, int fID, int sNo, String ADID, String ASID, String date, String time, String name, int aClass) {
		ticketID = tID;
		price = p;
		flightID = fID;
		seatNo = sNo;
		airportDestinationID = ADID;
		airportSourceID = ASID;
		planeDepartureDateAndTime = new DateAndTime(date, time);
		planeArrivalDateAndTime = new DateAndTime();
		airplaneInfo = new Airplane(name, aClass);
	}
	
	// Setters
	public void setTicketID(int tID) {
		ticketID = tID;
	}
	public void setPrice(int p) {
		price = p;
	}
	public void setFlightIF(int fID) {
		flightID = fID;
	}
	public void setSeatNo(int sNo) {
		seatNo = sNo;
	}
	public void setAirportDestinationID(String ADID) {
		airportDestinationID = ADID;
	}
	public void setAirportSourceID(String ASID) {
		airportSourceID = ASID;
	}
	public void setPlaneDepartureDateAndTime(String date, String time) {
		if (planeDepartureDateAndTime == null) {
			planeDepartureDateAndTime = new DateAndTime();
		}
		planeDepartureDateAndTime.setDate(date);
		planeDepartureDateAndTime.setTime(time);
	}
	public void setPlaneArrivalDateAndTime(String date, String time) {
		if (planeArrivalDateAndTime == null) {
			planeArrivalDateAndTime = new DateAndTime();
		}
		planeArrivalDateAndTime.setDate(date);
		planeArrivalDateAndTime.setTime(time);
	}
	public void setAirplaneInfo(String airplaneName, int airplaneClass) {
		airplaneInfo.setAirplaneName(airplaneName);
		airplaneInfo.setAirplaneClass(airplaneClass);
	}
	
	// Getters
	public int getTicketID() {
		return ticketID;
	}
	public int getPrice() {
		return price;
	}
	public int getFlightID() {
		return flightID;
	}
	public int getSeatNo() {
		return seatNo;
	}
	public String getAirportDestinationID() {
		return airportDestinationID;
	}
	public String getAirportSourceID() {
		return airportSourceID;
	}
	public String getPlaneDepartureDate() {
		return planeDepartureDateAndTime.getDate();
	}
	public String getPlaneDepartureTime() {
		return planeDepartureDateAndTime.getTime();
	}
	public String getPlaneDepartureDateAndTime() {
		String date = planeDepartureDateAndTime.getDate();
		String time = planeDepartureDateAndTime.getTime();
		String finalString = date + " " + time;
		return finalString;
	}
	public String getPlaneArrivalDate() {
		return planeArrivalDateAndTime.getDate();
	}
	public String getPlaneArrivalTime() {
		return planeArrivalDateAndTime.getTime();
	}
	public String getPlaneArrivalDateAndTime() {
		String date = planeArrivalDateAndTime.getDate();
		String time = planeArrivalDateAndTime.getTime();
		String finalString = date + " " + time;
		return finalString;
	}
	public String getAirplaneName() {
		return airplaneInfo.getAirplaneName();
	}
	public String getAirplaneClass() {
		return airplaneInfo.getAirplaneClass();
	}
	
	// Specified Methods
	public void displayTicketInfo(AirportList obj) {
		System.out.println("Ticket ID: " + ticketID);
		System.out.println("Flight ID: " + flightID);
		System.out.println("Seat No.: " + seatNo);
		System.out.println("Price: " + price);
		System.out.print("Departure Airport: ");
		if (obj != null)
			System.out.println(obj.getAirportName(airportSourceID));
		else
			System.out.println(airportSourceID);
		System.out.print("Arrival Airport: ");
		if (obj != null)
			System.out.println(obj.getAirportName(airportDestinationID));
		else
			System.out.println(airportDestinationID);
		System.out.println("Date and Time of departure: " + planeDepartureDateAndTime.getDateAndTime());
		System.out.println("Date and Time of arrival: " + planeArrivalDateAndTime.getDateAndTime());
		System.out.println("Airplane name: " + airplaneInfo.getAirplaneName());
		System.out.println("Airplane class: " + airplaneInfo.getAirplaneClass());
	}

	public int getAirplaneClassNum() {
		return airplaneInfo.getAirplaneClassNum();
	}
}
