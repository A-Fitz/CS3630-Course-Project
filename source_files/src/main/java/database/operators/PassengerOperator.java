package database.operators;

import database.DatabaseConnection;
import database.extractors.PassengerExtractor;
import database.tables.Passenger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PassengerOperator
{
    private static PassengerOperator instance = new PassengerOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a PassengerOperator object. This is a Singleton.
     */
    private PassengerOperator()
    {

    }

    public static PassengerOperator getInstance() {
        return instance;
    }

    /**
     * Selects a passenger row, in the form of a Java object, from the passenger table given an id.
     * @param id The value of the id column for a passenger row
     * @return (null if no passenger row exists with that id) (a Passenger object if row exists with that id)
     */
    public Passenger selectById(int id)
    {
        PassengerExtractor extractor = new PassengerExtractor();

        String queryTemplate = "SELECT * FROM passenger WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Passenger> passengerList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if(passengerList.size() == 0)
            return null;
        else
            return passengerList.get(0);
    }

    /**
     * Tries to update a row in the passenger table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param passenger A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Passenger passenger)
    {
        String queryTemplate = "UPDATE passenger SET "
                + Passenger.PASSPORT_NUMBER_COLUMN_NAME + " = :new_passport_number, "
                + Passenger.FIRST_NAME_COLUMN_NAME + " = :new_first_name, "
                + Passenger.MIDDLE_NAME_COLUMN_NAME + " = :new_middle_name, "
                + Passenger.LAST_NAME_COLUMN_NAME + " = :new_last_name, "
                + Passenger.EMAIL_COLUMN_NAME + " = :new_email, "
                + Passenger.ADDRESS_COLUMN_NAME + " = :new_address, "
                + Passenger.PHONE_COLUMN_NAME + " = :new_phone, "
                + Passenger.BIRTH_DATE_COLUMN_NAME + " = :new_birth_date"
                + " WHERE "+ Passenger.ID_COLUMN_NAME + " = :id";

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

    /**
     * Tries to insert a new row into the passenger table given a representative Java object.
     * @param passenger The Passenger object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Passenger passenger)
    {
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

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Special object to hold any values from the inserted row
        // Typically used for columns we did not provide, such as the "id" column
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        // Statement to insert the row
        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        // Can get the keys returned
        Map<String, Object> keyMap = keyHolder.getKeys();
        int id = (int)keyMap.get(Passenger.ID_COLUMN_NAME);

        passenger.setId(id);

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the passenger table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM passenger "
                + " WHERE "+ Passenger.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
