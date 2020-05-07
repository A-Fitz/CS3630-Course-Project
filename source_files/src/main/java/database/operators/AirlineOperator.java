package database.operators;

import database.DatabaseConnection;
import database.OperatorInterface;
import database.extractors.AirlineExtractor;
import database.tables.Airline;
import database.tables.Ticket;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirlineOperator implements OperatorInterface<Airline> {
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
    public List<Airline> selectAll() {
        AirlineExtractor extractor = new AirlineExtractor();

        String queryTemplate = "SELECT * FROM airline";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Airline selectById(int id) {
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
    public int updateById(int id, Airline airline) {
        String queryTemplate = "UPDATE airline SET "
                + Airline.NAME_COLUMN_NAME + " = :name,"
                + Airline.ABBREVIATION_COLUMN_NAME + " = :abbreviation"
                + " WHERE " + Ticket.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airline.getName());
        parameters.addValue("abbreviation", airline.getAbbreviation());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(Airline airline) throws DuplicateKeyException {
        String queryTemplate = "INSERT INTO airline ("
                + Airline.NAME_COLUMN_NAME + ", "
                + Airline.ABBREVIATION_COLUMN_NAME + ") "
                + "VALUES(:name, :abbreviation)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airline.getName());
        parameters.addValue("abbreviation", airline.getAbbreviation());

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
        String queryTemplate = "DELETE FROM airline "
                + " WHERE " + Airline.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
