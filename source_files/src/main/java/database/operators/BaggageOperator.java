package database.operators;

import database.DatabaseConnection;
import database.extractors.BaggageExtractor;
import database.tables.Baggage;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaggageOperator {
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

    /**
     * Selects a baggage row, in the form of a Java object, from the baggage table given an id.
     *
     * @param id The value of the id column for an baggage row
     * @return (null if no baggage row exists with that id) (a Baggage object if row exists with that id)
     */
    public Baggage selectById(int id) {
        BaggageExtractor extractor = new BaggageExtractor();

        String queryTemplate = "SELECT * FROM baggage WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Baggage> baggageList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (baggageList.size() == 0)
            return null;
        else
            return baggageList.get(0);
    }

    /**
     * Tries to update a row in the baggage table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param baggage A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
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

    /**
     * Tries to insert a new row into the baggage table given a representative Java object.
     *
     * @param baggage The Baggage object which holds the data to insert into columns.
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
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

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(Baggage.ID_COLUMN_NAME);

        baggage.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the baggage table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM baggage "
                + " WHERE " + Baggage.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
