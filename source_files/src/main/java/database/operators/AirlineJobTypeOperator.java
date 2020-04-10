package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineJobTypeExtractor;
import database.tables.AirlineJobType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirlineJobTypeOperator {
    private static AirlineJobTypeOperator instance = new AirlineJobTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AircraftOperator object. This is a Singleton.
     */
    private AirlineJobTypeOperator() {

    }

    public static AirlineJobTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects an airline_job_type row, in the form of a Java object, from the airline_job_type table given an id.
     *
     * @param id The value of the id column for an airline_job_type row
     * @return (null if no airline_job_type row exists with that id) (an AirlineJobType object if row exists with that id)
     */
    public AirlineJobType selectById(int id) {
        AirlineJobTypeExtractor extractor = new AirlineJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airline_job_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirlineJobType> airlineJobTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airlineJobTypeList.size() == 0)
            return null;
        else
            return airlineJobTypeList.get(0);
    }

    /**
     * Tries to update a row in the airline_job_type table given an id and a representative Java object.
     *
     * @param id             The value of the id column of the row to update.
     * @param airlineJobType A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, AirlineJobType airlineJobType) {
        String queryTemplate = "UPDATE airline_job_type SET "
                + AirlineJobType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE " + AirlineJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", airlineJobType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the airline_job_type table given a representative Java object.
     *
     * @param airlineJobType The AirlineJobType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(AirlineJobType airlineJobType) {
        String queryTemplate = "INSERT INTO airline_job_type ("
                + AirlineJobType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", airlineJobType.getTitle());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(AirlineJobType.ID_COLUMN_NAME);

        airlineJobType.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airline_job_type table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airline_job_type "
                + " WHERE " + AirlineJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
