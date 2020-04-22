package ui.converters;

import database.tables.FlightStatusType;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and FlightStatusType data objects. Useful for UI display of objects.
 */
public class FlightStatusTypeStringConverter extends StringConverter<FlightStatusType> {
    /**
     * Creates a String containing the information which can identify a given object.
     * @param flightStatusType The given object.
     * @return The String.
     */
    @Override
    public String toString(FlightStatusType flightStatusType) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: [");
        sb.append(flightStatusType.getTitle());
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
    public FlightStatusType fromString(String s) {
        return null;
    }
}
