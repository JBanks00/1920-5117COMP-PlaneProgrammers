package aircraftDataV2;

import java.io.File;
import java.io.IOException;

import aircraftDataV1.CSV.Serialisation;

public class AircraftCrash {
	
	private static Serialisation ser;
	
	public static void main(String[] args) throws IOException {
		
		System.out.print("Programme");
		System.err.print(" V2 ");
		System.out.println("Starting....");
		
		ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		System.out.println("Programme Initialised.... \n");
		
		Menu.display();	

	}
	
	public static Serialisation getSer() {
		return ser;
	}

}
