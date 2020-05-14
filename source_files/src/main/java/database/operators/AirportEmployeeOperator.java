package database.operators;

import database.DatabaseConnection;
import database.extractors.AirportEmployeeExtractor;
import database.tables.Airport;
import database.tables.AirportEmployee;
import database.tables.AirportJobType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirportEmployeeOperator implements DatabaseOperator<AirportEmployee> {
    private static AirportEmployeeOperator instance = new AirportEmployeeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirportEmployeeOperator object. This is a Singleton.
     */
    private AirportEmployeeOperator() {

    }

    public static AirportEmployeeOperator getInstance() {
        return instance;
    }

    @Override
    public List<AirportEmployee> selectAll() throws DataAccessException {
        AirportEmployeeExtractor extractor = new AirportEmployeeExtractor();

        String queryTemplate = "SELECT airport_employee." + AirportEmployee.ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.BIRTH_DATE_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + AirportEmployee.AIRPORT_NAME_COLUMN_NAME + ", "
                + "airport_job_type." + AirportJobType.ID_COLUMN_NAME + " AS " + AirportEmployee.JOB_TITLE_COLUMN_NAME + " "
                + "FROM airport_employee "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport_job_type ON (airport_job_type." + AirportJobType.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public AirportEmployee selectById(int id) throws DataAccessException {
        AirportEmployeeExtractor extractor = new AirportEmployeeExtractor();

        String queryTemplate = "SELECT airport_employee." + AirportEmployee.ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.BIRTH_DATE_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + AirportEmployee.AIRPORT_NAME_COLUMN_NAME + ", "
                + "airport_job_type." + AirportJobType.ID_COLUMN_NAME + " AS " + AirportEmployee.JOB_TITLE_COLUMN_NAME + " "
                + "FROM airport_employee "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport_job_type ON (airport_job_type." + AirportJobType.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ") "
                + "WHERE airport_employee." + AirportEmployee.ID_COLUMN_NAME + " = :airportEmployeeId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airportEmployeeId", id);

        List<AirportEmployee> airportEmployeeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airportEmployeeList == null || airportEmployeeList.size() == 0)
            return null;
        else
            return airportEmployeeList.get(0);
    }

    @Override
    public void updateById(int id, AirportEmployee airportEmployee) throws DataAccessException {
        String queryTemplate = "UPDATE airport_employee SET "
                + AirportEmployee.AIRPORT_ID_COLUMN_NAME + " = :new_airport_id, "
                + AirportEmployee.JOB_ID_COLUMN_NAME + " = :new_job_id, "
                + AirportEmployee.FIRST_NAME_COLUMN_NAME + " = :new_first_name, "
                + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + " = :new_middle_name, "
                + AirportEmployee.LAST_NAME_COLUMN_NAME + " = :new_last_name, "
                + AirportEmployee.EMAIL_COLUMN_NAME + " = :new_email, "
                + AirportEmployee.ADDRESS_COLUMN_NAME + " = :new_address, "
                + AirportEmployee.PHONE_COLUMN_NAME + " = :new_phone, "
                + AirportEmployee.BIRTH_DATE_COLUMN_NAME + " = :new_birth_date"
                + " WHERE " + AirportEmployee.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_airport_id", airportEmployee.getAirport_id());
        parameters.addValue("new_job_id", airportEmployee.getJob_id());
        parameters.addValue("new_first_name", airportEmployee.getFirst_name());
        parameters.addValue("new_middle_name", airportEmployee.getMiddle_name());
        parameters.addValue("new_last_name", airportEmployee.getLast_name());
        parameters.addValue("new_email", airportEmployee.getEmail());
        parameters.addValue("new_address", airportEmployee.getAddress());
        parameters.addValue("new_phone", airportEmployee.getPhone());
        parameters.addValue("new_birth_date", airportEmployee.getBirth_date());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(AirportEmployee airportEmployee) throws DataAccessException {
        String queryTemplate = "INSERT INTO airport_employee ("
                + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ", "
                + AirportEmployee.JOB_ID_COLUMN_NAME + ", "
                + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + AirportEmployee.BIRTH_DATE_COLUMN_NAME + ") "
                + "VALUES(:airport_id, :job_id, :first_name, :middle_name, :last_name, :email, :address, :phone, :birth_date)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airport_id", airportEmployee.getAirport_id());
        parameters.addValue("job_id", airportEmployee.getJob_id());
        parameters.addValue("first_name", airportEmployee.getFirst_name());
        parameters.addValue("middle_name", airportEmployee.getMiddle_name());
        parameters.addValue("last_name", airportEmployee.getLast_name());
        parameters.addValue("email", airportEmployee.getEmail());
        parameters.addValue("address", airportEmployee.getAddress());
        parameters.addValue("phone", airportEmployee.getPhone());
        parameters.addValue("birth_date", airportEmployee.getBirth_date());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM airport_employee "
                + " WHERE " + AirportEmployee.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
