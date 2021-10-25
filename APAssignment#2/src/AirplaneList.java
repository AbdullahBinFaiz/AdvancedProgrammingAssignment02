import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class AirplaneList {
	private int noOfAirplanes;
	Airplane airplane[];
	
	AirplaneList() {
		noOfAirplanes = 0;
		airplane = new Airplane[100];
		for (int i = 0; i < 100; i++) {
			airplane[i] = null;
		}
	}
	
	// Getters
	public int getNoOfAirplanes() {
		return noOfAirplanes;
	}
	public Airplane[] getListOfAirplanes() {
		if (noOfAirplanes > 0) {
			Airplane[] array = new Airplane[noOfAirplanes];
			int idx = 0;
			for (int i = 0; i < noOfAirplanes; i++) {
				if (airplane[i] != null) {
					array[idx] = airplane[i];
					idx++;
				}
			}
			return array;
		}
		return null;
	}

	// Specified Methods
	public void addNewAirplane(String name, int type, int seatCount) {
		// Check first if an airplane of the same name exists 
		for (int i = 0; i < 100; i++) {
			if (airplane[i] != null) {
				if (airplane[i].getAirplaneName().equals(name)) {
					System.out.println("ERROR: Plane already exists.");
					return;
				}
			}
		}
		// If not, add it into database
		for (int i = 0; i < 100; i++) {
			if (airplane[i] == null) {
				airplane[i] = new Airplane(name, type, seatCount);
				System.out.println("Airplane successfully added into database.");
				break;
			}
		}
	}
	public void initializeAirplanes() throws IOException {
		FileReader fIn = new FileReader("files\\airplanes.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		int idx = 0;
		while ( (line = bIn.readLine()) != null ) {
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			String[] temp = new String[3];
			int i = 0;
			while (tokenTab.hasMoreTokens()) {
				temp[i] = tokenTab.nextToken();
				i++;
			}
			try {
				int type = Integer.parseInt(temp[2]);
				int seatsCount = Integer.parseInt(temp[1]);
				airplane[idx] = new Airplane(temp[0], type, seatsCount);
				idx++;
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		noOfAirplanes = idx;
		bIn.close();
		fIn.close();
	}
	public void displayAirplanes() {
		for (int i = 0; i < 100; i++) {
			if (airplane[i] != null) {
				System.out.println(airplane[i].getAirplaneName());
			}
		}
		System.out.println();
	}
	public boolean checkAirplane(String airplaneName) {
		for (int i = 0; i < 100; i++) {
			if (airplane[i] != null) {
				if (airplaneName.equals(airplane[i].getAirplaneName())) {
					return true;
				}
			}
		}
		return false;
	}
	public void saveAirplanes() throws IOException {
		FileWriter fOut = new FileWriter("files\\airplanes.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 100; i++) {
			if (airplane[i] != null) {
				String lineToWrite = "";
				lineToWrite += airplane[i].getAirplaneName();
				lineToWrite += '\t';
				lineToWrite += String.valueOf(airplane[i].getNoOfSeats());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(airplane[i].getAirplaneClassNum());
				lineToWrite += '\n';
				bOut.write(lineToWrite);
			}
		}
		bOut.close();
		fOut.close();
	}
}