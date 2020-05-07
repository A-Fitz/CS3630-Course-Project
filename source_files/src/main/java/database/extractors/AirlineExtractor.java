package database.extractors;

import database.tables.Airline;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineExtractor implements ResultSetExtractor<List<Airline>> {

    @Override
    public List<Airline> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Airline> airlineList = new ArrayList<>();

        while (rs.next()) {
            Airline airline = new Airline();

            Integer id;
            String name;
            String abbreviation;

            id = rs.getInt(Airline.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            name = rs.getString(Airline.NAME_COLUMN_NAME);
            abbreviation = rs.getString(Airline.ABBREVIATION_COLUMN_NAME);

            airline.setId(id);
            airline.setName(name);
            airline.setAbbreviation(abbreviation);

            airlineList.add(airline);
        }
        return airlineList;
    }
}
