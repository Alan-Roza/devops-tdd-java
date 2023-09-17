package ac1;

import java.util.Objects;

public class AuthUser {
	private String user;
	private String password;
	private int failTries;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUser username = (AuthUser) o;
        return Objects.equals(user, username.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
    
    @Override
    public String toString() {
        return "AuthUser [username=" + user + ", password=" + password + "]";
    }
	
	public AuthUser(String user, String password) {
		this.user = user;
		this.password = password;
		this.setFailTries(0);
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public int getFailTries() {
		return failTries;
	}

	public void setFailTries(int failTries) {
		this.failTries = failTries;
	}	
}
