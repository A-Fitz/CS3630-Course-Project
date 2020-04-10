package database.operators;

import database.DatabaseConnection;
import database.extractors.AirportJobTypeExtractor;
import database.tables.AirportJobType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirportJobTypeOperator {
    private static AirportJobTypeOperator instance = new AirportJobTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AircraftOperator object. This is a Singleton.
     */
    private AirportJobTypeOperator() {

    }

    public static AirportJobTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects an airport_job_type row, in the form of a Java object, from the airport_job_type table given an id.
     *
     * @param id The value of the id column for an airport_job_type row
     * @return (null if no airport_job_type row exists with that id) (an AirportJobType object if row exists with that id)
     */
    public AirportJobType selectById(int id) {
        AirportJobTypeExtractor extractor = new AirportJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airport_job_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirportJobType> airportJobTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airportJobTypeList.size() == 0)
            return null;
        else
            return airportJobTypeList.get(0);
    }

    /**
     * Tries to update a row in the airport_job_type table given an id and a representative Java object.
     *
     * @param id             The value of the id column of the row to update.
     * @param airportJobType A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, AirportJobType airportJobType) {
        String queryTemplate = "UPDATE airport_job_type SET "
                + AirportJobType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE " + AirportJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", airportJobType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the airport_job_type table given a representative Java object.
     *
     * @param airportJobType The AirportJobType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(AirportJobType airportJobType) {
        String queryTemplate = "INSERT INTO airport_job_type ("
                + AirportJobType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", airportJobType.getTitle());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(AirportJobType.ID_COLUMN_NAME);

        airportJobType.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airport_job_type table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airport_job_type "
                + " WHERE " + AirportJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
