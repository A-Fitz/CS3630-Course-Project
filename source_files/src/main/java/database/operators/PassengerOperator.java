package database.operators;

import database.DatabaseConnection;
import database.extractors.PassengerExtractor;
import database.tables.Passenger;
import database.tables.PassengerOnFlight;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PassengerOperator implements DatabaseOperator<Passenger> {
    private static PassengerOperator instance = new PassengerOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a PassengerOperator object. This is a Singleton.
     */
    private PassengerOperator() {

    }

    public static PassengerOperator getInstance() {
        return instance;
    }

    public List<Passenger> selectManyByFlightId(int flightId)
    {
        PassengerExtractor extractor = new PassengerExtractor();

        String queryTemplate = "SELECT * " +
                "FROM passenger " +
                "INNER JOIN passenger_on_flight ON (" + PassengerOnFlight.FLIGHT_ID_COLUMN_NAME + " = :flightId) " +
                "WHERE passenger." + Passenger.ID_COLUMN_NAME + " = passenger_on_flight." + PassengerOnFlight.PASSSENGER_ID_COLUMN_NAME;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("flightId", flightId);

        return namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
    }

    @Override
    public List<Passenger> selectAll()
    {
        PassengerExtractor extractor = new PassengerExtractor();

        String queryTemplate = "SELECT * FROM passenger";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public Passenger selectById(int id) {
        PassengerExtractor extractor = new PassengerExtractor();

        String queryTemplate = "SELECT * FROM passenger WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Passenger> passengerList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (passengerList == null || passengerList.size() == 0)
            return null;
        else
            return passengerList.get(0);
    }

    @Override
    public int updateById(int id, Passenger passenger) {
        String queryTemplate = "UPDATE passenger SET "
                + Passenger.PASSPORT_NUMBER_COLUMN_NAME + " = :new_passport_number, "
                + Passenger.FIRST_NAME_COLUMN_NAME + " = :new_first_name, "
                + Passenger.MIDDLE_NAME_COLUMN_NAME + " = :new_middle_name, "
                + Passenger.LAST_NAME_COLUMN_NAME + " = :new_last_name, "
                + Passenger.EMAIL_COLUMN_NAME + " = :new_email, "
                + Passenger.ADDRESS_COLUMN_NAME + " = :new_address, "
                + Passenger.PHONE_COLUMN_NAME + " = :new_phone, "
                + Passenger.BIRTH_DATE_COLUMN_NAME + " = :new_birth_date"
                + " WHERE " + Passenger.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_passport_number", passenger.getPassport_number());
        parameters.addValue("new_first_name", passenger.getFirst_name());
        parameters.addValue("new_middle_name", passenger.getMiddle_name());
        parameters.addValue("new_last_name", passenger.getLast_name());
        parameters.addValue("new_email", passenger.getEmail());
        parameters.addValue("new_address", passenger.getAddress());
        parameters.addValue("new_phone", passenger.getPhone());
        parameters.addValue("new_birth_date", passenger.getBirth_date());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(Passenger passenger) {
        String queryTemplate = "INSERT INTO passenger ("
                + Passenger.PASSPORT_NUMBER_COLUMN_NAME + ", "
                + Passenger.FIRST_NAME_COLUMN_NAME + ", "
                + Passenger.MIDDLE_NAME_COLUMN_NAME + ", "
                + Passenger.LAST_NAME_COLUMN_NAME + ", "
                + Passenger.EMAIL_COLUMN_NAME + ", "
                + Passenger.ADDRESS_COLUMN_NAME + ", "
                + Passenger.PHONE_COLUMN_NAME + ", "
                + Passenger.BIRTH_DATE_COLUMN_NAME + ") "
                + "VALUES(:passport_number, :first_name, :middle_name, :last_name, :email, :address, :phone, :birth_date)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("passport_number", passenger.getPassport_number());
        parameters.addValue("first_name", passenger.getFirst_name());
        parameters.addValue("middle_name", passenger.getMiddle_name());
        parameters.addValue("last_name", passenger.getLast_name());
        parameters.addValue("email", passenger.getEmail());
        parameters.addValue("address", passenger.getAddress());
        parameters.addValue("phone", passenger.getPhone());
        parameters.addValue("birth_date", passenger.getBirth_date());

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
        String queryTemplate = "DELETE FROM passenger "
                + " WHERE " + Passenger.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
