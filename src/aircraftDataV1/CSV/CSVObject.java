package aircraftDataV1.CSV;

import java.util.List;
import java.util.ArrayList;

public class CSVObject {
	public enum ParsePhase {
		QUOTES, CHARS
	};

	public String EventID, InvestigationType, AccidentNumber, EventDate, Location, Country, Latitude, Longitude,
			AirportCode, AirportName, InjurySeverity, AircraftDamage, AircraftCategory, RegistrationNumber, Make, Model,
			AmateurBuilt, NumberOfEngines, EngineType, FARDescription, Schedule, PurposeOfFlight, AirCarrier,
			TotalFatalInjuries, TotalSeriousInjuries, TotalMinorInjuries, TotalUninjured, WeatherCondition,
			BroadPhaseOfFlight, ReportStatus, PublicationDate;

	public CSVObject(String csv) {
		List<String> splits = splitCSV(csv);
		//System.out.println(csv);
		//System.out.println(splits.get(30));
		
//		String[] split = csv.split("((?<! ),(?! ))", -1);
		
		EventID = splits.get(0);
		InvestigationType = splits.get(1);
		AccidentNumber = splits.get(2);
		EventDate = splits.get(3);
		Location = splits.get(4);
		Country = splits.get(5);
		Latitude = splits.get(6);
		Longitude = splits.get(7);
		AirportCode = splits.get(8);
		AirportName = splits.get(9);
		InjurySeverity = splits.get(10);
		AircraftDamage = splits.get(11);
		AircraftCategory = splits.get(12);
		RegistrationNumber = splits.get(13);
		Make = splits.get(14);
		Model = splits.get(15);
		AmateurBuilt = splits.get(16);
		NumberOfEngines = splits.get(17);
		EngineType = splits.get(18);
		FARDescription = splits.get(19);
		Schedule = splits.get(20);
		PurposeOfFlight = splits.get(21);
		AirCarrier = splits.get(22);
		TotalFatalInjuries = splits.get(23);
		TotalSeriousInjuries = splits.get(24);
		TotalMinorInjuries = splits.get(25);
		TotalUninjured = splits.get(26);
		WeatherCondition = splits.get(27);
		BroadPhaseOfFlight = splits.get(28);
		ReportStatus = splits.get(29);
		PublicationDate = splits.get(30);

	}

	public static List<String> splitCSV(String csv) {
		List<String> splits = new ArrayList<String>();

		ParsePhase phase = ParsePhase.CHARS;
		StringBuffer buf = new StringBuffer();

		for (char c : csv.toCharArray()) {
			if (phase != ParsePhase.QUOTES) {
				if (c == ',') {
					splits.add(buf.toString());
					buf = new StringBuffer();
				} else if (c == '\"')
					phase = ParsePhase.QUOTES;
				else
					buf.append(c);
			} else if (c == '\"') {
				phase = ParsePhase.CHARS;
			} else
				buf.append(c);
		}
		splits.add(buf.toString()); // add last string
		return splits;
	}

	public String getString() {
		return EventID + " " + EventDate + " " + AirportCode + " " + Make + " " + Model;
	}

	public String getMake() {
		return this.Make;
	}
	public String getDate() {
		String dateString = this.EventDate;
		return dateString;
	}

	public String getYear() {
		String yearString = this.EventDate.substring(6, 10);
		return yearString;
	}

	public String getPhases() {
		String flightPhases = this.BroadPhaseOfFlight;
		return flightPhases;
	}
	public int getTotalFatalInjuries() {
		String totalFatalInjuries= this.TotalFatalInjuries;
		return Integer.parseInt(totalFatalInjuries);
	}
	public int getTotalSeriousInjuries() {
		String totalSeriousInjuries= this.TotalSeriousInjuries;
		return Integer.parseInt(totalSeriousInjuries);
	}
	public int getTotalMinorInjuries() {
		String TotalMinorInjuries= this.TotalMinorInjuries;
		return Integer.parseInt(TotalMinorInjuries);
	}
	public int getTotalUninjured() {
		String TotalUninjured= this.TotalUninjured;
		return Integer.parseInt(TotalUninjured);
	}
	
	public String getReports() {
		String Report = this.ReportStatus;
		return Report;
	}
	public String genReport() {
		String genReport = EventID + "\t " + this.getDate() + "\t "; 
		return genReport;
	}
	
	
	
	
}
