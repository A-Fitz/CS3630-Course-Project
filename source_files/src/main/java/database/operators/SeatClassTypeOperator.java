package database.operators;

import database.DatabaseConnection;
import database.extractors.SeatClassTypeExtractor;
import database.tables.SeatClassType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class SeatClassTypeOperator implements DatabaseOperator<SeatClassType> {
    private static SeatClassTypeOperator instance = new SeatClassTypeOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a SeatClassTypeOperator object. This is a Singleton.
     */
    private SeatClassTypeOperator() {

    }

    public static SeatClassTypeOperator getInstance() {
        return instance;
    }

    @Override
    public List<SeatClassType> selectAll() {
        SeatClassTypeExtractor extractor = new SeatClassTypeExtractor();

        String queryTemplate = "SELECT * FROM seat_class_type";

        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public SeatClassType selectById(int id) {
        SeatClassTypeExtractor extractor = new SeatClassTypeExtractor();

        String queryTemplate = "SELECT * FROM seat_class_type WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<SeatClassType> seatClassTypeList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (seatClassTypeList == null || seatClassTypeList.size() == 0)
            return null;
        else
            return seatClassTypeList.get(0);
    }

    @Override
    public int updateById(int id, SeatClassType seatClassTypeList) {
        String queryTemplate = "UPDATE seat_class_type SET "
                + SeatClassType.TITLE_COLUMN_NAME + " = :title"
                + " WHERE " + SeatClassType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", seatClassTypeList.getTitle());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    @Override
    public int insert(SeatClassType seatClassType) {
        String queryTemplate = "INSERT INTO seat_class_type ("
                + SeatClassType.TITLE_COLUMN_NAME + ") "
                + "VALUES(:title)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", seatClassType.getTitle());

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
        String queryTemplate = "DELETE FROM seat_class_type "
                + " WHERE " + SeatClassType.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
