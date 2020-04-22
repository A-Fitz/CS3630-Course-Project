package database.operators;

import database.DatabaseConnection;
import database.extractors.PassengerOnFlightExtractor;
import database.tables.PassengerOnFlight;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

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
