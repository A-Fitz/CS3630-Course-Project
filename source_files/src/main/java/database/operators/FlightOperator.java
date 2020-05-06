package database.operators;

import database.DatabaseConnection;
import database.extractors.base.FlightExtractor;
import database.extractors.information.FlightInformationExtractor;
import database.tables.base.*;
import database.tables.information.FlightInformation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightOperator {
    private static FlightOperator instance = new FlightOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a FlightOperator object. This is a Singleton.
     */
    private FlightOperator() {

    }

    public static FlightOperator getInstance() {
        return instance;
    }

    public FlightInformation getInformationFromId(int flightId)
    {
        FlightInformationExtractor extractor = new FlightInformationExtractor();

        String queryTemplate = "SELECT flight." + Flight.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + FlightInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "departure_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_AIRPORT_NAME_COLUMN_NAME + ", "
                + "arrival_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_AIRPORT_NAME_COLUMN_NAME + ", "
                + "departure_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_GATE_CODE_COLUMN_NAME + ", "
                + "arrival_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_GATE_CODE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + " AS " + FlightInformation.AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME + ", "
                + "flight_status_type." + FlightStatusType.TITLE_COLUMN_NAME + " AS " + FlightInformation.FLIGHT_STATUS_TITLE_COLUMN_NAME + ", "
                + "flight." + Flight.BOARDING_DATE_COLUMN_NAME + " "
                + "FROM flight "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = flight." + Flight.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS departure_airport ON (departure_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS arrival_airport ON (arrival_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS departure_gate ON (departure_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS arrival_gate ON (arrival_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN aircraft ON (aircraft." + Aircraft.ID_COLUMN_NAME + " = flight." + Flight.AIRCRAFT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight_status_type ON (flight_status_type." + FlightStatusType.ID_COLUMN_NAME + " = flight." + Flight.FLIGHT_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE flight." + Flight.ID_COLUMN_NAME + " = :flightId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flightId", flightId);

        List<FlightInformation> flightInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (flightInformationList == null || flightInformationList.size() == 0)
            return null;
        else
            return flightInformationList.get(0);
    }

    public List<FlightInformation> getInformationFromListOfIds(List<Integer> flightIds)
    {
        FlightInformationExtractor extractor = new FlightInformationExtractor();

        String queryTemplate = "SELECT flight." + Flight.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + FlightInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "departure_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_AIRPORT_NAME_COLUMN_NAME + ", "
                + "arrival_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_AIRPORT_NAME_COLUMN_NAME + ", "
                + "departure_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_GATE_CODE_COLUMN_NAME + ", "
                + "arrival_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_GATE_CODE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + " AS " + FlightInformation.AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME + ", "
                + "flight_status_type." + FlightStatusType.TITLE_COLUMN_NAME + " AS " + FlightInformation.FLIGHT_STATUS_TITLE_COLUMN_NAME + ", "
                + "flight." + Flight.BOARDING_DATE_COLUMN_NAME + " "
                + "FROM flight "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = flight." + Flight.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS departure_airport ON (departure_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS arrival_airport ON (arrival_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS departure_gate ON (departure_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS arrival_gate ON (arrival_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN aircraft ON (aircraft." + Aircraft.ID_COLUMN_NAME + " = flight." + Flight.AIRCRAFT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight_status_type ON (flight_status_type." + FlightStatusType.ID_COLUMN_NAME + " = flight." + Flight.FLIGHT_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE flight." + Flight.ID_COLUMN_NAME + " IN :flightIds";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flightIds", flightIds);

        List<FlightInformation> flightInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (flightInformationList == null || flightInformationList.size() == 0)
            return null;
        else
            return flightInformationList;
    }

    public List<FlightInformation> getInformationForAll()
    {
        FlightInformationExtractor extractor = new FlightInformationExtractor();

        String queryTemplate = "SELECT flight." + Flight.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + FlightInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "departure_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_AIRPORT_NAME_COLUMN_NAME + ", "
                + "arrival_airport." + Airport.NAME_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_AIRPORT_NAME_COLUMN_NAME + ", "
                + "departure_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.DEPARTURE_GATE_CODE_COLUMN_NAME + ", "
                + "arrival_gate." + Gate.GATE_CODE_COLUMN_NAME + " AS " + FlightInformation.ARRIVAL_GATE_CODE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + " AS " + FlightInformation.AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME + ", "
                + "flight_status_type." + FlightStatusType.TITLE_COLUMN_NAME + " AS " + FlightInformation.FLIGHT_STATUS_TITLE_COLUMN_NAME + ", "
                + "flight." + Flight.BOARDING_DATE_COLUMN_NAME + " "
                + "FROM flight "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = flight." + Flight.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS departure_airport ON (departure_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport AS arrival_airport ON (arrival_airport." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS departure_gate ON (departure_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.DEPARTURE_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN gate AS arrival_gate ON (arrival_gate." + Airport.ID_COLUMN_NAME + " = flight." + Flight.ARRIVAL_GATE_ID_COLUMN_NAME + ") "
                + "INNER JOIN aircraft ON (aircraft." + Aircraft.ID_COLUMN_NAME + " = flight." + Flight.AIRCRAFT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight_status_type ON (flight_status_type." + FlightStatusType.ID_COLUMN_NAME + " = flight." + Flight.FLIGHT_STATUS_ID_COLUMN_NAME + ")";


        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
    }

    /**
     * Selects a flight row, in the form of a Java object,
     * from the flight table given an id.
     *
     * @param id The value of the id column for a flight row
     * @return (null if no flight exists with that id)
     * (an Flight object if row exists with that id)
     */
    public Flight selectById(int id) {
        FlightExtractor extractor = new FlightExtractor();

        String queryTemplate = "SELECT * FROM flight WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Flight> flightList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (flightList == null || flightList.size() == 0)
            return null;
        else
            return flightList.get(0);
    }

    /**
     * Tries to update a row in the flight table given an id and a representative Java object.
     *
     * @param id     The value of the id column of the row to update.
     * @param flight A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Flight flight) {
        String queryTemplate = "UPDATE flight SET "
                + Flight.CALLSIGN_COLUMN_NAME + " = :new_callsign, "
                + Flight.AIRLINE_ID_COLUMN_NAME + " = :new_airline_id, "
                + Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + " = :new_departure_airport_id, "
                + Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + " = :new_arrival_airport_id, "
                + Flight.DEPARTURE_GATE_ID_COLUMN_NAME + " = :new_departure_gate_id, "
                + Flight.ARRIVAL_GATE_ID_COLUMN_NAME + " = :new_arrival_gate_id, "
                + Flight.AIRCRAFT_ID_COLUMN_NAME + " = :new_aircraft_id, "
                + Flight.FLIGHT_STATUS_ID_COLUMN_NAME + " = :new_flight_status_id, "
                + Flight.BOARDING_DATE_COLUMN_NAME + " = :new_boarding_date_id"
                + " WHERE " + Flight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_callsign", flight.getCallsign());
        parameters.addValue("new_airline_id", flight.getAirline_id());
        parameters.addValue("new_departure_airport_id", flight.getDeparture_airport_id());
        parameters.addValue("new_arrival_airport_id", flight.getArrival_airport_id());
        parameters.addValue("new_departure_gate_id", flight.getDeparture_gate_id());
        parameters.addValue("new_arrival_gate_id", flight.getArrival_gate_id());
        parameters.addValue("new_aircraft_id", flight.getAircraft_id());
        parameters.addValue("new_flight_status_id", flight.getFlight_status_id());
        parameters.addValue("new_boarding_date_id", flight.getBoarding_date());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the flight table given a representative Java object.
     *
     * @param flight The Flight object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Flight flight) {
        String queryTemplate = "INSERT INTO flight ("
                + Flight.CALLSIGN_COLUMN_NAME + ", "
                + Flight.AIRLINE_ID_COLUMN_NAME + ", "
                + Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + ", "
                + Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + ", "
                + Flight.DEPARTURE_GATE_ID_COLUMN_NAME + ", "
                + Flight.ARRIVAL_GATE_ID_COLUMN_NAME + ", "
                + Flight.AIRCRAFT_ID_COLUMN_NAME + ", "
                + Flight.FLIGHT_STATUS_ID_COLUMN_NAME + ", "
                + Flight.BOARDING_DATE_COLUMN_NAME + ") "
                + "VALUES(:callsign, :airline_id, :departure_airport_id, :arrival_airport_id, " +
                ":departure_gate_id, :arrival_gate_id, :aircraft_id, :flight_status_id, :boarding_date)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("callsign", flight.getCallsign());
        parameters.addValue("airline_id", flight.getAirline_id());
        parameters.addValue("departure_airport_id", flight.getDeparture_airport_id());
        parameters.addValue("arrival_airport_id", flight.getArrival_airport_id());
        parameters.addValue("departure_gate_id", flight.getDeparture_gate_id());
        parameters.addValue("arrival_gate_id", flight.getArrival_gate_id());
        parameters.addValue("aircraft_id", flight.getAircraft_id());
        parameters.addValue("flight_status_id", flight.getFlight_status_id());
        parameters.addValue("boarding_date", flight.getBoarding_date());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke) {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the flight table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM flight "
                + " WHERE " + Flight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
