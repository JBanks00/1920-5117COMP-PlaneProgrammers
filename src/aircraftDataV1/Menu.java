package aircraftDataV1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

	public static String dateInput;

	private static boolean inputDate() {
		dateInput = userInput.nextLine().toLowerCase();
		if (dateInput.length() == 4) {
			try {
				Integer.parseInt(dateInput);
				return false;
			} catch (Exception e) {
				System.out.println("Invalid entry");
				return true;
			}
		} else {
			System.out.println("Invalid entry");
			return true;
		}

	}

	public static void accidentRate() {
		System.out.println("Enter option to view:");
		System.out.println("i) Accident rate for 10 year period.");
		System.out.println("ii) Fatality count for 10 year period");
		System.out.print("> ");

		switch (userInput.nextLine().toLowerCase()) {
		case "i":
			do {
				System.out.println("Enter the start year:");
				System.out.print("> ");

			} while (inputDate());

			ArrayList<CSVObject> accidents = new ArrayList<CSVObject>();
			for (int i = 0; i <= 10; i++) {
				for (int j = 0; j < ser.getArrayList().size(); j++) {
					if (ser.getArrayList().get(j).getYear().equals(Integer.toString(Integer.parseInt(dateInput) + i))) {
						accidents.add(ser.getArrayList().get(j));
					}
				}
			}

			HashMap<String, Integer> frequencymap = new HashMap<String, Integer>();

			for (CSVObject obj : accidents) {
				String make = obj.getMake() + " " + obj.getModel();
				if (frequencymap.containsKey(make)) {
					frequencymap.put(make, frequencymap.get(make) + 1);
				} else {
					frequencymap.put(make, 1);
				}
			}

			int val = 0;

			for (String s : frequencymap.keySet()) {
				String key = s;
				int value = Integer.parseInt(frequencymap.get(s).toString());
				if (value > val) {
					val = value;
				}
			}
			System.out.println("The following plane(s) type(s) have the most recorded accidents.");
			for (String s : frequencymap.keySet()) {
				String key = s;
				int value = Integer.parseInt(frequencymap.get(s).toString());
				if (value == val) {
					System.out.println(key);
				}
			}

			System.out.println();
			break;
		case "ii":
			do {
				System.out.println("Enter the start year:");
				System.out.print("> ");

			} while (inputDate());

			ArrayList<CSVObject> fatalities = new ArrayList<CSVObject>();
			for (int i = 0; i <= 10; i++) {
				for (int j = 0; j < ser.getArrayList().size(); j++) {
					if (ser.getArrayList().get(j).getYear().equals(Integer.toString(Integer.parseInt(dateInput) + i))) {
						if (ser.getArrayList().get(j).getTotalFatalInjuries() > 0) {
							fatalities.add(ser.getArrayList().get(j));
						}
					}
				}
			}

			HashMap<String, Integer> frequencymap2 = new HashMap<String, Integer>();

			for (CSVObject obj : fatalities) {
				String make = obj.getMake() + " " + obj.getModel();
				frequencymap2.put(make, obj.getTotalFatalInjuries());

			}

			int val2 = 0;

			for (String s : frequencymap2.keySet()) {
				String key = s;
				int value = Integer.parseInt(frequencymap2.get(s).toString());
				if (value > val2) {
					val2 = value;
				}
			}
			System.out.println("The following plane(s) types(s) have the most recorded fatalities.");
			for (String s : frequencymap2.keySet()) {
				String key = s;
				int value = Integer.parseInt(frequencymap2.get(s).toString());
				if (value == val2) {
					System.out.println(key + " with " + val2 + " fatalities");
				}
			}

			System.out.println();
			break;
		default:
			break;
		}

	}

	public static void TaskE() {
		System.out.println("Enter option to view:");
		System.out.println("i) Accident rate for 10 year period.");
		System.out.println("ii) Phase of Flight");
		System.out.println("iii) Aircraft Make and Type");
		System.out.print("> ");
		String optionChoice = userInput.nextLine().toLowerCase();

		switch (optionChoice) {
		case "i":
			do {
				System.out.println("Enter the start year:");
				System.out.print("> ");
			} while (inputDate());

			// System.out.println(dateInput);
			ArrayList<CSVObject> accidents = new ArrayList<CSVObject>();
			int date = Integer.parseInt(dateInput);
			System.out.println("Years chosen to view data:");

			ArrayList<CSVObject> totalFatal = new ArrayList<CSVObject>();
			for (int e = 0; e <= 10; e++) {
				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
									// IF DATE IS ON LIST, OUTPUT
									if (ser.getArrayList().get(i).getYear()
											.equals(Integer.toString(Integer.parseInt(dateInput) + e)))

										totalFatal.add(ser.getArrayList().get(i));
								}
							}
						}
					}
				}

				for (CSVObject csvObject : totalFatal) {
					System.out.println(csvObject.Location);
				}

				System.out.println("Accidents " + totalFatal.size());

				System.out.println();
			}
			break;

		/*
		 * case "ii": do
		 * {System.out.println("Enter the start year:");System.out.print("> "); } while
		 * (inputDate()); int fatalities = 0;for (int i = 0; i <= 10; i++) { for (int j
		 * = 0; j < ser.getArrayList().size(); j++) { if
		 * (ser.getArrayList().get(j).getYear().equals(Integer.toString(Integer.parseInt
		 * (dateInput) + i))) { fatalities +=
		 * ser.getArrayList().get(j).getTotalFatalInjuries();} }}
		 * System.out.println("Fatalities " + fatalities); break;default:break;
		 */
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
			System.out.println("I: Everyone onboard killed");
			totalFatal();

		} else if (entry.equals("ii")) {
			System.out.println("II: Deadly, but survivors >= perished");
			totalDeadly();

		} else if (entry.equals("iii")) {
			System.out.println("OPTION III: Not deadly, but resulted in serious or minor injuries");
			totalNotDeadly();

		} else if (entry.equals("iv")) {
			System.out.println("OPTION IV: No fatalities");
			totalNoFatalities();
		}

		else {
			System.out.println("Invalid choice entered, please try again.");
		}
	}

	public static void totalFatal() throws FileNotFoundException {

		ArrayList<CSVObject> totalFatal = new ArrayList<CSVObject>();

		System.out.println("\nWould you like to filter these results? Yes/No");
		System.out.println(">");
		String userChoice = userInput.nextLine().toLowerCase();

		if (userChoice.equalsIgnoreCase("no")) { // run normal

			for (int i = 0; i < ser.getArrayList().size(); i++) {
				if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

					if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
								totalFatal.add(ser.getArrayList().get(i));
							}
						}
					}
				}
			}

			System.out.println("Total results for fatal accidents : " + totalFatal.size());
			System.out.println("Would you like to list the accidents Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalFatal) {
					System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
				}
			}
		}

		else if (userChoice.equalsIgnoreCase("yes")) {

			System.out.println("\nCHOSEN TO FILTER DATA");

			System.out.println("Enter option to view:");
			System.out.println("i) Accident rate for 10 year period.");
			System.out.println("ii) Phase of Flight");
			System.out.println("iii) Aircraft Make and Type");
			System.out.print("> ");
			String optionChoice = userInput.nextLine().toLowerCase();

			switch (optionChoice) {
			case "i":
				do {
					System.out.println("Enter the start year:");
					System.out.print("> ");
				} while (inputDate());

				ArrayList<CSVObject> fatalAccidentRate = new ArrayList<CSVObject>();
				for (int e = 0; e <= 10; e++) {

					for (int i = 0; i < ser.getArrayList().size(); i++) {
						if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

							if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

									if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
										// IF DATE IS ON LIST, OUTPUT
										if (ser.getArrayList().get(i).getYear()
												.equals(Integer.toString(Integer.parseInt(dateInput) + e)))
											fatalAccidentRate.add(ser.getArrayList().get(i));
									}
								}
							}
						}
					}
				}

				for (CSVObject csvObject : fatalAccidentRate) {
					System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
				}
				System.out.println("Accidents " + fatalAccidentRate.size());
				System.out.println();
			}
		}
	}

	public static void totalDeadly() throws FileNotFoundException {

		System.out.println("\nWould you like to filter these results? Yes/No");
		System.out.println(">");
		String userChoice = userInput.nextLine().toLowerCase();

		if (userChoice.equalsIgnoreCase("no")) { // run normal

			ArrayList<CSVObject> totalDeadly = new ArrayList<CSVObject>();

			for (int i = 0; i < ser.getArrayList().size(); i++) {
				// if(ser.getArrayList().get(i).getTotalFatalInjuries()>0) {
				int fatal = ser.getArrayList().get(i).getTotalFatalInjuries();

				int x = ser.getArrayList().get(i).getTotalUninjured()
						+ ser.getArrayList().get(i).getTotalMinorInjuries()
						+ ser.getArrayList().get(i).getTotalSeriousInjuries();

				if (x != 0) {
					if (fatal != 0) {
						if (x > fatal) {

							totalDeadly.add(ser.getArrayList().get(i));
						}
					}

				}
			}

			System.out.println("Total results for fatal accidents where survivors >= perished : " + totalDeadly.size());
			System.out.println("Would you like to list the accidents Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalDeadly) {
					System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
				}
			}

		} else if (userChoice.equalsIgnoreCase("yes")) {

			System.out.println("\nCHOSEN TO FILTER DATA");

			System.out.println("Enter option to view:");
			System.out.println("i) Accident rate for 10 year period.");
			System.out.println("ii) Phase of Flight");
			System.out.println("iii) Aircraft Make and Type");
			System.out.print("> ");
			String optionChoice = userInput.nextLine().toLowerCase();

			switch (optionChoice) {
			case "i":
				do {
					System.out.println("Enter the start year:");
					System.out.print("> ");
				} while (inputDate());

				ArrayList<CSVObject> fatalAccidentRate = new ArrayList<CSVObject>();
				for (int e = 0; e <= 10; e++) {

					for (int i = 0; i < ser.getArrayList().size(); i++) {
						if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

							if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

									if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
										// IF DATE IS ON LIST, OUTPUT
										if (ser.getArrayList().get(i).getYear()
												.equals(Integer.toString(Integer.parseInt(dateInput) + e)))
											fatalAccidentRate.add(ser.getArrayList().get(i));
									}
								}
							}
						}

					}
				}

				// Deadly, but survivors >= perished
				ArrayList<CSVObject> totalDeadly = new ArrayList<CSVObject>();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					// if(ser.getArrayList().get(i).getTotalFatalInjuries()>0) {
					int fatal = ser.getArrayList().get(i).getTotalFatalInjuries();

					int x = (ser.getArrayList().get(i).getTotalUninjured())
							+ ser.getArrayList().get(i).getTotalMinorInjuries()
							+ ser.getArrayList().get(i).getTotalSeriousInjuries();

					if (x != 0) {
						if (fatal != 0) {
							if (x > fatal) {

								totalDeadly.add(ser.getArrayList().get(i));
							}
						}
					}
				}

				for (CSVObject csvObject : totalDeadly) {
					System.out.println(csvObject.Location);
				}
				System.out.println("Total results for deadly accidents : " + totalDeadly.size());
			}

		}

	}

	public static void totalNoFatalities() throws FileNotFoundException {

		ArrayList<CSVObject> totalNoFatalities = new ArrayList<CSVObject>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

				if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

					if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalUninjured() > 0) {
							totalNoFatalities.add(ser.getArrayList().get(i));
						}
					}
				}
			}
		}

		System.out.println("Total results for accidents with no fatalities: " + totalNoFatalities.size());
		System.out.println("\nWould you like to list the accidents Yes/No");
		String input = userInput.nextLine().toLowerCase();
		if (input.equals("yes")) {
			for (CSVObject csvObject : totalNoFatalities) {
				System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
			}
		}
	}

	public static void totalNotDeadly() throws FileNotFoundException {

		ArrayList<CSVObject> totalNotDeadly = new ArrayList<CSVObject>();

		for (int i = 0; i < ser.getArrayList().size(); i++) {
			if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

				if (ser.getArrayList().get(i).getTotalMinorInjuries() >= 0) {

					if (ser.getArrayList().get(i).getTotalSeriousInjuries() >= 0) {

						if (ser.getArrayList().get(i).getTotalUninjured() >= 0) {
							totalNotDeadly.add(ser.getArrayList().get(i));
						}
					}
				}
			}
		}
		System.out
				.println("\nTotal results for fewer fatalities than injuries in an accident: " + totalNotDeadly.size());
		System.out.println("\nWould you like to list the accidents Yes/No");
		String input = userInput.nextLine().toLowerCase();
		if (input.equals("yes")) {
			for (CSVObject csvObject : totalNotDeadly) {
				System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
			}
		}

	}

}
