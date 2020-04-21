package database.operators;

import database.DatabaseConnection;
import database.extractors.AirportExtractor;
import database.tables.Airport;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirportOperator
{
    private static AirportOperator instance = new AirportOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirportOperator object. This is a Singleton.
     */
    private AirportOperator()
    {

    }

    public static AirportOperator getInstance() {
        return instance;
    }

    /**
     * Selects an airport row, in the form of a Java object,
     * from the airport table given an id.
     * @param id The value of the id column for an airport row
     * @return (null if no airport exists with that id)
     * (an Airport object if row exists with that id)
     */
    public Airport selectById(int id)
    {
        AirportExtractor extractor = new AirportExtractor();

        String queryTemplate = "SELECT * FROM airport WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Airport> airportList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if(airportList.size() == 0)
            return null;
        else
            return airportList.get(0);
    }

    /**
     * Tries to update a row in the airport table given an id and a representative Java object.
     * @param id The value of the id column of the row to update.
     * @param airport A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Airport airport)
    {
        String queryTemplate = "UPDATE airport SET "
                + Airport.NAME_COLUMN_NAME + " = :new_name, "
                + Airport.IATA_CODE_COLUMN_NAME + " = :new_iata_code, "
                + Airport.CITY_COLUMN_NAME + " = :new_city, "
                + Airport.COUNTRY_COLUMN_NAME + " = :new_country"
                + " WHERE "+ Airport.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_name", airport.getName());
        parameters.addValue("new_iata_code", airport.getIata_code());
        parameters.addValue("new_city", airport.getCity());
        parameters.addValue("new_country", airport.getCountry());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the airport table given a representative Java object.
     * @param airport The Airport object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Airport airport)
    {
        String queryTemplate = "INSERT INTO airport ("
                + Airport.NAME_COLUMN_NAME + ", "
                + Airport.IATA_CODE_COLUMN_NAME + ", "
                + Airport.CITY_COLUMN_NAME + ", "
                + Airport.COUNTRY_COLUMN_NAME + ") "
                + "VALUES(:name, :iata_code, :city, :country)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", airport.getName());
        parameters.addValue("iata_code", airport.getIata_code());
        parameters.addValue("city", airport.getCity());
        parameters.addValue("country", airport.getCountry());

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
     * Tries to delete a row in the airport table given an id.
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id)
    {
        String queryTemplate = "DELETE FROM airport "
                + " WHERE "+ Airport.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
