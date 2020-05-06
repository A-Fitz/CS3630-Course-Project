package database.tables.information;

import java.sql.Date;

public class AirportEmployeeInformation {
    public static String AIRPORT_NAME_COLUMN_NAME = "airport_name";
    public static String JOB_TITLE_COLUMN_NAME = "job_title";

    private Integer id;
    private String airport_name;
    private String job_title;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String address;
    private String phone;
    private Date birth_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString()
    {
        return "Airport: [" +
                airport_name +
                "] " +
                "Job: [" +
                job_title +
                "] " +
                "First Name: [" +
                first_name +
                "] " +
                "Middle Name: [" +
                middle_name +
                "] " +
                "Last Name: [" +
                last_name +
                "] " +
                "Email: [" +
                email +
                "] " +
                "Address: [" +
                address +
                "] " +
                "Phone: [" +
                phone +
                "] " +
                "Birth Date: [" +
                birth_date.toString() +
                "]";
    }
}
