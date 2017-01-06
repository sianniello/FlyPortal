package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the db_managers database table.
 * 
 */
@Entity
@Table(name="db_managers")
@NamedQuery(name="DbManager.findAll", query="SELECT d FROM DbManager d")
public class DbManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String password;

	private String username;

	public DbManager() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}