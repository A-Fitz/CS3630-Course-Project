package database.extractors;

import database.tables.AirportEmployee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportEmployeeExtractor implements ResultSetExtractor<List<AirportEmployee>> {
    @Override
    public List<AirportEmployee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<AirportEmployee> airportEmployeeList = new ArrayList<>();

        while (resultSet.next()) {
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

            String airport_name;
            String job_title;

            id = resultSet.getInt(AirportEmployee.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            airport_id = resultSet.getInt(AirportEmployee.AIRPORT_ID_COLUMN_NAME);
            job_id = resultSet.getInt(AirportEmployee.JOB_ID_COLUMN_NAME);
            first_name = resultSet.getString(AirportEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = resultSet.getString(AirportEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = resultSet.getString(AirportEmployee.LAST_NAME_COLUMN_NAME);
            email = resultSet.getString(AirportEmployee.EMAIL_COLUMN_NAME);
            address = resultSet.getString(AirportEmployee.ADDRESS_COLUMN_NAME);
            phone = resultSet.getString(AirportEmployee.PHONE_COLUMN_NAME);
            birth_date = resultSet.getDate(AirportEmployee.BIRTH_DATE_COLUMN_NAME);

            airport_name = resultSet.getString(AirportEmployee.AIRPORT_NAME_COLUMN_NAME);
            job_title = resultSet.getString(AirportEmployee.JOB_TITLE_COLUMN_NAME);

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

            airportEmployee.setAirport_name(airport_name);
            airportEmployee.setJob_title(job_title);

            airportEmployeeList.add(airportEmployee);
        }

        return airportEmployeeList;
    }
}
