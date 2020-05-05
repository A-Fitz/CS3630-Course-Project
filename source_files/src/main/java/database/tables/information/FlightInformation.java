package database.tables.information;

import java.sql.Date;

public class FlightInformation {
    public static String AIRLINE_NAME_COLUMN_NAME = "airline_name";
    public static String DEPARTURE_AIRPORT_NAME_COLUMN_NAME = "departure_airport_name";
    public static String ARRIVAL_AIRPORT_NAME_COLUMN_NAME = "arrival_airport_name";
    public static String DEPARTURE_GATE_CODE_COLUMN_NAME = "departure_gate_code";
    public static String ARRIVAL_GATE_CODE_COLUMN_NAME = "arrival_gate_code";
    public static String AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME = "aircraft_serial_number";
    public static String FLIGHT_STATUS_TITLE_COLUMN_NAME = "flight_status_title";

    private Integer id;
    private String callsign;
    private String airline_name;
    private String departure_airport_name;
    private String arrival_airport_name;
    private String departure_gate_code;
    private String arrival_gate_code;
    private String aircraft_serial_number;
    private String flight_status_title;
    private Date boarding_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    public String getDeparture_airport_name() {
        return departure_airport_name;
    }

    public void setDeparture_airport_name(String departure_airport_name) {
        this.departure_airport_name = departure_airport_name;
    }

    public String getArrival_airport_name() {
        return arrival_airport_name;
    }

    public void setArrival_airport_name(String arrival_airport_name) {
        this.arrival_airport_name = arrival_airport_name;
    }

    public String getDeparture_gate_code() {
        return departure_gate_code;
    }

    public void setDeparture_gate_code(String departure_gate_code) {
        this.departure_gate_code = departure_gate_code;
    }

    public String getArrival_gate_code() {
        return arrival_gate_code;
    }

    public void setArrival_gate_code(String arrival_gate_code) {
        this.arrival_gate_code = arrival_gate_code;
    }

    public String getAircraft_serial_number() {
        return aircraft_serial_number;
    }

    public void setAircraft_serial_number(String aircraft_serial_number) {
        this.aircraft_serial_number = aircraft_serial_number;
    }

    public String getFlight_status_title() {
        return flight_status_title;
    }

    public void setFlight_status_title(String flight_status_title) {
        this.flight_status_title = flight_status_title;
    }

    public Date getBoarding_date() {
        return boarding_date;
    }

    public void setBoarding_date(Date boarding_date) {
        this.boarding_date = boarding_date;
    }

    @Override
    public String toString() {
        return "Callsign: [" +
                callsign +
                "] " +
                "Airline: [" +
                airline_name +
                "] " +
                "Departure Airport: [" +
                departure_airport_name +
                "] " +
                "Arrival Airport: [" +
                arrival_airport_name +
                "] " +
                "Departure Gate: [" +
                departure_gate_code +
                "] " +
                "Arrival Gate: [" +
                arrival_gate_code +
                "] " +
                "Aircraft: [" +
                aircraft_serial_number +
                "] " +
                "Status: [" +
                flight_status_title +
                "] " +
                "Boarding Date: [" +
                boarding_date.toString() +
                "]";
    }
}
