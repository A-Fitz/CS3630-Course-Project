package database.tables;

public class Terminal implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String AIRPORT_ID_COLUMN_NAME = "airport_id";
    public static final String TERMINAL_CODE_COLUMN_NAME = "terminal_code";

    private Integer id;
    private Integer airport_id;
    private String terminal_code;

    /* User readable information */
    public static final String AIRPORT_NAME_COLUMN_NAME = "airport_name";

    private String airport_name;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(Integer airport_id) {
        this.airport_id = airport_id;
    }

    public String getTerminal_code() {
        return terminal_code;
    }

    public void setTerminal_code(String terminal_code) {
        this.terminal_code = terminal_code;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    @Override
    public String toString() {
        return "Airport: [" +
                airport_name +
                "] " +
                "Terminal Code" +
                terminal_code +
                "]";
    }
}
