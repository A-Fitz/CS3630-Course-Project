package database.operators;

import database.DatabaseConnection;
import database.extractors.base.GateExtractor;
import database.extractors.information.FlightInformationExtractor;
import database.extractors.information.GateInformationExtractor;
import database.tables.base.*;
import database.tables.information.FlightInformation;
import database.tables.information.GateInformation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GateOperator {
    private static GateOperator instance = new GateOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a GateOperator object. This is a Singleton.
     */
    private GateOperator() {

    }

    public static GateOperator getInstance() {
        return instance;
    }

    public GateInformation getInformationFromId(int gateId)
    {
        GateInformationExtractor extractor = new GateInformationExtractor();

        String queryTemplate = "SELECT gate." + Flight.ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ") "
                + "WHERE gate." + Gate.ID_COLUMN_NAME + " = :gateId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gateId", gateId);

        List<GateInformation> gateInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (gateInformationList == null || gateInformationList.size() == 0)
            return null;
        else
            return gateInformationList.get(0);
    }

    public List<GateInformation> getInformationFromAirportId(int airportId)
    {
        GateInformationExtractor extractor = new GateInformationExtractor();

        String queryTemplate = "SELECT gate." + Flight.ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ") "
                + "INNER JOIN airport ON (airport." + Airport.ID_COLUMN_NAME + " = terminal." + Terminal.AIRPORT_ID_COLUMN_NAME + ") "
                + "WHERE airport." + Airport.ID_COLUMN_NAME + " = :airportId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("airportId", airportId);

        List<GateInformation> gateInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (gateInformationList == null || gateInformationList.size() == 0)
            return null;
        else
            return gateInformationList;
    }

    public List<GateInformation> getInformationFromListOfId(List<Integer> gateIds)
    {
        GateInformationExtractor extractor = new GateInformationExtractor();

        String queryTemplate = "SELECT gate." + Flight.ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ") "
                + "WHERE gate." + Gate.ID_COLUMN_NAME + " IN :gateIds";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gateIds", gateIds);

        List<GateInformation> gateInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (gateInformationList == null || gateInformationList.size() == 0)
            return null;
        else
            return gateInformationList;
    }

    public List<GateInformation> getInformationFromAll()
    {
        GateInformationExtractor extractor = new GateInformationExtractor();

        String queryTemplate = "SELECT gate." + Flight.ID_COLUMN_NAME + ", "
                + "gate." + Gate.GATE_CODE_COLUMN_NAME + ", "
                + "terminal." + Terminal.TERMINAL_CODE_COLUMN_NAME + " "
                + "FROM gate "
                + "INNER JOIN terminal ON (terminal." + Terminal.ID_COLUMN_NAME + " = gate." + Gate.TERMINAL_ID_COLUMN_NAME + ")";

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
    }

    /**
     * Selects a gate row, in the form of a Java object, from the gate table given an id.
     *
     * @param id The value of the id column for an gate row
     * @return (null if no gate row exists with that id) (an gate object if row exists with that id)
     */
    public Gate selectById(int id) {
        GateExtractor extractor = new GateExtractor();

        String queryTemplate = "SELECT * FROM gate WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Gate> gatetList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (gatetList.size() == 0)
            return null;
        else
            return gatetList.get(0);
    }

    /**
     * Tries to update a row in the gate table given an id and a representative Java object.
     *
     * @param id   The value of the id column of the row to update.
     * @param gate A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Gate gate) {
        String queryTemplate = "UPDATE gate SET "
                + Gate.TERMINAL_ID_COLUMN_NAME + " = :terminal_id,"
                + Gate.GATE_CODE_COLUMN_NAME + " = :gate_code"
                + " WHERE " + Gate.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("terminal_id", gate.getTerminal_id());
        parameters.addValue("gate_code", gate.getGate_code());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the gate table given a representative Java object.
     *
     * @param gate The gate object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Gate gate) {
        String queryTemplate = "INSERT INTO gate ("
                + Gate.TERMINAL_ID_COLUMN_NAME + ", "
                + Gate.GATE_CODE_COLUMN_NAME + ")"
                + "VALUES(:terminal_id, :gate_code)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("terminal_id", gate.getTerminal_id());
        parameters.addValue("gate_code", gate.getGate_code());

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
     * Tries to delete a row in the gate table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM gate "
                + " WHERE " + Gate.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
