package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineEmployeeExtractor;
import database.tables.AirlineEmployee;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirlineEmployeeOperator
{
    private static AirlineEmployeeOperator instance = new AirlineEmployeeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirlineEmployeeOperator object. This is a Singleton.
     */
    private AirlineEmployeeOperator()
    {

    }

    public static AirlineEmployeeOperator getInstance() {
        return instance;
    }

    /**
     * Selects a airline_employee row, in the form of a Java object, from the airline_employee table given an id.
     * @param id The value of the id column for a airline_employee row
     * @return (null if no airline_employee exists with that id) (a AirlineEmployee object if row exists with that id)
     */
    public AirlineEmployee selectById(int id)
    {
        AirlineEmployeeExtractor extractor = new AirlineEmployeeExtractor();

        String queryTemplate = "SELECT * FROM airline_employee WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirlineEmployee> airlineEmployeeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if(airlineEmployeeList == null || airlineEmployeeList.size() == 0)
            return null;
        else
            return airlineEmployeeList.get(0);
    }

    /**
     * Tries to update a row in the airline_employee table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param airlineEmployee A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, AirlineEmployee airlineEmployee)
    {
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
                + " WHERE "+ AirlineEmployee.ID_COLUMN_NAME + " = :id";

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

    /**
     * Tries to insert a new row into the airline_employee table given a representative Java object.
     * @param airlineEmployee The AirlineEmployee object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(AirlineEmployee airlineEmployee)
    {
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
        } catch (DuplicateKeyException dke)
        {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airline_employee table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM airline_employee "
                + " WHERE "+ AirlineEmployee.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
