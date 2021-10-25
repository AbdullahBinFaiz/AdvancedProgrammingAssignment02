
public class DateAndTime {
	private String date;
	private String time;
	
	DateAndTime() {
		date = null;
		time = null;
	}
	
	DateAndTime(String d, String t) {
		date = d;
		time = t;
	}
	
	// Setters
	public void setDate(String d) {
		date = d;
	}
	public void setTime(String t) {
		time = t;
	}
	
	// Getters
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getDateAndTime() {
		String concatenatedString = date + " " + time;
		return concatenatedString;
	}
}
