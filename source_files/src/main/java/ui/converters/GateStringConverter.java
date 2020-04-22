package ui.converters;

import database.operators.TerminalOperator;
import database.tables.Gate;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Airport data objects. Useful for UI display of objects.
 */
public class GateStringConverter extends StringConverter<Gate> {
    private TerminalOperator terminalOperator = TerminalOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     * @param gate The given object.
     * @return The String.
     */
    @Override
    public String toString(Gate gate) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gate Code: [");
        sb.append(gate.getGate_code());
        sb.append("] ");
        sb.append("Terminal Code: [");
        sb.append(terminalOperator.selectById(gate.getTerminal_id()).getTerminal_code());
        sb.append("]");

        return sb.toString();
    }

    /**
     * Usually used to create an object from identifiable information. In this case we do not need to use it
     * as we have specific fields for each table's columns.
     * @param s Not used.
     * @return null
     */
    @Override
    public Gate fromString(String s) {
        return null;
    }
}
