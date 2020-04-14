package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineExtractor;
import database.extractors.TicketExtractor;
import database.tables.Airline;
import database.tables.Ticket;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirlineOperator {
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

    /**
     * Selects a airline row, in the form of a Java object, from the ticket table given an id.
     *
     * @param id The value of the id column for an airline row
     * @return (null if no airline row exists with that id) (an ticket object if row exists with that id)
     */
    public Airline selectById(int id) {
        AirlineExtractor extractor = new AirlineExtractor();

        String queryTemplate = "SELECT * FROM airline WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Airline> airlineList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airlineList.size() == 0)
            return null;
        else
            return airlineList.get(0);
    }

    /**
     * Tries to update a row in the airline table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param airline A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
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

    /**
     * Tries to insert a new row into the ticket table given a representative Java object.
     *
     * @param airline The Airline object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Airline airline) {
        String queryTemplate = "INSERT INTO airline ("
                + Airline.NAME_COLUMN_NAME  + ", "
                + Airline.ABBREVIATION_COLUMN_NAME  + ") "
                + "VALUES(:name, :abbreviation)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airline.getName());
        parameters.addValue("abbreviation", airline.getAbbreviation());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(Ticket.ID_COLUMN_NAME);

        airline.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airline table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airline "
                + " WHERE " + Airline.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
