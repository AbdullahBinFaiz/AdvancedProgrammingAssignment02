
public class Admin {
	private final String username;
	private final String password;
	
	Admin() { // since the variables are final, the only way to alter Admin login credentials is inside the system for now
		username = "admin123";
		password = "100POW192";
	}
	
	// Getters
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
