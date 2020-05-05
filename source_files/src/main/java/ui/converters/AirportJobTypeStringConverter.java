package ui.converters;

import database.tables.base.AirportJobType;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and AirlineJobType data objects. Useful for UI display of objects.
 */
public class AirportJobTypeStringConverter extends StringConverter<AirportJobType> {
    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param airportJobType The given object.
     * @return The String.
     */
    @Override
    public String toString(AirportJobType airportJobType) {

        return "Title: [" +
                airportJobType.getTitle() +
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
    public AirportJobType fromString(String s) {
        return null;
    }
}
