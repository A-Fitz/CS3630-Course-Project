package database.tables.information;

public class GateInformation {
    private Integer id;
    private String gate_code;
    private String terminal_code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
