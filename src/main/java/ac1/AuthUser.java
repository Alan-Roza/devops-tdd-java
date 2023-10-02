package ac1;

public class AuthUser {
	private String user;
	private String password;
	private int failTries;
	
	public AuthUser(String user, String password) {
		this.user = user;
		this.password = password;
		this.setFailTries(0);
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUser() {
		return user;
	}

	public int getFailTries() {
		return failTries;
	}

	public void setFailTries(int failTries) {
		this.failTries = failTries;
	}	
}
