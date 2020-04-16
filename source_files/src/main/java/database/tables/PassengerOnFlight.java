package database.tables;

public class PassengerOnFlight {
    public static final String ID_COLUMN_NAME = "id";
    public static final String FLIGHT_ID_COLUMN_NAME = "flight_id";
    public static final String PASSSENGER_ID_COLUMN_NAME = "passenger_id";

    private Integer id;
    private Integer flight_id;
    private Integer passenger_employee_id;

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

    public Integer getPassenger_employee_id() {
        return passenger_employee_id;
    }

    public void setPassenger_employee_id(Integer passenger_employee_id) {
        this.passenger_employee_id = passenger_employee_id;
    }
}
