package aircraftDataV1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import aircraftDataV1.CSV.Serialisation;

public class Menu {

	private static boolean menuLoop = true;
	private static Scanner userInput = new Scanner(System.in);

	
	public static void display() {
		while (menuLoop) {
			switch (menuChoice()) {
			case "1":
				System.out.println("List of all years you have data from");
				break;
			case "2":
				System.out.println("List all phases of flight data");
				break;
			case "3":
				System.out.println("Display reports for specified phases of flight");
				break;
			case "4":
				System.out.println("List accidents(with additional features added)");
				break;
			case "X":
				try {
					Serialisation ser = new Serialisation(new File("src/aircraftDataV1/Data/aviationdata.csv"));
					System.out.println(ser.getArrayList().get(1).getString());

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
}
