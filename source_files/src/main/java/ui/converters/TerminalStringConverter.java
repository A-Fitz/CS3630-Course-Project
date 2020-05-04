package ui.converters;

import database.operators.AirportOperator;
import database.tables.Terminal;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Airport data objects. Useful for UI display of objects.
 */
public class TerminalStringConverter extends StringConverter<Terminal> {
    private AirportOperator airportOperator = AirportOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param terminal The given object.
     * @return The String.
     */
    @Override
    public String toString(Terminal terminal) {

        return "Terminal Code: [" +
                terminal.getTerminal_code() +
                "] " +
                "Airport: [" +
                airportOperator.selectById(terminal.getAirport_id()).getIata_code() +
                "]";
    }

    /**
     * Usually used to create an object from identifiable information. In this case we do not need to use it
     * as we have specific fields for each table's columns.
     *
     * @param s Not used.
     * @return null
     */
    @Override
    public Terminal fromString(String s) {
        return null;
    }
}
