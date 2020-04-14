package database.operators;

import database.DatabaseConnection;
import database.extractors.TerminalExtractor;
import database.tables.Terminal;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TerminalOperator {
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

    /**
     * Selects a Terminal row, in the form of a Java object, from the terminal table given an id.
     *
     * @param id The value of the id column for an Terminal row
     * @return (null if no Terminal row exists with that id) (an Terminal object if row exists with that id)
     */
    public Terminal selectById(int id) {
        TerminalExtractor extractor = new TerminalExtractor();

        String queryTemplate = "SELECT * FROM terminal WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Terminal> terminal = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (terminal.size() == 0)
            return null;
        else
            return terminal.get(0);
    }

    /**
     * Tries to update a row in the terminal table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param terminal A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
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

    /**
     * Tries to insert a new row into the terminal table given a representative Java object.
     *
     * @param terminal The terminal object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Terminal terminal) {
        String queryTemplate = "INSERT INTO terminal ("
                + Terminal.AIRPORT_ID_COLUMN_NAME  + ", "
                + Terminal.TERMINAL_CODE_COLUMN_NAME  + ") "
                + "VALUES(:airport_id, :terminal_code)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airport_id", terminal.getAirport_id());
        parameters.addValue("terminal_code", terminal.getTerminal_code());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(Terminal.ID_COLUMN_NAME);

        terminal.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the terminal table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM terminal "
                + " WHERE " + Terminal.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
