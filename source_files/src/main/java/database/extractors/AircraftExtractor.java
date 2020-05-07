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
    public List<Aircraft> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Aircraft> aircraftList = new ArrayList<>();

        while (resultSet.next()) {
            Aircraft aircraft = new Aircraft();

            Integer id;
            Integer airline_id;
            String serial_number;
            String make;
            String model;
            Integer year;
            Integer capacity;

            String airline_name;

            id = resultSet.getInt(Aircraft.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            airline_id = resultSet.getInt(Aircraft.AIRLINE_ID_COLUMN_NAME);
            serial_number = resultSet.getString(Aircraft.SERIAL_NUMBER_COLUMN_NAME);
            make = resultSet.getString(Aircraft.MAKE_COLUMN_NAME);
            model = resultSet.getString(Aircraft.MODEL_COLUMN_NAME);
            year = resultSet.getInt(Aircraft.YEAR_COLUMN_NAME);
            capacity = resultSet.getInt(Aircraft.CAPACITY_COLUMN_NAME);

            airline_name = resultSet.getString(Aircraft.AIRLINE_NAME_COLUMN_NAME);

            aircraft.setId(id);
            aircraft.setAirline_id(airline_id);
            aircraft.setSerial_number(serial_number);
            aircraft.setMake(make);
            aircraft.setModel(model);
            aircraft.setYear(year);
            aircraft.setCapacity(capacity);

            aircraft.setAirline_name(airline_name);

            aircraftList.add(aircraft);
        }

        return aircraftList;
    }
}
