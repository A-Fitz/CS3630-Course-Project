package database.extractors;

import database.tables.Airport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportExtractor implements ResultSetExtractor<List<Airport>> {
    @Override
    public List<Airport> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Airport> airportList = new ArrayList<>();

        while (rs.next()) {
            Airport airport = new Airport();

            Integer id;
            String name;
            String iata_code;
            String city;
            String country;

            id = rs.getInt(Airport.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            name = rs.getString(Airport.NAME_COLUMN_NAME);
            iata_code = rs.getString(Airport.IATA_CODE_COLUMN_NAME);
            city = rs.getString(Airport.CITY_COLUMN_NAME);
            country = rs.getString(Airport.COUNTRY_COLUMN_NAME);

            airport.setId(id);
            airport.setName(name);
            airport.setIata_code(iata_code);
            airport.setCity(city);
            airport.setCountry(country);

            airportList.add(airport);
        }

        return airportList;
    }
}

