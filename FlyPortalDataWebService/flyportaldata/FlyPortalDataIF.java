/**
 * FlyPortalDataIF.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package flyportaldata;

import java.util.LinkedList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface FlyPortalDataIF {
    
	@WebMethod
	public LinkedList<Airport> getAirports();
    
	@WebMethod
	public LinkedList<String> getAirlines();
}
