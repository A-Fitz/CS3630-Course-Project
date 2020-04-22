package database.extractors;

import database.tables.Aircraft;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AircraftExtractor implements ResultSetExtractor<List<Aircraft>> {
    @Override
    public List<Aircraft> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Aircraft> aircraftList = new ArrayList<>();

        while (rs.next()) {
            Aircraft aircraft = new Aircraft();

            Integer id;
            Integer airline_id;
            String serial_number;
            String make;
            String model;
            Integer year;
            Integer capacity;

            id = rs.getInt(Aircraft.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            airline_id = rs.getInt(Aircraft.AIRLINE_ID_COLUMN_NAME);
            serial_number = rs.getString(Aircraft.SERIAL_NUMBER_COLUMN_NAME);
            make = rs.getString(Aircraft.MAKE_COLUMN_NAME);
            model = rs.getString(Aircraft.MODEL_COLUMN_NAME);
            year = rs.getInt(Aircraft.YEAR_COLUMN_NAME);
            capacity = rs.getInt(Aircraft.CAPACITY_COLUMN_NAME);

            aircraft.setId(id);
            aircraft.setAirline_id(airline_id);
            aircraft.setSerial_number(serial_number);
            aircraft.setMake(make);
            aircraft.setModel(model);
            aircraft.setYear(year);
            aircraft.setCapacity(capacity);


            aircraftList.add(aircraft);
        }

        return aircraftList;
    }
}
