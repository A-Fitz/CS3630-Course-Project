package database.extractors.base;

import database.tables.base.Gate;
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
            Gate object = new Gate();

            Integer id;
            Integer terminal_id;
            String gate_code;

            id = resultSet.getInt(Gate.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            terminal_id = resultSet.getInt(Gate.TERMINAL_ID_COLUMN_NAME);
            gate_code = resultSet.getString(Gate.GATE_CODE_COLUMN_NAME);

            object.setId(id);
            object.setTerminal_id(terminal_id);
            object.setGate_code(gate_code);
            list.add(object);
        }
        return list;
    }
}
