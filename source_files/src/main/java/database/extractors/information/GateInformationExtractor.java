package database.extractors.information;

import database.tables.base.Gate;
import database.tables.base.Terminal;
import database.tables.information.GateInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GateInformationExtractor implements ResultSetExtractor<List<GateInformation>> {
    @Override
    public List<GateInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GateInformation> gateInformationList = new ArrayList<>();

        while (rs.next()) {
            GateInformation gateInformation = new GateInformation();

            Integer id;
            String gate_code;
            String terminal_code;

            id = rs.getInt(Gate.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            gate_code = rs.getString(Gate.GATE_CODE_COLUMN_NAME);
            terminal_code = rs.getString(Terminal.TERMINAL_CODE_COLUMN_NAME);

            gateInformation.setId(id);
            gateInformation.setGate_code(gate_code);
            gateInformation.setTerminal_code(terminal_code);

            gateInformationList.add(gateInformation);
        }

        return gateInformationList;
    }
}

