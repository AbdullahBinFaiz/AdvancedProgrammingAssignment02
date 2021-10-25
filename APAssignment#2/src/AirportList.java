import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class AirportList {
	class Airport {
		private String airportID;
		private String airportName;
		
		Airport() {
			airportID = null;
			airportName = null;
		}
		
		// Setters
		public void setAirportID(String aID) {
			airportID = aID;
		}
		public void setAirportName(String name) {
			airportName = name;
		}
		
		// Getters
		public String getAirportID() {
			return airportID;
		}
		public String getAirportName() {
			return airportName;
		}
	}
	
	private int noOfAirports;
	private Airport[] list;
	
	AirportList() {
		noOfAirports = 0;
		list = new Airport[100];
		for (int i = 0; i < 100; i++) {
			list[i] = null;
		}
	}
	
	// Getters
	public int getNoOfAirports() {
		return noOfAirports;
	}
	public Airport[] getListOfAirports() {
		if (noOfAirports > 0) {
			Airport[] array = new Airport[noOfAirports];
			for (int i = 0; i < noOfAirports; i++) {
				array[i] = list[i];
			}
			return array;
		}
		return null;
	}
	
	// Specified Methods
	public void initializeAirports() throws IOException {
		FileReader fIn = new FileReader("files\\airports.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		while ( (line = bIn.readLine()) != null ) {
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			String[] temp = new String[2];
			temp[0] = tokenTab.nextToken();
			temp[1] = tokenTab.nextToken();
			list[noOfAirports] = new Airport();
			list[noOfAirports].setAirportID(temp[0]);
			list[noOfAirports].setAirportName(temp[1]);
			noOfAirports++;
		}
		bIn.close();
		fIn.close();
	}
	public String getAirportName(String ID) {
		for (int i = 0; i < 100; i++) {
			if (list[i] != null) {
				if (list[i].getAirportID().equals(ID)) {
					return list[i].getAirportName();
				}
			}
		}
		return null;
	}
	public void displayAirports() {
		for (int i = 0; i < 100; i++) {
			if (list[i] != null) {
				System.out.print("Airplane code: " + list[i].getAirportID());
				System.out.println("\tAirplane Name: " + list[i].getAirportName());
			}
		}
		System.out.println();
	}
	public void saveAirports() throws IOException {
		FileWriter fOut = new FileWriter("files\\airports.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 100; i++) {
			if (list[i] != null) {
				String lineToWrite = "";
				lineToWrite += list[i].getAirportID();
				lineToWrite += '\t';
				lineToWrite += list[i].getAirportName();
				lineToWrite += '\n';
				bOut.write(lineToWrite);
			}
		}
		bOut.close();
		fOut.close();
	}
}