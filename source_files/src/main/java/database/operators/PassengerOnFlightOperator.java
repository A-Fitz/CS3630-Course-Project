package database.operators;

import database.DatabaseConnection;
import database.extractors.base.PassengerOnFlightExtractor;
import database.tables.base.PassengerOnFlight;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PassengerOnFlightOperator {
    private static PassengerOnFlightOperator instance = new PassengerOnFlightOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a PassengerOnFlightOperator object. This is a Singleton.
     */
    private PassengerOnFlightOperator() {

    }

    public static PassengerOnFlightOperator getInstance() {
        return instance;
    }

    /**
     * Selects a passenger_on_flight row, in the form of a Java object,
     * from the passenger_on_flight table given a passenger_id and flight_id.
     *
     * @param flightId The value of the flight_id column for a passenger_on_flight row
     * @return (null if no passenger_on_flight exists with that passenger_id and flight_id) or
     * (a java object representing that passenger_on_flight row)
     */
    public PassengerOnFlight selectByPassengerAndFlightId(int passengerId, int flightId) {
        PassengerOnFlightExtractor extractor = new PassengerOnFlightExtractor();

        String queryTemplate = "SELECT * FROM passenger_on_flight WHERE " +
                PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + " = :passenger_id AND "
                + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + " = :flight_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("passenger_id", passengerId);
        parameters.addValue("flight_id", flightId);

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor))).get(0);
    }

    /**
     * Selects all passenger_on_flight rows, in the form of a list of Java objects,
     * from the passenger_on_flight table given a flight_id.
     *
     * @param flightId The value of the flight_id column for a passenger_on_flight row
     * @return (null if no passenger_on_flight exists with that flight_id) or
     * (A List of objects representing the rows in the table)
     */
    public List<PassengerOnFlight> selectByFlightId(int flightId) {
        PassengerOnFlightExtractor extractor = new PassengerOnFlightExtractor();

        String queryTemplate = "SELECT * FROM passenger_on_flight WHERE " +
                PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + " = :flight_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flight_id", flightId);

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor)));
    }

    /**
     * Selects a passenger_on_flight row, in the form of a Java object,
     * from the passenger_on_flight table given an id.
     *
     * @param id The value of the id column for a passenger_on_flight row
     * @return (null if no passenger_on_flight exists with that id)
     * (an PassengerOnFlight object if row exists with that id)
     */
    public PassengerOnFlight selectById(int id) {
        PassengerOnFlightExtractor extractor = new PassengerOnFlightExtractor();

        String queryTemplate = "SELECT * FROM passenger_on_flight WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<PassengerOnFlight> passengerOnFlightList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (passengerOnFlightList == null || passengerOnFlightList.size() == 0)
            return null;
        else
            return passengerOnFlightList.get(0);
    }

    /**
     * Tries to update a row in the passenger_on_flight table given an id and a representative Java object.
     *
     * @param id                The value of the id column of the row to update.
     * @param passengerOnFlight A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, PassengerOnFlight passengerOnFlight) {
        String queryTemplate = "UPDATE passenger_on_flight SET "
                + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + " = :new_flight_id, "
                + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + " = :new_airline_employee_id"
                + " WHERE " + PassengerOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_flight_id", passengerOnFlight.getFlight_id());
        parameters.addValue("new_passenger_id", passengerOnFlight.getPassenger_id());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the passenger_on_flight table given a representative Java object.
     *
     * @param passengerOnFlight The PassengerOnFlight object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(PassengerOnFlight passengerOnFlight) {
        String queryTemplate = "INSERT INTO passenger_on_flight ("
                + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ", "
                + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "VALUES(:flight_id, :passenger_id)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flight_id", passengerOnFlight.getFlight_id());
        parameters.addValue("passenger_id", passengerOnFlight.getPassenger_id());

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
     * Tries to delete a row in the passenger_on_flight table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM passenger_on_flight "
                + " WHERE " + PassengerOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
