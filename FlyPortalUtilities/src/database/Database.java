package database;

import java.net.InetSocketAddress;

public class Database {

	private InetSocketAddress isa;
	private String name;
	private boolean primary;
	
	public Database(InetSocketAddress isa, String name) {
		this.isa = isa;
		this.name = name;
		primary = false;
	}
	
	public Database(InetSocketAddress isa, String name, boolean primary) {
		this(isa, name);
		this.primary = primary;
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

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
}
