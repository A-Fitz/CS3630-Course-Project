package database.extractors;

import database.tables.AirportJobType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportJobTypeExtractor implements ResultSetExtractor<List<AirportJobType>> {
    @Override
    public List<AirportJobType> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirportJobType> airportJobTypeList = new ArrayList<>();

        while (rs.next()) {
            AirportJobType airportJobType = new AirportJobType();

            Integer id;
            String title;

            id = rs.getInt(AirportJobType.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            title = rs.getString(AirportJobType.TITLE_COLUMN_NAME);

            airportJobType.setId(id);
            airportJobType.setTitle(title);


            airportJobTypeList.add(airportJobType);
        }

        return airportJobTypeList;
    }
}
