package flyPortalData;

import java.util.Comparator;

public class Airport {

	private String name, city, country;

	public Airport(String name, String city, String country) {
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Airport [" + (name != null ? "name=" + name + ", " : "")
				+ (city != null ? "city=" + city + ", " : "")
				+ (country != null ? "country=" + country : "") + "]";
	}

	public static Comparator<Airport> AirportCityComparator = new Comparator<Airport>() {

		public int compare(Airport a1, Airport a2) {
		   String AirportCity1 = a1.getCity().toUpperCase();
		   String AirportCity2 = a2.getCity().toUpperCase();

		   //ascending order
		   return AirportCity1.compareTo(AirportCity2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }};

}
