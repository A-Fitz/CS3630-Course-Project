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
            String baggage_status_title;

            id = rs.getInt(Baggage.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            passenger_on_flight_id = rs.getInt(Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME);
            weight = rs.getFloat(Baggage.WEIGHT_COLUMN_NAME);
            baggage_status_id = rs.getInt(Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME);

            flight_callsign = rs.getString(Baggage.FLIGHT_CALLSIGN_COLUMN_NAME);
            baggage_status_title = rs.getString(Baggage.BAGGAGE_STATUS_TITLE_COLUMN_NAME);

            baggage.setId(id);
            baggage.setPassenger_on_flight_id(passenger_on_flight_id);
            baggage.setWeight(weight);
            baggage.setBaggage_status_id(baggage_status_id);

            baggage.setFlight_callsign(flight_callsign);
            baggage.setWeight(weight);
            baggage.setBaggage_status_title(baggage_status_title);

            baggageList.add(baggage);
        }

        return baggageList;
    }
}
