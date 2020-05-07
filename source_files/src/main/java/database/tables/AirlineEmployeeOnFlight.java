package database.tables;

import database.DatabaseObject;

public class AirlineEmployeeOnFlight extends DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String FLIGHT_ID_COLUMN_NAME = "flight_id";
    public static final String AIRLINE_EMPLOYEE_ID_COLUMN_NAME = "airline_employee_id";

    private Integer id;
    private Integer flight_id;
    private Integer airline_employee_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Integer flight_id) {
        this.flight_id = flight_id;
    }

    public Integer getAirline_employee_id() {
        return airline_employee_id;
    }

    public void setAirline_employee_id(Integer airline_employee_id) {
        this.airline_employee_id = airline_employee_id;
    }

    @Override
    public String toString() {
        return null;
    }
}
