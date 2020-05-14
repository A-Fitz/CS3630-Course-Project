package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineExtractor;
import database.tables.Airline;
import database.tables.Ticket;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirlineOperator implements DatabaseOperator<Airline> {
    private static AirlineOperator instance = new AirlineOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirlineOperator object. This is a Singleton.
     */
    private AirlineOperator() {

    }

    public static AirlineOperator getInstance() {
        return instance;
    }

    @Override
    public List<Airline> selectAll() throws DataAccessException {
        AirlineExtractor extractor = new AirlineExtractor();

        String queryTemplate = "SELECT * FROM airline";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Airline selectById(int id) throws DataAccessException {
        AirlineExtractor extractor = new AirlineExtractor();

        String queryTemplate = "SELECT * FROM airline WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Airline> airlineList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airlineList == null || airlineList.size() == 0)
            return null;
        else
            return airlineList.get(0);
    }

    @Override
    public void updateById(int id, Airline airline) throws DataAccessException {
        String queryTemplate = "UPDATE airline SET "
                + Airline.NAME_COLUMN_NAME + " = :name,"
                + Airline.ABBREVIATION_COLUMN_NAME + " = :abbreviation"
                + " WHERE " + Ticket.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airline.getName());
        parameters.addValue("abbreviation", airline.getAbbreviation());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(Airline airline) throws DataAccessException {
        String queryTemplate = "INSERT INTO airline ("
                + Airline.NAME_COLUMN_NAME + ", "
                + Airline.ABBREVIATION_COLUMN_NAME + ") "
                + "VALUES(:name, :abbreviation)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airline.getName());
        parameters.addValue("abbreviation", airline.getAbbreviation());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM airline "
                + " WHERE " + Airline.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
