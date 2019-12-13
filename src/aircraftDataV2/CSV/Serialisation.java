package aircraftDataV2.CSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Serialisation {

	public String fillInHere;
	private ArrayList<CSVObject> objects = new ArrayList<CSVObject>();

	public String toCSVString() {
		return fillInHere;
	}

	public Serialisation(File data) throws IOException {

		
		Scanner scanner = new Scanner(data);
		if (data.canRead()) {
			scanner.nextLine();
			while (scanner.hasNext()) {
				String csvString = scanner.nextLine();
				CSVObject obj = new CSVObject(csvString);
				objects.add(obj);
			}
		} else {
			System.out.println("Error reading file");
		}
		scanner.close();

		
		
	}

	public ArrayList<CSVObject> getArrayList() {
		return objects;
	}

}