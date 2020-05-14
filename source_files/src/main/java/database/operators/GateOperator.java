package database.operators;

import database.DatabaseConnection;
import database.extractors.GateExtractor;
import database.tables.Airport;
import database.tables.Gate;
import database.tables.Terminal;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class GateOperator implements DatabaseOperator<Gate> {
    private static GateOperator instance = new GateOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a GateOperator object. This is a Singleton.
     */
    private GateOperator() {

    }

    public static GateOperator getInstance() {
        return instance;
    }

    public List<Gate> selectManyByAirportId(int airportId) throws DataAccessException
    {
        GateExtractor extractor = new GateExtractor();

        String queryTemplate = "SELECT gate." + Gate.ID_COLUMN_NAME + ", "
                + "gate." + Gate.TERMINAL_ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = :airportId)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airportId", airportId);

        return namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
    }

    @Override
    public List<Gate> selectAll() throws DataAccessException {
        GateExtractor extractor = new GateExtractor();

        String queryTemplate = "SELECT gate." + Gate.ID_COLUMN_NAME + ", "
                + "gate." + Gate.TERMINAL_ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Gate selectById(int id) throws DataAccessException {
        GateExtractor extractor = new GateExtractor();

        String queryTemplate = "SELECT gate." + Gate.ID_COLUMN_NAME + ", "
                + "gate." + Gate.TERMINAL_ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ") "
                + "WHERE gate." + Gate.ID_COLUMN_NAME + " = :gateId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gateId", id);

        List<Gate> gatetList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (gatetList == null || gatetList.size() == 0)
            return null;
        else
            return gatetList.get(0);
    }

    @Override
    public int updateById(int id, Gate gate) throws DataAccessException {
        String queryTemplate = "UPDATE gate SET "
                + Gate.TERMINAL_ID_COLUMN_NAME + " = :terminal_id,"
                + Gate.GATE_CODE_COLUMN_NAME + " = :gate_code"
                + " WHERE " + Gate.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("terminal_id", gate.getTerminal_id());
        parameters.addValue("gate_code", gate.getGate_code());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(Gate gate) throws DataAccessException {
        String queryTemplate = "INSERT INTO gate ("
                + Gate.TERMINAL_ID_COLUMN_NAME + ", "
                + Gate.GATE_CODE_COLUMN_NAME + ")"
                + "VALUES(:terminal_id, :gate_code)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("terminal_id", gate.getTerminal_id());
        parameters.addValue("gate_code", gate.getGate_code());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM gate "
                + " WHERE " + Gate.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
