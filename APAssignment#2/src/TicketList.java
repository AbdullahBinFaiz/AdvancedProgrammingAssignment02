import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class TicketList {
	private int noOfTickets;
	private Ticket[] list;
	
	TicketList() {
		noOfTickets = 0;
		list = new Ticket[1000];
		for (int i = 0; i < 1000; i++) {
			list[i] = null;
		}
	}
	
	// Getters
	public int getNoOfTickets() {
		return noOfTickets;
	}
	
	// Specified Methods
	public void initializeTickets(FlightSchedule obj) throws IOException {
		Flight[] array = obj.getFlights();
		int noOfFlights = obj.getNoOfFlights();
		FileReader fIn = new FileReader("files\\tickets.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		while ( (line = bIn.readLine()) != null ) {
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			String[] temp = new String[10];
			int idx = 0;
			while (tokenTab.hasMoreTokens()) {
				temp[idx] = tokenTab.nextToken();
				idx++;
			}
			try {
				int ticketID = Integer.parseInt(temp[0]);
				int price = Integer.parseInt(temp[1]);
				int flightID = Integer.parseInt(temp[2]);
				int seatNo = Integer.parseInt(temp[3]);
				int type = Integer.parseInt(temp[9]);
				list[noOfTickets] = new Ticket(ticketID, price, flightID, seatNo, temp[4], temp[5], temp[6], temp[7], temp[8], type);
				for (int i = 0; i < noOfFlights; i++) {
					if (array[i].getFlightID() == flightID) {
						list[noOfTickets].setPlaneArrivalDateAndTime(array[i].getArrivalDate(), array[i].getArrivalTime());
						break;
					}
				}
				noOfTickets++;
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		bIn.close();
		fIn.close();
	}
	public void getSpecifiedTicketDetails(int ticketID, AirportList obj) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getTicketID() == ticketID) {
					list[i].displayTicketInfo(obj);
					break;
				}
			}
		}
	}
	public void deleteTicket(int tID) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getTicketID() == tID) {
					list[i] = null;
					break;
				}
			}
		}
	}

	public void saveTickets() throws IOException {
		FileWriter fOut = new FileWriter("files\\tickets.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				String lineToWrite = "";
				lineToWrite += String.valueOf(list[i].getTicketID());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getPrice());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getFlightID());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getSeatNo());
				lineToWrite += '\t';
				lineToWrite += list[i].getAirportDestinationID();
				lineToWrite += '\t';
				lineToWrite += list[i].getAirportSourceID();
				lineToWrite += '\t';
				lineToWrite += list[i].getPlaneDepartureDate();
				lineToWrite += '\t';
				lineToWrite += list[i].getPlaneDepartureTime();
				lineToWrite += '\t';
				lineToWrite += list[i].getAirplaneName();
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getAirplaneClassNum());
				lineToWrite += '\n';
				bOut.write(lineToWrite);
			}
		}
		bOut.close();
		fOut.close();
	}
}
