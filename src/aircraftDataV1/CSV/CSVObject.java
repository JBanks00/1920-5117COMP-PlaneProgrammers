package aircraftDataV1.CSV;

public class CSVObject {

	public String EventID, InvestigationType, AccidentNumber, EventDate, Location, Country, Latitude, Longitude,
			AirportCode, AirportName, InjurySeverity, AircraftDamage, AircraftCategory, RegistrationNumber, Make, Model,
			AmateurBuilt, NumberOfEngines, EngineType, FARDescription, Schedule, PurposeOfFlight, AirCarrier,
			TotalFatalInjuries, TotalSeriousInjuries, TotalMinorInjuries, TotalUninjured, WeatherCondition,
			BroadPhaseOfFlight, ReportStatus, PublicationDate;

	public CSVObject(String csv) {
		String[] split = csv.split(",", -1);
		EventID = split[0];
		InvestigationType = split[0];
		AccidentNumber = split[1];
		EventDate = split[2];
		Location = split[3];
		Country = split[4];
		Latitude = split[5];
		Longitude = split[6];
		AirportCode = split[7];
		AirportName = split[8];
		InjurySeverity = split[9];
		AircraftDamage = split[10];
		AircraftCategory = split[11];
		RegistrationNumber = split[12];
		Make = split[13];
		Model = split[14];
		AmateurBuilt = split[15];
		NumberOfEngines = split[16];
		EngineType = split[17];
		FARDescription = split[18];
		Schedule = split[19];
		PurposeOfFlight = split[20];
		AirCarrier = split[21];
		TotalFatalInjuries = split[22];
		TotalSeriousInjuries = split[23];
		TotalMinorInjuries = split[24];
		TotalUninjured = split[25];
		WeatherCondition = split[26];
		BroadPhaseOfFlight = split[27];
		ReportStatus = split[28];
		PublicationDate = split[29];

	}
	
	public String getString() {
		return EventID + " " + EventDate + " " + AirportCode + " " + Make + " " + Model;
	}
}
