package aircraftDataV1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import aircraftDataV1.CSV.CSVObject;
import aircraftDataV1.CSV.Serialisation;

public class Menu {

	private static boolean menuLoop = true;
	private static Scanner userInput = new Scanner(System.in);
	private static Serialisation ser = AircraftCrash.getSer();

	public static void display() throws FileNotFoundException {

		while (menuLoop) {
			switch (menuChoice()) {
			case "1":
				System.out.println("Below are the years from which we have data:");

				ArrayList<String> years = new ArrayList<String>();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (!(years.contains(ser.getArrayList().get(i).getYear()))) {
						years.add(ser.getArrayList().get(i).getYear());
					}
				}
				Collections.sort(years);
				for (String string : years) {
					System.out.println(string);
				}

				break;
			case "2":
				System.out.println("List all phases of flight data");

				ArrayList<String> phases = new ArrayList<String>();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (!(phases.contains(ser.getArrayList().get(i).getPhases()))) {
						// CSVObject object = ser2.getArrayList().get(i);
						// System.out.println(object.toString());
						phases.add(ser.getArrayList().get(i).getPhases());
					}
				}
				// Collections.sort(phases);
				for (String string : phases) {
					System.out.println(string);
				}

				break;
			case "3":
				ArrayList<CSVObject> reports = new ArrayList<CSVObject>();

				System.out.println("Please specify a phase of flight to return associated reports:");
				System.out.print("> ");

				String selectionPhase = userInput.nextLine().toUpperCase();

				System.out.println("And the year:");
				System.out.print("> ");

				String selectionDate = userInput.nextLine();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getPhases().equals(selectionPhase)
							&& ser.getArrayList().get(i).getYear().equals(selectionDate)) {
						reports.add(ser.getArrayList().get(i));
					}
				}
				if (reports.size() == 0) {
					System.out.println("No Matching Data");
					break;
				}

				System.out.println("Event ID:        Event Date:     " + StringUtils.rightPad("Location:", 25)
						+ StringUtils.rightPad("Reg:", 8) + StringUtils.rightPad("Total Fatal:", 14)
						+ StringUtils.rightPad("Total Serious:", 16) + StringUtils.rightPad("Total Minor:", 14)
						+ StringUtils.rightPad("Total Uninjured", 17) + StringUtils.rightPad("Weather:", 10));
				for (CSVObject obj : reports) {
					System.out.println(obj.genReport());
				}

				break;
			case "4":
				System.out.println("List accidents(with additional features added)");
				accidentSummary();
				break;
			case "X":
				System.out.println(ser.getArrayList().get(1).getString());
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

		System.out.println("\nAir Accident Data Analysis System");
		System.out.println("Pick:");
		System.out.println("1. Yearly data");
		System.out.println("2. Phases of flight");
		System.out.println("3. Phases of flight reports");
		System.out.println("4. Accident Summary");
		System.out.println("X. TEST OPTION");
		System.out.println("Q. Quit");
		System.out.print("> ");
		String choice = userInput.nextLine();

		return choice.toUpperCase();
	}

	public static void accidentSummary() throws FileNotFoundException {

		System.out.println("Enter option to view:");
		System.out.println("i) killed everyone aboard");
		System.out.println("ii) were deadly, but survivors >= perished");
		System.out.println("iii) were not deadly, but resulted in serious or minor injuries");
		System.out.println("iv) resulted in no fatalities or injuries");
		System.out.print("> ");
		String entry = userInput.nextLine();
		System.out.printf(entry);

		if (entry.equals("i")) {

			// killed everyone aboard
			// if serious minor & uninjured is 0 crash is fatal
			totalFatal();
			System.out.println("///////////////////////////////////////////// Total serious:");
			totalSeriousInjuries();
			System.out.println("///////////////////////////////////////////// Total minor:");
			totalMinorInjuries();

			if (entry.equals("ii")) {
				// i) were deadly, but survivors >= perished")
				//
				if (entry.equals("iii")) {
					// iii) were not deadly, but resulted in serious or minor injuries
					//
					if (entry.equals("iv")) {
						// iv) resulted in no fatalities or injuries"

						System.out.println("List all phases of flight data");

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

		ArrayList<String> totalFatal = new ArrayList<String>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalFatal.contains(ser.getArrayList().get(i).getTotalFatalInjuries()))) {
				totalFatal.add(ser.getArrayList().get(i).getTotalFatalInjuries());
			}
		}
		// Collections.sort(phases);
		for (String string : totalFatal) {
			System.out.println(string);
		}
	}

	public static void totalSeriousInjuries() throws FileNotFoundException {
		// TODO Auto-generated method stub

		Serialisation ser3 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		ArrayList<String> totalSerious = new ArrayList<String>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalSerious.contains(ser.getArrayList().get(i).getTotalSeriousInjuries()))) {
				totalSerious.add(ser3.getArrayList().get(i).getTotalSeriousInjuries());
			}
		}
		// Collections.sort(phases);
		for (String string : totalSerious) {
			System.out.println(string);
		}
	}

	public static void totalMinorInjuries() throws FileNotFoundException {
		// TODO Auto-generated method stub

		ArrayList<String> totalMinor = new ArrayList<String>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalMinor.contains(ser.getArrayList().get(i).getTotalMinorInjuries()))) {
				totalMinor.add(ser.getArrayList().get(i).getTotalMinorInjuries());
			}
		}
		// Collections.sort(phases);
		for (String string : totalMinor) {
			System.out.println(string);
		}
	}

}
