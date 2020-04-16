package database.extractors;

import database.tables.Flight;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightExtractor implements ResultSetExtractor<List<Flight>>
{
    @Override
    public List<Flight> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Flight> flightList = new ArrayList<>();

        while(rs.next())
        {
            Flight flight = new Flight();

            Integer id;
            String callsign;
            Integer airline_id;
            Integer departure_airport_id;
            Integer arrival_airport_id;
            Integer departure_gate_id;
            Integer arrival_gate_id;
            Integer aircraft_id;
            Integer flight_status_id;
            Date boarding_date;

            id = rs.getInt(Flight.ID_COLUMN_NAME);
            if(rs.wasNull())
                id = null;
            callsign = rs.getString(Flight.CALLSIGN_COLUMN_NAME);
            airline_id = rs.getInt(Flight.AIRLINE_ID_COLUMN_NAME);
            departure_airport_id = rs.getInt(Flight.DEPARTURE_AIRPORT_ID_COLUMN_NAME);
            arrival_airport_id = rs.getInt(Flight.ARRIVAL_AIRPORT_ID_COLUMN_NAME);
            departure_gate_id = rs.getInt(Flight.DEPARTURE_GATE_ID_COLUMN_NAME);
            arrival_gate_id = rs.getInt(Flight.ARRIVAL_GATE_ID_COLUMN_NAME);
            aircraft_id = rs.getInt(Flight.AIRCRAFT_ID_COLUMN_NAME);
            flight_status_id = rs.getInt(Flight.FLIGHT_STATUS_ID_COLUMN_NAME);
            boarding_date = rs.getDate(Flight.BOARDING_DATE_COLUMN_NAME);

            flight.setId(id);;
            flight.setCallsign(callsign);
            flight.setAirline_id(airline_id);
            flight.setDeparture_airport_id(departure_airport_id);
            flight.setArrival_airport_id(arrival_airport_id);
            flight.setDeparture_gate_id(departure_gate_id);
            flight.setArrival_gate_id(arrival_gate_id);
            flight.setAircraft_id(aircraft_id);
            flight.setFlight_status_id(flight_status_id);
            flight.setBoarding_date(boarding_date);

            flightList.add(flight);
        }

        return flightList;
    }
}

