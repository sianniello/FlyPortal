package flyPortalData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class FlyPortalData {

	static BufferedReader br = null;
	static FileReader fr = null;

	public LinkedList<Airport> getAirports() {
		ClassLoader classLoader = getClass().getClassLoader();
		final String FILENAME = classLoader.getResource("airports.dat").getFile();
		LinkedList<Airport> airportsList = new LinkedList<Airport>();
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replace("\"", "");
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
		ClassLoader classLoader = getClass().getClassLoader();
		final String FILENAME = classLoader.getResource("airlines.dat").getFile();
		LinkedList<String> airlinesList = new LinkedList<String>();
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replace("\"", "");
				String a[] = sCurrentLine.split(",");
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


