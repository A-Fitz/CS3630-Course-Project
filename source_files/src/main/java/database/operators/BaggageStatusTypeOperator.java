package database.operators;

import database.DatabaseConnection;
import database.extractors.BaggageStatusTypeExtractor;
import database.tables.BaggageStatusType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaggageStatusTypeOperator {
    private static BaggageStatusTypeOperator instance = new BaggageStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a BaggageStatusTypeOperator object. This is a Singleton.
     */
    private BaggageStatusTypeOperator() {

    }

    public static BaggageStatusTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects a baggageStatusType row, in the form of a Java object, from the baggageStatusType table given an id.
     *
     * @param id The value of the id column for an baggageStatusType row
     * @return (null if no baggageStatusType row exists with that id) (an BaggageStatusTypeOperator object if row exists with that id)
     */
    public BaggageStatusType selectById(int id) {
        BaggageStatusTypeExtractor extractor = new BaggageStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM baggage_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<BaggageStatusType> baggageStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (baggageStatusTypeList.size() == 0)
            return null;
        else
            return baggageStatusTypeList.get(0);
    }

    /**
     * Tries to update a row in the baggageStatusType table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param baggageStatusTypeList A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, BaggageStatusType baggageStatusTypeList) {
        String queryTemplate = "UPDATE baggage_status_type SET "
                + BaggageStatusType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + BaggageStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", baggageStatusTypeList.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the baggageStatusType table given a representative Java object.
     *
     * @param baggageStatusType The BaggageStatusType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(BaggageStatusType baggageStatusType) {
        String queryTemplate = "INSERT INTO baggage_status_type ("
                + BaggageStatusType.TITLE_COLUMN_NAME  + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", baggageStatusType.getTitle());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(BaggageStatusType.ID_COLUMN_NAME);

        baggageStatusType.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the baggage_status_type table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM baggage_status_type "
                + " WHERE " + BaggageStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
