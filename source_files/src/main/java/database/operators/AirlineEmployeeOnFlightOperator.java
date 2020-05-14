package database.operators;

import database.DatabaseConnection;
import database.extractors.AirlineEmployeeOnFlightExtractor;
import database.tables.AirlineEmployeeOnFlight;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirlineEmployeeOnFlightOperator implements DatabaseOperator<AirlineEmployeeOnFlight> {
    private static AirlineEmployeeOnFlightOperator instance = new AirlineEmployeeOnFlightOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirlineEmployeeOnFlightOperator object. This is a Singleton.
     */
    private AirlineEmployeeOnFlightOperator() {

    }

    public static AirlineEmployeeOnFlightOperator getInstance() {
        return instance;
    }

    @Override
    public List<AirlineEmployeeOnFlight> selectAll() throws DataAccessException {
        AirlineEmployeeOnFlightExtractor extractor = new AirlineEmployeeOnFlightExtractor();

        String queryTemplate = "SELECT * FROM airline_employee_on_flight";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public AirlineEmployeeOnFlight selectById(int id) throws DataAccessException {
        AirlineEmployeeOnFlightExtractor extractor = new AirlineEmployeeOnFlightExtractor();

        String queryTemplate = "SELECT * FROM airline_employee_on_flight WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<AirlineEmployeeOnFlight> airlineEmployeeOnFlightList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (airlineEmployeeOnFlightList == null || airlineEmployeeOnFlightList.size() == 0)
            return null;
        else
            return airlineEmployeeOnFlightList.get(0);
    }

    @Override
    public void updateById(int id, AirlineEmployeeOnFlight airlineEmployeeOnFlight) throws DataAccessException {
        String queryTemplate = "UPDATE airline_employee_on_flight SET "
                + AirlineEmployeeOnFlight.FLIGHT_ID_COLUMN_NAME + " = :new_flight_id, "
                + AirlineEmployeeOnFlight.AIRLINE_EMPLOYEE_ID_COLUMN_NAME + " = :new_airline_employee_id"
                + " WHERE " + AirlineEmployeeOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_flight_id", airlineEmployeeOnFlight.getFlight_id());
        parameters.addValue("new_airline_employee_id", airlineEmployeeOnFlight.getAirline_employee_id());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(AirlineEmployeeOnFlight airlineEmployeeOnFlight) throws DataAccessException {
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

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM airline_employee_on_flight "
                + " WHERE " + AirlineEmployeeOnFlight.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
