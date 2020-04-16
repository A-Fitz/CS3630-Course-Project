package database.operators;

import database.DatabaseConnection;
import database.extractors.FlightExtractor;
import database.tables.Flight;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightOperator
{
    private static FlightOperator instance = new FlightOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a FlightOperator object. This is a Singleton.
     */
    private FlightOperator()
    {

    }

    public static FlightOperator getInstance() {
        return instance;
    }

    /**
     * Selects a flight row, in the form of a Java object,
     * from the flight table given an id.
     * @param id The value of the id column for a flight row
     * @return (null if no flight exists with that id)
     * (an Flight object if row exists with that id)
     */
    public Flight selectById(int id)
    {
        FlightExtractor extractor = new FlightExtractor();

        String queryTemplate = "SELECT * FROM flight WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Flight> flightList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if(flightList.size() == 0)
            return null;
        else
            return flightList.get(0);
    }

    /**
     * Tries to update a row in the flight table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param flight A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Flight flight)
    {
        String queryTemplate = "UPDATE flight SET "
                + flight.CALLSIGN_COLUMN_NAME + " = :new_callsign, "
                + flight.AIRLINE_ID_COLUMN_NAME + " = :new_airline_id, "
                + flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + " = :new_departure_airport_id, "
                + flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + " = :new_arrival_airport_id, "
                + flight.DEPARTURE_GATE_ID_COLUMN_NAME + " = :new_departure_gate_id, "
                + flight.ARRIVAL_GATE_ID_COLUMN_NAME + " = :new_arrival_gate_id, "
                + flight.AIRCRAFT_ID_COLUMN_NAME + " = :new_aircraft_id, "
                + flight.FLIGHT_STATUS_ID_COLUMN_NAME + " = :new_flight_status_id, "
                + flight.BOARDING_DATE_COLUMN_NAME + " = :new_boarding_date_id"
                + " WHERE "+ Flight.ID_COLUMN_NAME + " = :id";

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
     * @param flight The Flight object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Flight flight)
    {
        String queryTemplate = "INSERT INTO flight ("
                + flight.CALLSIGN_COLUMN_NAME + ", "
                + flight.AIRLINE_ID_COLUMN_NAME + ", "
                + flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME + ", "
                + flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME + ", "
                + flight.DEPARTURE_GATE_ID_COLUMN_NAME + ", "
                + flight.ARRIVAL_GATE_ID_COLUMN_NAME + ", "
                + flight.AIRCRAFT_ID_COLUMN_NAME + ", "
                + flight.FLIGHT_STATUS_ID_COLUMN_NAME + ", "
                + flight.BOARDING_DATE_COLUMN_NAME + ") "
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

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int)keyMap.get(flight.ID_COLUMN_NAME);

        flight.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the flight table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM flight "
                + " WHERE "+ Flight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
