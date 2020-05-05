package database.tables.base;

import java.sql.Date;

public class Flight {
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
}
