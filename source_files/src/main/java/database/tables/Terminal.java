package database.tables;

public class Terminal {
    public static final String ID_COLUMN_NAME = "id";
    public static final String AIRPORT_ID_COLUMN_NAME = "airport_id";
    public static final String TERMINAL_CODE_COLUMN_NAME = "terminal_code";

    private Integer id;
    private Integer airport_id;
    private String terminal_code;

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

    public String getTerminal_code() {
        return terminal_code;
    }

    public void setTerminal_code(String terminal_code) {
        this.terminal_code = terminal_code;
    }
}
