package database.extractors;

import database.tables.AirlineEmployee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineEmployeeExtractor implements ResultSetExtractor<List<AirlineEmployee>>
{
    @Override
    public List<AirlineEmployee> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirlineEmployee> airlineEmployeeList = new ArrayList<>();

        while(rs.next())
        {
            AirlineEmployee airlineEmployee = new AirlineEmployee();

            Integer id;
            Integer airline_id;
            Integer job_id;
            String first_name;
            String middle_name;
            String last_name;
            String email;
            String address;
            Integer phone;
            Date birth_date;

            id = rs.getInt(AirlineEmployee.ID_COLUMN_NAME);
            if(rs.wasNull())
                id = null;
            airline_id = rs.getInt(AirlineEmployee.AIRLINE_ID_COLUMN_NAME);
            job_id = rs.getInt(AirlineEmployee.JOB_ID_COLUMN_NAME);
            first_name = rs.getString(AirlineEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = rs.getString(AirlineEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = rs.getString(AirlineEmployee.LAST_NAME_COLUMN_NAME);
            email = rs.getString(AirlineEmployee.EMAIL_COLUMN_NAME);
            address = rs.getString(AirlineEmployee.ADDRESS_COLUMN_NAME);
            phone = rs.getInt(AirlineEmployee.PHONE_COLUMN_NAME);
            birth_date = rs.getDate(AirlineEmployee.BIRTH_DATE_COLUMN_NAME);

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

            airlineEmployeeList.add(airlineEmployee);
        }

        return airlineEmployeeList;
    }
}
