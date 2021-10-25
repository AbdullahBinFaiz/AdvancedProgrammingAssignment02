import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		FlightReservator superclass = new FlightReservator();
		superclass.initialize();
		//superclass.display();
		System.out.println("Do you want to login as a user (0) or admin (1)?");
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		do {
			try {
				System.out.print("Enter the corresponding number: ");
				choice = scan.nextInt();
			}
			catch (InputMismatchException e) {
				scan.nextLine();
			}
		} while (choice != 0 && choice != 1);
		String username = null, password = null;
		System.out.print("Enter your username: ");
		scan.nextLine(); // first ".nextLine()" is to clear buffer from any input other than string input
		username = scan.nextLine();
		System.out.print("Enter your password: ");
		password = scan.nextLine();
		boolean verification = false;
		do {
			if (choice == 0) {
				verification = superclass.CustomerLogin(username, password);
			} else if (choice == 1) {
				System.out.println("SUDO");
				verification = superclass.AdminLogin(username, password);
			}
			if (verification == false) {
				System.out.println("Your credentials could not be verified.");
				System.out.println("Do you want to retry logging in?");
				System.out.print("Enter '1' to retry logging in, enter '0' to change login mode: ");
				int retry = 0;
				retry = scan.nextInt();
				if (retry == 1) {
					System.out.print("Re-enter your username: ");
					scan.nextLine();
					username = scan.nextLine();
					System.out.print("Re-enter your password: ");
					password = scan.nextLine();
				} else {
					if (choice == 1)
						choice = 0 ;
					else if (choice == 0)
						choice = 1;
					System.out.print("Enter your username: ");
					scan.nextLine();
					username = scan.nextLine();
					System.out.print("Enter your password: ");
					password = scan.nextLine();
				}
			}
		} while (verification == false);
		while (superclass.getInUseState()) {
			int option = 0;
			if (!superclass.getAdminLoginState()) { // Customer
				System.out.println();
				System.out.println("What would you like to do?");
				System.out.println("1- Display your profile");
				System.out.println("2- Edit your profile");
				System.out.println("3- Search for a flight");
				System.out.println("4- Book a seat for a flight");
				System.out.println("5- Purchase a ticket for a flight");
				System.out.println("6- Print your ticket (if purchased)");
				System.out.println("7- Cancel your booking/ticket");
				System.out.println("0- Log out");
				do {
					try {
						option = scan.nextInt();
					}
					catch (InputMismatchException e) {
						scan.nextLine();
					}
				} while (option < 0 || option > 7); 
				if (option == 0) {
					superclass.save();
					break;
				} else if (option == 1) { // Display profile
					superclass.displayCustomerProfile(username);
				} else if (option == 2) { // Edit profile
					System.out.println("What would you like to edit?");
					System.out.println("1- Change your full name");
					System.out.println("2- Change your age");
					System.out.println("3- Change your gender");
					System.out.println("4- Change your mobile phone number");
					System.out.print("Enter your choice: ");
					option = scan.nextInt();
					if (option == 1) {
						System.out.print("Enter your new name: ");
						String name = null;
						scan.nextLine();
						name = scan.nextLine();
						superclass.changeUserName(username, name);
					} else if (option == 2) {
						int age = 0;
						do {
							try {
								System.out.print("Enter your new age: ");
								age = scan.nextInt();
							}
							catch (InputMismatchException e) {
								scan.nextLine();
							}
						} while (age < 0 || age > 130);
						superclass.changeUserAge(username, age);
					} else if (option == 3) {
						short gender = 0;
						boolean genderFlag = false;
						do {
							try {
								genderFlag = true;
								System.out.print("Enter 0 for \"Male\", 1 for \"Female\", and any other number for \"Other\": ");
								gender = scan.nextShort();
							}
							catch (InputMismatchException e) {
								scan.nextLine();
								genderFlag = false;
							}
						} while (genderFlag == false);
						superclass.changeUserGender(username, gender);
					} else if (option == 4) {
						System.out.print("Enter your new mobile number (in the format +XX-XXX-XXXXXXX");
						String mobileNo = null;
						scan.nextLine();
						mobileNo = scan.nextLine();
						try {
							superclass.changeUserMobileNo(username, mobileNo);
						}
						catch (MobilePhoneVerificationException e) {
							// let RuntimeException handle it
						}
					}
				} else if (option == 3) { // Search for flight
					System.out.print("Enter a date to check the list of flights departuring on that day (in the format \"DD/MM/YY\"): ");
					String dateToCheck = scan.nextLine();
					boolean directRouteFlag = false, lowestFareFlag = false, noOfAvailableSeatsFlag = false;
					int inputFlag = 2;
					System.out.println("Do you want to search for direct route-only flights?");
					do {
						try {
							System.out.print("Enter 0 for \"No\", enter 1 for \"Yes\": ");
							inputFlag = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
						}
					} while (inputFlag != 0 && inputFlag != 1);
					if (inputFlag == 1) {
						directRouteFlag = true;
					}
					System.out.println("Do you want to search for the lowest-fare flight?");
					System.out.print("Enter 0 for \"No\", enter 1 for \"Yes\": ");
					do {
						try {
							System.out.print("Enter 0 for \"No\", enter 1 for \"Yes\": ");
							inputFlag = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
						}
					} while (inputFlag != 0 && inputFlag != 1);
					if (inputFlag == 1) {
						lowestFareFlag = true;
					}
					System.out.println("Do you want to list the number of available seats for a flight?");
					System.out.print("Enter 0 for \"No\", enter 1 for \"Yes\": ");
					do {
						try {
							System.out.print("Enter 0 for \"No\", enter 1 for \"Yes\": ");
							inputFlag = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
						}
					} while (inputFlag != 0 && inputFlag != 1);
					if (inputFlag == 1) {
						noOfAvailableSeatsFlag = true;
					}
					try {
						superclass.searchFunction(dateToCheck, directRouteFlag, lowestFareFlag, noOfAvailableSeatsFlag);
					}
					catch (SearchFlightException e) {
						// let RuntimeException handle it
					}
				} else if (option == 4) { // Book ticket
					if (superclass.getTicketID(username) != 0) {
						System.out.println("ERROR: you already have purchased a ticket.");
					} else if (superclass.getBookingStatus(username)) {
						System.out.println("ERROR: you have already booked a seat.");
					} else {
						int flightID = 0;
						boolean flightFlag = false;
						do {
							try {
								flightFlag = true;
								System.out.print("Enter a flight ID for which you want to reserve a booking: ");
								flightID = scan.nextInt();
							}
							catch (InputMismatchException e) {
								scan.nextLine();
								flightFlag = false;
							}
						} while (flightFlag == false);
						if (superclass.isFlightAvailable(flightID)) {
							System.out.println("These are the seats available for the flight:");
							superclass.displaySeats(flightID);
							System.out.println("Which seat would you like to book?");
							int seatNo = 0;
							boolean seatFlag = false;
							do {
								try {
									seatFlag = true;
									System.out.print("Enter the corresponding seat number: ");
									seatNo = scan.nextInt();
								}
								catch (InputMismatchException e) {
									scan.nextLine();
									seatFlag = false;
								}
							} while (seatFlag == false);
							boolean checkSeatFlag = false;
							try {
								checkSeatFlag = superclass.checkSeatAvailability(flightID, seatNo);
							}
							catch (InvalidSeatNumberException e) {
								// let RuntimeException do its thing
							}
							if (checkSeatFlag) {
								System.out.println("Seat is available for booking.");
								System.out.println("Do you wish to book this seat?");
								int confirmation = 0;
								do {
									try {
										System.out.print("Enter '1' to confirm, enter '0' to cancel: ");
										confirmation = scan.nextInt();
									}
									catch (InputMismatchException e) {
										scan.nextLine();
									}
								} while (confirmation != 0 && confirmation != 1);
								if (confirmation == 1) {
									superclass.bookSeat(flightID, seatNo, username);
								}
							} else {
								System.out.println("ERROR: Seat is already booked/purchased.");
							}
						} else {
							System.out.println("ERROR: no such scheduled flight exists.");
						}
					}
				} else if (option == 5) { // Purchase ticket
					if (superclass.getTicketID(username) != 0) {
						System.out.println("ERROR: you already have purchased a ticket.");
					} else if (superclass.getBookingStatus(username)) {
						System.out.println("You already have a booking.");
						System.out.println("Do you wish to purchase seat number " + superclass.getSeat(username) + "for flight no. " + superclass.getFlight(username) + "?");
						int confirmation = 0;
						do {
							try {
								System.out.print("Enter '1' to confirm purchase, enter '0' to decline purchase: ");
								confirmation = scan.nextInt();
							}
							catch (InputMismatchException e) {
								scan.nextLine();
							}
						} while (confirmation != 0 && confirmation != 1);
						if (confirmation == 1) {
							scan.nextLine();
							System.out.println("Enter your credit card (in the format XXXX-XXXX-XXXX-XXXX) (Numbers only): ");
							String crdNo = null;
							boolean confirmationFlag = false;
							//
							do {
								try {
									crdNo = scan.nextLine();
									boolean checkVerification = false;
									checkVerification = superclass.verifyCreditCard(crdNo);
									if (checkVerification == true) {
										confirmationFlag = true;
									}
								}
								catch (CardVerificationException e) {
									e.printStackTrace();
								}
							} while (confirmationFlag == false);
							superclass.purchaseSeat(superclass.getFlight(username), superclass.getSeat(username));
							System.out.println("Seat successfully purchased.");
						}
					} else {
						int flightID = 0;
						boolean flightFlag = false;
						do {
							try {
								flightFlag = true;
								System.out.print("Enter a flight ID for which you want to purchase a ticket: ");
								flightID = scan.nextInt();
							}
							catch (InputMismatchException e) {
								scan.nextLine();
								flightFlag = false;
							}
						} while (flightFlag == false);
						if (superclass.isFlightAvailable(flightID)) {
							System.out.println("These are the seats available for the flight:");
							superclass.displaySeats(flightID);
							System.out.println("Which seat would you like to purchase?");
							int seatNo = 0;
							boolean seatFlag = false;
							do {
								try {
									seatFlag = true;
									System.out.print("Enter the corresponding seat number: ");
									seatNo = scan.nextInt();
								}
								catch (InputMismatchException e) {
									scan.nextLine();
									seatFlag = false;
								}
							} while (seatFlag == false);
							if (superclass.checkSeatAvailability(flightID, seatNo)) {
								System.out.println("Seat is available for purchasing.");
								System.out.println("Do you wish to purchase this seat?");
								int confirmation = 0;
								do {
									try {
										System.out.print("Enter '1' to confirm, enter '0' to cancel: ");
										confirmation = scan.nextInt();
									}
									catch (InputMismatchException e) {
										scan.nextLine();
									}
								} while (confirmation != 0 && confirmation != 1);
								if (confirmation == 1) {
									scan.nextLine();
									System.out.println("Enter your credit card (in the format XXXX-XXXX-XXXX-XXXX) (Numbers only): ");
									String crdNo = null;
									boolean confirmationFlag = false;
									//
									do {
										try {
											crdNo = scan.nextLine();
											boolean checkVerification = false;
											checkVerification = superclass.verifyCreditCard(crdNo);
											if (checkVerification == true) {
												confirmationFlag = true;
											}
										}
										catch (CardVerificationException e) {
											// let RuntimeException handle it
										}
									} while (confirmationFlag == false);
									superclass.purchaseSeat(flightID, seatNo);
								}
							} else {
								System.out.println("ERROR: Seat is already booked/purchased.");
							}
						} else {
							System.out.println("ERROR: no such scheduled flight exists.");
						}
					}
				} else if (option == 6) {
					if (superclass.getTicketID(username) == 0) {
						System.out.println("ERROR: you have not yet purchased a ticket.");
					} else {
						superclass.printTicket(superclass.getTicketID(username));
					}
				} else if (option == 7) {
					if (superclass.getTicketID(username) != 0) {
						System.out.println("You have a purchased ticket for: ");
						System.out.println("Flight ID: " + superclass.getFlight(username));
						System.out.println("Seat No.: " + superclass.getSeat(username));
						System.out.println();
						System.out.println("Do you wish to cancel your purchase?");
						System.out.print("Enter '1' to cancel, enter '0' to leave as is: ");
						int confirm = 0;
						do {
							confirm = scan.nextInt();
						} while (confirm != 0 && confirm != 1);
						if (confirm == 1) {
							superclass.cancelPurchase(username, superclass.getFlight(username), superclass.getSeat(username), superclass.getTicketID(username));
							System.out.println("Your ticket has been successfully cancelled.");
						}
					} else if (superclass.getBookingStatus(username)) {
						System.out.println("You have a booked seat for:");
						System.out.println("Flight ID: " + superclass.getFlight(username));
						System.out.println("Seat No.: " + superclass.getSeat(username));
						System.out.println();
						System.out.println("Do you wish to cancel your booking?");
						System.out.print("Enter '1' to cancel, enter '0' to leave as is: ");
						int confirm = 0;
						do {
							confirm = scan.nextInt();
						} while (confirm != 0 && confirm != 1);
						if (confirm == 1) {
							superclass.cancelBooking(username, superclass.getFlight(username), superclass.getSeat(username));
							System.out.println("Your booking has been successfully cancelled.");
						}
					} else {
						
					}
				}
			} else if (superclass.getAdminLoginState()) { // Admin
				System.out.println();
				System.out.println("What would you like to do?");
				System.out.println("1- Add new scheduled flight");
				System.out.println("2- Add new airplane");
				System.out.println("3- Add new customer");
				System.out.println("0- Log out");
				int adminOption = 0;
				do {
					try {
						System.out.print("Enter: ");
						adminOption = scan.nextInt();
					}
					catch (InputMismatchException e) {
						scan.nextLine();
					}
				} while (adminOption < 0 || adminOption > 3); 
				if (adminOption == 0) {
					superclass.save();
					break;
				} else if (adminOption == 1) { // flight
					scan.nextLine();
					System.out.print("Enter a scheduled departure date for the flight (in the format DD/MM/YY): ");
					String dDate = scan.nextLine();
					System.out.print("Enter the arrival date for the flight: ");
					String aDate = scan.nextLine();
					System.out.print("Enter the source airport code (In the format XXYYY) (e.g. PK786): ");
					String saID = scan.nextLine();
					System.out.print("Enter the destination airport code (In the format XXYYY) (e.g. PK786): ");
					String daID = scan.nextLine();
					System.out.print("Enter the departure time (in the format HH:MM): ");
					String dTime = scan.nextLine();
					System.out.print("Enter the arrival time: ");
					String aTime = scan.nextLine();
					System.out.print("Enter the name of the airplane that will be used for the flight: ");
					String airplaneName = scan.nextLine();
					int price = 0;
					boolean inputFlag = false;
					do {
						try {
							System.out.print("Enter the fare of the flight: ");
							inputFlag = true;
							price = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
							inputFlag = false;
						}
					} while (inputFlag == false);
					System.out.println("Is the flight direct or indirect?");
					int noOfStops = 0;
					inputFlag = false;
					do {
						try {
							System.out.print("Enter the number of stops the flight makes before reaching its destination:");
							inputFlag = true;
							noOfStops = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
							inputFlag = false;
						}
					} while (inputFlag == false && noOfStops < 0);
					String[] stops = null;
					if (noOfStops > 0) {
						scan.nextLine();
						stops = new String[noOfStops];
						for (int i = 0; i < noOfStops; i++) {
							System.out.print("Enter stop " + (i+1) + "'s airport code (in the format XXYYY) (e.g. AE176): ");
							stops[i] = scan.nextLine();
						}
					}
					superclass.createNewFlight(saID, daID, dDate, dTime, aDate, aTime, airplaneName, price, noOfStops, stops);
				} else if (adminOption == 2) { // airplane
					scan.nextLine();
					System.out.print("Enter the name of the new airplane: ");
					String newName = scan.nextLine();
					System.out.println("Enter the class of the airplane:");
					int airplaneClass = 0;
					do {
						try {
							System.out.print("Enter '0' for Business class, enter '1' for First class, enter '2' for Economy class: ");
							airplaneClass = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
						}
					} while (airplaneClass < 0 || airplaneClass > 2);
					int noOfSeats = 0;
					do {
						try {
							System.out.print("Enter the amount of seats this airplane has: ");
							noOfSeats = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
						}
					} while (noOfSeats <= 0);
					superclass.createNewAirplane(newName, airplaneClass, noOfSeats);
					System.out.println("Added a new airplane into database successfully.");
				} else if (adminOption == 3) { // customer
					scan.nextLine();
					System.out.print("Enter their full name: ");
					String name = scan.nextLine();
					short gender = 0;
					boolean inputFlag = false;
					do {
						try {
							inputFlag = true;
							System.out.print("Enter their gender ('0' for Male, '1' for Female, any other number for Other): ");
							gender = scan.nextShort();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
							inputFlag = false;
							
						}
					} while (inputFlag == false);
					int age = 0;
					inputFlag = false;
					do {
						try {
							inputFlag = true;
							System.out.print("Enter their age: ");
							age = scan.nextInt();
						}
						catch (InputMismatchException e) {
							scan.nextLine();
							inputFlag = false;
							
						}
					} while (inputFlag == false);
					scan.nextLine();
					System.out.print("Enter their mobile phone number: ");
					String mobileNo = scan.nextLine();
					System.out.print("Enter their Passport ID: ");
					String passportNo = scan.nextLine();
					System.out.print("Enter their username: ");
					String newUser = scan.nextLine();
					System.out.print("Enter the password associated with their username: ");
					String newPass = scan.nextLine();
					superclass.createNewCustomer(name, gender, age, mobileNo, passportNo, newUser, newPass);
				}
			}
		}
		scan.close();
	}
}
