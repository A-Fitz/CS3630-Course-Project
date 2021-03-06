package database.tables;

public class Aircraft implements DatabaseObject {
    /* Basic information */
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

    /* User readable information */
    private String airline_name;

    public static final String AIRLINE_NAME_COLUMN_NAME = "airline_name";

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
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

    @Override
    public String toString() {
        return "Airline: [" +
                airline_name +
                "] " +
                "Serial #: [" +
                serial_number +
                "] " +
                "Make: [" +
                make +
                "] " +
                "Model: [" +
                model +
                "] " +
                "Year: [" +
                year +
                "] " +
                "Capacity: [" +
                capacity +
                "]";
    }
}
