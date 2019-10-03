package aircraftDataV1;

import java.util.Scanner;

public class AircraftCrash {

	private static boolean menuLoop = true;
	private static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Setup main menu

		while (menuLoop) {
			switch (displayMainMenu()) {
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
			case "Q":
				menuLoop = false;
				break;
			default:
				System.out.println("Invalid choice entered, please try again.");
			}
		}

	}

	public static String displayMainMenu() {
		String choice = "";

		System.out.println("--Air Accident Data Analysis System");
		System.out.println("Pick:");
		System.out.println("1. Yearly data");
		System.out.println("2. Phases of flight");
		System.out.println("3. Phases of flight reports");
		System.out.println("4. Accident Summary");
		System.out.println("Q. Quit");
		System.out.println(">");
		choice = userInput.nextLine();
		
		return choice;
	}
}
