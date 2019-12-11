package aircraftDataV1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import javax.tools.OptionChecker;

import org.apache.commons.lang3.StringUtils;

import aircraftDataV1.CSV.CSVObject;
import aircraftDataV1.CSV.Serialisation;
import jdk.nashorn.internal.objects.annotations.Where;

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
		System.out.println("Q. Quit");
		System.out.print("> ");
		String choice = userInput.nextLine();

		return choice.toUpperCase();
	}

	public static void regSearch() throws FileNotFoundException {
		ArrayList<CSVObject> planes = new ArrayList<CSVObject>();

		System.out.println("Please enter the desired plane Registration:");
		System.out.print("> ");

		String planeSearch = userInput.nextLine();
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
			totalNoInjuries();
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

		if (userChoice.equalsIgnoreCase("yes")) {

			System.out.println("\nCHOSEN TO FILTER DATA");

			System.out.println("Enter option to view:");
			System.out.println("i) Accident rate for 10 year period.");
			System.out.println("ii) Phase of Flight");
			System.out.println("iii) Aircraft Make and Type");
			System.out.print("> ");
			String optionChoice = userInput.nextLine().toLowerCase();

			ArrayList<CSVObject> fatalAccidentRate = new ArrayList<CSVObject>();

			switch (optionChoice) {
			case "i":
				do {
					System.out.println("Enter the start year:");
					System.out.print("> ");
				} while (inputDate());

				for (int e = 0; e <= 10; e++) {

					for (int i = 0; i < ser.getArrayList().size(); i++) {
						if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

							if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

									if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
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
					System.out.println(csvObject.getReg() + " " + csvObject.getLocation());
				}
				System.out.println("Accidents =" + fatalAccidentRate.size());
				System.out.println();
				break;

			case "ii": {

				System.out.println("Enter phase of flight to filter by: ");
				System.out.print("> ");

				String phaseChoice = userInput.nextLine().toUpperCase();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() == 0) {

									if (ser.getArrayList().get(i).getPhases().equals(phaseChoice)) {

										fatalAccidentRate.add(ser.getArrayList().get(i));
									}
								}
							}
						}
					}
				}

				System.out.println("\nAccidents for phase " + phaseChoice + " = " + fatalAccidentRate.size());
				System.out.println();

			}
				System.out.println("\nWould you like to list the accidents Yes/No");

				String listChoice = userInput.nextLine().toLowerCase();
				if (listChoice.equals("yes")) {
					for (CSVObject csvObject : fatalAccidentRate) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}

				} else {
				}

				break;

			case "iii":
				System.out.println("Filter by Aircraft make and type:");

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() > 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() == 0) {
									fatalAccidentRate.add(ser.getArrayList().get(i));
								}
							}
						}
					}
				}

				for (int x = 0; x < fatalAccidentRate.size(); x++) {
					System.out.println(StringUtils.rightPad("Make: " + fatalAccidentRate.get(x).getMake(), 20)
							+ StringUtils.rightPad("  Model: " + fatalAccidentRate.get(x).getModel(), 10));
				}
				System.out.println("Total Accidents = " + fatalAccidentRate.size());

				break;
			}
		}

		else { // run as normal

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
			System.out.println("\nWould you like to list the accidents Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalFatal) {
					System.out.println(csvObject.getReg() + " " + csvObject.getLocation() + " "
							+ csvObject.getTotalFatalInjuries());
				}
			} else {
			}
		}
	}

	public static void totalDeadly() throws FileNotFoundException { 

		ArrayList<CSVObject> totalDeadly = new ArrayList<CSVObject>();

		System.out.println("\nWould you like to filter these results? Yes/No");
		System.out.println(">");
		String userChoice = userInput.nextLine().toLowerCase();

		if (userChoice.equalsIgnoreCase("yes")) {

			System.out.println("\nCHOSEN TO FILTER DATA");

			System.out.println("Enter option to view:");
			System.out.println("i) Accident rate for 10 year period.");
			System.out.println("ii) Phase of Flight");
			System.out.println("iii) Aircraft Make and Type");
			System.out.print("> ");
			String optionChoice = userInput.nextLine().toLowerCase();

			switch (optionChoice) {
			case "i": // filter 10 years
				do {
					System.out.println("Enter the start year:");
					System.out.print("> ");
				} while (inputDate());

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					int fatal = ser.getArrayList().get(i).getTotalFatalInjuries();

					int x = (ser.getArrayList().get(i).getTotalUninjured())
							+ ser.getArrayList().get(i).getTotalMinorInjuries()
							+ ser.getArrayList().get(i).getTotalSeriousInjuries();

					if (x != 0) {
						if (fatal != 0) {
							if (x > fatal) {

								if (ser.getArrayList().get(i).getYear()
										.equals(Integer.toString(Integer.parseInt(dateInput))))
									totalDeadly.add(ser.getArrayList().get(i));

							}
						}
					}
				}

				for (CSVObject csvObject : totalDeadly) {
					System.out.println(csvObject.getReg() + " " + csvObject.getLocation());
				}
				System.out.println("Total results for deadly accidents : " + totalDeadly.size());

				break;

			case "ii":// phase of flight

				System.out.println("Enter phase of flight to filter by: ");
				System.out.print("> ");

				String phaseChoice = userInput.nextLine().toUpperCase();

				for (int i = 0; i < ser.getArrayList().size(); i++) {

					int fatal = ser.getArrayList().get(i).getTotalFatalInjuries();

					int x = (ser.getArrayList().get(i).getTotalUninjured())
							+ ser.getArrayList().get(i).getTotalMinorInjuries()
							+ ser.getArrayList().get(i).getTotalSeriousInjuries();

					if (x != 0) {
						if (fatal != 0) {
							if (x > fatal) {
								if (ser.getArrayList().get(i).getPhases().equals(phaseChoice)) {

									totalDeadly.add(ser.getArrayList().get(i));
								}
							}
						}
					}
				}

				System.out.println("\nTotal results for accidents with no fatalities in phase: " + phaseChoice + " = "
						+ totalDeadly.size());
				System.out.println("\nWould you like to list the accidents Yes/No");

				String listChoice = userInput.nextLine().toLowerCase();
				if (listChoice.equals("yes")) {
					for (CSVObject csvObject : totalDeadly) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}

				}

				break;

			case "iii": // by aircraft make and type

				System.out.println("Filter by Aircraft make and type:");

				for (int i = 0; i < ser.getArrayList().size(); i++) {

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

				for (int x = 0; x < totalDeadly.size(); x++) {
					System.out.println(StringUtils.rightPad("Make: " + totalDeadly.get(x).getMake(), 20)
							+ StringUtils.rightPad("  Model: " + totalDeadly.get(x).getModel(), 10));

				}
				System.out.println("Total deadly accidents = " + totalDeadly.size());
				break;
			}
		}

		else if (userChoice.equalsIgnoreCase("no")) { // run normal

			for (int i = 0; i < ser.getArrayList().size(); i++) {

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

			System.out.println(
					"\noTotal results for fatal accidents where survivors >= perished : " + totalDeadly.size());

			System.out.println("\nWould you like to list the accidents? Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalDeadly) {
					System.out.println(csvObject.AccidentNumber + " " + csvObject.Location);
				}
			}
		}

	}

	public static void totalNotDeadly() throws FileNotFoundException { 
																		

		ArrayList<CSVObject> totalNotDeadly = new ArrayList<CSVObject>();

		System.out.println("\nWould you like to filter these results? Yes/No");
		System.out.println(">");
		String userChoice = userInput.nextLine().toLowerCase();

		if (userChoice.equalsIgnoreCase("yes")) {

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
				for (int e = 0; e <= 10; e++) {
					for (int i = 0; i < ser.getArrayList().size(); i++) {
						if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalMinorInjuries() >= 0) {

								if (ser.getArrayList().get(i).getTotalSeriousInjuries() >= 0) {

									if (ser.getArrayList().get(i).getTotalUninjured() >= 0) {

										if (ser.getArrayList().get(i).getYear()
												.equals(Integer.toString(Integer.parseInt(dateInput) + e))) {
											totalNotDeadly.add(ser.getArrayList().get(i));
										}
									}
								}
							}
						}
					}
				}

				System.out.println(
						"\nTotal results for fewer fatalities than injuries in an accident: " + totalNotDeadly.size());
				System.out.println("\nWould you like to list the accidents Yes/No");
				String input = userInput.nextLine().toLowerCase();
				if (input.equals("yes")) {
					for (CSVObject csvObject : totalNotDeadly) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}
				}

				else {
				}
				break;

			case "ii":

				System.out.println("Enter phase of flight to filter by: ");
				System.out.print("> ");

				String phaseChoice = userInput.nextLine().toUpperCase();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() >= 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() >= 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() >= 0) {

									if (ser.getArrayList().get(i).getPhases().equals(phaseChoice)) {

										totalNotDeadly.add(ser.getArrayList().get(i));
									}
								}
							}
						}
					}
				}
				System.out.println(
						"\nTotal results for fewer fatalities than injuries in an accident: " + totalNotDeadly.size());
				System.out.println("\nWould you like to list the accidents Yes/No");
				String listInput = userInput.nextLine().toLowerCase();
				if (listInput.equals("yes")) {
					for (CSVObject csvObject : totalNotDeadly) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}

				} else {
				}

				break;

			case "iii":
				System.out.println("Filter by Aircraft make and type:");

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

				for (int x = 0; x < totalNotDeadly.size(); x++) {
					System.out.println(StringUtils.rightPad("Make: " + totalNotDeadly.get(x).getMake(), 20)
							+ StringUtils.rightPad("  Model: " + totalNotDeadly.get(x).getModel(), 10));
				}
				System.out.println("Total Accidents = " + totalNotDeadly.size());
				break;
			}

		} else {

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
			System.out.println(
					"\nTotal results for fewer fatalities than injuries in an accident: " + totalNotDeadly.size());
			System.out.println("\nWould you like to list the accidents Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalNotDeadly) {
					System.out.println(csvObject.getReg() + " " + csvObject.Location);
				}
			}
		}
	}

	public static void totalNoInjuries() throws FileNotFoundException {
																	
		ArrayList<CSVObject> totalNoInjuries = new ArrayList<CSVObject>();

		System.out.println("\nWould you like to filter these results? Yes/No");
		System.out.println(">");
		String userChoice = userInput.nextLine().toLowerCase();

		if (userChoice.equalsIgnoreCase("yes")) {

			System.out.println("\nCHOSEN TO FILTER DATA");

			System.out.println("Enter option to view:");
			System.out.println("i) Accident rate for 10 year period.");
			System.out.println("ii) Phase of Flight");
			System.out.println("iii) Aircraft Make and Type");
			System.out.print("> ");
			String optionChoice = userInput.nextLine().toLowerCase();

			switch (optionChoice) {
			case "i": //
				do {
					System.out.println("Enter the start year:");
					System.out.print("> ");
				} while (inputDate());

				for (int e = 0; e <= 10; e++) {

					for (int i = 0; i < ser.getArrayList().size(); i++) {
						if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

									if (ser.getArrayList().get(i).getTotalUninjured() > 0) {

										if (ser.getArrayList().get(i).getYear()
												.equals(Integer.toString(Integer.parseInt(dateInput) + e))) {
											totalNoInjuries.add(ser.getArrayList().get(i));
										}
									}
								}
							}
						}
					}
				}

				System.out.println("\nTotal results for accidents with no fatalities: " + totalNoInjuries.size());
				System.out.println("\nWould you like to list the accidents Yes/No");
				String input = userInput.nextLine().toLowerCase();
				if (input.equals("yes")) {
					for (CSVObject csvObject : totalNoInjuries) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}
				}
				break;
			case "ii":

				System.out.println("Enter phase of flight to filter by: ");
				System.out.print("> ");

				String phaseChoice = userInput.nextLine().toUpperCase();

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() > 0) {

									if (ser.getArrayList().get(i).getPhases().equals(phaseChoice)) {

										totalNoInjuries.add(ser.getArrayList().get(i));
									}

								}
							}
						}
					}
				}

				System.out.println("\nTotal results for accidents with no fatalities in phase: " + phaseChoice + " = "
						+ totalNoInjuries.size());
				System.out.println("\nWould you like to list the accidents Yes/No");

				String listChoice = userInput.nextLine().toLowerCase();
				if (listChoice.equals("yes")) {
					for (CSVObject csvObject : totalNoInjuries) {
						System.out.println(csvObject.getReg() + " " + csvObject.Location);
					}

				}

				break;
			case "iii":

				System.out.println("Filter by Aircraft make and type:");

				for (int i = 0; i < ser.getArrayList().size(); i++) {
					if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

								if (ser.getArrayList().get(i).getTotalUninjured() > 0) {

									totalNoInjuries.add(ser.getArrayList().get(i));

								}
							}
						}
					}
				}
				for (int x = 0; x < totalNoInjuries.size(); x++) {
					System.out.println(StringUtils.rightPad("Make: " + totalNoInjuries.get(x).getMake(), 20)
							+ StringUtils.rightPad("  Model: " + totalNoInjuries.get(x).getModel(), 10));
				}
				System.out.println(totalNoInjuries.size());
				break;
			}
		} else { // end case, just as normal

			for (int i = 0; i < ser.getArrayList().size(); i++) {
				if (ser.getArrayList().get(i).getTotalFatalInjuries() == 0) {

					if (ser.getArrayList().get(i).getTotalMinorInjuries() == 0) {

						if (ser.getArrayList().get(i).getTotalSeriousInjuries() == 0) {

							if (ser.getArrayList().get(i).getTotalUninjured() > 0) {
								totalNoInjuries.add(ser.getArrayList().get(i));
							}
						}
					}
				}
			}
			System.out.println("\nTotal results for accidents with no fatalities: " + totalNoInjuries.size());
			System.out.println("\nWould you like to list the accidents Yes/No");
			String input = userInput.nextLine().toLowerCase();
			if (input.equals("yes")) {
				for (CSVObject csvObject : totalNoInjuries) {
					System.out.println(csvObject.getReg() + " " + csvObject.Location);
				}
			}
		}
	}
}
