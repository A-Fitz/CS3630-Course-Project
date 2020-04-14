package database.extractors;

import database.tables.Airline;
import database.tables.BaggageStatusType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaggageStatusTypeExtractor implements ResultSetExtractor<List<BaggageStatusType>> {
    @Override
    public List<BaggageStatusType> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BaggageStatusType> list = new ArrayList<>();

        while (rs.next()) {
            BaggageStatusType baggageStatusType = new BaggageStatusType();

            Integer id;
            String title;

            id = rs.getInt(BaggageStatusType.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            title = rs.getString(BaggageStatusType.TITLE_COLUMN_NAME);

            baggageStatusType.setId(id);
            baggageStatusType.setTitle(title);

            list.add(baggageStatusType);
        }
        return list;
    }
}
