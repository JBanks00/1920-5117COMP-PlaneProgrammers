package aircraftDataV1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import aircraftDataV1.CSV.Serialisation;

public class Menu {

	private static boolean menuLoop = true;
	private static Scanner userInput = new Scanner(System.in);

	
	public static void display() throws FileNotFoundException {
		while (menuLoop) {
			switch (menuChoice()) {
			case "1":
				System.out.println("Below are the years from which we have data:");
				
				Serialisation ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));
				
				ArrayList<String> years = new ArrayList<String>();
				
				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (!(years.contains(ser.getArrayList().get(i).getDate()))) {
						years.add(ser.getArrayList().get(i).getDate());
					}
				}
				Collections.sort(years);
				for (String string : years) {
					System.out.println(string);
				}
				
				break;
			case "2":
				System.out.println("List all phases of flight data");
				
				Serialisation ser2 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

				ArrayList<String> phases = new ArrayList<String>();
				
				for (int i = 0; i < ser2.getArrayList().size(); i++) {
					if (!(phases.contains(ser2.getArrayList().get(i).getPhases()))) {
						phases.add(ser2.getArrayList().get(i).getPhases());
					}
				}
				//Collections.sort(phases);
				for (String string : phases) {
					System.out.println(string);
				}
				
				break;
			case "3":
				System.out.println("Display reports for specified phases of flight");
				break;
			case "4":
				System.out.println("List accidents(with additional features added)");
				accidentSummary();
				break;
			case "X":
				try {
					Serialisation serx = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));
					System.out.println(serx.getArrayList().get(1).getString());

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.err.println("Error in loading Data File");
				}
					break;
			case "Q":
				menuLoop = false;
				break;
			default:
				System.out.println("Invalid choice entered, please try again.");
			}
		}
	}
	
	public static String menuChoice() {

		System.out.println("--Air Accident Data Analysis System");
		System.out.println("Pick:");
		System.out.println("1. Yearly data");
		System.out.println("2. Phases of flight");
		System.out.println("3. Phases of flight reports");
		System.out.println("4. Accident Summary");
		System.out.println("X. TEST OPTION");
		System.out.println("Q. Quit");
		System.out.println(">");
		String choice = userInput.nextLine();

		return choice.toUpperCase();
	}
	
	public static void accidentSummary() throws FileNotFoundException {

		System.out.println("Enter option to view:");
		System.out.println("i) killed everyone aboard");
		System.out.println("ii) were deadly, but survivors >= perished");
		System.out.println("iii) were not deadly, but resulted in serious or minor injuries");
		System.out.println("iv) resulted in no fatalities or injuries");
		System.out.println(">");
		String entry = userInput.nextLine();
		System.out.printf(entry);

		if (entry.equals("i")) {
			
			//killed everyone aboard
			//if serious minor & uninjured is 0 crash is fatal
			totalFatal();
			System.out.println("///////////////////////////////////////////// Total serious:");
			totalSeriousInjuries();
			System.out.println("///////////////////////////////////////////// Total minor:");
			totalMinorInjuries();
			
		
		
			
		if (entry.equals("ii")) {
		//i) were deadly, but survivors >= perished")
		//
		if (entry.equals("iii")) {
		//iii) were not deadly, but resulted in serious or minor injuries
		//
		if (entry.equals("iv")) {
			//iv) resulted in no fatalities or injuries"
			
			
			
			System.out.println("List all phases of flight data");
			
			
			
		Serialisation ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));
		System.out.println(ser.getArrayList().get(25).getString());
		}
		}

		}
		} else {
		System.out.println("Invalid choice entered, please try again.");
		}
		
	}

	public static void totalFatal() throws FileNotFoundException {
		// TODO Auto-generated method stub
	
		Serialisation ser3 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		
		
		ArrayList<String> totalFatal = new ArrayList<String>();
		
		for (int i = 0; i < ser3.getArrayList().size(); i++) {
			if (!(totalFatal.contains(ser3.getArrayList().get(i).getTotalFatalInjuries()))) {
				totalFatal.add(ser3.getArrayList().get(i).getTotalFatalInjuries());
			}
		}
		//Collections.sort(phases);
		for (String string : totalFatal) {
			System.out.println(string);
		}
	}
	
	public static void totalSeriousInjuries() throws FileNotFoundException {
		// TODO Auto-generated method stub
	
		Serialisation ser3 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		
		
		ArrayList<String> totalSerious = new ArrayList<String>();
		
		for (int i = 0; i < ser3.getArrayList().size(); i++) {
			if (!(totalSerious.contains(ser3.getArrayList().get(i).getTotalSeriousInjuries()))) {
				totalSerious.add(ser3.getArrayList().get(i).getTotalSeriousInjuries());
			}
		}
		//Collections.sort(phases);
		for (String string : totalSerious) {
			System.out.println(string);
		}
	}

	
	
	public static void totalMinorInjuries() throws FileNotFoundException {
		// TODO Auto-generated method stub
	
		Serialisation ser3 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		
		
		ArrayList<String> totalMinor = new ArrayList<String>();
		
		for (int i = 0; i < ser3.getArrayList().size(); i++) {
			if (!(totalMinor.contains(ser3.getArrayList().get(i).getTotalMinorInjuries()))) {
				totalMinor.add(ser3.getArrayList().get(i).getTotalMinorInjuries());
			}
		}
		//Collections.sort(phases);
		for (String string : totalMinor) {
			System.out.println(string);
		}
	}
	
	
	
	
}
