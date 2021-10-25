import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class CustomerList {
	private int noOfCustomers;
	private Customer[] list;
	
	CustomerList() {
		noOfCustomers = 0;
		list = new Customer[1000];
		for (int i = 0; i < 1000; i++) {
			list[i] = null;
		}
	}
	
	public void displaySpecifiedUser(String uName, TicketList obj) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(uName)) {
					list[i].displayProfile();
					if (list[i].getTicketID() != 0) {
						System.out.println("You have a purchased ticket.");
					} else if (list[i].getFlightID() != 0) {
						System.out.println("You have a reserved booking for:");
						System.out.println("Flight no.: " + list[i].getFlightID());
						System.out.println("At seat no.: " + list[i].getSeatNo());
					}
					break;
				}
			}
		}
	}
	public void initializeCustomers() throws IOException {
		FileReader fIn = new FileReader("files\\customers.txt");
		BufferedReader bIn = new BufferedReader(fIn);
		String line = null;
		while ( (line = bIn.readLine()) != null ) {
			String[] temp = new String[10]; // 0-Name, 1-gender, 2-age, 3-phoneNumber, 4-passportID, 5-username, 6-password, 7-flightID, 8-seatID, 9-ticketID
			StringTokenizer tokenTab = new StringTokenizer(line, "\t");
			int noOfEntries = 0;
			while (tokenTab.hasMoreTokens()) {
				temp[noOfEntries] = tokenTab.nextToken();
				noOfEntries++;
			}
			try {
				int age = Integer.parseInt(temp[2]);
				short gender = Short.parseShort(temp[1]);
				int ticketID = 0, flightID = 0, seatID = 0;
				if (temp[9] != null) {
					ticketID = Integer.parseInt(temp[9]);
				}
				if (temp[7] != null) {
					flightID = Integer.parseInt(temp[7]);
				}
				if (temp[8] != null) {
					seatID = Integer.parseInt(temp[8]);
				}
				if (list[noOfCustomers] == null) {
					list[noOfCustomers] = new Customer(temp[0], gender, age, temp[3], temp[4], temp[5], temp[6], ticketID);
					if (flightID != 0) {
						list[noOfCustomers].setFlightID(flightID);
						list[noOfCustomers].setSeatNo(seatID);
					}
					noOfCustomers++;
				}
				}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		bIn.close();
		fIn.close();
	}
	public void displayAllCustomers(TicketList obj) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				list[i].displayProfile();
				if (list[i].getTicketID() != 0) {
					obj.getSpecifiedTicketDetails(list[i].getTicketID(), null);
				} else if (list[i].getFlightID() != 0) {
					System.out.println("You have a reserved booking for:");
					System.out.println("Flight no.: " + list[i].getFlightID());
					System.out.println("At Seat no.: " + list[i].getSeatNo());
				}
				System.out.println();
			}
		}
	}
	public boolean verifyCustomerLogin(String username, String password) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].verifyLogin(username, password)) {
					return true;
				}
			}
		}
		return false;
	}
	public int getSpecifiedUserTicket(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					return list[i].getTicketID();
				}
			}
		}
		return 0;
	}
	
	// Extended Setter Methods
	public void setSpecifiedUserName(String username, String name) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setName(name);
					break;
				}
			}
		}
	}
	public void setSpecifiedUserAge(String username, int age) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setAge(age);
					break;
				}
			}
		}
	}
	public void setSpecifiedUserGender(String username, short gender) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setGender(gender);
					break;
				}
			}
		}
	}
	public void setSpecifiedUserMobilePhone(String username, String mobileNo) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setMobileNumber(mobileNo);
					break;
				}
			}
		}
	}
	public boolean getBookingStatus(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					if (list[i].getFlightID() != 0) {
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	public int getSeatNo(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					return list[i].getSeatNo();
				}
			}
		}
		return 0;
	}
	public int getFlightID(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					return list[i].getFlightID();
				}
			}
		}
		return 0;
	}
	public void setBook(String username, int fID, int sNo) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setFlightID(fID);
					list[i].setSeatNo(sNo);
					break;
				}
			}
		}
	}
	public void cancelBooking(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setFlightID(0);
					list[i].setSeatNo(0);
					break;
				}
			}
		}
	}
	public void cancelPurchase(String username) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getUsername().equals(username)) {
					list[i].setFlightID(0);
					list[i].setSeatNo(0);
					list[i].setTicketID(0);
					break;
				}
			}
		}
	}
	public void addNewCustomer(String name, short gender, int age, String mobileNo, String passportNo, String uName, String uPass) {
		for (int i = 0; i < 1000; i++) {
			if (list[i] == null) {
				list[i] = new Customer(name, gender, age, mobileNo, passportNo, uName, uPass, 0);
				System.out.println("Added customer to database successfully.");
				break;
			}
		}
	}

	public void saveCustomers() throws IOException {
		FileWriter fOut = new FileWriter("files\\customers.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				String lineToWrite = "";
				lineToWrite += list[i].getName();
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getGenderShort());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getAge());
				lineToWrite += '\t';
				lineToWrite += list[i].getMobileNumber();
				lineToWrite += '\t';
				lineToWrite += list[i].getPassportNumber();
				lineToWrite += '\t';
				lineToWrite += list[i].getUsername();
				lineToWrite += '\t';
				lineToWrite += list[i].getPassword();
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getFlightID());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getSeatNo());
				lineToWrite += '\t';
				lineToWrite += String.valueOf(list[i].getTicketID());
				lineToWrite += '\n';
				bOut.write(lineToWrite);
			}
		}
		bOut.close();
		fOut.close();
	}

	public void saveBookings() throws IOException {
		FileWriter fOut = new FileWriter("files\\bookings.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getTicketID() == 0) {
					if (list[i].getFlightID() != 0) {
						String lineToWrite = "";
						lineToWrite += list[i].getUsername();
						lineToWrite += '\t';
						lineToWrite += String.valueOf(list[i].getFlightID());
						lineToWrite += '\t';
						lineToWrite += String.valueOf(list[i].getSeatNo());
						lineToWrite += '\n';
						bOut.write(lineToWrite);
					}
				}
			}
		}
		bOut.close();
		fOut.close();
	}
	public void savePurchases() throws IOException {
		FileWriter fOut = new FileWriter("files\\purchases.txt");
		BufferedWriter bOut = new BufferedWriter(fOut);
		for (int i = 0; i < 1000; i++) {
			if (list[i] != null) {
				if (list[i].getTicketID() != 0) {
					String lineToWrite = "";
					lineToWrite += list[i].getUsername();
					lineToWrite += '\t';
					lineToWrite += String.valueOf(list[i].getFlightID());
					lineToWrite += '\t';
					lineToWrite += String.valueOf(list[i].getSeatNo());
					lineToWrite += '\n';
					bOut.write(lineToWrite);
				}
			}
		}
		bOut.close();
		fOut.close();
	}
}
