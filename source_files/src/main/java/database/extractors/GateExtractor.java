package database.extractors;

import database.tables.Gate;
import database.tables.Terminal;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GateExtractor implements ResultSetExtractor<List<Gate>> {
    @Override
    public List<Gate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Gate> list = new ArrayList<>();

        while (resultSet.next()) {
            Gate gate = new Gate();

            Integer id;
            Integer terminal_id;
            String gate_code;

            String terminal_code;

            id = resultSet.getInt(Gate.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            terminal_id = resultSet.getInt(Gate.TERMINAL_ID_COLUMN_NAME);
            gate_code = resultSet.getString(Gate.GATE_CODE_COLUMN_NAME);

            terminal_code = resultSet.getString(Terminal.TERMINAL_CODE_COLUMN_NAME);

            gate.setId(id);
            gate.setTerminal_id(terminal_id);
            gate.setGate_code(gate_code);

            gate.setTerminal_code(terminal_code);

            list.add(gate);
        }
        return list;
    }
}
