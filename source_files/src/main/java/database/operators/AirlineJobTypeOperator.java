package database.operators;

import database.DatabaseConnection;
import database.extractors.base.AirlineJobTypeExtractor;
import database.tables.base.AirlineJobType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * Selects all rows from the airline_job_type table. Returns them as a list of AirlineJobType objects.
     *
     * @return A List of objects representing the rows in the table.
     */
    public List<AirlineJobType> selectAll() {
        AirlineJobTypeExtractor extractor = new AirlineJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airline_job_type";

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
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

        if (airlineJobTypeList == null || airlineJobTypeList.size() == 0)
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
