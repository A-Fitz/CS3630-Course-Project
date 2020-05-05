package database.extractors.base;

import database.tables.base.AirportEmployee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportEmployeeExtractor implements ResultSetExtractor<List<AirportEmployee>> {
    @Override
    public List<AirportEmployee> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirportEmployee> airportEmployeeList = new ArrayList<>();

        while (rs.next()) {
            AirportEmployee airportEmployee = new AirportEmployee();

            Integer id;
            Integer airport_id;
            Integer job_id;
            String first_name;
            String middle_name;
            String last_name;
            String email;
            String address;
            String phone;
            Date birth_date;

            id = rs.getInt(AirportEmployee.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            airport_id = rs.getInt(AirportEmployee.AIRPORT_ID_COLUMN_NAME);
            job_id = rs.getInt(AirportEmployee.JOB_ID_COLUMN_NAME);
            first_name = rs.getString(AirportEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = rs.getString(AirportEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = rs.getString(AirportEmployee.LAST_NAME_COLUMN_NAME);
            email = rs.getString(AirportEmployee.EMAIL_COLUMN_NAME);
            address = rs.getString(AirportEmployee.ADDRESS_COLUMN_NAME);
            phone = rs.getString(AirportEmployee.PHONE_COLUMN_NAME);
            birth_date = rs.getDate(AirportEmployee.BIRTH_DATE_COLUMN_NAME);

            airportEmployee.setId(id);
            airportEmployee.setAirport_id(airport_id);
            airportEmployee.setJob_id(job_id);
            airportEmployee.setFirst_name(first_name);
            airportEmployee.setMiddle_name(middle_name);
            airportEmployee.setLast_name(last_name);
            airportEmployee.setEmail(email);
            airportEmployee.setAddress(address);
            airportEmployee.setPhone(phone);
            airportEmployee.setBirth_date(birth_date);

            airportEmployeeList.add(airportEmployee);
        }

        return airportEmployeeList;
    }
}
