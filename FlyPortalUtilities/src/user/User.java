package user;

public class User {

	private String username, password;
	private double account;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, double account) {
		this(username, password);
		this.account = account;
	}

	public User (String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

}
