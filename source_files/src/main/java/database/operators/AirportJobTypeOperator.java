package database.operators;

import database.DatabaseConnection;
import database.extractors.AirportJobTypeExtractor;
import database.tables.AirportJobType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirportJobTypeOperator implements DatabaseOperator<AirportJobType> {
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

    @Override
    public List<AirportJobType> selectAll() {
        AirportJobTypeExtractor extractor = new AirportJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airport_job_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);    }

    @Override
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

    @Override
    public int updateById(int id, AirportJobType airportJobType) {
        String queryTemplate = "UPDATE airport_job_type SET "
                + AirportJobType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE " + AirportJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", airportJobType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
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

    @Override
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airport_job_type "
                + " WHERE " + AirportJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
