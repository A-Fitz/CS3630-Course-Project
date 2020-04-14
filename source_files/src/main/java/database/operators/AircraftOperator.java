package database.operators;

import database.DatabaseConnection;
import database.extractors.AircraftExtractor;
import database.tables.Aircraft;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AircraftOperator {
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

    /**
     * Selects an aircraft row, in the form of a Java object, from the aircraft table given an id.
     *
     * @param id The value of the id column for an aircraft row
     * @return (null if no aircraft row exists with that id) (an Aircraft object if row exists with that id)
     */
    public Aircraft selectById(int id) {
        AircraftExtractor extractor = new AircraftExtractor();

        String queryTemplate = "SELECT * FROM aircraft WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Aircraft> aircraftList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (aircraftList.size() == 0)
            return null;
        else
            return aircraftList.get(0);
    }

    /**
     * Tries to update a row in the aircraft table given an id and a representative Java object.
     *
     * @param id       The value of the id column of the row to update.
     * @param aircraft A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Aircraft aircraft) {
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

    /**
     * Tries to insert a new row into the aircraft table given a representative Java object.
     *
     * @param aircraft The Aircraft object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Aircraft aircraft) {
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

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int) keyMap.get(Aircraft.ID_COLUMN_NAME);

        aircraft.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the aircraft table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM aircraft "
                + " WHERE " + Aircraft.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
