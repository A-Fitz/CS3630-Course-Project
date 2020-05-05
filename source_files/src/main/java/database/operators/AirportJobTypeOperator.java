package database.operators;

import database.DatabaseConnection;
import database.extractors.base.AirportJobTypeExtractor;
import database.tables.base.AirportJobType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * Selects all rows from the airport_job_type table. Returns them as a list of AirportJobType objects.
     *
     * @return A List of objects representing the rows in the table.
     */
    public List<AirportJobType> selectAll() {
        AirportJobTypeExtractor extractor = new AirportJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airport_job_type";

        return new ArrayList<AirportJobType>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
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

        if (airportJobTypeList == null || airportJobTypeList.size() == 0)
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
