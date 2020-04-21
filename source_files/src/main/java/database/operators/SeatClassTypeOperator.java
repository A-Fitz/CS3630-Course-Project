package database.operators;

import database.DatabaseConnection;
import database.extractors.SeatClassTypeExtractor;
import database.tables.SeatClassType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeatClassTypeOperator
{
    private static SeatClassTypeOperator instance = new SeatClassTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a SeatClassTypeOperator object. This is a Singleton.
     */
    private SeatClassTypeOperator()
    {

    }

    public static SeatClassTypeOperator getInstance() {
        return instance;
    }

    /**
     * Selects a seat_class_type row, in the form of a Java object,
     * from the seat_class_type table given an id.
     * @param id The value of the id column for a seat_class_type row
     * @return (null if no seat_class_type exists with that id)
     * (an SeatClassType object if row exists with that id)
     */
    public SeatClassType selectById(int id)
    {
        SeatClassTypeExtractor extractor = new SeatClassTypeExtractor();

        String queryTemplate = "SELECT * FROM seat_class_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<SeatClassType> seatClassTypeList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if(seatClassTypeList.size() == 0)
            return null;
        else
            return seatClassTypeList.get(0);
    }

    /**
     * Tries to update a row in the seat_class_type table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param seatClassType A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, SeatClassType seatClassType)
    {
        String queryTemplate = "UPDATE seat_class_type SET "
                + SeatClassType.TITLE_COLUMN_NAME + " = :new_title"
                + " WHERE "+ SeatClassType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_title", seatClassType.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the seat_class_type table given a representative Java object.
     * @param seatClassType The SeatClassType object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(SeatClassType seatClassType)
    {
        String queryTemplate = "INSERT INTO seat_class_type ("
                + SeatClassType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", seatClassType.getTitle());

        // Empty list of maps to hold a mapping for column names and their values, which is held by the key holder (see next line)
        List<Map<String, Object>> keyList = new ArrayList<>();

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke)
        {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the seat_class_type table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM seat_class_type "
                + " WHERE "+ SeatClassType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
