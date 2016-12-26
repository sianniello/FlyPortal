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
	private int freeSeats;
	
	public Flight(String flight, String depAirport, String arrAirport,
			String dep_time, String company, String state, int freeSeats) {
		this.flight = flight;
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		this.freeSeats = freeSeats;
	}
	
	public Flight(String flight) {
		this.flight = flight;
	}

	public  String getFlight() {
		return flight;
	}
	public  void setFlight(String flight) {
		this.flight = flight;
	}
	public  String getDepAirport() {
		return depAirport;
	}
	public  void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}
	public  String getArrAirport() {
		return arrAirport;
	}
	public  void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}
	public  GregorianCalendar getDep_time() {
		return dep_time;
	}
	public  void setDep_time(GregorianCalendar dep_time) {
		this.dep_time = dep_time;
	}
	public  String getCompany() {
		return company;
	}
	public  void setCompany(String company) {
		this.company = company;
	}
	public  String getState() {
		return state;
	}
	public  void setState(String state) {
		this.state = state;
	}

	public String toString() {
		return "Flight [" + (flight != null ? "flight=" + flight + ", " : "")
				+ (depAirport != null ? "depAirport=" + depAirport + ", " : "")
				+ (arrAirport != null ? "arrAirport=" + arrAirport + ", " : "")
				+ (dep_time != null ? "dep_time=" + dep_time + ", " : "")
				+ (company != null ? "company=" + company + ", " : "")
				+ (state != null ? "state=" + state + ", " : "") + "freeSeats="
				+ freeSeats + "]";
	}
	
}
