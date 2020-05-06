package database.tables.information;

public class AircraftInformation {
    public static String AIRLINE_NAME_COLUMN_NAME = "airline_name";

    private Integer id;
    private String airline_name;
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

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
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
