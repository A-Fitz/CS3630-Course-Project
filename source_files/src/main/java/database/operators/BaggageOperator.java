package database.operators;

import database.DatabaseConnection;
import database.extractors.base.BaggageExtractor;
import database.extractors.information.AirlineEmployeeInformationExtractor;
import database.extractors.information.BaggageInformationExtractor;
import database.tables.base.*;
import database.tables.information.AirlineEmployeeInformation;
import database.tables.information.BaggageInformation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaggageOperator {
    private static BaggageOperator instance = new BaggageOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a BaggageOperator object. This is a Singleton.
     */
    private BaggageOperator() {

    }

    public static BaggageOperator getInstance() {
        return instance;
    }

    public BaggageInformation getInformationFromId(int baggageId)
    {
        BaggageInformationExtractor extractor = new BaggageInformationExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + BaggageInformation.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "passenger." + Passenger.PASSPORT_NUMBER_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_PASSPORT_NUMBER_COLUMN_NAME + ", "
                + "passenger." + Passenger.FIRST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_FIRST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.MIDDLE_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_MIDDLE_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.LAST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_LAST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.BIRTH_DATE_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_BIRTH_DATE_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + BaggageInformation.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE baggage." + Baggage.ID_COLUMN_NAME + " = :baggageId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("baggageId", baggageId);

        List<BaggageInformation> baggageInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (baggageInformationList == null || baggageInformationList.size() == 0)
            return null;
        else
            return baggageInformationList.get(0);
    }

    public List<BaggageInformation> getInformationFromIdList(List<Integer> baggageIds)
    {
        BaggageInformationExtractor extractor = new BaggageInformationExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + BaggageInformation.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "passenger." + Passenger.PASSPORT_NUMBER_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_PASSPORT_NUMBER_COLUMN_NAME + ", "
                + "passenger." + Passenger.FIRST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_FIRST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.MIDDLE_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_MIDDLE_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.LAST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_LAST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.BIRTH_DATE_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_BIRTH_DATE_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + BaggageInformation.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "WHERE baggage." + Baggage.ID_COLUMN_NAME + " IN :baggageIds";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("baggageIds", baggageIds);

        List<BaggageInformation> baggageInformationList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (baggageInformationList == null || baggageInformationList.size() == 0)
            return null;
        else
            return baggageInformationList;
    }

    public List<BaggageInformation> getInformationFromAll()
    {
        BaggageInformationExtractor extractor = new BaggageInformationExtractor();

        String queryTemplate = "SELECT baggage." + Baggage.ID_COLUMN_NAME + ", "
                + "flight." + Flight.CALLSIGN_COLUMN_NAME + " AS " + BaggageInformation.FLIGHT_CALLSIGN_COLUMN_NAME + ", "
                + "passenger." + Passenger.PASSPORT_NUMBER_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_PASSPORT_NUMBER_COLUMN_NAME + ", "
                + "passenger." + Passenger.FIRST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_FIRST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.MIDDLE_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_MIDDLE_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.LAST_NAME_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_LAST_NAME_COLUMN_NAME + ", "
                + "passenger." + Passenger.BIRTH_DATE_COLUMN_NAME + " AS " + BaggageInformation.PASSENGER_BIRTH_DATE_COLUMN_NAME + ", "
                + "baggage." + Baggage.WEIGHT_COLUMN_NAME + ", "
                + "baggage_status_type." + BaggageStatusType.TITLE_COLUMN_NAME + " AS " + BaggageInformation.BAGGAGE_STATUS_TITLE_COLUMN_NAME + " "
                + "FROM baggage "
                + "INNER JOIN passenger_on_flight ON (passenger_on_flight." + PassengerOnFlight.ID_COLUMN_NAME + " = baggage." + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN flight ON (flight." + Flight.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + ") "
                + "INNER JOIN passenger ON (passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME + ") "
                + "INNER JOIN baggage_status_type ON (baggage_status_type." + BaggageStatusType.ID_COLUMN_NAME + " = baggage." + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ")";

        return new ArrayList<>(Objects.requireNonNull(namedParameterJdbcTemplate.query(queryTemplate, extractor)));
    }

    /**
     * Selects a baggage row, in the form of a Java object, from the baggage table given an id.
     *
     * @param id The value of the id column for an baggage row
     * @return (null if no baggage row exists with that id) (a Baggage object if row exists with that id)
     */
    public Baggage selectById(int id) {
        BaggageExtractor extractor = new BaggageExtractor();

        String queryTemplate = "SELECT * FROM baggage WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Baggage> baggageList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (baggageList == null || baggageList.size() == 0)
            return null;
        else
            return baggageList.get(0);
    }

    /**
     * Tries to update a row in the baggage table given an id and a representative Java object.
     *
     * @param id      The value of the id column of the row to update.
     * @param baggage A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Baggage baggage) {
        String queryTemplate = "UPDATE baggage SET "
                + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + " = :new_passenger_on_flight_id, "
                + Baggage.WEIGHT_COLUMN_NAME + " = :new_weight, "
                + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + " = :new_baggage_status_id"
                + " WHERE " + Baggage.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_passenger_on_flight_id", baggage.getPassenger_on_flight_id());
        parameters.addValue("new_weight", baggage.getWeight());
        parameters.addValue("new_baggage_status_id", baggage.getBaggage_status_id());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the baggage table given a representative Java object.
     *
     * @param baggage The Baggage object which holds the data to insert into columns.
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Baggage baggage) {
        String queryTemplate = "INSERT INTO baggage ("
                + Baggage.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + Baggage.WEIGHT_COLUMN_NAME + ", "
                + Baggage.BAGGAGE_STATUS_ID_COLUMN_NAME + ") "
                + "VALUES(:passenger_on_flight_id, :weight, :baggage_status_id)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("passenger_on_flight_id", baggage.getPassenger_on_flight_id());
        parameters.addValue("weight", baggage.getWeight());
        parameters.addValue("baggage_status_id", baggage.getBaggage_status_id());

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
     * Tries to delete a row in the baggage table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM baggage "
                + " WHERE " + Baggage.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
