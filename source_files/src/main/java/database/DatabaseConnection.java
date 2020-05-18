package database;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

public class DatabaseConnection {
	/* In previous commits you can see the DataSource information below. The information was temporary and has since changed. */
    private static final String DATABASE_URL = "jdbc:postgresql://";
    private static final String DATABASE_USER = "";
    private static final String DATABASE_PASSWORD = "";
    private static final String DATABASE_DRIVER = "org.postgresql.Driver";
    private static DatabaseConnection instance = new DatabaseConnection();
    // Class for the driver manager to manage the JDBC connection and interface with the driver
    private DriverManagerDataSource dataSource;

    // Class to provide methods to execute commands on the database
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Do not allow instantiation of a PassengerOperator object. This is a Singleton.
     */
    private DatabaseConnection() {
        dataSource = new DriverManagerDataSource();

        dataSource.setUrl(DATABASE_URL);
        dataSource.setUsername(DATABASE_USER);
        dataSource.setPassword(DATABASE_PASSWORD);
        dataSource.setDriverClassName(DATABASE_DRIVER);

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    /**
     * Used by Launcher class to check when the instance is made.
     *
     * @return True if the instance is created
     */
    public boolean operate() throws SQLException {
        return !instance.dataSource.getConnection().isClosed();
    }
}
