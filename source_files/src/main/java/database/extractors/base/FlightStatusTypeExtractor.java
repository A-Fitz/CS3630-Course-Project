package database.extractors.base;

import database.tables.base.FlightStatusType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightStatusTypeExtractor implements ResultSetExtractor<List<FlightStatusType>> {
    @Override
    public List<FlightStatusType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<FlightStatusType> list = new ArrayList<>();

        while (resultSet.next()) {
            FlightStatusType object = new FlightStatusType();

            Integer id;
            String title;

            id = resultSet.getInt(FlightStatusType.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            title = resultSet.getString(FlightStatusType.TITLE_COLUMN_NAME);

            object.setId(id);
            object.setTitle(title);
            list.add(object);
        }
        return list;
    }
}
