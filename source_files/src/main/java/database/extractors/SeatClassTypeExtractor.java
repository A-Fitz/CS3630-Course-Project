package database.extractors;

import database.tables.SeatClassType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatClassTypeExtractor implements ResultSetExtractor<List<SeatClassType>>
{
    @Override
    public List<SeatClassType> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SeatClassType> seatClassTypeList = new ArrayList<>();

        while(rs.next())
        {
            SeatClassType seatClassType = new SeatClassType();

            Integer id;
            String title;

            id = rs.getInt(SeatClassType.ID_COLUMN_NAME);
            if(rs.wasNull())
                id = null;
            title = rs.getString(SeatClassType.TITLE_COLUMN_NAME);

            seatClassType.setId(id);
            seatClassType.setTitle(title);

            seatClassTypeList.add(seatClassType);
        }

        return seatClassTypeList;
    }
}
