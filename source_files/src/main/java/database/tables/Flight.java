package database.tables;

import java.sql.Date;

public class Flight implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String CALLSIGN_COLUMN_NAME = "callsign";
    public static final String AIRLINE_ID_COLUMN_NAME = "airline_id";
    public static final String DEPARTURE_AIRPORT_ID_COLUMN_NAME = "departure_airport_id";
    public static final String ARRIVAL_AIRPORT_ID_COLUMN_NAME = "arrival_airport_id";
    public static final String DEPARTURE_GATE_ID_COLUMN_NAME = "departure_gate_id";
    public static final String ARRIVAL_GATE_ID_COLUMN_NAME = "arrival_gate_id";
    public static final String AIRCRAFT_ID_COLUMN_NAME = "aircraft_id";
    public static final String FLIGHT_STATUS_ID_COLUMN_NAME = "flight_status_id";
    public static final String BOARDING_DATE_COLUMN_NAME = "boarding_date";

    private Integer id;
    private String callsign;
    private Integer airline_id;
    private Integer departure_airport_id;
    private Integer arrival_airport_id;
    private Integer departure_gate_id;
    private Integer arrival_gate_id;
    private Integer aircraft_id;
    private Integer flight_status_id;
    private Date boarding_date;

    /* User readable information */
    public static final String AIRLINE_NAME_COLUMN_NAME = "airline_name";
    public static final String DEPARTURE_AIRPORT_NAME_COLUMN_NAME = "departure_airport_name";
    public static final String ARRIVAL_AIRPORT_NAME_COLUMN_NAME = "arrival_airport_name";
    public static final String DEPARTURE_GATE_CODE_COLUMN_NAME = "departure_gate_code";
    public static final String ARRIVAL_GATE_CODE_COLUMN_NAME = "arrival_gate_code";
    public static final String AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME = "aircraft_serial_number";
    public static final String FLIGHT_STATUS_TITLE_COLUMN_NAME = "flight_status_title";

    private String airline_name;
    private String departure_airport_name;
    private String arrival_airport_name;
    private String departure_gate_code;
    private String arrival_gate_code;
    private String aircraft_serial_number;
    private String flight_status_title;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Integer getAirline_id() {
        return airline_id;
    }

    public void setAirline_id(Integer airline_id) {
        this.airline_id = airline_id;
    }

    public Integer getDeparture_airport_id() {
        return departure_airport_id;
    }

    public void setDeparture_airport_id(Integer departure_airport_id) {
        this.departure_airport_id = departure_airport_id;
    }

    public Integer getArrival_airport_id() {
        return arrival_airport_id;
    }

    public void setArrival_airport_id(Integer arrival_airport_id) {
        this.arrival_airport_id = arrival_airport_id;
    }

    public Integer getDeparture_gate_id() {
        return departure_gate_id;
    }

    public void setDeparture_gate_id(Integer departure_gate_id) {
        this.departure_gate_id = departure_gate_id;
    }

    public Integer getArrival_gate_id() {
        return arrival_gate_id;
    }

    public void setArrival_gate_id(Integer arrival_gate_id) {
        this.arrival_gate_id = arrival_gate_id;
    }

    public Integer getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(Integer aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public Integer getFlight_status_id() {
        return flight_status_id;
    }

    public void setFlight_status_id(Integer flight_status_id) {
        this.flight_status_id = flight_status_id;
    }

    public Date getBoarding_date() {
        return boarding_date;
    }

    public void setBoarding_date(Date boarding_date) {
        this.boarding_date = boarding_date;
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
