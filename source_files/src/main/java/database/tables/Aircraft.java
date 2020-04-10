package database.tables;

public class Aircraft {
    public static final String ID_COLUMN_NAME = "id";
    public static final String AIRLINE_ID_COLUMN_NAME = "airline_id";
    public static final String SERIAL_NUMBER_COLUMN_NAME = "serial_number";
    public static final String MAKE_COLUMN_NAME = "make";
    public static final String MODEL_COLUMN_NAME = "model";
    public static final String YEAR_COLUMN_NAME = "year";
    public static final String CAPACITY_COLUMN_NAME = "capacity";

    private Integer id;
    private Integer airline_id;
    private String serial_number;
    private String make;
    private String model;
    private Integer year;
    private Integer capacity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAirline_id() {
        return airline_id;
    }

    public void setAirline_id(Integer airline_id) {
        this.airline_id = airline_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
