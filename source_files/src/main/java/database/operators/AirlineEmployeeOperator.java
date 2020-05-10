package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineEmployeeExtractor;
import database.tables.Airline;
import database.tables.AirlineEmployee;
import database.tables.AirlineJobType;
import javafx.scene.control.Alert;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirlineEmployeeOperator implements DatabaseOperator<AirlineEmployee> {
    private static AirlineEmployeeOperator instance = new AirlineEmployeeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirlineEmployeeOperator object. This is a Singleton.
     */
    private AirlineEmployeeOperator() {

    }

    public static AirlineEmployeeOperator getInstance() {
        return instance;
    }

    @Override
    public List<AirlineEmployee> selectAll() {
        AirlineEmployeeExtractor extractor = new AirlineEmployeeExtractor();

        String queryTemplate = "SELECT airline_employee." + AirlineEmployee.ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.JOB_ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.EMAIL_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.PHONE_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.BIRTH_DATE_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AirlineEmployee.AIRLINE_NAME_COLUMN_NAME + ", "
                + "airline_job_type." + AirlineJobType.ID_COLUMN_NAME + " AS " + AirlineEmployee.JOB_TITLE_COLUMN_NAME + " "
                + "FROM airline_employee "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = airline_employee." + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN airline_job_type ON (airline_job_type." + AirlineJobType.ID_COLUMN_NAME + " = airline_employee." + AirlineEmployee.JOB_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public AirlineEmployee selectById(int id) {
        AirlineEmployeeExtractor extractor = new AirlineEmployeeExtractor();

        String queryTemplate = "SELECT airline_employee." + AirlineEmployee.ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.JOB_ID_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.EMAIL_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.PHONE_COLUMN_NAME + ", "
                + "airline_employee." + AirlineEmployee.BIRTH_DATE_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AirlineEmployee.AIRLINE_NAME_COLUMN_NAME + ", "
                + "airline_job_type." + AirlineJobType.ID_COLUMN_NAME + " AS " + AirlineEmployee.JOB_TITLE_COLUMN_NAME + " "
                + "FROM airline_employee "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = airline_employee." + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN airline_job_type ON (airline_job_type." + AirlineJobType.ID_COLUMN_NAME + " = airline_employee." + AirlineEmployee.JOB_ID_COLUMN_NAME + ") "
                + "WHERE airline_employee." + AirlineEmployee.ID_COLUMN_NAME + " = :airlineEmployeeId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airlineEmployeeId", id);

        List<AirlineEmployee> airlineEmployeeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airlineEmployeeList == null || airlineEmployeeList.size() == 0)
            return null;
        else
            return airlineEmployeeList.get(0);
    }

    @Override
    public int updateById(int id, AirlineEmployee airlineEmployee) {
        String queryTemplate = "UPDATE airline_employee SET "
                + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + " = :new_airline_id, "
                + AirlineEmployee.JOB_ID_COLUMN_NAME + " = :new_job_id, "
                + AirlineEmployee.FIRST_NAME_COLUMN_NAME + " = :new_first_name, "
                + AirlineEmployee.MIDDLE_NAME_COLUMN_NAME + " = :new_middle_name, "
                + AirlineEmployee.LAST_NAME_COLUMN_NAME + " = :new_last_name, "
                + AirlineEmployee.EMAIL_COLUMN_NAME + " = :new_email, "
                + AirlineEmployee.ADDRESS_COLUMN_NAME + " = :new_address, "
                + AirlineEmployee.PHONE_COLUMN_NAME + " = :new_phone, "
                + AirlineEmployee.BIRTH_DATE_COLUMN_NAME + " = :new_birth_date"
                + " WHERE " + AirlineEmployee.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_airline_id", airlineEmployee.getAirline_id());
        parameters.addValue("new_job_id", airlineEmployee.getJob_id());
        parameters.addValue("new_first_name", airlineEmployee.getFirst_name());
        parameters.addValue("new_middle_name", airlineEmployee.getMiddle_name());
        parameters.addValue("new_last_name", airlineEmployee.getLast_name());
        parameters.addValue("new_email", airlineEmployee.getEmail());
        parameters.addValue("new_address", airlineEmployee.getAddress());
        parameters.addValue("new_phone", airlineEmployee.getPhone());
        parameters.addValue("new_birth_date", airlineEmployee.getBirth_date());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(AirlineEmployee airlineEmployee) {
        String queryTemplate = "INSERT INTO airline_employee ("
                + AirlineEmployee.AIRLINE_ID_COLUMN_NAME + ", "
                + AirlineEmployee.JOB_ID_COLUMN_NAME + ", "
                + AirlineEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + AirlineEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + AirlineEmployee.LAST_NAME_COLUMN_NAME + ", "
                + AirlineEmployee.EMAIL_COLUMN_NAME + ", "
                + AirlineEmployee.ADDRESS_COLUMN_NAME + ", "
                + AirlineEmployee.PHONE_COLUMN_NAME + ", "
                + AirlineEmployee.BIRTH_DATE_COLUMN_NAME + ") "
                + "VALUES(:airline_id, :job_id, :first_name, :middle_name, :last_name, :email, :address, :phone, :birth_date)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airline_id", airlineEmployee.getAirline_id());
        parameters.addValue("job_id", airlineEmployee.getJob_id());
        parameters.addValue("first_name", airlineEmployee.getFirst_name());
        parameters.addValue("middle_name", airlineEmployee.getMiddle_name());
        parameters.addValue("last_name", airlineEmployee.getLast_name());
        parameters.addValue("email", airlineEmployee.getEmail());
        parameters.addValue("address", airlineEmployee.getAddress());
        parameters.addValue("phone", airlineEmployee.getPhone());
        parameters.addValue("birth_date", airlineEmployee.getBirth_date());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke) {
            // do nothing
        }

        return rowsAffected;
    }

    @Override
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airline_employee "
                + " WHERE " + AirlineEmployee.ID_COLUMN_NAME + " = :id";


        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);


        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
