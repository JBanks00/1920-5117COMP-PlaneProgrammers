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
			case "5":
				regSearch();
				break;
			case "6":
				accidentRate();
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
		System.out.println("5. Plane Registration Seach");
		System.out.println("6. Calcualte Accident Rate (10 year)");
		System.out.println("X. TEST OPTION");
		System.out.println("Q. Quit");
		System.out.print("> ");
		String choice = userInput.nextLine();

		return choice.toUpperCase();
	}

	public static void regSearch() throws FileNotFoundException {
		ArrayList<CSVObject> planes = new ArrayList<CSVObject>();

		System.out.println("Please enter the desired plane Registration:");
		System.out.print("> ");

		String planeSearch = userInput.next();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (ser.getArrayList().get(i).getReg().equals(planeSearch)) {
				planes.add(ser.getArrayList().get(i));
			}
		}
		System.out.println("Event ID:        Event Date:     " + StringUtils.rightPad("Location:", 25)
				+ StringUtils.rightPad("Reg:", 8) + StringUtils.rightPad("Total Fatal:", 14)
				+ StringUtils.rightPad("Total Serious:", 16) + StringUtils.rightPad("Total Minor:", 14)
				+ StringUtils.rightPad("Total Uninjured", 17) + StringUtils.rightPad("Weather:", 10));

		for (CSVObject obj : planes) {
			System.out.println(obj.regInfo());
		}
	}

	public static void accidentRate() {
		System.out.println("Enter option to view:");
		System.out.println("i) Accident rate for 10 year period.");
		System.out.println("ii) Fatality count for 10 year period");
		System.out.print("> ");

		switch (userInput.nextLine().toLowerCase()) {
		case "i":
			System.out.println();
			break;
		case "ii":

			break;
		default:
			break;
		}

	}

	public static void accidentSummary() throws FileNotFoundException {

		System.out.println("Enter option to view:");
		System.out.println("i) Killed everyone aboard.");
		System.out.println("ii) Were deadly, but survivors >= perished.");
		System.out.println("iii) Were not deadly, but resulted in serious or minor injuries.");
		System.out.println("iv) Resulted in no fatalities or injuries.");
		System.out.print("> ");

		String entry = userInput.nextLine().toLowerCase();

		if (entry.equals("i")) {

			// killed everyone aboard
			// if serious minor & uninjured is 0 crash is fatal
			System.out.println("///////////////////////////////////////////// Total fatal:");
			totalFatal();
			System.out.println("///////////////////////////////////////////// Total serious:");
			totalSeriousInjuries();
			System.out.println("///////////////////////////////////////////// Total minor:");
			totalMinorInjuries();
			System.out.println("///////////////////////////////////////////// Total uninjured:");
			totalUninjured();
		} else if (entry.equals("ii")) {
			// i) were deadly, but survivors >= perished")

			// select one row of data
			// if has serious
			// if minor+uninjured is > serious

			ArrayList<CSVObject> objects = new ArrayList<CSVObject>();

			for (int i = 0; i < ser.getArrayList().size(); i++) {
				int x = (ser.getArrayList().get(i).getTotalUninjured())
						+ ser.getArrayList().get(i).getTotalMinorInjuries()
						+ ser.getArrayList().get(i).getTotalSeriousInjuries();

				if (ser.getArrayList().get(i).getTotalSeriousInjuries() < x) {
					objects.add(ser.getArrayList().get(i));

				}
			}

			for (CSVObject object : objects) {
				System.out.println(object.getReports());
			}

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

		ArrayList<Integer> totalFatal = new ArrayList<Integer>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalFatal.contains(ser.getArrayList().get(i).getTotalFatalInjuries()))) {
				totalFatal.add(ser.getArrayList().get(i).getTotalFatalInjuries());
			}
		}
		// Collections.sort(phases);
		System.out.println(totalFatal.size());
	}

	public static void totalSeriousInjuries() throws FileNotFoundException {

		Serialisation ser3 = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));

		ArrayList<Integer> totalSerious = new ArrayList<Integer>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalSerious.contains(ser.getArrayList().get(i).getTotalSeriousInjuries()))) {
				totalSerious.add(ser3.getArrayList().get(i).getTotalSeriousInjuries());
			}
		}
		// Collections.sort(phases);
		System.out.println(totalSerious.size());
	}

	public static void totalMinorInjuries() throws FileNotFoundException {
		// TODO Auto-generated method stub

		ArrayList<Integer> totalMinor = new ArrayList<Integer>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalMinor.contains(ser.getArrayList().get(i).getTotalMinorInjuries()))) {
				totalMinor.add(ser.getArrayList().get(i).getTotalMinorInjuries());
			}
		}
		// Collections.sort(phases);
		System.out.println(totalMinor.size());
	}

	public static void totalUninjured() throws FileNotFoundException {

		ArrayList<Integer> totalUninjured = new ArrayList<Integer>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (!(totalUninjured.contains(ser.getArrayList().get(i).getTotalUninjured()))) {
				totalUninjured.add(ser.getArrayList().get(i).getTotalUninjured());
			}
		}
		System.out.println(totalUninjured.size());
	}

}
