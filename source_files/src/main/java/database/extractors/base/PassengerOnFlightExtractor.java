package database.extractors.base;

import database.tables.base.PassengerOnFlight;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerOnFlightExtractor implements ResultSetExtractor<List<PassengerOnFlight>> {
    @Override
    public List<PassengerOnFlight> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<PassengerOnFlight> passengerOnFlightList = new ArrayList<>();

        while (rs.next()) {
            PassengerOnFlight passengerOnFlight = new PassengerOnFlight();

            Integer id;
            Integer flight_id;
            Integer passenger_id;

            id = rs.getInt(PassengerOnFlight.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            flight_id = rs.getInt(PassengerOnFlight.FLIGHT_ID_COLUMN_NAME);
            passenger_id = rs.getInt(PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME);

            passengerOnFlight.setId(id);
            passengerOnFlight.setFlight_id(flight_id);
            passengerOnFlight.setPassenger_id(passenger_id);

            passengerOnFlightList.add(passengerOnFlight);
        }

        return passengerOnFlightList;
    }
}
