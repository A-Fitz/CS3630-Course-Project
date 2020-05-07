package database.operators;

import database.DatabaseConnection;
import database.OperatorInterface;
import database.extractors.TerminalExtractor;
import database.tables.Airport;
import database.tables.Terminal;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class TerminalOperator implements OperatorInterface<Terminal> {
    private static TerminalOperator instance = new TerminalOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a TerminalOperator object. This is a Singleton.
     */
    private TerminalOperator() {

    }

    public static TerminalOperator getInstance() {
        return instance;
    }

    @Override
    public List<Terminal> selectAll() {
        TerminalExtractor extractor = new TerminalExtractor();

        String queryTemplate = "SELECT terminal." + Terminal.ID_COLUMN_NAME + ", "
                + "terminal." + Terminal.AIRPORT_ID_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + Terminal.AIRPORT_NAME_COLUMN_NAME + " "
                + "FROM terminal "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = terminal." + Terminal.AIRPORT_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Terminal selectById(int id) {
        TerminalExtractor extractor = new TerminalExtractor();

        String queryTemplate = "SELECT terminal." + Terminal.ID_COLUMN_NAME + ", "
                + "terminal." + Terminal.AIRPORT_ID_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + Terminal.AIRPORT_NAME_COLUMN_NAME + " "
                + "FROM terminal "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = terminal." + Terminal.AIRPORT_ID_COLUMN_NAME + ") "
                + "WHERE terminal." + Terminal.ID_COLUMN_NAME + " = :terminalId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("terminalId", id);

        List<Terminal> terminalList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (terminalList == null || terminalList.size() == 0)
            return null;
        else
            return terminalList.get(0);
    }

    @Override
    public int updateById(int id, Terminal terminal) {
        String queryTemplate = "UPDATE terminal SET "
                + Terminal.AIRPORT_ID_COLUMN_NAME + " = :airport_id,"
                + Terminal.TERMINAL_CODE_COLUMN_NAME + " = :terminal_code"
                + " WHERE " + Terminal.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airport_id", terminal.getAirport_id());
        parameters.addValue("terminal_code", terminal.getTerminal_code());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(Terminal terminal) {
        String queryTemplate = "INSERT INTO terminal ("
                + Terminal.AIRPORT_ID_COLUMN_NAME + ", "
                + Terminal.TERMINAL_CODE_COLUMN_NAME + ") "
                + "VALUES(:airport_id, :terminal_code)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airport_id", terminal.getAirport_id());
        parameters.addValue("terminal_code", terminal.getTerminal_code());

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
        String queryTemplate = "DELETE FROM terminal "
                + " WHERE " + Terminal.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
