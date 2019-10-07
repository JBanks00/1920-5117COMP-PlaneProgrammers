package aircraftDataV1.CSV;

public class CSVObject {

	public String EventID, InvestigationType, AccidentNumber, EventDate, Location, Country, Latitude, Longitude,
			AirportCode, AirportName, InjurySeverity, AircraftDamage, AircraftCategory, RegistrationNumber, Make, Model,
			AmateurBuilt, NumberOfEngines, EngineType, FARDescription, Schedule, PurposeOfFlight, AirCarrier,
			TotalFatalInjuries, TotalSeriousInjuries, TotalMinorInjuries, TotalUninjured, WeatherCondition,
			BroadPhaseOfFlight, ReportStatus, PublicationDate;

	public CSVObject(String csv) {
		String[] split = csv.split(",", 32);
		for (int i = 0; i < split.length; i ++) {
			System.out.println(split[i]);
		}
		EventID = split[0];
		InvestigationType = split[1];
		AccidentNumber = split[2];
		EventDate = split[3];
		Location = split[4];
		Country = split[5];
		Latitude = split[6];
		Longitude = split[7];
		AirportCode = split[8];
		AirportName = split[9];
		InjurySeverity = split[10];
		AircraftDamage = split[11];
		AircraftCategory = split[12];
		RegistrationNumber = split[13];
		Make = split[14];
		Model = split[15];
		AmateurBuilt = split[16];
		NumberOfEngines = split[17];
		EngineType = split[18];
		FARDescription = split[19];
		Schedule = split[20];
		PurposeOfFlight = split[21];
		AirCarrier = split[22];
		TotalFatalInjuries = split[23];
		TotalSeriousInjuries = split[24];
		TotalMinorInjuries = split[25];
		TotalUninjured = split[26];
		WeatherCondition = split[27];
		BroadPhaseOfFlight = split[28];
		ReportStatus = split[29];
		PublicationDate = split[30];

	}
	
	public String getString() {
		return EventID + " " + EventDate + " " + AirportCode + " " + Make + " " + Model;
	}
	
	public String getMake() {
		return this.Make;
	}
	
	public String getDate() {
		String dateString = this.EventID.substring(0,4);
		return dateString;
	}
	
	public String getPhases() {
		String flightPhases = this.BroadPhaseOfFlight;
		return flightPhases;
	}
}
