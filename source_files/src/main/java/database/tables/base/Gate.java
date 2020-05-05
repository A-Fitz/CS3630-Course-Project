package database.tables.base;

public class Gate {
    public static final String ID_COLUMN_NAME = "id";
    public static final String TERMINAL_ID_COLUMN_NAME = "terminal_id";
    public static final String GATE_CODE_COLUMN_NAME = "gate_code";

    private Integer id;
    private Integer terminal_id;
    private String gate_code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getGate_code() {
        return gate_code;
    }

    public void setGate_code(String gate_code) {
        this.gate_code = gate_code;
    }
}
