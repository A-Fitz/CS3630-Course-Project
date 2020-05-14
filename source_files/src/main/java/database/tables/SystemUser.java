package database.tables;

import java.sql.Timestamp;

public class SystemUser implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String USER_NAME_COLUMN_NAME = "user_name";
    public static final String PASSWORD_COLUMN_NAME = "password";
    public static final String USER_REGION_COLUMN_NAME = "user_region";
    public static final String USER_JOB_TYPE_COLUMN_NAME = "user_job_type";
    public static final String CREATE_AT_COLUMN_NAME = "create_at";

    private Integer id;
    private String userName;
    private String password;
    private Integer userRegion;
    private Integer userJobType;
    private Timestamp createAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(Integer region) {
        this.userRegion = region;
    }

    public Integer getUserJobType() {
        return userJobType;
    }

    public void setUserJobType(Integer type) {
        this.userJobType = type;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp time) {
        this.createAt = time;
    }



    @Override
    public String toString() {
        return null;
    }
}
