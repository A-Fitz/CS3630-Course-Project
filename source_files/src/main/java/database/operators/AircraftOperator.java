package database.operators;

import database.DatabaseConnection;
import database.extractors.information.AircraftInformationExtractor;
import database.tables.base.*;
import database.tables.information.AircraftInformation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

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

    public AircraftInformation getInformationFromId(int aircraftId)
    {
        AircraftInformationExtractor extractor = new AircraftInformationExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AircraftInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ") "
                + "WHERE aircraft." + Aircraft.ID_COLUMN_NAME + " = :aircraftId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("aircraftId", aircraftId);

        List<AircraftInformation> aircraftInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (aircraftInformationList == null || aircraftInformationList.size() == 0)
            return null;
        else
            return aircraftInformationList.get(0);
    }

    public AircraftInformation getInformationFromSerialNumber(String serialNumber)
    {
        AircraftInformationExtractor extractor = new AircraftInformationExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AircraftInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ") "
                + "WHERE aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + " = :serialNumber";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("serialNumber", serialNumber);

        List<AircraftInformation> aircraftInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (aircraftInformationList == null || aircraftInformationList.size() == 0)
            return null;
        else
            return aircraftInformationList.get(0);
    }

    public List<AircraftInformation> getInformationFromAirlineId(int airlineId)
    {
        AircraftInformationExtractor extractor = new AircraftInformationExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AircraftInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = :airlineId)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airlineId", airlineId);

        List<AircraftInformation> aircraftInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (aircraftInformationList == null || aircraftInformationList.size() == 0)
            return null;
        else
            return aircraftInformationList;
    }

    public List<AircraftInformation> getInformationFromFlightId(int flightId)
    {
        AircraftInformationExtractor extractor = new AircraftInformationExtractor();

        String queryTemplate = "SELECT aircraft." + Aircraft.ID_COLUMN_NAME + ", "
                + "airline." + Airline.NAME_COLUMN_NAME + " AS " + AircraftInformation.AIRLINE_NAME_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.SERIAL_NUMBER_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MAKE_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.MODEL_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.YEAR_COLUMN_NAME + ", "
                + "aircraft." + Aircraft.CAPACITY_COLUMN_NAME + " "
                + "FROM aircraft "
                + "INNER JOIN airline ON (airline." + Airline.ID_COLUMN_NAME + " = aircraft." + Aircraft.AIRLINE_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.AIRLINE_ID_COLUMN_NAME + " = airline." + Airline.ID_COLUMN_NAME + ") "
                + "WHERE flight." + Flight.ID_COLUMN_NAME + " = :flightId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flightId", flightId);

        List<AircraftInformation> aircraftInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (aircraftInformationList == null || aircraftInformationList.size() == 0)
            return null;
        else
            return aircraftInformationList;
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
