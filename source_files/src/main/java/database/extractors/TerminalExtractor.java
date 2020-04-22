package database.extractors;

import database.tables.Terminal;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TerminalExtractor implements ResultSetExtractor<List<Terminal>> {
    @Override
    public List<Terminal> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Terminal> list = new ArrayList<>();

        while (resultSet.next()) {
            Terminal object = new Terminal();

            Integer id;
            Integer airport_id;
            String terminal_code;

            id = resultSet.getInt(Terminal.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            airport_id = resultSet.getInt(Terminal.AIRPORT_ID_COLUMN_NAME);
            terminal_code = resultSet.getString(Terminal.TERMINAL_CODE_COLUMN_NAME);

            object.setId(id);
            object.setAirport_id(airport_id);
            object.setTerminal_code(terminal_code);
            list.add(object);
        }
        return list;
    }
}
