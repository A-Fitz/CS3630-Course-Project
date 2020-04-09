package database.extractors;

import database.tables.Passenger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerExtractor implements ResultSetExtractor<List<Passenger>>
{
    @Override
    public List<Passenger> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Passenger> passengerList = new ArrayList<>();

        while(rs.next())
        {
            Passenger passenger = new Passenger();

            Integer id;
            String passport_number;
            String first_name;
            String middle_name;
            String last_name;
            String email;
            String address;
            Integer phone;
            Date birth_date;

            id = rs.getInt(Passenger.ID_COLUMN_NAME);
            if(rs.wasNull())
                id = null;
            passport_number = rs.getString(Passenger.PASSPORT_NUMBER_COLUMN_NAME);
            if(rs.wasNull())
                passport_number = null;
            first_name = rs.getString(Passenger.FIRST_NAME_COLUMN_NAME);
            middle_name = rs.getString(Passenger.MIDDLE_NAME_COLUMN_NAME);
            last_name = rs.getString(Passenger.LAST_NAME_COLUMN_NAME);
            email = rs.getString(Passenger.EMAIL_COLUMN_NAME);
            address = rs.getString(Passenger.ADDRESS_COLUMN_NAME);
            phone = rs.getInt(Passenger.PHONE_COLUMN_NAME);
            birth_date = rs.getDate(Passenger.BIRTH_DATE_COLUMN_NAME);

            passenger.setId(id);
            passenger.setPassport_number(passport_number);
            passenger.setFirst_name(first_name);
            passenger.setMiddle_name(middle_name);
            passenger.setLast_name(last_name);
            passenger.setEmail(email);
            passenger.setAddress(address);
            passenger.setPhone(phone);
            passenger.setBirth_date(birth_date);

            passengerList.add(passenger);
        }

        return passengerList;
    }
}
