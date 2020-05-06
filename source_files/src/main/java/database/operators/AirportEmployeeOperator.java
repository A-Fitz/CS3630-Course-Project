package database.operators;

import database.DatabaseConnection;
import database.extractors.base.AirportEmployeeExtractor;
import database.extractors.information.AirportEmployeeInformationExtractor;
import database.tables.base.*;
import database.tables.information.AirportEmployeeInformation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirportEmployeeOperator {
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

    public AirportEmployeeInformation getInformationFromId(int airportEmployeeId)
    {
        AirportEmployeeInformationExtractor extractor = new AirportEmployeeInformationExtractor();

        String queryTemplate = "SELECT airport_employee." + AirportEmployee.ID_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + AirportEmployeeInformation.AIRPORT_NAME_COLUMN_NAME + ", "
                + "airport_job_type." + AirportJobType.ID_COLUMN_NAME + " AS " + AirportEmployeeInformation.JOB_TITLE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.BIRTH_DATE_COLUMN_NAME + " "
                + "FROM airport_employee "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport_job_type ON (airport_job_type." + AirportJobType.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ") "
                + "WHERE airport_employee." + AirportEmployee.ID_COLUMN_NAME + " = :airportEmployeeId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airportEmployeeId", airportEmployeeId);

        List<AirportEmployeeInformation> airportEmployeeInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (airportEmployeeInformationList == null || airportEmployeeInformationList.size() == 0)
            return null;
        else
            return airportEmployeeInformationList.get(0);
    }

    public List<AirportEmployeeInformation> getInformationFromListOfIds(List<Integer> airportEmployeeIds)
    {
        AirportEmployeeInformationExtractor extractor = new AirportEmployeeInformationExtractor();

        String queryTemplate = "SELECT airport_employee." + AirportEmployee.ID_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + AirportEmployeeInformation.AIRPORT_NAME_COLUMN_NAME + ", "
                + "airport_job_type." + AirportJobType.ID_COLUMN_NAME + " AS " + AirportEmployeeInformation.JOB_TITLE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.BIRTH_DATE_COLUMN_NAME + " "
                + "FROM airport_employee "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport_job_type ON (airport_job_type." + AirportJobType.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ") "
                + "WHERE airport_employee." + AirportEmployee.ID_COLUMN_NAME + " IN :airportEmployeeIds";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airportEmployeeIds", airportEmployeeIds);

        List<AirportEmployeeInformation> airportEmployeeInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (airportEmployeeInformationList == null || airportEmployeeInformationList.size() == 0)
            return null;
        else
            return airportEmployeeInformationList;
    }

    public List<AirportEmployeeInformation> getInformationForAll()
    {
        AirportEmployeeInformationExtractor extractor = new AirportEmployeeInformationExtractor();

        String queryTemplate = "SELECT airport_employee." + AirportEmployee.ID_COLUMN_NAME + ", "
                + "airport." + Airport.NAME_COLUMN_NAME + " AS " + AirportEmployeeInformation.AIRPORT_NAME_COLUMN_NAME + ", "
                + "airport_job_type." + AirportJobType.ID_COLUMN_NAME + " AS " + AirportEmployeeInformation.JOB_TITLE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.FIRST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.MIDDLE_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.LAST_NAME_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.EMAIL_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.ADDRESS_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.PHONE_COLUMN_NAME + ", "
                + "airport_employee." + AirportEmployee.BIRTH_DATE_COLUMN_NAME + " "
                + "FROM airport_employee "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.AIRPORT_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport_job_type ON (airport_job_type." + AirportJobType.ID_COLUMN_NAME + " = airport_employee." + AirportEmployee.JOB_ID_COLUMN_NAME + ")";


        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
    }

    /**
     * Selects a airport_employee row, in the form of a Java object, from the airport_employee table given an id.
     *
     * @param id The value of the id column for a airport_employee row
     * @return (null if no airport_employee row exists with that id) (a AirportEmployee object if row exists with that id)
     */
    public AirportEmployee selectById(int id) {
        AirportEmployeeExtractor extractor = new AirportEmployeeExtractor();

        String queryTemplate = "SELECT * FROM airport_employee WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirportEmployee> airportEmployeeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (airportEmployeeList == null || airportEmployeeList.size() == 0)
            return null;
        else
            return airportEmployeeList.get(0);
    }

    /**
     * Tries to update a row in the airport_employee table given an id and a representative Java object.
     *
     * @param id              The value of the id column of the row to update.
     * @param airportEmployee A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, AirportEmployee airportEmployee) {
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

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the airport_employee table given a representative Java object.
     *
     * @param airportEmployee The AirportEmployee object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(AirportEmployee airportEmployee) {
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
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke) {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airport_employee table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM airport_employee "
                + " WHERE " + AirportEmployee.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
