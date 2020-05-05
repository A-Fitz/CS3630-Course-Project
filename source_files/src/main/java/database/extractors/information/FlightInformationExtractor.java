package database.extractors.information;

import database.tables.base.Flight;
import database.tables.information.FlightInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightInformationExtractor implements ResultSetExtractor<List<FlightInformation>> {
    @Override
    public List<FlightInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<FlightInformation> flightInformationList = new ArrayList<>();

        while (rs.next()) {
            FlightInformation flightInformation = new FlightInformation();

            Integer id;
            String callsign;
            String airline_name;
            String departure_airport_name;
            String arrival_airport_name;
            String departure_gate_code;
            String arrival_gate_code;
            String aircraft_serial_number;
            String flight_status_title;
            Date boarding_date;

            id = rs.getInt(Flight.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            callsign = rs.getString(Flight.CALLSIGN_COLUMN_NAME);
            airline_name = rs.getString(FlightInformation.AIRLINE_NAME_COLUMN_NAME);
            departure_airport_name = rs.getString(FlightInformation.DEPARTURE_AIRPORT_NAME_COLUMN_NAME);
            arrival_airport_name = rs.getString(FlightInformation.ARRIVAL_AIRPORT_NAME_COLUMN_NAME);
            departure_gate_code = rs.getString(FlightInformation.DEPARTURE_GATE_CODE_COLUMN_NAME);
            arrival_gate_code = rs.getString(FlightInformation.ARRIVAL_GATE_CODE_COLUMN_NAME);
            aircraft_serial_number = rs.getString(FlightInformation.AIRCRAFT_SERIAL_NUMBER_COLUMN_NAME);
            flight_status_title = rs.getString(FlightInformation.FLIGHT_STATUS_TITLE_COLUMN_NAME);
            boarding_date = rs.getDate(Flight.BOARDING_DATE_COLUMN_NAME);

            flightInformation.setId(id);
            flightInformation.setCallsign(callsign);
            flightInformation.setAirline_name(airline_name);
            flightInformation.setDeparture_airport_name(departure_airport_name);
            flightInformation.setArrival_airport_name(arrival_airport_name);
            flightInformation.setDeparture_gate_code(departure_gate_code);
            flightInformation.setArrival_gate_code(arrival_gate_code);
            flightInformation.setAircraft_serial_number(aircraft_serial_number);
            flightInformation.setFlight_status_title(flight_status_title);
            flightInformation.setBoarding_date(boarding_date);

            flightInformationList.add(flightInformation);
        }

        return flightInformationList;
    }
}

