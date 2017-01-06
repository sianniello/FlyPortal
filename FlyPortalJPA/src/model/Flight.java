package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the flights database table.
 * 
 */
@Entity
@Table(name="flights")
@NamedQuery(name="Flight.findAll", query="SELECT f FROM Flight f")
public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="arr_airport")
	private String arrAirport;

	private String company;

	@Column(name="dep_airport")
	private String depAirport;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dep_time")
	private Date depTime;

	private String flight;

	@Column(name="free_seats")
	private int freeSeats;

	@Column(name="seat_price")
	private double seatPrice;

	private String state;

	public Flight() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArrAirport() {
		return this.arrAirport;
	}

	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepAirport() {
		return this.depAirport;
	}

	public void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}

	public Date getDepTime() {
		return this.depTime;
	}

	public void setDepTime(Date depTime) {
		this.depTime = depTime;
	}

	public String getFlight() {
		return this.flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public int getFreeSeats() {
		return this.freeSeats;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}

	public double getSeatPrice() {
		return this.seatPrice;
	}

	public void setSeatPrice(double seatPrice) {
		this.seatPrice = seatPrice;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}