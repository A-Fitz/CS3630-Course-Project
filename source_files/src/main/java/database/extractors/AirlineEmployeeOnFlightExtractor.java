package database.extractors;

import database.tables.AirlineEmployeeOnFlight;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineEmployeeOnFlightExtractor implements ResultSetExtractor<List<AirlineEmployeeOnFlight>> {
    @Override
    public List<AirlineEmployeeOnFlight> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirlineEmployeeOnFlight> airlineEmployeeOnFlightList = new ArrayList<>();

        while (rs.next()) {
            AirlineEmployeeOnFlight airlineEmployeeOnFlight = new AirlineEmployeeOnFlight();

            Integer id;
            Integer flight_id;
            Integer airline_employee_id;

            id = rs.getInt(AirlineEmployeeOnFlight.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            flight_id = rs.getInt(AirlineEmployeeOnFlight.FLIGHT_ID_COLUMN_NAME);
            airline_employee_id = rs.getInt(AirlineEmployeeOnFlight.AIRLINE_EMPLOYEE_ID_COLUMN_NAME);

            airlineEmployeeOnFlight.setId(id);
            airlineEmployeeOnFlight.setFlight_id(flight_id);
            airlineEmployeeOnFlight.setAirline_employee_id(airline_employee_id);

            airlineEmployeeOnFlightList.add(airlineEmployeeOnFlight);
        }

        return airlineEmployeeOnFlightList;
    }
}
