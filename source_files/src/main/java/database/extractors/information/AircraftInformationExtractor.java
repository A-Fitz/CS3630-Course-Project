package database.extractors.information;

import database.tables.base.Aircraft;
import database.tables.base.Gate;
import database.tables.information.AircraftInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AircraftInformationExtractor implements ResultSetExtractor<List<AircraftInformation>> {
    @Override
    public List<AircraftInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AircraftInformation> aircraftInformationList = new ArrayList<>();

        while (rs.next()) {
            AircraftInformation aircraftInformation = new AircraftInformation();

            Integer id;
            String airline_name;
            String serial_number;
            String make;
            String model;
            Integer year;
            Integer capacity;

            id = rs.getInt(Gate.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            airline_name = rs.getString(AircraftInformation.AIRLINE_NAME_COLUMN_NAME);
            serial_number = rs.getString(Aircraft.SERIAL_NUMBER_COLUMN_NAME);
            make = rs.getString(Aircraft.MAKE_COLUMN_NAME);
            model = rs.getString(Aircraft.MODEL_COLUMN_NAME);
            year = rs.getInt(Aircraft.YEAR_COLUMN_NAME);
            capacity = rs.getInt(Aircraft.CAPACITY_COLUMN_NAME);

            aircraftInformation.setId(id);
            aircraftInformation.setAirline_name(airline_name);
            aircraftInformation.setSerial_number(serial_number);
            aircraftInformation.setMake(make);
            aircraftInformation.setModel(model);
            aircraftInformation.setYear(year);
            aircraftInformation.setCapacity(capacity);

            aircraftInformationList.add(aircraftInformation);
        }

        return aircraftInformationList;
    }
}

