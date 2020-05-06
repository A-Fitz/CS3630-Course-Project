package database.extractors.information;

import database.tables.base.Baggage;
import database.tables.information.BaggageInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaggageInformationExtractor implements ResultSetExtractor<List<BaggageInformation>> {
    @Override
    public List<BaggageInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BaggageInformation> baggageInformationList = new ArrayList<>();

        while (rs.next()) {
            BaggageInformation baggageInformation = new BaggageInformation();

            Integer id;
            String flight_callsign;
            String passenger_passport_number;
            String passenger_first_name;
            String passenger_middle_name;
            String passenger_last_name;
            Date passenger_birthdate;
            Float weight;
            String baggage_status_title;

            id = rs.getInt(Baggage.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            flight_callsign = rs.getString(BaggageInformation.FLIGHT_CALLSIGN_COLUMN_NAME);
            passenger_passport_number = rs.getString(BaggageInformation.PASSENGER_PASSPORT_NUMBER_COLUMN_NAME);
            if (rs.wasNull())
                passenger_passport_number = null;
            passenger_first_name = rs.getString(BaggageInformation.PASSENGER_FIRST_NAME_COLUMN_NAME);
            passenger_middle_name = rs.getString(BaggageInformation.PASSENGER_MIDDLE_NAME_COLUMN_NAME);
            passenger_last_name = rs.getString(BaggageInformation.PASSENGER_LAST_NAME_COLUMN_NAME);
            passenger_birthdate = rs.getDate(BaggageInformation.PASSENGER_BIRTH_DATE_COLUMN_NAME);
            weight = rs.getFloat(Baggage.WEIGHT_COLUMN_NAME);
            baggage_status_title = rs.getString(BaggageInformation.BAGGAGE_STATUS_TITLE_COLUMN_NAME);

            baggageInformation.setId(id);
            baggageInformation.setFlight_callsign(flight_callsign);
            baggageInformation.setPassenger_passport_number(passenger_passport_number);
            baggageInformation.setPassenger_first_name(passenger_first_name);
            baggageInformation.setPassenger_middle_name(passenger_middle_name);
            baggageInformation.setPassenger_last_name(passenger_last_name);
            baggageInformation.setPassenger_birthdate(passenger_birthdate);
            baggageInformation.setWeight(weight);
            baggageInformation.setBaggage_status_title(baggage_status_title);

            baggageInformationList.add(baggageInformation);
        }

        return baggageInformationList;
    }
}

