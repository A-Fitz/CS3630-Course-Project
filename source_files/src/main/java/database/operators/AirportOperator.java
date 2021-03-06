package database.operators;

import database.DatabaseConnection;
import database.extractors.AirportExtractor;
import database.tables.Airport;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class AirportOperator implements DatabaseOperator<Airport> {
    private static AirportOperator instance = new AirportOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a AirportOperator object. This is a Singleton.
     */
    private AirportOperator() {

    }

    public static AirportOperator getInstance() {
        return instance;
    }

    @Override
    public List<Airport> selectAll() throws DataAccessException {
        AirportExtractor extractor = new AirportExtractor();

        String queryTemplate = "SELECT * FROM airport";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);    }

    @Override
    public Airport selectById(int id) throws DataAccessException {
        AirportExtractor extractor = new AirportExtractor();

        String queryTemplate = "SELECT * FROM airport WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Airport> airportList = namedParameterJdbcTemplate.query(queryTemplate,
                parameters, extractor);

        if (airportList == null || airportList.size() == 0)
            return null;
        else
            return airportList.get(0);
    }

    @Override
    public void updateById(int id, Airport airport) throws DataAccessException {
        String queryTemplate = "UPDATE airport SET "
                + Airport.NAME_COLUMN_NAME + " = :new_name, "
                + Airport.IATA_CODE_COLUMN_NAME + " = :new_iata_code, "
                + Airport.CITY_COLUMN_NAME + " = :new_city, "
                + Airport.COUNTRY_COLUMN_NAME + " = :new_country"
                + " WHERE " + Airport.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_name", airport.getName());
        parameters.addValue("new_iata_code", airport.getIata_code());
        parameters.addValue("new_city", airport.getCity());
        parameters.addValue("new_country", airport.getCountry());
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void insert(Airport airport) throws DataAccessException {
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
        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public void deleteById(int id) throws DataAccessException {
        String queryTemplate = "DELETE FROM airport "
                + " WHERE " + Airport.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
