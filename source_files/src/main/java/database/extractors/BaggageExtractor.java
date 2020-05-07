package database.extractors;

import database.tables.Baggage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaggageExtractor implements ResultSetExtractor<List<Baggage>> {
    @Override
    public List<Baggage> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Baggage> baggageList = new ArrayList<>();

        while (rs.next()) {
            Baggage baggage = new Baggage();

            Integer id;
            Integer passenger_on_flight_id;
            Float weight;
            Integer baggage_status_id;

            String flight_callsign;
            String passenger_passport_number;
            String passenger_first_name;
            String passenger_middle_name;
            String passenger_last_name;
            Date passenger_birthdate;
            String baggage_status_title;

            id = rs.getInt(Baggage.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            passenger_on_flight_id = rs.getInt(Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME);
            weight = rs.getFloat(Baggage.WEIGHT_COLUMN_NAME);
            baggage_status_id = rs.getInt(Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME);

            flight_callsign = rs.getString(Baggage.FLIGHT_CALLSIGN_COLUMN_NAME);
            passenger_passport_number = rs.getString(Baggage.PASSENGER_PASSPORT_NUMBER_COLUMN_NAME);
            if (rs.wasNull())
                passenger_passport_number = null;
            passenger_first_name = rs.getString(Baggage.PASSENGER_FIRST_NAME_COLUMN_NAME);
            passenger_middle_name = rs.getString(Baggage.PASSENGER_MIDDLE_NAME_COLUMN_NAME);
            passenger_last_name = rs.getString(Baggage.PASSENGER_LAST_NAME_COLUMN_NAME);
            passenger_birthdate = rs.getDate(Baggage.PASSENGER_BIRTH_DATE_COLUMN_NAME);
            baggage_status_title = rs.getString(Baggage.BAGGAGE_STATUS_TITLE_COLUMN_NAME);

            baggage.setId(id);
            baggage.setPassenger_on_flight_id(passenger_on_flight_id);
            baggage.setWeight(weight);
            baggage.setBaggage_status_id(baggage_status_id);

            baggage.setFlight_callsign(flight_callsign);
            baggage.setPassenger_passport_number(passenger_passport_number);
            baggage.setPassenger_first_name(passenger_first_name);
            baggage.setPassenger_middle_name(passenger_middle_name);
            baggage.setPassenger_last_name(passenger_last_name);
            baggage.setPassenger_birthdate(passenger_birthdate);
            baggage.setWeight(weight);
            baggage.setBaggage_status_title(baggage_status_title);

            baggageList.add(baggage);
        }

        return baggageList;
    }
}
