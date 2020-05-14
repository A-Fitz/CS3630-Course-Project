package database.operators;

import database.DatabaseConnection;
import database.extractors.TicketStatusTypeExtractor;
import database.tables.TicketStatusType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class TicketStatusTypeOperator implements DatabaseOperator<TicketStatusType> {
    private static TicketStatusTypeOperator instance = new TicketStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a TicketStatusTypeOperator object. This is a Singleton.
     */
    private TicketStatusTypeOperator() {

    }

    public static TicketStatusTypeOperator getInstance() {
        return instance;
    }

    @Override
    public List<TicketStatusType> selectAll() throws DataAccessException {
        TicketStatusTypeExtractor extractor = new TicketStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM ticket_status_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public TicketStatusType selectById(int id) throws DataAccessException {
        TicketStatusTypeExtractor extractor = new TicketStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM ticket_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<TicketStatusType> ticketStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (ticketStatusTypeList == null || ticketStatusTypeList.size() == 0)
            return null;
        else
            return ticketStatusTypeList.get(0);
    }

    @Override
    public void updateById(int id, TicketStatusType ticketStatusTypeList) throws DataAccessException {
        String queryTemplate = "UPDATE ticket_status_type SET "
                + TicketStatusType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + TicketStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", ticketStatusTypeList.getTitle());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(TicketStatusType ticketStatusType) throws DataAccessException

    {
        String queryTemplate = "INSERT INTO ticket_status_type ("
                + TicketStatusType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", ticketStatusType.getTitle());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM ticket_status_type "
                + " WHERE " + TicketStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
