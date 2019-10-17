package aircraftDataV1;

import java.io.File;
import java.io.FileNotFoundException;

import aircraftDataV1.CSV.Serialisation;

public class AircraftCrash {
	
	private static Serialisation ser;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		Menu.display();

	}
	
	public static Serialisation getSer() {
		return ser;
	}

}
