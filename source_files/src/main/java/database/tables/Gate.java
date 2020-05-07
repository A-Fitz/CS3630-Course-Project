package database.tables;

import database.DatabaseObject;

public class Gate implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String TERMINAL_ID_COLUMN_NAME = "terminal_id";
    public static final String GATE_CODE_COLUMN_NAME = "gate_code";

    private Integer id;
    private Integer terminal_id;
    private String gate_code;

    /* User readable information */
    private String terminal_code;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public String getTerminal_code() {
        return terminal_code;
    }

    public void setTerminal_code(String terminal_code) {
        this.terminal_code = terminal_code;
    }

    @Override
    public String toString() {
        return "Gate Code: [" +
                gate_code +
                "] " +
                "Terminal Code: [" +
                terminal_code +
                "]";
    }
}
