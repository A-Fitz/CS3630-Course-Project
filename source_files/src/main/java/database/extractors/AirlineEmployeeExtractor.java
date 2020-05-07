package database.extractors;

import database.tables.AirlineEmployee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineEmployeeExtractor implements ResultSetExtractor<List<AirlineEmployee>> {
    @Override
    public List<AirlineEmployee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<AirlineEmployee> airlineEmployeeList = new ArrayList<>();

        while (resultSet.next()) {
            AirlineEmployee airlineEmployee = new AirlineEmployee();

            Integer id;
            Integer airline_id;
            Integer job_id;
            String first_name;
            String middle_name;
            String last_name;
            String email;
            String address;
            String phone;
            Date birth_date;

            String airline_name;
            String job_title;

            id = resultSet.getInt(AirlineEmployee.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            airline_id = resultSet.getInt(AirlineEmployee.AIRLINE_ID_COLUMN_NAME);
            job_id = resultSet.getInt(AirlineEmployee.JOB_ID_COLUMN_NAME);
            first_name = resultSet.getString(AirlineEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = resultSet.getString(AirlineEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = resultSet.getString(AirlineEmployee.LAST_NAME_COLUMN_NAME);
            email = resultSet.getString(AirlineEmployee.EMAIL_COLUMN_NAME);
            address = resultSet.getString(AirlineEmployee.ADDRESS_COLUMN_NAME);
            phone = resultSet.getString(AirlineEmployee.PHONE_COLUMN_NAME);
            birth_date = resultSet.getDate(AirlineEmployee.BIRTH_DATE_COLUMN_NAME);

            airline_name = resultSet.getString(AirlineEmployee.AIRLINE_NAME_COLUMN_NAME);
            job_title = resultSet.getString(AirlineEmployee.JOB_TITLE_COLUMN_NAME);

            airlineEmployee.setId(id);
            airlineEmployee.setAirline_id(airline_id);
            airlineEmployee.setJob_id(job_id);
            airlineEmployee.setFirst_name(first_name);
            airlineEmployee.setMiddle_name(middle_name);
            airlineEmployee.setLast_name(last_name);
            airlineEmployee.setEmail(email);
            airlineEmployee.setAddress(address);
            airlineEmployee.setPhone(phone);
            airlineEmployee.setBirth_date(birth_date);

            airlineEmployee.setAirline_name(airline_name);
            airlineEmployee.setJob_title(job_title);

            airlineEmployeeList.add(airlineEmployee);
        }

        return airlineEmployeeList;
    }
}
