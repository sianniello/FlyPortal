package flyportal;

import java.util.LinkedList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import flyPortalData.Airport;
import flyPortalData.FlyPortalData;

@WebService
public class FlyPortalWebServices {

	@WebMethod
	public LinkedList<Airport> getAirports() {
		return new FlyPortalData().getAirports();
	}
	
	@WebMethod
	public LinkedList<String> getAirlines() {
		return new FlyPortalData().getAirlines();
	}
	
}
