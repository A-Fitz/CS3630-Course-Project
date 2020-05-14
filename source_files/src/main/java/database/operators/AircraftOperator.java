package database.operators;

import database.DatabaseConnection;
import database.extractors.AircraftExtractor;
import database.tables.Aircraft;
import database.tables.Airline;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AircraftOperator implements DatabaseOperator<Aircraft> {
    private static AircraftOperator instance = new AircraftOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AircraftOperator object. This is a Singleton.
     */
    private AircraftOperator() {

    }

    public static AircraftOperator getInstance() {
        return instance;
    }

    public List<Aircraft> selectManyByAirlineId(int airlineId) throws DataAccessException {
        AircraftExtractor extractor = new AircraftExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + Aircraft.AIRLINE_NAME_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = :airlineId)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airlineId", airlineId);

        return namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
    }

    @Override
    public List<Aircraft> selectAll() throws DataAccessException {
        AircraftExtractor extractor = new AircraftExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + Aircraft.AIRLINE_NAME_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ")";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Aircraft selectById(int id) throws DataAccessException {
        AircraftExtractor extractor = new AircraftExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + Aircraft.AIRLINE_NAME_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ") "
                + "WHERE aircraft." + Aircraft.ID_COLUMN_NAME + " = :aircraftId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("aircraftId", id);

        List<Aircraft> aircraftList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (aircraftList == null || aircraftList.size() == 0)
            return null;
        else
            return aircraftList.get(0);
    }

    @Override
    public int updateById(int id, Aircraft aircraft) throws DataAccessException {
        String queryTemplate = "UPDATE aircraft SET "
                + Aircraft.AIRLINE_ID_COLUMN_NAME + " = :new_airline_id, "
                + Aircraft.SERIAL_NUMBER_COLUMN_NAME + " = :new_serial_number, "
                + Aircraft.MAKE_COLUMN_NAME + " = :new_make, "
                + Aircraft.MODEL_COLUMN_NAME + " = :new_model, "
                + Aircraft.YEAR_COLUMN_NAME + " = :new_year, "
                + Aircraft.CAPACITY_COLUMN_NAME + " = :new_capacity"
                + " WHERE " + Aircraft.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_airline_id", aircraft.getAirline_id());
        parameters.addValue("new_serial_number", aircraft.getSerial_number());
        parameters.addValue("new_make", aircraft.getMake());
        parameters.addValue("new_model", aircraft.getModel());
        parameters.addValue("new_year", aircraft.getYear());
        parameters.addValue("new_capacity", aircraft.getCapacity());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(Aircraft aircraft) throws DataAccessException {
        String queryTemplate = "INSERT INTO aircraft ("
                + Aircraft.AIRLINE_ID_COLUMN_NAME + ", "
                + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + Aircraft.MAKE_COLUMN_NAME + ", "
                + Aircraft.MODEL_COLUMN_NAME + ", "
                + Aircraft.YEAR_COLUMN_NAME + ", "
                + Aircraft.CAPACITY_COLUMN_NAME + ") "
                + "VALUES(:airline_id, :serial_number, :make, :model, :year, :capacity)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airline_id", aircraft.getAirline_id());
        parameters.addValue("serial_number", aircraft.getSerial_number());
        parameters.addValue("make", aircraft.getMake());
        parameters.addValue("model", aircraft.getModel());
        parameters.addValue("year", aircraft.getYear());
        parameters.addValue("capacity", aircraft.getCapacity());

        // Statement to insert the row
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM aircraft "
                + " WHERE " + Aircraft.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
