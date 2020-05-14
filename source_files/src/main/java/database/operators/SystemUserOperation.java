package database.operators;

import database.DatabaseConnection;
import database.extractors.AircraftExtractor;
import database.extractors.SystemUserExtractor;
import database.tables.Aircraft;
import database.tables.Airline;
import database.tables.SystemUser;
import javafx.scene.control.Alert;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class SystemUserOperation implements DatabaseOperator<SystemUser> {
    private static SystemUserOperation instance = new SystemUserOperation();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a SystemUserOperation object. This is a Singleton.
     */
    private SystemUserOperation() {

    }

    public static SystemUserOperation getInstance() {
        return instance;
    }

    @Override
    public List<SystemUser> selectAll()
    {
        SystemUserExtractor extractor = new SystemUserExtractor();


        String queryTemplate = "SELECT * FROM system_user ";


        return namedParameterJdbcTemplate.query(queryTemplate, extractor);
    }

    @Override
    public int updateById(int id, SystemUser holdingUpdatedValues) {
        return 0;
    }

    @Override
    public void insert(SystemUser toInsert) throws DataAccessException {

    }

    @Override
    public SystemUser selectById(int id)
    {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }
}
