package database.operators;

import database.DatabaseConnection;
import database.OperatorInterface;
import database.extractors.BaggageExtractor;
import database.tables.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class BaggageOperator implements OperatorInterface<Baggage> {
    private static BaggageOperator instance = new BaggageOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a BaggageOperator object. This is a Singleton.
     */
    private BaggageOperator() {

    }

    public static BaggageOperator getInstance() {
        return instance;
    }

    public List<Baggage> selectByPassengerId(int passengerId) {
        BaggageExtractor extractor = new BaggageExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + Baggage.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + Baggage.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE passenger." + Passenger.ID_COLUMN_NAME + " = :passengerId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("passengerId", passengerId);

        return namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
    }

    @Override
    public List<Baggage> selectAll() {
        BaggageExtractor extractor = new BaggageExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + Baggage.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + Baggage.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Baggage selectById(int id) {
        BaggageExtractor extractor = new BaggageExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + Baggage.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + Baggage.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE baggage." + Baggage.ID_COLUMN_NAME + " = :baggageId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("baggageId", id);

        List<Baggage> baggageList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (baggageList == null || baggageList.size() == 0)
            return null;
        else
            return baggageList.get(0);
    }

    @Override
    public int updateById(int id, Baggage baggage) {
        String queryTemplate = "UPDATE baggage SET "
                + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + " = :new_passenger_on_flight_id, "
                + Baggage.WEIGHT_COLUMN_NAME + " = :new_weight, "
                + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + " = :new_baggage_status_id"
                + " WHERE " + Baggage.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_passenger_on_flight_id", baggage.getPassenger_on_flight_id());
        parameters.addValue("new_weight", baggage.getWeight());
        parameters.addValue("new_baggage_status_id", baggage.getBaggage_status_id());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(Baggage baggage) {
        String queryTemplate = "INSERT INTO baggage ("
                + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + Baggage.WEIGHT_COLUMN_NAME + ", "
                + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "VALUES(:passenger_on_flight_id, :weight, :baggage_status_id)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("passenger_on_flight_id", baggage.getPassenger_on_flight_id());
        parameters.addValue("weight", baggage.getWeight());
        parameters.addValue("baggage_status_id", baggage.getBaggage_status_id());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke) {
            // do nothing
        }

        return rowsAffected;
    }

    @Override
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM baggage "
                + " WHERE " + Baggage.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
