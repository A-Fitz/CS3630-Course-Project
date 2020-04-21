package database.operators;

import database.DatabaseConnection;
import database.extractors.FlightStatusTypeExtractor;
import database.tables.FlightStatusType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightStatusTypeOperator {
    private static FlightStatusTypeOperator instance = new FlightStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a FightStatusTypeOperator object. This is a Singleton.
     */
    private FlightStatusTypeOperator() {

    }

    public static FlightStatusTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects a FlightStatusType row, in the form of a Java object, from the FlightStatusType table given an id.
     *
     * @param id The value of the id column for an FlightStatusType row
     * @return (null if no FlightStatusType row exists with that id) (an FlightStatusType object if row exists with that id)
     */
    public FlightStatusType selectById(int id) {
        FlightStatusTypeExtractor extractor = new FlightStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM flight_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<FlightStatusType> flightStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (flightStatusTypeList.size() == 0)
            return null;
        else
            return flightStatusTypeList.get(0);
    }

    /**
     * Tries to update a row in the FlightStatusType table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param flightStatusType A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, FlightStatusType flightStatusType) {
        String queryTemplate = "UPDATE flight_status_type SET "
                + FlightStatusType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + FlightStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", flightStatusType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the flightStatusType table given a representative Java object.
     *
     * @param flightStatusType The FlightStatusType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(FlightStatusType flightStatusType) {
        String queryTemplate = "INSERT INTO flight_status_type ("
                + FlightStatusType.TITLE_COLUMN_NAME  + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", flightStatusType.getTitle());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke)
        {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the FlightStatusType table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM flight_status_type "
                + " WHERE " + FlightStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
