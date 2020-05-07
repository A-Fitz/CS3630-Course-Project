package database.operators;

import database.DatabaseConnection;
import database.DatabaseOperator;
import database.extractors.BaggageStatusTypeExtractor;
import database.tables.BaggageStatusType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class BaggageStatusTypeOperator implements DatabaseOperator<BaggageStatusType> {
    private static BaggageStatusTypeOperator instance = new BaggageStatusTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a BaggageStatusTypeOperator object. This is a Singleton.
     */
    private BaggageStatusTypeOperator() {

    }

    public static BaggageStatusTypeOperator getInstance() {
        return instance;
    }

    @Override
    public List<BaggageStatusType> selectAll() {
        BaggageStatusTypeExtractor extractor = new BaggageStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM baggage_status_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public BaggageStatusType selectById(int id) {
        BaggageStatusTypeExtractor extractor = new BaggageStatusTypeExtractor();

        String queryTemplate = "SELECT * FROM baggage_status_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<BaggageStatusType> baggageStatusTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (baggageStatusTypeList == null || baggageStatusTypeList.size() == 0)
            return null;
        else
            return baggageStatusTypeList.get(0);
    }

    @Override
    public int updateById(int id, BaggageStatusType baggageStatusTypeList) {
        String queryTemplate = "UPDATE baggage_status_type SET "
                + BaggageStatusType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + BaggageStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", baggageStatusTypeList.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(BaggageStatusType baggageStatusType) {
        String queryTemplate = "INSERT INTO baggage_status_type ("
                + BaggageStatusType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", baggageStatusType.getTitle());

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
        String queryTemplate = "DELETE FROM baggage_status_type "
                + " WHERE " + BaggageStatusType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
