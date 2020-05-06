package database.extractors.information;

import database.tables.base.AirlineEmployee;
import database.tables.base.Flight;
import database.tables.information.AirlineEmployeeInformation;
import database.tables.information.FlightInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineEmployeeInformationExtractor implements ResultSetExtractor<List<AirlineEmployeeInformation>> {
    @Override
    public List<AirlineEmployeeInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirlineEmployeeInformation> airlineEmployeeInformationList = new ArrayList<>();

        while (rs.next()) {
            AirlineEmployeeInformation airlineEmployeeInformation = new AirlineEmployeeInformation();

            Integer id;
            String airline_name;
            String job_title;
            String first_name;
            String middle_name;
            String last_name;
            String email;
            String address;
            String phone;
            Date birth_date;

            id = rs.getInt(AirlineEmployee.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            airline_name = rs.getString(AirlineEmployeeInformation.AIRLINE_NAME_COLUMN_NAME);
            job_title = rs.getString(AirlineEmployeeInformation.JOB_TITLE_COLUMN_NAME);
            first_name = rs.getString(AirlineEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = rs.getString(AirlineEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = rs.getString(AirlineEmployee.LAST_NAME_COLUMN_NAME);
            email = rs.getString(AirlineEmployee.EMAIL_COLUMN_NAME);
            address = rs.getString(AirlineEmployee.ADDRESS_COLUMN_NAME);
            phone = rs.getString(AirlineEmployee.PHONE_COLUMN_NAME);
            birth_date = rs.getDate(AirlineEmployee.BIRTH_DATE_COLUMN_NAME);

            airlineEmployeeInformation.setId(id);
            airlineEmployeeInformation.setAirline_name(airline_name);
            airlineEmployeeInformation.setJob_title(job_title);
            airlineEmployeeInformation.setFirst_name(first_name);
            airlineEmployeeInformation.setMiddle_name(middle_name);
            airlineEmployeeInformation.setLast_name(last_name);
            airlineEmployeeInformation.setEmail(email);
            airlineEmployeeInformation.setAddress(address);
            airlineEmployeeInformation.setPhone(phone);
            airlineEmployeeInformation.setBirth_date(birth_date);

            airlineEmployeeInformationList.add(airlineEmployeeInformation);
        }

        return airlineEmployeeInformationList;
    }
}

