package database.operators;

import database.DatabaseConnection;
import database.extractors.PassengerOnFlightExtractor;
import database.tables.PassengerOnFlight;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PassengerOnFlightOperator implements DatabaseOperator<PassengerOnFlight> {
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
    public PassengerOnFlight selectByPassengerAndFlightId(int passengerId, int flightId) throws DataAccessException {
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
    public List<PassengerOnFlight> selectByFlightId(int flightId) throws DataAccessException {
        PassengerOnFlightExtractor extractor = new PassengerOnFlightExtractor();

        String queryTemplate = "SELECT * FROM passenger_on_flight WHERE " +
                PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + " = :flight_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flight_id", flightId);

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor)));
    }

    @Override
    public List<PassengerOnFlight> selectAll() throws DataAccessException {
        PassengerOnFlightExtractor extractor = new PassengerOnFlightExtractor();

        String queryTemplate = "SELECT * FROM passenger_on_flight";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public PassengerOnFlight selectById(int id) throws DataAccessException {
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

    @Override
    public int updateById(int id, PassengerOnFlight passengerOnFlight) throws DataAccessException {
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

    @Override
    public void insert(PassengerOnFlight passengerOnFlight) throws DataAccessException {
        String queryTemplate = "INSERT INTO passenger_on_flight ("
                + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ", "
                + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "VALUES(:flight_id, :passenger_id)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flight_id", passengerOnFlight.getFlight_id());
        parameters.addValue("passenger_id", passengerOnFlight.getPassenger_id());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM passenger_on_flight "
                + " WHERE " + PassengerOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
