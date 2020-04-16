package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineEmployeeOnFlightExtractor;
import database.tables.AirlineEmployeeOnFlight;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirlineEmployeeOnFlightOperator
{
    private static AirlineEmployeeOnFlightOperator instance = new AirlineEmployeeOnFlightOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirlineEmployeeOnFlightOperator object. This is a Singleton.
     */
    private AirlineEmployeeOnFlightOperator()
    {

    }

    public static AirlineEmployeeOnFlightOperator getInstance() {
        return instance;
    }

    /**
     * Selects an airline_employee_on_flight row, in the form of a Java object,
     * from the airline_employee_on_flight table given an id.
     * @param id The value of the id column for a airline_employee_on_flight row
     * @return (null if no airline_employee_on_flight exists with that id)
     * (an AirlineEmployeeOnFlight object if row exists with that id)
     */
    public AirlineEmployeeOnFlight selectById(int id)
    {
        AirlineEmployeeOnFlightExtractor extractor = new AirlineEmployeeOnFlightExtractor();

        String queryTemplate = "SELECT * FROM airline_employee_on_flight WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirlineEmployeeOnFlight> airlineEmployeeOnFlightList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if(airlineEmployeeOnFlightList.size() == 0)
            return null;
        else
            return airlineEmployeeOnFlightList.get(0);
    }

    /**
     * Tries to update a row in the airline_employee_on_flight table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param airlineEmployeeOnFlight A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, AirlineEmployeeOnFlight airlineEmployeeOnFlight)
    {
        String queryTemplate = "UPDATE airline_employee_on_flight SET "
                + AirlineEmployeeOnFlight.FLIGHT_ID_COLUMN_NAME + " = :new_flight_id, "
                + AirlineEmployeeOnFlight.AIRLINE_EMPLOYEE_ID_COLUMN_NAME + " = :new_airline_employee_id"
                + " WHERE "+ AirlineEmployeeOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_flight_id", airlineEmployeeOnFlight.getFlight_id());
        parameters.addValue("new_airline_employee_id", airlineEmployeeOnFlight.getAirline_employee_id());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the airline_employee_on_flight table given a representative Java object.
     * @param airlineEmployeeOnFlight The AirlineEmployeeOnFlight object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(AirlineEmployeeOnFlight airlineEmployeeOnFlight)
    {
        String queryTemplate = "INSERT INTO airline_employee_on_flight ("
                + AirlineEmployeeOnFlight.FLIGHT_ID_COLUMN_NAME + ", "
                + AirlineEmployeeOnFlight.AIRLINE_EMPLOYEE_ID_COLUMN_NAME + ") "
                + "VALUES(:flight_id, :airline_employee_id)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flight_id", airlineEmployeeOnFlight.getFlight_id());
        parameters.addValue("airline_employee_id", airlineEmployeeOnFlight.getAirline_employee_id());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int)keyMap.get(AirlineEmployeeOnFlight.ID_COLUMN_NAME);

        airlineEmployeeOnFlight.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the airline_employee_on_flight table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM airline_employee_on_flight "
                + " WHERE "+ AirlineEmployeeOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
