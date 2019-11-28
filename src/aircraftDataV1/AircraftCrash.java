package aircraftDataV1;

import java.io.File;
import java.io.IOException;

import aircraftDataV1.CSV.Serialisation;

public class AircraftCrash {
	
	private static Serialisation ser;
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Programme Starting....");
		
		ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		System.out.println("Programme Initialised.... \n");
		
		Menu.display();	

	}
	
	public static Serialisation getSer() {
		return ser;
	}

}
