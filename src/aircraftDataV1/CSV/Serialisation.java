package aircraftDataV1.CSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Serialisation {

	public String fillInHere;
	private ArrayList<CSVObject> objects = new ArrayList<CSVObject>();

	public String toCSVString() {
		return fillInHere;
	}

	public Serialisation(File file) throws IOException {
/*
		// Profiling Code long start = System.currentTimeMillis();

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

		// Profiling Code long total = System.currentTimeMillis() - start;
		System.out.println("Executed serialisation read in : " + total + "ms");
*/
		// Profiling Code 
		long start = System.currentTimeMillis();

		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		while ((st = br.readLine()) != null) {
			String csvString = st;
			CSVObject obj = new CSVObject(csvString);
			objects.add(obj);
		}
		br.close();
		// Profiling Code 
		long total = System.currentTimeMillis() - start;
		System.out.println("Executed serialisation read in : " + total + "ms");
	}

	public ArrayList<CSVObject> getArrayList() {
		return objects;
	}

}
