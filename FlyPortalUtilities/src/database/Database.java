package database;

import java.net.InetSocketAddress;

public class Database {

	private InetSocketAddress isa;
	private String name;
	private String username;
	private String password;
	
	public Database(InetSocketAddress isa, String name) {
		this.isa = isa;
		this.name = name;
	}
	
	public Database(InetSocketAddress isa, String name, String username, String password) {
		this(isa, name);
		this.username = username;
		this.password = password;
	}
	

	public InetSocketAddress getIsa() {
		return isa;
	}

	public void setIsa(InetSocketAddress isa) {
		this.isa = isa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
