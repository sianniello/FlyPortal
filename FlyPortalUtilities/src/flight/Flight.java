package flight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class Flight {

	private String flight;
	private String depAirport;
	private String arrAirport;
	private GregorianCalendar dep_time;
	private String company;
	private String state;
	
	public Flight(String flight, String depAirport, String arrAirport,
			String dep_time, String company, String state) {
		this.flight = flight;
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = null;
		try {
			date = df.parse(dep_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dep_time.setTime(date);
		this.company = company;
		this.state = state;
	}

	public synchronized String getFlight() {
		return flight;
	}
	public synchronized void setFlight(String flight) {
		this.flight = flight;
	}
	public synchronized String getDepAirport() {
		return depAirport;
	}
	public synchronized void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}
	public synchronized String getArrAirport() {
		return arrAirport;
	}
	public synchronized void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}
	public synchronized GregorianCalendar getDep_time() {
		return dep_time;
	}
	public synchronized void setDep_time(GregorianCalendar dep_time) {
		this.dep_time = dep_time;
	}
	public synchronized String getCompany() {
		return company;
	}
	public synchronized void setCompany(String company) {
		this.company = company;
	}
	public synchronized String getState() {
		return state;
	}
	public synchronized void setState(String state) {
		this.state = state;
	}
	
}
