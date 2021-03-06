package database.operators;

import database.DatabaseConnection;
import database.extractors.FlightStatusTypeExtractor;
import database.tables.FlightStatusType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class FlightStatusTypeOperator implements DatabaseOperator<FlightStatusType> {
    private static FlightStatusTypeOperator instance = new FlightStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a FlightStatusTypeOperator object. This is a Singleton.
     */
    private FlightStatusTypeOperator() {

    }

    public static FlightStatusTypeOperator getInstance() {
        return instance;
    }

    @Override
    public List<FlightStatusType> selectAll() throws DataAccessException {
        FlightStatusTypeExtractor extractor = new FlightStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM flight_status_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public FlightStatusType selectById(int id) throws DataAccessException {
        FlightStatusTypeExtractor extractor = new FlightStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM flight_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<FlightStatusType> flightStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (flightStatusTypeList == null || flightStatusTypeList.size() == 0)
            return null;
        else
            return flightStatusTypeList.get(0);
    }

    @Override
    public void updateById(int id, FlightStatusType flightStatusTypeList) throws DataAccessException {
        String queryTemplate = "UPDATE flight_status_type SET "
                + FlightStatusType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + FlightStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", flightStatusTypeList.getTitle());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(FlightStatusType flightStatusType) throws DataAccessException {
        String queryTemplate = "INSERT INTO flight_status_type ("
                + FlightStatusType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", flightStatusType.getTitle());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM flight_status_type "
                + " WHERE " + FlightStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
