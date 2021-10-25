import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.StringTokenizer;

public class FlightSchedule {
	private int noOfFlights;
	private Flight[] flights;

	FlightSchedule() {
		noOfFlights = 0;
		flights = new Flight[100];
		for (int i = 0; i < 100; i++) {
			flights[i] = null;
		}
	}
	
	// Getters
	public int getNoOfFlights() {
		return noOfFlights;
	}
	
	// Specified Methods
	public void initializeFlights() throws IOException {
		FileReader fIn = new FileReader("files\\flights.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		int flightNo = 0;
		while ( (line = bIn.readLine()) != null ) {
			String[] temp = new String[11];
			for (int i = 0; i < 11; i++) {
				temp[i] = "";
			}
			int i = 0;
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			while(tokenTab.hasMoreTokens()) {
				temp[i] = tokenTab.nextToken();
				i++;
			}
			try {
				flights[flightNo] = new Flight();
				int stopsCount = Integer.parseInt(temp[9]);
				if (stopsCount > 0) {
					String[] array = new String[stopsCount];
					StringTokenizer tokenSpace = new StringTokenizer(temp[10], " ");
					for (int j = 0; j < stopsCount; j++) {
						array[j] += tokenSpace.nextToken();
						if (array[j].contains("null")) {
							array[j] = array[j].replace("null", "");
						}
					}
					flights[flightNo].initializeFlightRoute(stopsCount, array);
				} else {
					flights[flightNo].initializeFlightRoute(0, null);
				}
				/*
				 * AT INDEX:
				 * 0 - flight ID
				 * 1 - source Airport
				 * 2 - destination Airport
				 * 3 - date source Airport
				 * 4 - time source Airport
				 * 5 - date destination Airport
				 * 6 - time destination Airport
				 * 7 - Airplane name
				 * 8 - Price of flight
				 * 9 - how many stops (if indirect)
				 * 10 - list of airport ID where airplane is to stop (daisy-chained by a whitespace)
				 */
				int fID = Integer.parseInt(temp[0]);
				flights[flightNo].setflightID(fID);
				flights[flightNo].setDepartureAirportID(temp[1]);
				flights[flightNo].setArrivalAirportID(temp[2]);
				flights[flightNo].setDepartureDateAndTime(temp[3], temp[4]);
				flights[flightNo].setArrivalDateAndTime(temp[5], temp[6]);
				flights[flightNo].initializeAirplane(temp[7]);
				int price = Integer.parseInt(temp[8]);
				flights[flightNo].setPrice(price);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
			flightNo++;
		}
		noOfFlights = flightNo;
		bIn.close();
		fIn.close();
	}
	public void saveFlights() throws IOException {
		FileWriter fOut = new FileWriter("files\\flights.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				String lineToWrite = "";
				lineToWrite += String.valueOf(flights[i].getFlightID());
				lineToWrite += '\t';
				lineToWrite += flights[i].getDepartureAirportID();
				lineToWrite += '\t';
				lineToWrite += flights[i].getArrivalAirportID();
				lineToWrite += '\t';
				lineToWrite += flights[i].getDepartureDate();
				lineToWrite += '\t';
				lineToWrite += flights[i].getDepartureTime();
				lineToWrite += '\t';
				lineToWrite += flights[i].getArrivalDate();
				lineToWrite += '\t';
				lineToWrite += flights[i].getArrivalTime();
				lineToWrite += '\t';
				lineToWrite += flights[i].getAirplaneName();
				lineToWrite += '\t';
				lineToWrite += String.valueOf(flights[i].getPrice());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(flights[i].getNoOfStops());
				if (flights[i].getNoOfStops() > 0) {
					lineToWrite += '\t';
					String[] array = flights[i].getListOfStops();
					for (int j = 0; j < flights[i].getNoOfStops(); j++) {
						if (j == flights[i].getNoOfStops()-1) {
							lineToWrite += array[j];
						} else {
							lineToWrite += array[j];
							lineToWrite += ' ';
						}
					}
				}
				lineToWrite += '\n';
				bOut.write(lineToWrite);
			}
		}
		bOut.close();
		fOut.close();
	}
	public void getFlightsSchedule() {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				flights[i].getFlightDetails();
			}
		}
	}
	public Flight[] getFlights() {
		return flights;
	}
	public boolean isFlightAvailable(int fID) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				if (flights[i].getFlightID() == fID) {
					return true;
				}
			}
		}
		return false;
	}

	public void addNewFlight(int flightID, String srcCode, String destCode, String srcDate, String srcTime, String destDate, String destTime, String airplaneName, int price, int noOfStops, String[] stops, AirplaneList obj) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] == null) {
				flights[i] = new Flight();
				flights[i].setflightID(flightID);
				flights[i].setDepartureAirportID(srcCode);
				flights[i].setArrivalAirportID(destCode);
				flights[i].setDepartureDateAndTime(srcDate, srcTime);
				flights[i].setArrivalDateAndTime(destDate, destTime);
				flights[i].setAirplane(airplaneName, obj);
				flights[i].setPrice(price);
				flights[i].initializeFlightRoute(noOfStops, stops);
				System.out.println("Flight successfully scheduled.");
				break;
			}
		}
	}
	public void bookSeat(int fID, int seatNo) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				if (flights[i].getFlightID() == fID) {
					if (seatNo > 0 && seatNo <= (flights[i].getSeats()).getNoOfSeats()) {
						flights[i].bookSeat(seatNo);
						System.out.println("Seat booked successfully.");
						break;
					} else {
						throw new InvalidSeatNumberException("Seat number does not exist.");
					}
				}
			}
		}
	}
	public void cancelBooking(int fID, int seatNo) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				if (flights[i].getFlightID() == fID) {
					flights[i].cancelSeat(seatNo);
				}
			}
		}
	}
	public void purchaseSeat(int fID, int seatNo) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				if (flights[i].getFlightID() == fID) {
					if (seatNo > 0 && seatNo < (flights[i].getSeats()).getNoOfSeats()) {
						flights[i].purchaseSeat(seatNo);
						System.out.println("Seat purchased successfully.");
						break;
					} else {
						throw new InvalidSeatNumberException("Seat number does not exist.");
					}
				}
			}
		}
	}
	public void cancelPurchase(int fID, int seatNo) {
		for (int i = 0; i < 100; i++) {
			if (flights[i] != null) {
				if (flights[i].getFlightID() == fID) {
					flights[i].cancelSeat(seatNo);
				}
			}
		}
	}
	public void initalizeSeatAvailability() throws IOException {
		FileReader fIn = new FileReader("files\\purchases.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		while ( (line = bIn.readLine()) != null ) {
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			String[] temp = new String[3];
			int idx = 0;
			while (tokenTab.hasMoreTokens()) {
				temp[idx] = tokenTab.nextToken();
				idx++;
			}
			try {
				int fID = Integer.parseInt(temp[1]);
				int sNo = Integer.parseInt(temp[2]);
				for (int i = 0; i < 100; i++) {
					if (flights[i] != null) {
						if (flights[i].getFlightID() == fID) {
							flights[i].purchaseSeat(sNo);
							break;
						}
					}
				}
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		bIn.close();
		fIn.close();
		
		FileReader fIn2 = new FileReader("files\\bookings.txt");
		BufferedReader bIn2 = new BufferedReader(fIn2);
		line = null;
		while ( (line = bIn2.readLine()) != null ) {
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			String[] temp = new String[3];
			int idx = 0;
			while (tokenTab.hasMoreTokens()) {
				temp[idx] = tokenTab.nextToken();
				idx++;
			}
			try {
				int fID = Integer.parseInt(temp[1]);
				int sNo = Integer.parseInt(temp[2]);
				for (int i = 0; i < 100; i++) {
					if (flights[i] != null) {
						if (flights[i].getFlightID() == fID) {
							flights[i].bookSeat(sNo);
							break;
						}
					}
				}
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		bIn2.close();
		fIn2.close();
	}
}
