

public class Customer {
	
	private String name; // self-explanatory
	private short gender; // 0 for Male, 1 for Female
	private int age; // also self-explanatory
	private String mobileNumber; // will contain a hyphen to separate country code
	private String passportNumber; // numbers-only, 9 digits long
	private int ticketID; // for purchasing
	private int flightID; // for booking
	private int seatID; // also for booking
	private String username; // both used for logging into system
	private String password;
	
	// Constructor
	Customer() {
		name = null;
		gender = -1;
		age = 0;
		mobileNumber = null;
		passportNumber = null;
		ticketID = 0;
		flightID = 0;
		seatID = 0;
		username = null;
		password = null;
	}
	Customer(String n, short g, int a, String mobileNo, String passportNo, String uName, String pass, int tID) {
		name = n;
		gender = g;
		age = a;
		mobileNumber = mobileNo;
		passportNumber = passportNo;
		ticketID = tID;
		flightID = 0;
		seatID = 0;
		username = uName;
		password = pass;
	}
	
	// Setters
	public void setName(String n) {
		name = n;
	}
	public void setGender(short g) {
		gender = g;
	}
	public void setAge(int a) {
		age = a;
	}
	public void setMobileNumber(String mobileNo) {
		mobileNumber = mobileNo;
	}
	public void setPassportNumber(String passportNo) {
		passportNumber = passportNo;
	}
	public void setTicketID(int tID) {
		ticketID = tID;
	}
	public void setFlightID(int fID) {
		flightID = fID;
	}
	public void setSeatNo(int sID) {
		seatID = sID;
	}
	public void setUsername(String uName) {
		username = uName;
	}
	public void setPassword(String pass) {
		password = pass;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	public String getGender() {
		if (gender == 0) {
			return "Male";
		} else if (gender == 1) {
			return "Female";
		}
		return "Other";
	}
	public short getGenderShort() {
		return gender;
	}
	public int getAge() {
		return age;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public int getTicketID() {
		return ticketID;
	}
	public int getFlightID() {
		return flightID;
	}
	public int getSeatNo() {
		return seatID;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	// Specified Methods
	public void displayProfile() {
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
		System.out.print("Gender: ");
		if (gender == 0) {
			System.out.println("Male");
		} else if (gender == 1) {
			System.out.println("Female");
		} else {
			System.out.println("Other");
		}
		System.out.println("Mobile number: " + mobileNumber);
		System.out.println("Passport Number: " + passportNumber);
		if (ticketID != 0) {
			System.out.println("You have a purchased ticket.");
		}
	}
	public boolean verifyLogin(String uName, String pass) {
		if (username.equals(uName)) {
			if (password.equals(pass)) {
				return true;
			}
		}
		return false;
	}
}
