package database.extractors.information;

import database.tables.base.AirportEmployee;
import database.tables.information.AirportEmployeeInformation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportEmployeeInformationExtractor implements ResultSetExtractor<List<AirportEmployeeInformation>> {
    @Override
    public List<AirportEmployeeInformation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<AirportEmployeeInformation> airportEmployeeInformationList = new ArrayList<>();

        while (rs.next()) {
            AirportEmployeeInformation airportEmployeeInformation = new AirportEmployeeInformation();

            Integer id;
            String airport_name;
            String job_title;
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
            airport_name = rs.getString(AirportEmployeeInformation.AIRPORT_NAME_COLUMN_NAME);
            job_title = rs.getString(AirportEmployeeInformation.JOB_TITLE_COLUMN_NAME);
            first_name = rs.getString(AirportEmployee.FIRST_NAME_COLUMN_NAME);
            middle_name = rs.getString(AirportEmployee.MIDDLE_NAME_COLUMN_NAME);
            last_name = rs.getString(AirportEmployee.LAST_NAME_COLUMN_NAME);
            email = rs.getString(AirportEmployee.EMAIL_COLUMN_NAME);
            address = rs.getString(AirportEmployee.ADDRESS_COLUMN_NAME);
            phone = rs.getString(AirportEmployee.PHONE_COLUMN_NAME);
            birth_date = rs.getDate(AirportEmployee.BIRTH_DATE_COLUMN_NAME);

            airportEmployeeInformation.setId(id);
            airportEmployeeInformation.setAirport_name(airport_name);
            airportEmployeeInformation.setJob_title(job_title);
            airportEmployeeInformation.setFirst_name(first_name);
            airportEmployeeInformation.setMiddle_name(middle_name);
            airportEmployeeInformation.setLast_name(last_name);
            airportEmployeeInformation.setEmail(email);
            airportEmployeeInformation.setAddress(address);
            airportEmployeeInformation.setPhone(phone);
            airportEmployeeInformation.setBirth_date(birth_date);

            airportEmployeeInformationList.add(airportEmployeeInformation);
        }

        return airportEmployeeInformationList;
    }
}

