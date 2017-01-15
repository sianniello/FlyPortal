package flyportaldata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import javax.jws.WebService;

@WebService(endpointInterface="flyportaldata.FlyPortalDataIF")  
public class FlyPortalData implements FlyPortalDataIF{

	static BufferedReader br = null;
	static FileReader fr = null;

	public LinkedList<Airport> getAirports() {
		final String FILENAME = "C://Users//Stefano//git//FlyPortal//FlyPortalUtilities//resources//airports.dat";
		LinkedList<Airport> airportsList = new LinkedList<Airport>();
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replaceAll("/[^A-Za-z0-9 ^,]/", "");
				String a[] = sCurrentLine.split(",");
				airportsList.add(new Airport(a[1], a[2], a[3]));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Collections.sort(airportsList, Airport.AirportCityComparator);
		return airportsList;
	}

	public LinkedList<String> getAirlines() {
		final String FILENAME = "C://Users//Stefano//git//FlyPortal//FlyPortalUtilities//resources//airlines.dat";
		LinkedList<String> airlinesList = new LinkedList<String>();
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", "");
				sCurrentLine = sCurrentLine.replaceAll("[^A-Za-z0-9 ^,]", "");
				String a[] = sCurrentLine.split(",");
				if(!a[1].isEmpty())
					airlinesList.add(a[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Collections.sort(airlinesList);
		return airlinesList;
	}

}

