package database.extractors;

import database.tables.Aircraft;
import database.tables.SystemUser;
import javafx.scene.control.Alert;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SystemUserExtractor implements ResultSetExtractor<List<SystemUser>> {
    @Override
    public List<SystemUser> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<SystemUser> systemUserList = new ArrayList<>();

        while (resultSet.next()) {
            SystemUser user = new SystemUser();

            Integer id;
            String userName;
            String password;
            Integer userRegion;
            Integer userJobType;
            Timestamp createAt;

            id = resultSet.getInt(SystemUser.ID_COLUMN_NAME);
            if (resultSet.wasNull())
                id = null;
            userName = resultSet.getString(SystemUser.USER_NAME_COLUMN_NAME);
            password = resultSet.getString(SystemUser.PASSWORD_COLUMN_NAME);
            userRegion = resultSet.getInt(SystemUser.USER_REGION_COLUMN_NAME);
            userJobType = resultSet.getInt(SystemUser.USER_JOB_TYPE_COLUMN_NAME);
            createAt = resultSet.getTimestamp(SystemUser.CREATE_AT_COLUMN_NAME);

            user.setId(id);
            user.setUserName(userName);
            user.setPassword(password);
            user.setUserRegion(userRegion);
            user.setUserJobType(userJobType);
            user.setCreateAt(createAt);

            systemUserList.add(user);
        }



        return systemUserList;
    }
}
