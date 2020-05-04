package ui.converters;

import database.tables.SeatClassType;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and SeatClassType data objects. Useful for UI display of objects.
 */
public class SeatClassTypeStringConverter extends StringConverter<SeatClassType> {
    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param seatClassType The given object.
     * @return The String.
     */
    @Override
    public String toString(SeatClassType seatClassType) {

        return "Title: [" +
                seatClassType.getTitle() +
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
    public SeatClassType fromString(String s) {
        return null;
    }
}
