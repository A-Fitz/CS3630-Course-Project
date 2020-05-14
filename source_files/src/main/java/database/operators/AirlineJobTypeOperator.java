package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineJobTypeExtractor;
import database.tables.AirlineJobType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirlineJobTypeOperator implements DatabaseOperator<AirlineJobType> {
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

    @Override
    public List<AirlineJobType> selectAll() throws DataAccessException {
        AirlineJobTypeExtractor extractor = new AirlineJobTypeExtractor();

        String queryTemplate = "SELECT * FROM airline_job_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);    }

    @Override
    public AirlineJobType selectById(int id) throws DataAccessException {
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

    @Override
    public int updateById(int id, AirlineJobType airlineJobType) throws DataAccessException {
        String queryTemplate = "UPDATE airline_job_type SET "
                + AirlineJobType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE " + AirlineJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", airlineJobType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(AirlineJobType airlineJobType) throws DataAccessException {
        String queryTemplate = "INSERT INTO airline_job_type ("
                + AirlineJobType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", airlineJobType.getTitle());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM airline_job_type "
                + " WHERE " + AirlineJobType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
