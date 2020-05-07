package database.extractors;

import database.tables.Flight;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightExtractor implements ResultSetExtractor<List<Flight>> {
    @Override
    public List<Flight> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Flight> flightList = new ArrayList<>();

        while (rs.next()) {
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

            String airline_name;
            String departure_airport_name;
            String arrival_airport_name;
            String departure_gate_code;
            String arrival_gate_code;
            String aircraft_serial_number;
            String flight_status_title;

            id = rs.getInt(Flight.ID_COLUMN_NAME);
            if (rs.wasNull())
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

            airline_name = rs.getString(Flight.AIRLINE_NAME_COLUMN_NAME);
            departure_airport_name = rs.getString(Flight.DEPARTURE_AIRPORT_NAME_COLUMN_NAME);
            arrival_airport_name = rs.getString(Flight.ARRIVAL_AIRPORT_NAME_COLUMN_NAME);
            departure_gate_code = rs.getString(Flight.DEPARTURE_GATE_CODE_COLUMN_NAME);
            arrival_gate_code = rs.getString(Flight.ARRIVAL_GATE_CODE_COLUMN_NAME);
            aircraft_serial_number = rs.getString(Flight.AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME);
            flight_status_title = rs.getString(Flight.FLIGHT_STATUS_TITLE_COLUMN_NAME);

            flight.setId(id);
            flight.setCallsign(callsign);
            flight.setAirline_id(airline_id);
            flight.setDeparture_airport_id(departure_airport_id);
            flight.setArrival_airport_id(arrival_airport_id);
            flight.setDeparture_gate_id(departure_gate_id);
            flight.setArrival_gate_id(arrival_gate_id);
            flight.setAircraft_id(aircraft_id);
            flight.setFlight_status_id(flight_status_id);
            flight.setBoarding_date(boarding_date);

            flight.setCallsign(callsign);
            flight.setAirline_name(airline_name);
            flight.setDeparture_airport_name(departure_airport_name);
            flight.setArrival_airport_name(arrival_airport_name);
            flight.setDeparture_gate_code(departure_gate_code);
            flight.setArrival_gate_code(arrival_gate_code);
            flight.setAircraft_serial_number(aircraft_serial_number);
            flight.setFlight_status_title(flight_status_title);

            flightList.add(flight);
        }

        return flightList;
    }
}

