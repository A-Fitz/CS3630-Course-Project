package database.extractors;

import database.tables.TicketStatusType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketStatusTypeExtractor implements ResultSetExtractor<List<TicketStatusType>> {
    @Override
    public List<TicketStatusType> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TicketStatusType> ticketStatusTypeList = new ArrayList<>();

        while (rs.next()) {
            TicketStatusType ticketStatusType = new TicketStatusType();

            Integer id;
            String title;

            id = rs.getInt(TicketStatusType.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            title = rs.getString(TicketStatusType.TITLE_COLUMN_NAME);

            ticketStatusType.setId(id);
            ticketStatusType.setTitle(title);

            ticketStatusTypeList.add(ticketStatusType);
        }

        return ticketStatusTypeList;
    }
}
