package flight;


public class Flight {

	private String flight;
	private String depAirport;
	private String arrAirport;
	public String depTime;
	private String company;
	private String state;
	private int freeSeats;
	private double price;
	
	public Flight(String flight, String depAirport, String arrAirport, String depTime, String company, String state, int freeSeats) {
		this.flight = flight;
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		this.depTime = depTime;
		this.company = company;
		this.state = state;
		this.freeSeats = freeSeats;
	}
	
	public Flight(String flight, String depAirport, String arrAirport,
			String depTime, String company, String state, int freeSeats,
			double price) {
		this.flight = flight;
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		this.depTime = depTime;
		this.company = company;
		this.state = state;
		this.freeSeats = freeSeats;
		this.price = price;
	}

	public Flight(String flight) {
		this.flight = flight;
	}
	
	public  String getFlight() {
		return flight;
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
	public  String getDepTime() {
		return depTime;
	}
	public  void setDep_time(String dep_time) {
		this.depTime = dep_time;
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
	
	public int getFreeSeats() {
		return freeSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return (flight != null ? "Flight=" + flight + ", " : "")
				+ (depAirport != null ? "Departure airport=" + depAirport + ", " : "")
				+ (arrAirport != null ? "Arrival airport=" + arrAirport + ", " : "")
				+ (depTime != null ? "Departure time=" + depTime + ", " : "")
				+ (company != null ? "Company=" + company + ", " : "")
				+ (state != null ? "State=" + state + ", " : "") + "Free seats="
				+ freeSeats;
	}
	
}
