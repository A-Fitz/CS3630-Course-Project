package database.extractors;

import database.tables.AirlineJobType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineJobTypeExtractor implements ResultSetExtractor<List<AirlineJobType>> {
    @Override
    public List<AirlineJobType> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirlineJobType> airlineJobTypeList = new ArrayList<>();

        while (rs.next()) {
            AirlineJobType airlineJobType = new AirlineJobType();

            Integer id;
            String title;

            id = rs.getInt(AirlineJobType.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            title = rs.getString(AirlineJobType.TITLE_COLUMN_NAME);

            airlineJobType.setId(id);
            airlineJobType.setTitle(title);


            airlineJobTypeList.add(airlineJobType);
        }

        return airlineJobTypeList;
    }
}
