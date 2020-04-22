package database.tables;

import java.sql.Date;

public class AirportEmployee {
    public static final String ID_COLUMN_NAME = "id";
    public static final String AIRPORT_ID_COLUMN_NAME = "airport_id";
    public static final String JOB_ID_COLUMN_NAME = "job_id";
    public static final String FIRST_NAME_COLUMN_NAME = "first_name";
    public static final String MIDDLE_NAME_COLUMN_NAME = "middle_name";
    public static final String LAST_NAME_COLUMN_NAME = "last_name";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String ADDRESS_COLUMN_NAME = "address";
    public static final String PHONE_COLUMN_NAME = "phone";
    public static final String BIRTH_DATE_COLUMN_NAME = "birth_date";

    private Integer id;
    private Integer airport_id;
    private Integer job_id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String address;
    private Integer phone;
    private Date birth_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(Integer airport_id) {
        this.airport_id = airport_id;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }
}
