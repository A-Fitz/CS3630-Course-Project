package database.operators;

import database.DatabaseConnection;
import database.extractors.TicketStatusTypeExtractor;
import database.tables.TicketStatusType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketStatusTypeOperator
{
    private static TicketStatusTypeOperator instance = new TicketStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a TicketStatusTypeOperator object. This is a Singleton.
     */
    private TicketStatusTypeOperator()
    {

    }

    public static TicketStatusTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects a ticket_status_type row, in the form of a Java object,
     * from the ticket_status_type table given an id.
     * @param id The value of the id column for a ticket_status_type row
     * @return (null if no ticket_status_type exists with that id)
     * (an TicketStatusType object if row exists with that id)
     */
    public TicketStatusType selectById(int id)
    {
        TicketStatusTypeExtractor extractor = new TicketStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM ticket_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<TicketStatusType> ticketStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if(ticketStatusTypeList.size() == 0)
            return null;
        else
            return ticketStatusTypeList.get(0);
    }

    /**
     * Tries to update a row in the ticket_status_type table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param ticketStatusType A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, TicketStatusType ticketStatusType)
    {
        String queryTemplate = "UPDATE ticket_status_type SET "
                + TicketStatusType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE "+ TicketStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", ticketStatusType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the ticket_status_type table given a representative Java object.
     * @param ticketStatusType The TicketStatusType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(TicketStatusType ticketStatusType)
    {
        String queryTemplate = "INSERT INTO ticket_status_type ("
                + TicketStatusType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", ticketStatusType.getTitle());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke)
        {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the ticket_status_type table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM ticket_status_type "
                + " WHERE "+ TicketStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
