package ui.converters;

import database.tables.base.TicketStatusType;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and FlightStatusType data objects. Useful for UI display of objects.
 */
public class TicketStatusTypeStringConverter extends StringConverter<TicketStatusType> {
    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param ticketStatusType The given object.
     * @return The String.
     */
    @Override
    public String toString(TicketStatusType ticketStatusType) {

        return "Title: [" +
                ticketStatusType.getTitle() +
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
    public TicketStatusType fromString(String s) {
        return null;
    }
}
